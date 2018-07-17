package com.processor.utils;

import java.io.File;

public class FileUtils {

	public static String getFileSizeMegaBytes(File file) {
		return (double) file.length() / (1024 * 1024) + " mb";
	}
	
	public static String getFileSizeKiloBytes(File file) {
		return (double) file.length() / 1024 + "  kb";
	}

	public static String getFileSizeBytes(File file) {
		return file.length() + " bytes";
	}
	
}
