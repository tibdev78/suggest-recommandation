package com.suggest.recommandation.reader;

/**
 * Example of usage :
 * java -cp reader-1.0-SNAPSHOT.jar com.esgi.bigdata.reader.Main 'C:\Users\Jean\Documents\esgi202005\reader\src\test\resources\sample.txt' 'C:\Users\Jean\Documents\esgi202005\reader\src\test\resources\result.csv' 1
 */
public class Main {

    /**
     * Export text to N gram files.
     * Arguments are :
     * source file
     * destination file
     * N for the N gram
     * @param args the args for the program
     * @throws Exception for IO error
     */
    public static void main(String[] args) throws Exception {
        final String source = args[0];
        final String destination = args[1];
        final int N = Integer.parseInt(args[2]);
        final NGramsCsvExporter exporter = new NGramsCsvExporter();
        exporter.export(source,destination,N);
    }
}
