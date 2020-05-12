package com.esgi.bigdata.tools;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.junit.Assert;
import org.junit.Test;

public class TestCsvReaderWithType {

    private final static String PATH = TestCsvReaderWithType.class.getClassLoader().getResource("persons.csv").getFile();

    @Test
    public void testWithHeader() {
        SparkSession session = SparkSessionFactory.getSession();
        session.read().option("header","true").csv(PATH).show();
    }

    @Test
    public void testSession() throws Exception {
        Logger.getLogger("org").setLevel(Level.ERROR);
        Logger.getLogger("akka").setLevel(Level.ERROR);
        SparkSession session = SparkSession
                .builder()
                .appName("test ESGI")
                .master("local[2]")
                .getOrCreate();

        Dataset<Row> content = session
                .read()
                .option("header", "true")
                .option("inferSchema", "true")
                .option("delimiter", ",")
                .csv(PATH);

        content.show();
        content.createTempView("PERSONS");

        Dataset<Row> result = content
                .sqlContext()
                .sql("select PRENOM, count(*) from PERSONS group by PRENOM order by PRENOM");
        result.write()
                .option("header", "true")
                .option("inferSchema", "true")
                .option("delimiter", ",")
                .csv("test.csv");
    }

    @Test
    public void testWithMapping() {
        SparkSession session = SparkSessionFactory.getSession();
        Dataset<Person> persons = session
                .read().option("header","true").schema(new StructType(new StructField[]{
                        new StructField("ID", DataTypes.IntegerType, true, Metadata.empty()),
                        new StructField("PRENOM", DataTypes.StringType, true, Metadata.empty()),
                        new StructField("NOM", DataTypes.StringType, true, Metadata.empty())}
                )).csv(PATH)
                .as(Encoders.bean(Person.class));
        for(Person p : persons.collectAsList()) {
            Assert.assertEquals("Jean",p.getPrenom());
        }
        persons.show();
    }

}
