package com.processor;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.processor.modal.CityLot;
import com.processor.modal.Feature;
import com.processor.modal.Geometry;
import com.processor.modal.Property;

public class JsonSimpleParser implements Parser {

	@Override
	public void parse(String fileName) {

		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader(fileName))
		{
			JSONObject obj = (JSONObject) jsonParser.parse(reader);

			CityLot cityLot = readCityLot(obj);

		//	System.out.println(cityLot);
		} catch (Exception e) {
			e.printStackTrace();
		} 

	}

	private CityLot readCityLot(JSONObject obj) throws IOException {

		CityLot cityLot = new CityLot();

		if(obj.get("type") != null) {
			cityLot.setType(obj.get("type").toString());
		}

		if(obj.get("features") != null) {

			JSONArray jsonArray = (JSONArray) obj.get("features");

			if(jsonArray != null) {
				List<Feature> features = readFeatureArray(jsonArray);
				cityLot.getFeatures().addAll(features);
			}

		}

		return cityLot;
	}

	private List<Feature> readFeatureArray(JSONArray array) throws IOException {

		List<Feature> features = new ArrayList<>();

		for (Object object : array) {
			JSONObject jsonObject = (JSONObject) object;
			features.add(readFeature(jsonObject));
		}

		return features;

	}


	private Feature readFeature(JSONObject jsonObject) {
		Feature feature = new Feature();

		if (jsonObject.containsKey("type") ) {
			feature.setType(jsonObject.get("type").toString());
		}

		if (jsonObject.containsKey("properties")) {
			JSONObject jObj = (JSONObject) jsonObject.get("properties");

			if(jObj != null) {
				feature.setProperties(readProperties(jObj));
			}
		} 

		if (jsonObject.containsKey("geometry")) {
			JSONObject jObj = (JSONObject) jsonObject.get("geometry");

			if(jObj != null) {
				feature.setGeometry(readGeometry(jObj));
			}

		}
		return feature;
	}


	private Geometry readGeometry(JSONObject jsonObject) {

		Geometry geometry = new Geometry();

		if (jsonObject.containsKey("type")) {
			geometry.setType(jsonObject.get("type").toString());
		} 

		if (jsonObject.containsKey("coordinates")) {

			JSONArray array = (JSONArray) jsonObject.get("coordinates");

			if(geometry.getType().equals("MultiPolygon")) {
				Object coords = readMultiCoordinateList(array);
				geometry.setCoordinates(coords);
			} else {
				Object coords = readCoordinateList(array);
				geometry.setCoordinates(coords);
			}
		} 

		return geometry;
	}

	private List<List<List<List<Double>>>> readMultiCoordinateList(JSONArray array) {

		List<List<List<List<Double>>>> list =  new ArrayList<>();

		for (Object object : array) {
			JSONArray subArray = (JSONArray) object;
			list.add(readCoordinateList(subArray));
		}

		return list;
	}


	private List<List<List<Double>>> readCoordinateList(JSONArray array) {

		List<List<List<Double>>> list =  new ArrayList<>();;

		for (Object object : array) {
			JSONArray subArray = (JSONArray) object;
			list.add(readCoordinateSubList(subArray));
		}

		return list;
	}


	private List<List<Double>> readCoordinateSubList(JSONArray array) {

		List<List<Double>> list = new ArrayList<>();

		for (Object object : array) {
			JSONArray subArray = (JSONArray) object;
			list.add(readCoordinates(subArray));
		}

		return list;
	}

	private List<Double> readCoordinates(JSONArray array) {

		List<Double> list = new ArrayList<>();

		for (Object object : array) {
			Double val = (Double) object;
			list.add(val);
		}

		return list;
	}


	private Property readProperties(JSONObject jsonObject) {

		Property property = new Property();

		if (jsonObject.containsKey("MAPBLKLOT")) {

			if(jsonObject.get("MAPBLKLOT") != null) {
				property.setMAPBLKLOT(jsonObject.get("MAPBLKLOT").toString());
			}
		}

		if (jsonObject.containsKey("BLKLOT")) {

			if(jsonObject.get("BLKLOT") != null) {
				property.setBLKLOT(jsonObject.get("BLKLOT").toString());
			}
		}

		if (jsonObject.containsKey("BLOCK_NUM")) {

			if(jsonObject.get("BLOCK_NUM") != null) {
				property.setBLOCK_NUM(jsonObject.get("BLOCK_NUM").toString());
			}
		}

		if (jsonObject.containsKey("LOT_NUM")) {

			if(jsonObject.get("LOT_NUM") != null) {
				property.setLOT_NUM(jsonObject.get("LOT_NUM").toString());
			}
		} 

		if (jsonObject.containsKey("FROM_ST")) {

			if(jsonObject.get("FROM_ST") != null) {
				property.setFROM_ST(jsonObject.get("FROM_ST").toString());
			}
		}

		if (jsonObject.containsKey("TO_ST")) {

			if(jsonObject.get("TO_ST") != null) {
				property.setTO_ST(jsonObject.get("TO_ST").toString());
			}
		} 

		if (jsonObject.containsKey("STREET")) {

			if(jsonObject.get("STREET") != null) {
				property.setSTREET(jsonObject.get("STREET").toString());
			}

		}

		if (jsonObject.containsKey("ST_TYPE")) {

			if(jsonObject.get("ST_TYPE") != null) {
				property.setST_TYPE(jsonObject.get("ST_TYPE").toString());
			}

		} 

		if (jsonObject.containsKey("ODD_EVEN")) {

			if(jsonObject.get("ODD_EVEN") != null) {
				property.setODD_EVEN(jsonObject.get("ODD_EVEN").toString());
			}
		} 

		return property;
	}
}
