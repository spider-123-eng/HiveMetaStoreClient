# HiveMetaStoreClient
This Project explains how to use HiveMetaStoreClient, HiveJdbcDriver, HiveServer2                             

HOW-TO : CONNECT HIVESERVER2 SERVICE WITH JDBC CLIENT ?                                                       
HOW-TO : CONNECT HiveMetaStore SERVICE WITH HiveMetaStore CLIENT ?                                                                    
In-order to run this examples the following services should be up and running .                                   
>hive --service hiveserver2                                                             
>hive --service metastore                                                                     

Hive Installation :
-------------------

wget http://archive.apache.org/dist/hive/hive-2.1.0/apache-hive-2.1.0-bin.tar.gz                                                      
tar xzf apache-hive-2.1.0-bin.tar.gz                                               
mkdir hive                                               
mv apache-hive-2.1.0-bin hive                                               

Set Environment Variables :                                               
>vi ~/.bash_profile                                               
>export HADOOP_USER_CLASSPATH_FIRST=true                                               
>export HIVE_HOME=/home/centos/hive/apache-hive-2.1.0-bin                                               
>export PATH=$HIVE_HOME/bin:$PATH                                               
>source ~/.bash_profile                                               

Copy mysql jar to hive lib folder (To use mysql as hive meta store)                                               
cd $HIVE_HOME/lib                                               
wget http://central.maven.org/maven2/mysql/mysql-connector-java/5.1.35/mysql-connector-java-5.1.35.jar                                     


References :
------------
Configure Hive with MySQL Metastore : http://backtobazics.com/big-data/4-steps-to-configure-hive-with-mysql-metastore-on-centos/        
Install MySQL : https://www.linode.com/docs/databases/mysql/install-mysql-on-ubuntu-14-04                         
