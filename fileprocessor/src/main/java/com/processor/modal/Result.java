package com.processor.modal;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Result {

	private String parsingApi;
	private List<Long> timeInMillis = new ArrayList<Long>();
	private Double averageTime;
}
