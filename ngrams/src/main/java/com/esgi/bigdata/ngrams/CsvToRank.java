package com.esgi.bigdata.ngrams;

import org.apache.spark.sql.*;

public class CsvToRank {

    /**
     * Given a CSV with col1 and col2, and 2grams in it,
     * calculate the base recommandation dataset
     * @param session the spark session to perform sql requests
     * @param path the path of the file
     * @return the dataset with col1,col2,counter,rank and localid
     */
    public static Dataset<Row> rank2(SparkSession session, String path) throws Exception {
        Dataset<Row> csv = session.read()
                .option("inferSchema","true")
                .option("delimiter",",")
                .option("header",true)
                .csv(path);
        csv.createTempView("SOURCE");
        Dataset<Row> counters = csv.sqlContext().sql("select COL1, COL2, count(*) as counter from SOURCE group by COL1, COL2 order by COL1,COL2, COUNT(*) DESC");
        counters.createTempView("COUNTERS");
        Dataset<Row> orderedCouples = session
                .sql("select col1 , col2,  counter , "
                        + " dense_rank() over (partition by COL1 order by COUNTER desc ) as rank , "
                        + " row_number() over (partition by COL1  order by COL1, COUNTER desc, COL2 ) as localid "
                        + " from COUNTERS "
                        + " order by COL1 , rank asc , localid asc");
        return orderedCouples ;
    }
}
