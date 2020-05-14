package com.suggest.recommandation.tools;

import com.suggest.recommandation.ngrams.CsvToRank;
import com.suggest.recommandation.ngrams.TxtToRank;
import com.suggest.recommandation.utils.Variable;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.File;

public class TestTxtToCount {
    private final static SparkSession session = SparkSession.builder().appName("suggest recommandation").master("local[2]").getOrCreate();

    public static void testSql(String tempName, File path, File directory, String fileName) throws Exception {
        Logger.getLogger("org").setLevel(Level.OFF);
        Logger.getLogger("akka").setLevel(Level.OFF);
        Dataset<Row> counter = TxtToRank.counterRow(fileName, tempName, path.toString(), session);
        if (directory.exists()) FileUtils.deleteDirectory(directory);
        counter.write()
                .option("header", "true")
                .option("inferSchema", "true")
                .option("delimiter", ",")
                .csv(directory.toString());
    }

    public static void main(String[] args) throws Exception {
        testSql(Variable.twoGramsTempName, Variable.twoGramCorpusFile,
                Variable.twoGramCorpusDirectory, "2Gram.csv");
        /*testSql(Variable.threeGramsTempName, Variable.threeGramCorpusFile,
                Variable.threeGramCorpusDirectory, "3Gram.csv");*/
    }
}
