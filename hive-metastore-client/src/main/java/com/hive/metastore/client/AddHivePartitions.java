package com.hive.metastore.client;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.Partition;
import org.apache.hadoop.hive.metastore.api.StorageDescriptor;
import org.apache.log4j.Logger;

/**
 * @author revanthreddy
 */
public class AddHivePartitions {
	final static Logger logger = Logger.getLogger(AddHivePartitions.class);
	private static TimeZone tz = TimeZone.getTimeZone("UTC");
	private static HiveConf hiveConf = new HiveConf(AddHivePartitions.class);

	public static String getDateMonth() {
		String pattern = "yyyy-MM";
		Date date = new Date();
		date.setTime(date.getTime());
		return getDateFormater(pattern).format(date);
	}

	public static String getDateHour() {
		String pattern = "yyyy-MM-dd-HH";
		Date date = new Date();
		date.setTime(date.getTime());
		return getDateFormater(pattern).format(date);
	}

	public static SimpleDateFormat getDateFormater(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		format.setTimeZone(tz);
		return format;
	}

	public static void addPartition(Config cfg) throws Exception {
		try {
			String thriftURI = cfg.getProperty("thrift.uri");
			String dbName = cfg.getProperty("hive.hdfs.db.name");

			hiveConf.setVar(HiveConf.ConfVars.METASTOREURIS, thriftURI);
			HiveMetaStoreClient hiveClient = new HiveMetaStoreClient(hiveConf);

			String table = "employee";
			List<Partition> partiList = new ArrayList<Partition>();
			StorageDescriptor sd = hiveClient.getTable(dbName, table).getSd();
			logger.info("Adding Partitions for table : " + table);
			Partition parti = new Partition();
			List<String> values = new ArrayList<String>();
			values.add("1234");
			values.add(getDateMonth());
			values.add(getDateHour());
			parti.setValues(values);
			parti.setDbName(dbName);
			parti.setTableName(table);
			parti.setSd(sd);
			parti.getSd().setLocation(sd.getLocation());
			partiList.add(parti);
			hiveClient.add_partitions(partiList, true, true);
		} catch (Exception e) {
			logger.error("Exception in addPartition(): " + e.getMessage(), e);
			e.printStackTrace();
		} finally {
			CassandraClient.close();
		}

	}

	public static void main(String[] args) throws Exception {
		String path = args[0]; // path to config.properties file
		Config cfg = new Config(path);
		addPartition(cfg);

	}
}
