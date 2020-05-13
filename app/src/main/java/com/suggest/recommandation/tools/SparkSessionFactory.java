package com.suggest.recommandation.tools;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;

import java.io.File;

/**
 * Hides the details of the spark session creation, especially for windows users
 */
public class SparkSessionFactory {
    /** winutils management */
    private final static String HADOOP_HOME = "D:\\Hadoop\\winutils\\hadoop-2.7.1";

    /**
     * Get the current spark conf
     * @return the current spark conf
     */
    public static SparkConf getConf() {
        File file = new File(HADOOP_HOME);
        if (file.exists()) System.setProperty("hadoop.home.dir",HADOOP_HOME);
        return new SparkConf().setAppName("ESGI").setMaster("local[2]");
    }

    /**
     * Get the initialized session, include UDF
     * @return the session with the conf
     */
    public static SparkSession getSession() {
        SparkSession result = SparkSession.builder().config(getConf()).getOrCreate();
        result.udf().register("currentSize", new UDFCount(), DataTypes.IntegerType);
        return SparkSession.builder().config(getConf()).getOrCreate();
    }

}
