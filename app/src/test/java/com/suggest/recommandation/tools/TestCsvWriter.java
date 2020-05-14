package com.suggest.recommandation.tools;

import com.suggest.recommandation.utils.Variable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class TestCsvWriter {
    private final static String PATH = CsvWriter.class.getClassLoader().getResource("food.csv").getFile();

    private static void generate2GramFromCsv(String file) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File(file))) {
            String sb = "PREVIOUS,CURRENT"  + '\n';
            writer.write(sb);
            Scanner myReader = new Scanner(new File(PATH));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.replaceAll("[^A-Za-z0-9éèàê\\s]", "").split(" ");
                ArrayList<String> cleanParts = new ArrayList<>();
                StringBuilder builder = new StringBuilder();
                for (String part : parts) {
                    String word = part.toLowerCase().trim();
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
                    if (i < cleanParts.size() - 1) {
                        writer.write(cleanParts.get(i) + ',' + cleanParts.get(i + 1) + '\n');
                    }
                }
            }
            myReader.close();
        }
    }

    private static void generate3GramFromCsv(String file) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File(file))) {
            String sb = "PREVIOUS,CURRENT"  + '\n';
            writer.write(sb);
            Scanner myReader = new Scanner(new File(PATH));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.replaceAll("[^A-Za-z0-9éèàê\\s]", "").split(" ");

                ArrayList<String> cleanParts = new ArrayList<>();
                StringBuilder builder = new StringBuilder();
                for (String part : parts) {
                    String word = part.toLowerCase().trim();
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
                        writer.write(cleanParts.get(i) + ' ' + cleanParts.get(i + 1) +',' + cleanParts.get(i + 2)+ '\n');
                    }
                }
            }
            myReader.close();
        }
    }


    public static void writerCsvFile() throws Exception {
        generate2GramFromCsv(Variable.twoGramCustomFile.toString());
        generate3GramFromCsv(Variable.threeGramCustomFile.toString());
    }
}