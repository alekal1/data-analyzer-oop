package com.example.hireright.analyzers;

import com.example.hireright.prototype.Analyzer;
import org.apache.commons.cli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Capital analyzer class extends Analyzer.
 * This class is made for analyzing basic analyzing + words stat starts with capital letter
 */
public class CapitalAnalyzer extends Analyzer {

    private final CommandLine cmd;

    public CapitalAnalyzer(CommandLine cmd) {
        this.cmd = cmd;
    }

    @Override
    public void analyzeText(String filePath) throws IOException {
        BufferedReader bufferedReader = loadFile(filePath);  // Load the file according to it's path

        String line; // Variable to hold the current line
        int wordCount = 0; // Variable for word counting
        int characterCounter = 0; // Variable for character counting
        int capitalLetterCount = 0; // Variable for words which starts with capital letter
        String[] stopWords = this.cmd.getOptionValue("S").split(","); // Get stop words from user's input via command line


        while ((line = bufferedReader.readLine()) != null) { // While txt file has a line
            String[] allWordsInLine = line.split("\\s+"); // Separate all words in line
            // Filter the line (delete stop words from line)
            String[] filteredWordsInLine = filterWordInLine(allWordsInLine, stopWords);

            // Count all words in filtered line
            wordCount += countWordsInLine(filteredWordsInLine);
            // Count all words in filtered line that starts with capital letter
            capitalLetterCount += this.countCapitalWordsInLine(filteredWordsInLine);
            // Count all characters in filtered line
            characterCounter += countCharactersInLine(filteredWordsInLine);

        }

        this.outputResults(filePath, characterCounter, wordCount, capitalLetterCount);
        /* Collect data for printing result of all resources */
        totalCharactersAllResources += characterCounter;
        totalWordsAllResources += wordCount;
        totalCapitalWordsAllResources += capitalLetterCount;
    }

    @Override
    protected void outputResults(String filePath, int characterCounter, int wordCount, Integer capitalLetterCount) {
        System.out.println("The " + filePath + " has the following analyzed data: ");
        System.out.println("Characters in the file (stop words are not included in count): " + characterCounter);
        System.out.println("Words in the file (stop words are not included in count): " + wordCount);
        System.out.println("Words in the file with first capital letter (stop words are not included in count): "
                + capitalLetterCount + "\n");
    }


    @Override
    public void outputAllResources() {
        System.out.println("Total characters from all resources: " + totalCharactersAllResources);
        System.out.println("Total words from all resources: " + totalWordsAllResources);
        System.out.println("Total words that starts with capital letter from all resources: " + totalCapitalWordsAllResources);
    }

    /** Method for counting all words in line that start with capital letter, should return number of counted words */
    private int countCapitalWordsInLine(String[] filteredWordsInLine) {
        if (filteredWordsInLine.length > 0) {
            int capitalLetterCount = 0;
            for (String filteredWord : filteredWordsInLine) { // For each filtered line word
                if (Character.isUpperCase(filteredWord.charAt(0))) { // If word start with capital letter
                    capitalLetterCount += 1; // Add word to capital letter count
                }
            }
            return capitalLetterCount;
        } else {
            return 0;
        }
    }
}
