package com.example.hireright;

import com.example.hireright.analyzers.CapitalAnalyzer;
import com.example.hireright.analyzers.CommonAnalyzer;
import com.example.hireright.prototype.Analyzer;
import org.apache.commons.cli.*;

import java.io.IOException;

public class Main {
    private static CommandLine cmd = null; // The command line variable

    /** Method for command line configuration. */
    private static void configureCommandLine(String[] args) {

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
    }

    public static void main(String[] args) throws IOException {
        configureCommandLine(args);

        String[] filesPath = cmd.getOptionValue("F").split(",");
        boolean capitalLetterCount = cmd.hasOption("L");

        if (capitalLetterCount) {
            Analyzer capitalLetterAnalyzer = new CapitalAnalyzer(cmd);
            for (String path : filesPath) {
                capitalLetterAnalyzer.analyzeText(path);
            }
            capitalLetterAnalyzer.outputAllResources();
        } else {
            Analyzer commonAnalyzer = new CommonAnalyzer(cmd);
            for (String path : filesPath) {
                commonAnalyzer.analyzeText(path);
            }
            commonAnalyzer.outputAllResources();
        }
    }
}
