package com.suggest.recommandation.tools;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class TestUdfCount {

    @Test
    public void testCount() throws Exception {
        SparkSession session = SparkSessionFactory.getSession();
        Dataset<String> ds = session.createDataset(Arrays.asList("Hello","World"), Encoders.STRING());
        ds.createTempView("WORDS");
        Dataset<Row> result = session.sql("select value, currentSize(value) as Size from WORDS");
        List<Row> rows = result.collectAsList();
        for(Row row : rows) {
            Assert.assertEquals(row.getString(0).length(),row.getInt(1));
        }
    }
}
