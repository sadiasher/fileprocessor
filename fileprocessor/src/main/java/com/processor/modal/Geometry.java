package com.processor.modal;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Geometry {

	private String type;
	private  Object coordinates = new ArrayList<>();
}
