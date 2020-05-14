package com.suggest.recommandation.tools;

import com.suggest.recommandation.ngrams.CsvToRank;
import com.suggest.recommandation.utils.Variable;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Assert;
import org.junit.Test;

public class TestCsvToCount {

    private final static SparkSession session = SparkSession.builder().appName("suggest recommandation").master("local[2]").getOrCreate();

    private static void generatePartsCsv(String file, String dir,String tempName) throws Exception {
        Logger.getLogger("org").setLevel(Level.ERROR);
        Logger.getLogger("akka").setLevel(Level.ERROR);
        Dataset<Row> content = session
                .read()
                .option("header", "true")
                .option("inferSchema", "true")
                .option("delimiter", ",")
                .csv(file);

        content.show();
        content.createTempView(tempName);

        Dataset<Row> result = content.sqlContext().sql("select PREVIOUS, CURRENT, count(*) from " + tempName + " group by PREVIOUS , CURRENT ORDER BY PREVIOUS, COUNT(*) desc");
        result.write()
                .option("header", "true")
                .option("inferSchema", "true")
                .option("delimiter", ",")
                .csv(dir);
    }


    public static void main(String[] args) throws Exception {
        /*generatePartsCsv(Variable.twoGramCustomFile.toString(),
                Variable.twoGramCustomDirectory.toString(), Variable.twoGramsTempName);*/
        generatePartsCsv(Variable.threeGramCustomFile.toString(),
                Variable.threeGramCustomDirectory.toString(), Variable.threeGramsTempName);
    }
}
