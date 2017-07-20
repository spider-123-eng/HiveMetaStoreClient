package com.hive.metastore.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
/**
 * @author revanthreddy
 *
 */
import java.sql.SQLException;
import java.sql.Statement;

public class HiveJdbcClient {
	// hive --service hiveserver2
	private static String driverName = "org.apache.hive.jdbc.HiveDriver";

	public static void main(String[] args) throws SQLException {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		Connection con = DriverManager.getConnection("jdbc:hive2://localhost:10000/default", "root", "Aruba@123");
		Statement stmt = con.createStatement();

		String tableName = "employee";
		stmt.execute("DROP TABLE " + tableName);

		String query = "CREATE EXTERNAL TABLE IF NOT EXISTS employee ( eid int, name String,dept String, yoj String) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' ";
		stmt.execute(query);

		// show tables
		String sql = "show tables";
		System.out.println("Running Query : " + sql);
		ResultSet res1 = stmt.executeQuery(sql);
		if (res1.next()) {
			System.out.println(res1.getString(1));
		}

		// describe table
		sql = "describe " + tableName;
		System.out.println("Running Query : " + sql);
		res1 = stmt.executeQuery(sql);
		while (res1.next()) {
			System.out.println(res1.getString(1) + "\t" + res1.getString(2));
		}

		// load data into table
		String filepath = "/Users/revanthreddy/Desktop/Notes/hive/employee.txt";
		sql = "load data local inpath '" + filepath + "' into table " + tableName;
		System.out.println("Running Query : " + sql);
		stmt.execute(sql);

		// select * query
		String sqlquery = "select * from " + tableName;
		System.out.println("Running Query : " + sqlquery);
		ResultSet res2 = stmt.executeQuery(sqlquery);
		while (res2.next()) {
			System.out.println(String.valueOf(res2.getInt(1)) + "\t" + res2.getString(2));
		}

	}
}
