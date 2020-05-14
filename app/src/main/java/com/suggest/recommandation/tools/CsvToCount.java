package com.suggest.recommandation.tools;

import com.suggest.recommandation.utils.Variable;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

public class CsvToCount {

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

        content.createTempView(tempName);

        Dataset<Row> result = content.sqlContext().sql("select PREVIOUS, CURRENT, count(*) as COUNTER from " + tempName + " group by PREVIOUS , CURRENT ORDER BY PREVIOUS, COUNT(*) desc");
        result.write()
                .option("header", "true")
                .option("inferSchema", "true")
                .option("delimiter", ",")
                .csv(dir);
    }

    private static void removeUselessFiles(File directory) {
        FilenameFilter filter = (f, name) -> name.endsWith(".crc");
        String[] crcFiles = new File(directory.toString()).list(filter);
        new File(directory.toString() + "_SUCCESS").delete();
        for (String pathname : crcFiles) {
            new File(directory.toString() + "/" + pathname).delete();
        }
    }

    private static void handleFiles(File directory, File file, String filename) throws IOException {
        String[] files = new File(directory.toString()).list();
        int count = 0;
        for (String pathname : files) {
            new File(directory.toString() + "/" + pathname).renameTo(new File(directory.toString() + "/" + ++count + ".csv"));
        }
        File countFile = new File("output/" + filename + ".txt");
        countFile.createNewFile();
        FileWriter myWriter = new FileWriter(countFile);
        myWriter.write(Integer.toString(count));
        myWriter.close();

        FileUtils.deleteQuietly(file);
    }

    public static void process() throws Exception {
        CsvWriter.writerCsvFile();
        generatePartsCsv(Variable.twoGramCustomFile.toString(),
                Variable.twoGramCustomDirectory.toString(), Variable.twoGramsCSVTempName);
        generatePartsCsv(Variable.threeGramCustomFile.toString(),
                Variable.threeGramCustomDirectory.toString(), Variable.threeGramsCSVTempName);
        removeUselessFiles(Variable.twoGramCustomDirectory);
        removeUselessFiles(Variable.threeGramCustomDirectory);
        handleFiles(Variable.twoGramCustomDirectory, Variable.twoGramCustomFile, "step1_2Gram.txt");
        handleFiles(Variable.threeGramCustomDirectory, Variable.threeGramCustomFile, "step1_3Gram.txt");
    }
}
