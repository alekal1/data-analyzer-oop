package com.example.hireright;

import com.example.hireright.analyzers.CapitalAnalyzer;
import com.example.hireright.analyzers.CommonAnalyzer;
import com.example.hireright.prototype.Analyzer;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.Arrays;

public class Main {

    /** Method for command line configuration. */
    protected static CommandLine configureCommandLine(String[] args) {
        CommandLine cmd = null; // The command line variable

        Options options = new Options(); // All arguments of user's input will be saved in options

        /* Save all arguments to options variable */

        /* -F argument should have an argument(s)*/
        Option inputFile = new Option("F", "FILE", true, "Path to txt file(s) for analyzing");
        inputFile.setRequired(true); // must be as a required argument
        options.addOption(inputFile);

        /* -S argument should have an argument(s) and must be as a required argument  */
        Option stopWords = new Option("S", "STOPWORDS", true, "Stopwords which " +
                "will not be counted");
        stopWords.setRequired(true); // it must be as a required argument
        options.addOption(stopWords);

        /* -C argument should not have an argument */
        Option characterCounter = new Option("C", "CHARCOUNT", false, "Counts " +
                "characters in (each) file");
        characterCounter.setRequired(true); // it must be as a required argument
        options.addOption(characterCounter);

        /* -L argument should not have an argument and it's not a required argument   */
        Option capitalLetterCounter = new Option("L", "CAPITALCOUNT", false, "Count words " +
                "which starts with capital letter");
        capitalLetterCounter.setRequired(false); // it's not a required argument
        options.addOption(capitalLetterCounter);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            // If user will print a wrong argument, program should print the help message of how to use the program
            formatter.printHelp("Data analyzer", options);
            System.exit(1);
        }

        return cmd;
    }

    /** Main method */
    public static void main(String[] args) throws IOException {
        System.out.println(Arrays.asList(args));
        CommandLine cmd = configureCommandLine(args);

        // Since user can input multiple files, need to handle it
        String[] filesPath = cmd.getOptionValue("F").split(",");
        // Check if we should count capital letters
        boolean capitalLetterCount = cmd.hasOption("L");

        // If should count capital letters
        if (capitalLetterCount) {
            // Create capital letter analyzer
            Analyzer capitalLetterAnalyzer = new CapitalAnalyzer(cmd);
            for (String path : filesPath) { // For each file path
                capitalLetterAnalyzer.analyzeText(path); // analyze txt file
            }
            // Output all analyzing data
            capitalLetterAnalyzer.outputAllResources();
        } else { // If should not count capital letters
            // Create common letter analyzer
            Analyzer commonAnalyzer = new CommonAnalyzer(cmd);
            for (String path : filesPath) {  // For each file path
                commonAnalyzer.analyzeText(path);  // analyze txt file
            }
            // Output all analyzing data
            commonAnalyzer.outputAllResources();
        }
    }
}
