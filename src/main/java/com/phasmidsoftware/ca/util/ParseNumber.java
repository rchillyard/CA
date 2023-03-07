package com.phasmidsoftware.ca.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseNumber {

    /**
     * This method is simply to give an example of a Java program that might return null.
     *
     * @param string a String from which we want to parse a (integer) number.
     * @return the String corresponding to the first number (otherwise null if there is no number)
     */
    public static String parseNumber(String string) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(string);
        if (matcher.matches()) return matcher.group(0);
        else return null;
    }
}
