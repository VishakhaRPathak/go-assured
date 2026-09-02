package com.gorest.restassured.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class ConfigReader {

	private static Properties prop = new Properties();

//	Private Constructor to prevent instantiation
	private ConfigReader() {
		throw new UnsupportedOperationException("Utitlity class cannot be instantiated.");
	}

	public static void loadProperties() {
		if(!prop.isEmpty()) {
			return;
		}
		try {
			
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")
					+ "/config.properties");
			prop.load(fis);
			
			fis.close();
		} catch (IOException e) {
			throw new RuntimeException("Unable to load config file", e);
		} 
	}
	public static String getProperty(String propname) {
		if(prop.containsKey(propname)) {
			return prop.getProperty(propname);
		}
		else {
			throw new RuntimeException("Property does not exists "+propname);
		}
	}
}
