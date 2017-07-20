package com.hive.metastore.client;

import org.apache.log4j.Logger;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;

/**
 * @author revanthreddy
 */
public class CassandraClient {
	final static Logger logger = Logger.getLogger(CassandraClient.class);
	private static Cluster cluster;
	private static Session session;

	public static void connect(String node) {
		cluster = Cluster.builder().addContactPoint(node).build();
		session = cluster.connect();
		Metadata metadata = cluster.getMetadata();
		System.out.println("Connected to cluster:" + metadata.getClusterName());
		for (Host host : metadata.getAllHosts()) {
			System.out.println("Datatacenter: " + host.getDatacenter() + "; Host: " + host.getAddress() + "; Rack: "
					+ host.getRack());
		}
		System.out.println("\n");
	}

	public static void getSession() {
		session = cluster.connect();
	}

	public static void close() {
		session.close();
		cluster.close();
	}

	public static void querySchema() {
		Statement statement = QueryBuilder.select().all().from("dev", "emp");
		ResultSet results = session.execute(statement);
		System.out.println(String.format("%-10s\t%-10s\t%-10s\t%-10s\n%s", "empid", "emp_dept", "emp_first", "emp_last",
				"------------+--------------+-----------+-----------------"));
		for (Row row : results) {
			System.out.println(String.format("%-10s\t%-10s\t%-10s\t%-10s", row.getInt("empid"),
					row.getString("emp_dept"), row.getString("emp_first"), row.getString("emp_last")));
		}

		System.out.println();
	}

	public static void main(String[] args) {
		connect("127.0.0.1");
		querySchema();
		close();

	}

}
