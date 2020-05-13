package com.suggest.recommandation.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CsvWriter {
    private static final List<String> prepositions = Arrays.asList(
            "A","après","avant","avex","chez","concernant","contre","dans","de", "à", "la", "au",
            "depuis","derrière","dès","devant","durant","en","entre","envers","hormis", "des",
            "hors","jusque","malgré","moyennant","nonobstant","outre","par","parmi", "aux",
            "pendant","pour","près","sans","sauf","selon","sous","suivant","sur","touchant","vers","via",
            "avant", "après", "au", "grâce","hors","loin","lors","par","par suite","près",
            "proche","quant","quitte","sauf","sous", "et"
    );
    private final static String PATH = CsvWriter.class.getClassLoader().getResource("food.csv").getFile();

    private static void generate2GramFromCsv() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File("output/step1_2Gram.csv"))) {
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
                    if (prepositions.contains(word)) builder.append(word).append(" ");
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

    private static void generate3GramFromCsv() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File("output/step1_3Gram.csv"))) {
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
                    if (prepositions.contains(word)) builder.append(word).append(" ");
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

    public static void main(String[] args) throws FileNotFoundException {
        generate2GramFromCsv();
        generate3GramFromCsv();
    }

}