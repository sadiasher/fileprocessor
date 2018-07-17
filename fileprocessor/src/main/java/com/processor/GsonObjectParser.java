package com.processor;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.google.gson.GsonBuilder;
import com.processor.modal.CityLot;

public class GsonObjectParser implements Parser {

	@Override
	public void parse(String fileName) {
		
		try (InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName));) {

			com.google.gson.JsonParser parser = new com.google.gson.JsonParser();
			parser.parse(reader).getAsJsonObject();
			
			new GsonBuilder().create().fromJson(reader, CityLot.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
