package com.revature.daos;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionUtility {
	static {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection() {
		
		Properties properties = loadProperties();
		if(properties == null) return null;
		
		try {
			Connection conn = DriverManager.getConnection(
					properties.getProperty("url"),
					properties.getProperty("user"),
					properties.getProperty("password"));
			 //if(conn != null) System.out.println("Connected to database");
			return conn;
		} catch(SQLException e) {
			System.out.println("Unable to connect to database.");
			e.printStackTrace();
		}
		return null;
	}
	
	private static Properties loadProperties() {
		InputStream is = ConnectionUtility.class
				.getClassLoader()
				.getResourceAsStream("database.properties");
		
		Properties properties = new Properties();
		try {
			properties.load(is);
			return properties;
		} catch (IOException e) {
			System.out.println("Unable to load properties.");
			return null;
		}
	}
	
}
