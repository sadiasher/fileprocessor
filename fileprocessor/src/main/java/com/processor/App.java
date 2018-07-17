package com.processor;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.processor.modal.Result;
import com.processor.utils.FileUtils;

public class App {

	public static void main(String[] args) {

		String fileName = "/sadiasher/pgit/sf-city-lots-json/citylots.json"; //args[0];
		int counter = 10;
		List<Result> results = new ArrayList<Result>();		
		List<Long> timeInMillis = new ArrayList<Long>();

			File file = new File(fileName);		
		System.out.println("\nFile: "+file.getName());
		System.out.println("File Size: "+FileUtils.getFileSizeMegaBytes(file));
		System.out.println("No of times file will parse against each library: "+counter);
		System.out.println("Parsing Starts..... ");

		processResult(fileName, counter, results, timeInMillis);
		 

		/*fileName = "/sadiasher/pgit/sf-city-lots-json/citylots-small.json"; //args[0];
		file = new File(fileName);		
		System.out.println("\n\nFile: "+file.getName());
		System.out.println("File Size: "+FileUtils.getFileSizeKiloBytes(file));
		System.out.println("No of times file will parse against each library: "+counter);
		System.out.println("Parsing Starts..... ");

		results = new ArrayList<Result>();
		timeInMillis = new ArrayList<Long>();

		processResult(fileName, counter, results, timeInMillis);*/

	}

	public static void processResult(String fileName, int counter, List<Result> results, List<Long> timeInMillis) {
		for(int a =0; a < counter; a++) {
			Long time = analyseGsonStreamApi(fileName);
			timeInMillis.add(time);
		}
		Result result = new Result();
		result.setParsingApi("GsonStreamApi");
		result.getTimeInMillis().addAll(timeInMillis);
		double averageTime = timeInMillis.stream().mapToLong(Long::longValue).average().getAsDouble();
		result.setAverageTime(averageTime);
		results.add(result);


		timeInMillis = new ArrayList<Long>();
		for(int a =0; a < counter; a++) {
			Long time = analyseGsonObjectApi(fileName);
			timeInMillis.add(time);
		}		
		result = new Result();
		result.setParsingApi("GsonObjectApi");
		result.getTimeInMillis().addAll(timeInMillis);
		averageTime = timeInMillis.stream().mapToLong(Long::longValue).average().getAsDouble();
		result.setAverageTime(averageTime);
		results.add(result);


		timeInMillis = new ArrayList<Long>();
		for(int a =0; a < counter; a++) {
			Long time = analyseJacksonStreamApi(fileName);
			timeInMillis.add(time);
		}
		result = new Result();
		result.setParsingApi("JacksonStreamApi");
		result.getTimeInMillis().addAll(timeInMillis);
		averageTime = timeInMillis.stream().mapToLong(Long::longValue).average().getAsDouble();
		result.setAverageTime(averageTime);
		results.add(result);

		

		timeInMillis = new ArrayList<Long>();
		for(int a =0; a < counter; a++) {
			Long time = analyseJacksonObjectApi(fileName);
			timeInMillis.add(time);
		}
		result = new Result();
		result.setParsingApi("JacksonObjectApi");
		result.getTimeInMillis().addAll(timeInMillis);
		averageTime = timeInMillis.stream().mapToLong(Long::longValue).average().getAsDouble();
		result.setAverageTime(averageTime);
		results.add(result);

		
		
		timeInMillis = new ArrayList<Long>();
		for(int a =0; a < counter; a++) {
			Long time = analyseJsonSimpleApi(fileName);
			timeInMillis.add(time);
		}
		result = new Result();
		result.setParsingApi("JsonSimpleApi");
		result.getTimeInMillis().addAll(timeInMillis);
		averageTime = timeInMillis.stream().mapToLong(Long::longValue).average().getAsDouble();
		result.setAverageTime(averageTime);
		results.add(result);
		

		System.out.println("Parsing completed..... ");
		System.out.println("\n");
		Gson gson = new Gson().newBuilder().create();
		String str = gson.toJson(results);
		System.out.println(str);
	}

	private static Long analyseJacksonObjectApi(String fileName) {
		long lStartTime;
		long lEndTime;
		long output;

		Parser jsonParser = new JacksonObjectParser();

		lStartTime = Instant.now().toEpochMilli();
		jsonParser.parse(fileName);
		lEndTime = Instant.now().toEpochMilli();

		output = lEndTime - lStartTime;

		/*   System.out.println("\n");
        System.out.println("Parsing API: Jackson Object API");
        System.out.println("Elapsed time in milliseconds: " + output);*/

		return output;
	}

	private static Long analyseJacksonStreamApi(String fileName) {

		long lStartTime;
		long lEndTime;
		long output;

		Parser jsonParser = new JacksonStreamParser();

		lStartTime = Instant.now().toEpochMilli();
		jsonParser.parse(fileName);
		lEndTime = Instant.now().toEpochMilli();

		output = lEndTime - lStartTime;

		/*  System.out.println("\n");
        System.out.println("Parsing API: Jackson Streaming API");
        System.out.println("Elapsed time in milliseconds: " + output);*/

		return output;

	}

	public static Long analyseGsonObjectApi(String fileName) {
		long lStartTime;
		long lEndTime;
		long output;

		Parser jsonParser = new GsonObjectParser();

		lStartTime = Instant.now().toEpochMilli();
		jsonParser.parse(fileName);
		lEndTime = Instant.now().toEpochMilli();

		output = lEndTime - lStartTime;

		/*      System.out.println("\n");
        System.out.println("Parsing method: Gson Object API");
        System.out.println("Elapsed time in milliseconds: " + output);*/

		return output;
	}

	public static Long analyseGsonStreamApi(String fileName) {

		Parser jsonParser = new GsonStreamParser();

		long lStartTime = 0l, lEndTime = 0l, output = 0l;

		lStartTime = Instant.now().toEpochMilli();
		jsonParser.parse(fileName);
		lEndTime = Instant.now().toEpochMilli();

		output = lEndTime - lStartTime;

		/*        System.out.println("\n");
        System.out.println("Parsing API: Gson Streaming API");
        System.out.println("Elapsed time in milliseconds: " + output);*/

		return output;
	}

	public static Long analyseJsonSimpleApi(String fileName) {

		Parser jsonParser = new JsonSimpleParser();

		long lStartTime = 0l, lEndTime = 0l, output = 0l;

		lStartTime = Instant.now().toEpochMilli();
		jsonParser.parse(fileName);
		lEndTime = Instant.now().toEpochMilli();

		output = lEndTime - lStartTime;

      /*  System.out.println("\n");
        System.out.println("Parsing API: JSON Simple API");
        System.out.println("Elapsed time in milliseconds: " + output);*/

		return output;
	}

}
