package tinyurl.commons;

import java.io.IOException;
import java.util.Properties;

public class AppProperties {
	
	private static volatile AppProperties instance;
	private Properties properties;
	
	private AppProperties() throws IOException {
		loadProperties();
	}
	
	private void loadProperties() throws IOException {
		properties = new Properties();
		properties.load(AppProperties.class.getResourceAsStream("/application.properties"));
	}
	

	public static AppProperties getInstance() throws IOException {
		if(instance == null) {
			synchronized(AppProperties.class) {
				if(instance == null) {
					instance = new AppProperties();
				}
			}
		}
		return instance;
	}
	
	public String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}
	
	
}
