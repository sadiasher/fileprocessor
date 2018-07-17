package com.processor.modal;

import lombok.Data;

@Data
public class Feature {

	private String type;
	private Property properties;
	private Geometry geometry;
}
