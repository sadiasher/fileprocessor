package com.processor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.processor.modal.CityLot;
import com.processor.modal.Feature;
import com.processor.modal.Geometry;
import com.processor.modal.Property;

public class JacksonStreamParser implements Parser {


	@Override
	public void parse(String fileName) {

		JsonFactory jasonFactory = new JsonFactory();

		try (JsonParser jacksonParser = jasonFactory.createParser(new File(fileName))) {

			readCityLot(jacksonParser);

			/*ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(cityLot);
			System.out.println(json);*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private CityLot readCityLot(JsonParser jacksonParser) throws IOException {

		CityLot cityLot = new CityLot();

		jacksonParser.nextToken();

		while (jacksonParser.nextToken() != JsonToken.END_OBJECT) {

			String name = jacksonParser.getCurrentName();
			if (name.equals("type")) {

				jacksonParser.nextToken();

				String type = jacksonParser.getText();
				cityLot.setType(type);

			} else if (name.equals("features")) {

				JsonToken jToken = jacksonParser.nextToken();
				
				if(jToken == JsonToken.VALUE_NULL) {
					continue;
				}
				
				List<Feature> features = readFeatureArray(jacksonParser);				
				cityLot.getFeatures().addAll(features);
				
			}

		}

		return cityLot;
	}

	private List<Feature> readFeatureArray(JsonParser jacksonParser) throws IOException {

		List<Feature> features = new ArrayList<>();

		while (jacksonParser.nextToken() != JsonToken.END_ARRAY) {

			features.add(readFeature(jacksonParser));

		}

		return features;
	}

	private Feature readFeature(JsonParser jacksonParser) throws IOException {

		Feature feature = new Feature();

		while(jacksonParser.nextToken() != JsonToken.END_OBJECT) {

			String name = jacksonParser.getCurrentName();

			if(name == null) {
				System.out.println(jacksonParser.getCurrentToken());
				System.out.println(jacksonParser.getCurrentLocation());
			}

			if (name.equals("type")) {

				jacksonParser.nextToken();
				String type = jacksonParser.getText();
				feature.setType(type);

			} else if (name.equals("properties")) {
				feature.setProperties(readProperties(jacksonParser));
			} else if (name.equals("geometry")) {
				feature.setGeometry(readGeometry(jacksonParser));
			}
		}

		return feature;
	}

	private Geometry readGeometry(JsonParser jacksonParser) throws IOException {

		jacksonParser.nextToken();
		if(jacksonParser.getCurrentToken() == JsonToken.VALUE_NULL) 
			return null;
		
		Geometry geometry = new Geometry();

		while(jacksonParser.nextToken() != JsonToken.END_OBJECT) {

			String name = jacksonParser.getCurrentName();

			if (name.equals("type")) {
				geometry.setType(jacksonParser.getText());
			} else if (name.equals("coordinates")) {

				if(geometry.getType().equals("MultiPolygon")) {
					jacksonParser.nextToken();
					
					if(jacksonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
						geometry.setCoordinates(null);
						continue;
					}

					Object coords = readMultiCoordinateList(jacksonParser);
					geometry.setCoordinates(coords);
				} else {
					jacksonParser.nextToken();
					
					if(jacksonParser.getCurrentToken() == JsonToken.VALUE_NULL) {
						geometry.setCoordinates(null);
						continue;
					}
					
					Object coords = readCoordinateList(jacksonParser);
					geometry.setCoordinates(coords);
				}

			} 
		}

		return geometry;
	}

	private List<List<List<List<Double>>>> readMultiCoordinateList(JsonParser jacksonParser) throws IOException {

		List<List<List<List<Double>>>> list =  new ArrayList<>();


		while (jacksonParser.nextToken() != JsonToken.END_ARRAY) {
			list.add(readCoordinateList(jacksonParser));
		}

		return list;
	}


	private List<List<List<Double>>> readCoordinateList(JsonParser jacksonParser) throws IOException {

		List<List<List<Double>>> list =  new ArrayList<>();;

		while (jacksonParser.nextToken() != JsonToken.END_ARRAY) {

			list.add(readCoordinateSubList(jacksonParser));
		}

		return list;
	}


	private List<List<Double>> readCoordinateSubList(JsonParser jacksonParser) throws IOException {

		List<List<Double>> list = new ArrayList<>();

		while (jacksonParser.nextToken() != JsonToken.END_ARRAY) {
			list.add(readCoordinates(jacksonParser));
		}

		return list;
	}

	private List<Double> readCoordinates(JsonParser jacksonParser) throws IOException {

		List<Double> list = new ArrayList<>();

		while (jacksonParser.nextToken() != JsonToken.END_ARRAY) {

			list.add(jacksonParser.getDoubleValue());
		}

		return list;
	}

	private Property readProperties(JsonParser jacksonParser) throws IOException {

		Property property = new Property();

		if(jacksonParser.getCurrentToken() == JsonToken.VALUE_NULL) 
			return null;
		
		while(jacksonParser.nextToken() != JsonToken.END_OBJECT) {

			String name = jacksonParser.currentName();

			if (name.equals("MAPBLKLOT")) {
				property.setMAPBLKLOT(jacksonParser.getText());
			} else if (name.equals("BLKLOT")) {
				property.setBLKLOT(jacksonParser.getText());
			} else if (name.equals("BLOCK_NUM")) {
				property.setBLOCK_NUM(jacksonParser.getText());
			} else if (name.equals("LOT_NUM")) {
				property.setLOT_NUM(jacksonParser.getText());
			} else if (name.equals("FROM_ST")) {
				property.setFROM_ST(jacksonParser.getText());
			} else if (name.equals("TO_ST")) {
				property.setTO_ST(jacksonParser.getText());
			} else if (name.equals("STREET")) {
				property.setSTREET(jacksonParser.getText());
			} else if (name.equals("ST_TYPE")) {
				property.setST_TYPE(jacksonParser.getText());
			} else if (name.equals("ODD_EVEN")) {
				property.setODD_EVEN(jacksonParser.getText());
			} 
			
			/*if (name.equals("MAPBLKLOT")) {
				property.setMAPBLKLOT(jacksonParser.getText());
			} else if (name.equals("BLKLOT")) {
				property.setBlklot(jacksonParser.getText());
			} else if (name.equals("BLOCK_NUM")) {
				property.setBlock_num(jacksonParser.getText());
			} */
		}


		return property;
	}

}
