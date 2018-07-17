package com.processor;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.processor.modal.CityLot;
import com.processor.modal.Feature;
import com.processor.modal.Geometry;
import com.processor.modal.Property;

public class GsonStreamParser implements Parser {

	@Override
	public void parse(String fileName) {

		try (JsonReader reader = new JsonReader(new FileReader(fileName));) {

			CityLot cityLot = readCityLot(reader);

			//	String json = new Gson().toJson(cityLot);
			//	System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private CityLot readCityLot(JsonReader reader) throws IOException {

		CityLot cityLot = new CityLot();

		reader.beginObject();

		while(reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("type")) {
				String type = nextString(reader);
				cityLot.setType(type);

			} else if (name.equals("features")) {
				List<Feature> features = readFeatureArray(reader);
				cityLot.getFeatures().addAll(features);
			} else {
				reader.skipValue();
			}
		}

		reader.endObject();

		return cityLot;
	}

	private List<Feature> readFeatureArray(JsonReader reader) throws IOException {

		List<Feature> features = new ArrayList<>();

		reader.beginArray();

		while (reader.hasNext()) {
			features.add(readFeature(reader));
		}

		reader.endArray();

		return features;

	}

	private Feature readFeature(JsonReader reader) throws IOException {

		Feature feature = new Feature();

		reader.beginObject();

		while(reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("type")) {
				String type = nextString(reader);
				feature.setType(type);
			} else if (name.equals("properties")) {
				feature.setProperties(readProperties(reader));
			} else if (name.equals("geometry")) {
				feature.setGeometry(readGeometry(reader));
			}else {
				reader.skipValue();
			}
		}

		reader.endObject();

		return feature;
	}

	private Property readProperties(JsonReader reader) throws IOException {

		Property property = new Property();

		reader.beginObject();

		while(reader.hasNext()) {
			String name = reader.nextName();

			if (name.equals("MAPBLKLOT")) {
				property.setMAPBLKLOT(nextString(reader));
			} else if (name.equals("BLKLOT")) {
				property.setBLKLOT(nextString(reader));
			} else if (name.equals("BLOCK_NUM")) {
				property.setBLOCK_NUM(nextString(reader));
			} else if (name.equals("LOT_NUM")) {
				property.setLOT_NUM(nextString(reader));
			} else if (name.equals("FROM_ST")) {
				property.setFROM_ST(nextString(reader));
			} else if (name.equals("TO_ST")) {
				property.setTO_ST(nextString(reader));
			} else if (name.equals("STREET")) {
				property.setSTREET(nextString(reader));
			} else if (name.equals("ST_TYPE")) {
				property.setST_TYPE(nextString(reader));
			} else if (name.equals("ODD_EVEN")) {
				property.setODD_EVEN(nextString(reader));
			} else {
				reader.skipValue(); //avoid some unhandle events
			}
		}

		reader.endObject();

		return property;
	}

	private String nextString(JsonReader reader) throws IOException {
		String val = null;

		if(reader.peek() == JsonToken.NULL) {
			reader.nextNull();
		} else {
			val = reader.nextString();
		}
		return val;
	}

	private Geometry readGeometry(JsonReader reader) throws IOException {

		if(reader.peek() == JsonToken.NULL) {
			reader.nextNull();
			reader.skipValue();
			return null;
		} else {

			Geometry geometry = new Geometry();

			reader.beginObject();

			while(reader.hasNext()) {
				String name = reader.nextName();

				if (name.equals("type")) {
					geometry.setType(nextString(reader));
				} else if (name.equals("coordinates")) {

					if(geometry.getType().equals("MultiPolygon")) {
						Object coords = readMultiCoordinateList(reader);
						geometry.setCoordinates(coords);
					} else {
						Object coords = readCoordinateList(reader);
						geometry.setCoordinates(coords);
					}

				} else {
					reader.skipValue(); //avoid some unhandle events
				}
			}

			reader.endObject();

			return geometry;
		}
	}

	private List<List<List<List<Double>>>> readMultiCoordinateList(JsonReader reader) throws IOException {

		List<List<List<List<Double>>>> list =  new ArrayList<>();;

		reader.beginArray();

		while (reader.hasNext()) {
			list.add(readCoordinateList(reader));
		}

		reader.endArray();

		return list;
	}


	private List<List<List<Double>>> readCoordinateList(JsonReader reader) throws IOException {

		List<List<List<Double>>> list =  new ArrayList<>();;

		reader.beginArray();

		while (reader.hasNext()) {
			list.add(readCoordinateSubList(reader));
		}

		reader.endArray();

		return list;
	}


	private List<List<Double>> readCoordinateSubList(JsonReader reader) throws IOException {

		List<List<Double>> list = new ArrayList<>();

		reader.beginArray();

		while (reader.hasNext()) {
			list.add(readCoordinates(reader));
		}

		reader.endArray();

		return list;
	}

	private List<Double> readCoordinates(JsonReader reader) throws IOException {

		List<Double> list = new ArrayList<>();

		reader.beginArray();

		while (reader.hasNext()) {
			list.add(reader.nextDouble());
		}

		reader.endArray();

		return list;
	}

}
