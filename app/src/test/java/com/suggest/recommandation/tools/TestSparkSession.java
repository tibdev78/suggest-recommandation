package com.suggest.recommandation.tools;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TestSparkSession {

    @Test
    public void testSession() {
        SparkSession session = SparkSessionFactory.getSession();
        Dataset<String> ds = session.createDataset(Arrays.asList("Hello","World"), Encoders.STRING());
        Assert.assertEquals(2L,ds.count());
    }
}
