package com.suggest.recommandation.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class Variable {
    public final static File twoGramCorpusFile = new File("output/step2_2Gram.csv");
    public final static File threeGramCorpusFile = new File("output/step2_3Gram.csv");
    public final static File twoGramCorpusDirectory = new File("output/step2_dir_2Gram.csv");
    public final static File threeGramCorpusDirectory = new File("output/step2_dir_3Gram.csv");
    public final static File twoGramCustomFile = new File("output/step1_2Gram.csv");
    public final static File threeGramCustomFile = new File("output/step1_3Gram.csv");
    public final static File twoGramCustomDirectory = new File("output/step1_dir_2Gram.csv");
    public final static File threeGramCustomDirectory = new File("output/step1_dir_3Gram.csv");
    public final static String twoGramsCSVTempName = "DEUXGRAMSCSV";
    public final static String threeGramsCSVTempName = "TROISGRAMSCSV";
    public final static String twoGramsTXTTempName = "DEUXGRAMSTXT";
    public final static String threeGramsTXTTempName = "TROISGRAMSTXT";

    public static final List<String> prepositions = Arrays.asList(
            "A","après","avant","avex","chez","concernant","contre","dans","de", "à", "la", "au",
            "depuis","derrière","dès","devant","durant","en","entre","envers","hormis", "des",
            "hors","jusque","malgré","moyennant","nonobstant","outre","par","parmi", "aux",
            "pendant","pour","près","sans","sauf","selon","sous","suivant","sur","touchant","vers","via",
            "avant", "après", "au", "grâce","hors","loin","lors","par","par suite","près",
            "proche","quant","quitte","sauf","sous", "et", "du"
    );
}