package com.suggest.recommandation.tools;

import com.suggest.recommandation.ngrams.CsvToRank;
import com.suggest.recommandation.ngrams.TxtToRank;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class TestTxtToRank {
    private final static File path = new File("../output/step3_txt_2Gram.csv");
    @Test
    public void testSql() throws Exception {
        Logger.getLogger("org").setLevel(Level.OFF);
        Logger.getLogger("akka").setLevel(Level.OFF);
        SparkSession testSession = SparkSessionFactory.getSession();
        Dataset<Row> rank = TxtToRank.rank2(testSession,path.toString());
        rank.show();
    }
}
