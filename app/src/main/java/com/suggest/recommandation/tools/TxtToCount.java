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

public class TxtToCount {
    private final static SparkSession session = SparkSession.builder().appName("suggest recommandation").master("local[2]").getOrCreate();

    private static void testSql(String tempName, File path, File directory, String fileName) throws Exception {
        Logger.getLogger("org").setLevel(Level.OFF);
        Logger.getLogger("akka").setLevel(Level.OFF);
        Dataset<Row> counter = com.suggest.recommandation.ngrams.TxtToCount.counterRow(fileName, tempName, path.toString(), session);
        counter.write()
                .option("header", "true")
                .option("inferSchema", "true")
                .option("delimiter", ",")
                .csv(directory.toString());
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
            File currentFile = new File(directory.toString() + "/" + pathname);
            if (currentFile.length() > 0) {
                currentFile.renameTo(new File(directory.toString() + "/" + ++count + ".csv"));
            } else {
                FileUtils.deleteQuietly(currentFile);
            }
        }
        File countFile = new File("output/" + filename + ".txt");
        countFile.createNewFile();
        FileWriter myWriter = new FileWriter(countFile);
        myWriter.write(Integer.toString(count));
        myWriter.close();

        FileUtils.deleteQuietly(file);
    }

    public static void process() throws Exception {
        TxtWriter.writerFileTxt();
        testSql(Variable.twoGramsTXTTempName, Variable.twoGramCorpusFile,
                Variable.twoGramCorpusDirectory, "2Gram.csv");
        testSql(Variable.threeGramsTXTTempName, Variable.threeGramCorpusFile,
                Variable.threeGramCorpusDirectory, "3Gram.csv");
        removeUselessFiles(Variable.twoGramCorpusDirectory);
        removeUselessFiles(Variable.threeGramCorpusDirectory);
        handleFiles(Variable.twoGramCorpusDirectory, Variable.twoGramCorpusFile, "step2_2Gram.txt");
        handleFiles(Variable.threeGramCorpusDirectory, Variable.threeGramCorpusFile, "step2_3Gram.txt");
    }
}
