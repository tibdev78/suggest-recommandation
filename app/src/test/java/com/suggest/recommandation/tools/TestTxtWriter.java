package com.suggest.recommandation.tools;

import com.suggest.recommandation.utils.Variable;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TestTxtWriter {

    private static final Pattern p = Pattern.compile("[$«»()\\”\\“\\\"\\-\\–\\…\\...&%+,.:;=!?@#|]");
    private final static String PATH = TestTxtWriter.class.getClassLoader().getResource("general.txt").getFile();

    private static void generate2GramFromTXT(String path) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File(path))) {
            String sb = "PREVIOUS,CURRENT"  + '\n';
            writer.write(sb);
            Scanner myReader = new Scanner(new File(PATH));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.replace("\"", "").split(" ");
                ArrayList<String> cleanParts = new ArrayList<>();
                StringBuilder builder = new StringBuilder();
                for (String part : parts) {
                    String word = part.trim();
                    if (Variable.prepositions.contains(word)) builder.append(word).append(" ");
                    else {
                        if (builder.length() > 0) {
                            cleanParts.add(builder.toString().trim() + " " + word + "\n");
                            builder = new StringBuilder();
                        }
                        else cleanParts.add(word);
                    }
                }

                for (int i = 0; i < cleanParts.size(); i++) {
                    if (i < cleanParts.size() - 1) {
                        String currentWord = cleanParts.get(i);
                        String nextWord = cleanParts.get(i + 1);

                        if (p.matcher(currentWord).find()) {
                            currentWord = currentWord.replaceAll(p.toString(), "");
                        }

                        if (p.matcher(nextWord).find()) {
                            nextWord = nextWord.replaceAll(p.toString(), "");
                        }
                        writer.write(currentWord.trim() + ',' + nextWord.trim() + '\n');
                    }
                }
            }
            myReader.close();
        }
    }

    private static void generate3GramFromTXT(String path) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File(path))) {
            String sb = "PREVIOUS,CURRENT"  + '\n';
            writer.write(sb);
            Scanner myReader = new Scanner(new File(PATH));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.replace("\"", "").split(" ");

                ArrayList<String> cleanParts = new ArrayList<>();
                StringBuilder builder = new StringBuilder();
                for (String part : parts) {
                    String word = part.trim();
                    if (Variable.prepositions.contains(word)) builder.append(word).append(" ");
                    else {
                        if (builder.length() > 0) {
                            cleanParts.add(builder.toString().trim() + " " + word);
                            builder = new StringBuilder();
                        }
                        else cleanParts.add(word);
                    }
                }
                for (int i = 0; i < cleanParts.size(); i++) {
                    if (i < cleanParts.size() - 2) {
                        String currentWord = cleanParts.get(i).concat(' ' + cleanParts.get(i + 1));
                        String nextWord = cleanParts.get(i + 2);

                        if (p.matcher(currentWord).find()) {
                            currentWord = currentWord.replaceAll(p.toString(), "");
                        }

                        if (p.matcher(nextWord).find()) {
                            nextWord = nextWord.replaceAll(p.toString(), "");
                        }

                        writer.write(currentWord.trim() + ',' + nextWord.trim() + '\n');
                    }
                }
            }
            myReader.close();
        }
    }

    public static void writerFileTxt() throws FileNotFoundException {
        generate2GramFromTXT(Variable.twoGramCorpusFile.toString());
        generate3GramFromTXT(Variable.threeGramCorpusFile.toString());
    }
}
