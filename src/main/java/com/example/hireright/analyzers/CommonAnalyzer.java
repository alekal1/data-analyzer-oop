package com.example.hireright.analyzers;

import com.example.hireright.prototype.Analyzer;
import org.apache.commons.cli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Common analyzer class extends Analyzer.
 * This class is made for basic analyzing, so no capital letters counts
 */
public class CommonAnalyzer extends Analyzer {
    private final CommandLine cmd;

    public CommonAnalyzer(CommandLine cmd) {
        this.cmd = cmd;
    }

    @Override
    public void analyzeText(String filePath) throws IOException {
        BufferedReader bufferedReader = loadFile(filePath);
        String line; // Variable to hold the current line
        int wordCount = 0; // Variable for word counting
        int characterCounter = 0; // Variable for character counting
        String[] stopWords = this.cmd.getOptionValue("S").split(","); // Get stop words from user's input via command line

        while ((line = bufferedReader.readLine()) != null) { // While txt file has a line
            String[] allWordsInLine = line.split("\\s+"); // Separate all words in line
            // Filter the line (delete stop words from line)
            String[] filteredWordsInLine = filterWordInLine(allWordsInLine, stopWords);

            // Count all words in filtered line
            wordCount += countWordsInLine(filteredWordsInLine);

            // Count all characters in filtered line
            characterCounter += countCharactersInLine(filteredWordsInLine);

        }
        this.outputResults(filePath, characterCounter, wordCount, null);

        /* Collect data for printing result of all resources */
        totalCharactersAllResources += characterCounter;
        totalWordsAllResources += wordCount;
    }

    @Override
    protected void outputResults(String filePath, int characterCounter, int wordCount, Integer capitalLetterCount) {
        System.out.println("The " + filePath + " has the following analyzed data: ");
        System.out.println("Characters in the file (stop words are not included in count): " + characterCounter);
        System.out.println("Words in the file (stop words are not included in count): " + wordCount + "\n");
    }

    @Override
    public void outputAllResources() {
        System.out.println("Total characters from all resources: " + totalCharactersAllResources);
        System.out.println("Total words from all resources: " + totalWordsAllResources);
    }
}
