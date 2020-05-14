package com.suggest.recommandation.ngrams;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.Row;

public class TxtToRank {
    public static Dataset<Row> rank2(SparkSession session, String path) throws Exception {
        Dataset<Row> txt = session.read()
                .option("inferSchema","true")
                .option("delimiter",",")
                .option("header",true)
                .csv(path);
        txt.createTempView("SOURCE");
        Dataset<Row> counters = txt.sqlContext().sql("select PREVIOUS,CURRENT, count(*) as counter from SOURCE where PREVIOUS is not null group by PREVIOUS, CURRENT order by PREVIOUS, CURRENT, COUNT(*) DESC");
        counters.createTempView("COUNTERS");
        Dataset<Row> orderedCouples = session
                .sql("select PREVIOUS,CURRENT,COUNTER , "
                        + " dense_rank() over (partition by PREVIOUS order by COUNTER desc ) as rank , "
                        + " row_number() over (partition by PREVIOUS  order by PREVIOUS, COUNTER desc, CURRENT ) as localid "
                        + " from COUNTERS "
                        + "where CURRENT is not null"
                        + " order by CURRENT, rank asc, localid asc");
        return orderedCouples;
    }
}
