package com.suggest.recommandation.reader;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;

public class TestCorpusReader {
    /** path of the file that contains the text to read */
    private final static String PATH = TestCorpusReader.class.getClassLoader().getResource("test_reader.txt").getFile();

    @Test
    public void testInit() {
        File file = new File(PATH);
        Assert.assertTrue(file.exists());
        Assert.assertTrue(file.canRead());
    }

    @Test
    public void testReader() {
        // all the words
        Set<String> words = new TreeSet<>();
        try(CorpusReader reader = new CorpusReader(PATH)) {
            for (String word : reader) {
                Assert.assertTrue(word.equals(word.toLowerCase()));
                Assert.assertFalse(word.contains(" "));
                words.add(word);
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
        // last word
        Assert.assertTrue(words.contains("vie"));
        // remove dots
        Assert.assertTrue(words.contains("belle"));
        // first word
        Assert.assertTrue(words.contains("oiseaux"));
    }

}
