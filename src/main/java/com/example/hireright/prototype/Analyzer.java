package com.example.hireright.prototype;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Analyzer {
    protected int totalCharactersAllResources = 0;
    protected int totalWordsAllResources = 0;
    protected int totalCapitalWordsAllResources = 0;

    /** Method for deleting stop words from original line, should return current line with no stop words in it. */
    protected String[] filterWordInLine(String[] allWords, String[] stopWords) {
        // Change all words array to List for further usage
        List<String> allWordsInLine = new ArrayList<>(Arrays.asList(allWords));

        for (String stopWord : stopWords) { // For each stop words
            // Since document(s) can have stop word with first capital letter, need to handle it als0
            String upperCaseStopWord = stopWord.substring(0, 1).toUpperCase() + stopWord.substring(1);
            String loweCaseStopWord = stopWord.substring(0, 1).toLowerCase() + stopWord.substring(1);

            // List contains upper case stop word
            boolean containUpperCaseStopWord = allWordsInLine.contains(upperCaseStopWord);
            // List contains origin stop word
            boolean containsLowerCaseStopWord = allWordsInLine.contains(loweCaseStopWord);

            if (containsLowerCaseStopWord) { // If list contains origin stop word
                // remove all stop words from current line
                allWordsInLine.removeAll(Collections.singleton(loweCaseStopWord));
            }
            if (containUpperCaseStopWord) { // If list contains upper case stop word
                // remove all stop words from current line
                allWordsInLine.removeAll(Collections.singleton(upperCaseStopWord));
            }
        }
        // Create a new array of filtered line
        String[] filteredArray = new String[allWordsInLine.size()];
        filteredArray = allWordsInLine.toArray(filteredArray);
        return filteredArray;
    }

    /** Method for file loading, should return buffer reader of current file */
    protected BufferedReader loadFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        return new BufferedReader(inputStreamReader);
    }

    /** Method for counting all words in line,   should return number of counted words*/
    protected int countWordsInLine(String[] filteredWordsInLine) {
        return filteredWordsInLine.length;
    }

    /** Method for counting all characters in line, should return number of counted characters */
    protected int countCharactersInLine(String[] filteredWordsInLine) {
        if (filteredWordsInLine.length > 0) { // In case we will have a empty file
            int charCounter = 0;
            for (String filteredWord : filteredWordsInLine) { // For each filtered line word
                // Add length of word (since spaces are also characters need to add +1)
                charCounter += filteredWord.length() + 1;
            }
            // We added +1 because of a space, but last word on line will have no space after need to substract 1
            return charCounter - 1;
        } else {
            return 0;
        }
    }

    /** Method for analyzing txt file based on analyzer type */
    public abstract void analyzeText(String filePath) throws IOException;

    /** Method for results' output based on analyzer type */
    protected abstract void outputResults(String filePath, int characterCounter, int wordCount, Integer capitalLetterCount);

    /** Method for printing total analyzed data to user */
    public abstract void outputAllResources();
}
