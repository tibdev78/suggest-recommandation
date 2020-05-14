package com.suggest.recommandation.ngrams;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Row;

public class TxtToRank {
    public static Dataset<Row> counterRow(String name, String tempName, String path, SparkSession session) throws Exception {
        Dataset<Row> txt = session.read()
                .option("inferSchema","true")
                .option("delimiter",",")
                .option("header",true)
                .csv(path);
        txt.createTempView(tempName);
        Dataset<Row> counters = txt.sqlContext().sql("select PREVIOUS, CURRENT, count(*) as COUNTER from " + tempName + " where PREVIOUS is not null and CURRENT is not null group by PREVIOUS , CURRENT ORDER BY COUNT(*) desc");
        return counters;
    }
}
