package com.esgi.bigdata.tools;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.*;
import org.junit.Test;

public class Test2Gram {
    @Test
    public void test2Gram() throws Exception {
        Logger.getLogger("org").setLevel(Level.ERROR);
        Logger.getLogger("akka").setLevel(Level.ERROR);

        final SparkSession session = SparkSessionFactory.getSession();
        Dataset<Row> rows = session.read()
                .option("header","true").option("inferSchema","true").option("delimiter",",")
                .csv(Test2Gram.class.getClassLoader().getResource("joujou.csv").getFile());
        rows.createTempView("DEUXGRAMS");
        session.sql("select PREVIOUS, CURRENT, count(*) from DEUXGRAMS group by PREVIOUS , CURRENT ORDER BY PREVIOUS, COUNT(*) desc").show();
    }
}