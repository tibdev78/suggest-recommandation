package com.suggest.recommandation.reader;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * We read a file that contains the words to group and we want to get all the words.
 * So we create an iterator over the file content.
 *
 * ATTENTION : all words are set to lower case
 */
public class CorpusReader implements AutoCloseable, Iterator<String>, Iterable<String> {
    /** the reader to read the lines */
    private final BufferedReader reader;
    /** the current line */
    private String currentLine;
    /** the current words in the current line */
    private String[] currentWords;
    /** the current index of the word in the currentWords */
    private int currentIndex;

    /**
     * Constructs a new corpus to read from src/main/resources
     * @param path the absolute file path
     */
    public CorpusReader(String path) {
        try {
            reader = new BufferedReader(new FileReader(path));
            String newLine = nextLine();
            this.processNewLine(newLine);
        } catch (Exception e) {
            throw new RuntimeException("Could not open file : " + path, e);
        }
    }

    /**
     * is there a next word in the corpus ?
     * @return true if there is a next word in the corpus, false otherwise
     */
    @Override
    public boolean hasNext() {
        return this.currentLine != null;
    }

    /**
     * Get the next word (if any) in the corpus
     * @return the next word we read
     */
    @Override
    public String next() {
        String result = currentWords[this.currentIndex];
        try {
            increment();
        } catch (Exception e) {
            throw new RuntimeException("IO error while going for next token", e);
        }
        return result;
    }

    @Override
    public Iterator<String> iterator() {
        return this;
    }

    @Override
    public void close() throws Exception {
        this.reader.close();
    }

    /**
     * Add one to the counters, and, if necessary, go for the next line
     *
     * @throws IOException
     */
    private void increment() throws IOException {
        final int size = currentWords.length;
        ++this.currentIndex;
        if (currentIndex >= size) {
            // go for next line
            String newLine = nextLine();
            this.processNewLine(newLine);
        }
    }

    /**
     * Get the next significant line from the file
     *
     * @return the raw line from the file, null for end of file
     * @throws IOException
     */
    private String nextLine() throws IOException {
        String localLine = reader.readLine();
        boolean found = false;
        while (localLine != null && !found) {
            found = !isEmpty(localLine);
            if (!found) {
                localLine = reader.readLine();
            }
        }
        return localLine;
    }

    /**
     * Process the line, that is split it by tokens
     *
     * @param line the new line to process
     */
    private void processNewLine(String line) {
        if (line != null) {
            this.currentIndex = 0;
            this.currentLine = clean(line);
            List<String> words = new LinkedList<>();
            String[] tokens = this.currentLine.split(" ");
            for(int i = 0 ; i < tokens.length; i++) {
                if (!isEmpty(tokens[i])) words.add(tokens[i]);
            }
            int resultSize = words.size();
            this.currentWords = new String[resultSize];
            this.currentWords = words.toArray(this.currentWords);
        } else {
            this.currentIndex = -1;
            this.currentLine = null ;
            this.currentWords = null ;
        }
    }

    /**
     * Test if a line or a word is not null and empty
     *
     * @param line the current line to test
     * @return not null and empty
     */
    private boolean isEmpty(String line) {
        return line != null && line.replaceAll("\\s", "").replaceAll("[^a-zA-Zéèàù]", "").length() == 0 ;
    }

    /**
     * Clean the line
     *
     * @param line a non null line from the file
     * @return the clean line ready to be split
     */
    private String clean(String line) {
        String result = line;
        result = Pattern.compile("--", Pattern.CASE_INSENSITIVE).matcher(result).replaceAll(" ");
        result = result.replace('.', ' ').replace(',', ' ').replace(';', ' ').replace("\"", " ");
        result = result.replace('\'', ' ').replace('?', ' ').replace('!', ' ');
        result = result.replace(')', ' ').replace('(', ' ').replace('-', ' ');
        result = result.toLowerCase().trim();
        return result;
    }
}