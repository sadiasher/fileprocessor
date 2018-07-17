package com.processor;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.processor.modal.CityLot;

public class JacksonObjectParser implements Parser {

	@Override
	public void parse(String fileName) {

		ObjectMapper mapper = new ObjectMapper();
		try {
			CityLot json = mapper.readValue(new File(fileName), CityLot.class);
			//System.out.println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
