package com.esgi.bigdata.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CsvWriter {
    private final static String PATH = CsvWriter.class.getClassLoader().getResource("food.csv").getFile();

    public static void main(String[] args) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File("output/step1.csv"))) {
            String sb = "PREVIOUS,CURRENT"  + '\n';
            writer.write(sb);
            Scanner myReader = new Scanner(new File(PATH));
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] parts = data.replace("\"", "").split(" ");
                for (int i = 0; i < parts.length; i++) {
                    if (i < parts.length - 1) {
                        writer.write(parts[i] + ',' + parts[i + 1] + '\n');
                    }
                }
            }
            myReader.close();
        }
    }

}