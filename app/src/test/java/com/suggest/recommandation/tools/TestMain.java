package com.suggest.recommandation.tools;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class TestMain {
    public static void main(String[] args) throws Exception {
        FileUtils.cleanDirectory(new File("output/"));
        TestCsvToCount.process();
        TestTxtToCount.process();
    }
}
