package com.hive.metastore.client;

import java.io.FileInputStream;
import java.io.InputStream;
/**
 * @author revanthreddy
 *
 */
import java.util.Properties;

public class Config {
	Properties configFile;
	public Config(String filePath) {
		configFile = new java.util.Properties();
		try {
			InputStream input = new FileInputStream(filePath);
			configFile.load(input);
		} catch (Exception eta) {
			eta.printStackTrace();
		}
	}

	public String getProperty(String key) {
		String value = this.configFile.getProperty(key);
		return value;
	}
}