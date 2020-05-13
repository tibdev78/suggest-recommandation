package com.suggest.recommandation.tools;

import org.apache.spark.sql.api.java.UDF1;

/**
 * UDF to prove the concept.
 * It returns the length of a string
 */
public class UDFCount implements UDF1<String,Integer> {
    /**
     * Get the size of a string
     * @param s the string to get size of
     * @return the size of the string, 0 for null
     * @throws Exception
     */
    @Override
    public Integer call(String s) throws Exception {
        return s == null ? 0 : s.length();
    }
}
