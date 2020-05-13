package com.esgi.bigdata.reader;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TestNGramsCsvExporter {
    /** path of the file that contains the text to read */
    private final static String PATH = TestCorpusReader.class.getClassLoader().getResource("sample.txt").getFile();

    @Test(expected = IndexOutOfBoundsException.class)
    public void testBadParameters() throws Exception {
        NGramsCsvExporter exporter = new NGramsCsvExporter();
        exporter.exportToLines(PATH,0);
    }

    @Test
    public void testCsv() throws Exception {
        NGramsCsvExporter exporter = new NGramsCsvExporter();
        List<String> lines = exporter.exportToLines(PATH,2);
        String previousLine = null ;
        System.out.println(lines);
        for(String line : lines) {
            String[] words = line.split(",");
            if (words.length != 2) Assert.assertEquals("baylon,,",line);
            else Assert.assertEquals(2,words.length);
            if (previousLine != null) {
                // last word of previous line equals the first word of current line
                Assert.assertEquals(previousLine.split(",")[1],words[0]);
            }
            previousLine = line ;
        }
    }
}
