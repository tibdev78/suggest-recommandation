package com.suggest.recommandation.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * A N Gram exporter allows user to export a text into the CSV dedicated format.
 * For instance, Big Data is nice for N = 1 will become
 * Big,Data,
 * Data,is
 * is,nice
 * nice,
 */
public class NGramsCsvExporter {

    /**
     * Read source, make the N+1 grams file, and export it to destination
     * @param source the file to find the original text
     * @param destination the destination to write csv in
     * @param N the N for the N grams file
     * @throws Exception for IO Exception
     */
    public void export(String source, String destination, int N) throws Exception {
        File sourceFile = new File(source);
        if (!sourceFile.exists()) throw new FileNotFoundException("Source file should exist");
        File destinationFile = new File(destination);
        if (destinationFile.exists()) destinationFile.delete();
        destinationFile.createNewFile();
        PrintStream out = new PrintStream(destinationFile);
        for(String line : exportToLines(source,N+1)) {
            out.println(line);
            out.flush();
        }
        out.close();
    }

    /**
     * Read a file and make it a CSV with wordsPerLine per line
     * @param sourceFile the source file to read words from
     * @param wordsPerLine the number of words per line
     * @return the list of all lines as the CSV
     * @throws Exception for any IO error
     */
    public List<String> exportToLines(String sourceFile, int wordsPerLine) throws Exception {
        if (wordsPerLine <= 0) throw new IndexOutOfBoundsException("At least one word per line");
        List<String> lines = new LinkedList<>();
        try(CorpusReader reader = new CorpusReader(sourceFile)) {
            Iterator<String> iterator = reader.iterator();
            // Init the loader to read first elements
            LinkedList<String> previousWords = new LinkedList<>();
            int index = 0 ;
            while(index < wordsPerLine && iterator.hasNext()) {
                previousWords.add(iterator.next());
                ++index;
            }
            // OK, parse
            while(! previousWords.isEmpty()) {
                lines.add(serialize(previousWords,wordsPerLine));
                previousWords.poll();
                if (iterator.hasNext()) previousWords.addLast(iterator.next());
            }
        }
        return lines ;
    }

    /**
     * Given a line as a list of words, transform it to a CSV line
     * @param words the words to split with ,
     * @param numberOfColumns the number of columns expected for the line
     * @return the line with the columns, words in each column when possible
     */
    private String serialize(List<String> words, int numberOfColumns) {
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = words.iterator();
        int index = 0 ;
        while(index < numberOfColumns) {
            if (iterator.hasNext()) builder.append(iterator.next());
            builder.append(",");
            ++index;
        }
        return builder.toString();
    }
}
