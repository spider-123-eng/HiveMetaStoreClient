package com.hive.jdbc.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 * @author revanthreddy 
 * Basic example to test the hiveserver2 connection 
 * hive --service hiveserver2
 */
public class HiveJdbcConnection {
	private static String driverName = "org.apache.hive.jdbc.HiveDriver";

	public static void main(String[] args) throws Exception {
		try {

			Class.forName(driverName);
			Connection connection = null;
			System.out.println("Before getting connection");
			connection = DriverManager.getConnection("jdbc:hive2://localhost:10000/default", "root", "root@123");
			System.out.println("After getting connection " + connection);

			ResultSet resultSet = connection.createStatement().executeQuery("select * from default.employee");

			while (resultSet.next()) {
				System.out.println("EmpId : " + resultSet.getString(1) + ", EmpName : " + resultSet.getString(2));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

	}
}
