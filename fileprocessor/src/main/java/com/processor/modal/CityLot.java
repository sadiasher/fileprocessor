package com.processor.modal;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CityLot {
	
	private String type;
	private List<Feature> features = new ArrayList<>();
}
