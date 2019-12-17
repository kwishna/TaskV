package com.vlocity.util;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.Properties;

/**
 * This Is A Utility Class Class Property File Reading.
 * Stored Locators And Configuration Can Be Loaded Inside The static Method. And Used Elsewhere In The Suite.
 *
 * @author Krishna
 * @since 18 Dec 2019
 */
public class PropertyReader {

	public static Properties prop;

	static {
		prop = new Properties();
		try {
			prop.load(Files.newInputStream(Paths.get(System.getProperty("user.dir")+"/src/test/resources/data.properties")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
