/**
 * 
 */
package com.hive.metastore.client;

import java.util.List;

import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.FieldSchema;

/**
 * @author revanthreddy
 *
 */
public class SimpleHiveMetaStoreClient {

	public static void main(String[] args) throws Exception {

		HiveConf hiveConf = new HiveConf(SimpleHiveMetaStoreClient.class);
		hiveConf.setVar(HiveConf.ConfVars.METASTOREURIS, "thrift://127.0.0.1:9083");
		HiveMetaStoreClient hiveClient = new HiveMetaStoreClient(hiveConf);

		List<String> dbsList = hiveClient.getAllDatabases();

		for (String dbName : dbsList) {
			System.out.println("DataBase Name : " + dbName);

			List<String> tblsList = hiveClient.getAllTables(dbName);
			for (String tableName : tblsList) {
				System.out.println("Table Name : " + tableName);

				List<FieldSchema> fldsList = hiveClient.getFields(dbName, tableName);
				for (FieldSchema flds : fldsList) {
					System.out.println("Field Name : " + flds);

				}

			}
		}

	}
}
