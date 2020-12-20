package com.example.hireright;

import com.example.hireright.analyzers.CapitalAnalyzer;
import com.example.hireright.analyzers.CommonAnalyzer;
import com.example.hireright.prototype.Analyzer;
import org.apache.commons.cli.CommandLine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;


public class AnalyzerTest {


    private CommandLine cmd = null;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }


    @Test
    public void testAnalyzeEmptyFile() throws IOException {
        String filePath = configureFileInArg("empty.txt");
        int charCount = 0;
        int wordCount = 0;

        Analyzer commonAnalyzer = new CommonAnalyzer(cmd);

        commonAnalyzer.analyzeText(filePath);

        Assert.assertEquals(
                "The " + filePath + " has the following analyzed data: " + "\r\n" +
                        "Characters in the file (stop words are not included in count): " + charCount +"\r\n" +
                        "Words in the file (stop words are not included in count): " + wordCount,
                outputStreamCaptor.toString().trim()
                );

    }

    @Test
    public void testAnalyzeFileThatConsistsOfStopWords() throws IOException {
        String filePath = configureFileInArg("onlystopwords.txt");
        int charCount = 0;
        int wordCount = 0;

        Analyzer commonAnalyzer = new CommonAnalyzer(cmd);

        commonAnalyzer.analyzeText(filePath);

        Assert.assertEquals(
                "The " + filePath + " has the following analyzed data: " + "\r\n" +
                        "Characters in the file (stop words are not included in count): " + charCount +"\r\n" +
                        "Words in the file (stop words are not included in count): " + wordCount,
                outputStreamCaptor.toString().trim()
        );
    }

    @Test
    public void testVerifyCapitalLetterCounterShouldHaveOne() throws IOException {
        String filePath = configureFileInArg("capital.txt");
        int charCount = 64;
        int wordCount = 12;
        int capitalCount = 1;

        Analyzer capitalAnalyzer = new CapitalAnalyzer(cmd);
        capitalAnalyzer.analyzeText(filePath);

        Assert.assertEquals(
                "The " + filePath + " has the following analyzed data: " + "\r\n" +
                        "Characters in the file (stop words are not included in count): " + charCount +"\r\n" +
                        "Words in the file (stop words are not included in count): " + wordCount + "\r\n" +
                        "Words in the file with first capital letter (stop words are not included in count): "
                        + capitalCount,
                outputStreamCaptor.toString().trim()
        );
    }

    @Test
    public void testVerifyCapitalLetterCounterShouldHaveZero() throws IOException {
        String filePath = configureFileInArg("nocapital.txt");
        int charCount = 64;
        int wordCount = 12;
        int capitalCount = 0;

        Analyzer capitalAnalyzer = new CapitalAnalyzer(cmd);
        capitalAnalyzer.analyzeText(filePath);

        Assert.assertEquals(
                "The " + filePath + " has the following analyzed data: " + "\r\n" +
                        "Characters in the file (stop words are not included in count): " + charCount +"\r\n" +
                        "Words in the file (stop words are not included in count): " + wordCount + "\r\n" +
                        "Words in the file with first capital letter (stop words are not included in count): "
                        + capitalCount,
                outputStreamCaptor.toString().trim()
        );
    }

    @Test(expected = FileNotFoundException.class)
    public void testAnalyzeUnexistingFile() throws IOException {

        String filePath = configureFileInArg("dummy.txt");
        Analyzer commonAnalyzer = new CommonAnalyzer(cmd);

        commonAnalyzer.analyzeText(filePath);

    }

    @Test
    public void testOutputFromAllResources() throws IOException {
        String fileOne = configureFileInArg("capital.txt");
        String fileTwo = configureFileInArg("nocapital.txt");

        String joinedFilesPath = configureMultipleFilesInArgs(fileOne, fileTwo);

        Analyzer capitalAnalyzer = new CapitalAnalyzer(cmd);

        capitalAnalyzer.analyzeText(joinedFilesPath);
        capitalAnalyzer.outputAllResources();

        Assert.assertEquals(
                "The " + fileOne + " has the following analyzed data: " + "\r\n" +
                        "Characters in the file (stop words are not included in count): " + String.valueOf(64) +"\r\n" +
                        "Words in the file (stop words are not included in count): " + String.valueOf(12) + "\r\n" +
                        "Words in the file with first capital letter (stop words are not included in count): "
                        + String.valueOf(1),
                outputStreamCaptor.toString().trim()
        );

    }

    private String configureFileInArg(String name) {
        Path resourceDir = Paths.get("src", "test", "resources", name);
        String absolutePath = resourceDir.toFile().getAbsolutePath();
        String[] args = {"-C", "-L", "-S=at,the,on", "-F=" + absolutePath + ""};
        this.cmd = Main.configureCommandLine(args);
        return absolutePath;
    }

    private String configureMultipleFilesInArgs(String f1, String f2) {
        String joinedTwoFiles = f1 + "," + f2;
        String[] args = {"-C", "-L", "-S=at,the,on", "-F=" + joinedTwoFiles + ""};
        this.cmd = Main.configureCommandLine(args);
        return joinedTwoFiles;
    }
}
