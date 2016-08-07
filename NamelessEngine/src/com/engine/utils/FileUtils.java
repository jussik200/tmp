package com.engine.utils;

import java.io.*;

public class FileUtils {
	
	private FileUtils(){}
	
	public static String loadAsString(String file){
		StringBuilder result = new StringBuilder();
		
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String buffer ="";
			while ((buffer = reader.readLine()) != null) {
				result.append(buffer + '\n');
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result.toString();
	}
}
