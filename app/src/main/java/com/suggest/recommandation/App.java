package com.suggest.recommandation;

import com.suggest.recommandation.tools.CsvToCount;
import com.suggest.recommandation.tools.TxtCount;
import org.apache.commons.io.FileUtils;

import java.io.File;

public class App {
    public static void main(String[] args) throws Exception {
        FileUtils.cleanDirectory(new File("output/"));
        CsvToCount.process();
        TxtCount.process();
    }
}
