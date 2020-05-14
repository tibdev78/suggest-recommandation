package com.suggest.recommandation.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TxtWriter {
    private static final List<String> prepositions = Arrays.asList(
            "A","après","avant","avex","chez","concernant","contre","dans","de", "à", "la", "au",
            "depuis","derrière","dès","devant","durant","en","entre","envers","hormis", "des",
            "hors","jusque","malgré","moyennant","nonobstant","outre","par","parmi", "aux",
            "pendant","pour","près","sans","sauf","selon","sous","suivant","sur","touchant","vers","via",
            "avant", "après", "au", "grâce","hors","loin","lors","par","par suite","près",
            "proche","quant","quitte","sauf","sous", "et"
    );

    private static final Pattern p = Pattern.compile("[$«»()\\”\\“\\\"\\-\\–\\…&+,.:;=!?@#|]");
    private final static String PATH = TxtWriter.class.getClassLoader().getResource("general.txt").getFile();

    private static void generate2GramFromTXT() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File("output/step3_txt_2Gram.csv"))) {
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
                            currentWord = currentWord.isEmpty() ? cleanParts.get(i) : currentWord;
                        }

                        if (p.matcher(nextWord).find()) {
                            nextWord = nextWord.replaceAll(p.toString(), "");
                            nextWord = nextWord.isEmpty() ? cleanParts.get(i + 1) : nextWord;
                        }
                        writer.write(currentWord.trim() + ',' + nextWord.trim() + '\n');
                    }
                }
            }
            myReader.close();
        }
    }

    private static void generate3GramFromTXT() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File("output/step3_txt_3Gram.csv"))) {
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
                        String currentWord = cleanParts.get(i).concat(' ' + cleanParts.get(i + 1));
                        String nextWord = cleanParts.get(i + 2);

                        if (p.matcher(currentWord).find()) {
                            currentWord = currentWord.replaceAll(p.toString(), "");
                            currentWord = currentWord.isEmpty()
                                    ? cleanParts.get(i).concat(' ' + cleanParts.get(i + 1))
                                    : currentWord;
                        }

                        if (p.matcher(nextWord).find()) {
                            nextWord = nextWord.replaceAll(p.toString(), "");
                            nextWord = nextWord.isEmpty() ? cleanParts.get(i + 2) : nextWord;
                        }

                        writer.write(currentWord.trim() + ',' + nextWord.trim() + '\n');
                    }
                }
            }
            myReader.close();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        generate2GramFromTXT();
        //generate3GramFromTXT();
    }
}
