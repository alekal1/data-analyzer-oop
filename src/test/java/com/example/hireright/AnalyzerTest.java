package com.example.hireright;

import com.example.hireright.analyzers.CapitalAnalyzer;
import com.example.hireright.analyzers.CommonAnalyzer;
import com.example.hireright.prototype.Analyzer;
import org.apache.commons.cli.CommandLine;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;


public class AnalyzerTest {


    private CommandLine cmd = null;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final int ZERO = 0;
    private final int CAPITALCOUNT = 1;
    private final int CHARCOUNTCAPITAL = 64;
    private final int WORDCOUNTCAPITAL = 12;
    private final String EMPTYFILE = "empty.txt";
    private final String ONLYSTOPWORDS = "onlystopwords.txt";
    private final String CAPITALFILE = "capital.txt";
    private final String NOCAPITALFILE = "nocapital.txt";
    private final String NONEXISTING = "dummy.txt";

    @Before
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }


    @Test
    public void testAnalyzeEmptyFile() throws IOException {
        String filePath = configureFileInArg(EMPTYFILE);

        Analyzer commonAnalyzer = new CommonAnalyzer(cmd);

        commonAnalyzer.analyzeText(filePath);

        Assert.assertEquals(
                "The " + filePath + " has the following analyzed data: " + "\r\n" +
                        "Characters in the file (stop words are not included in count): " + ZERO +"\r\n" +
                        "Words in the file (stop words are not included in count): " + ZERO,
                outputStreamCaptor.toString().trim()
                );

    }

    @Test
    public void testAnalyzeFileThatConsistsOfStopWords() throws IOException {
        String filePath = configureFileInArg(ONLYSTOPWORDS);

        Analyzer commonAnalyzer = new CommonAnalyzer(cmd);

        commonAnalyzer.analyzeText(filePath);

        Assert.assertEquals(
                "The " + filePath + " has the following analyzed data: " + "\r\n" +
                        "Characters in the file (stop words are not included in count): " + ZERO +"\r\n" +
                        "Words in the file (stop words are not included in count): " + ZERO,
                outputStreamCaptor.toString().trim()
        );
    }

    @Test
    public void testVerifyCapitalLetterCounterShouldHaveOne() throws IOException {
        String filePath = configureFileInArg(CAPITALFILE);

        Analyzer capitalAnalyzer = new CapitalAnalyzer(cmd);
        capitalAnalyzer.analyzeText(filePath);

        Assert.assertEquals(
                "The " + filePath + " has the following analyzed data: " + "\r\n" +
                        "Characters in the file (stop words are not included in count): " + CHARCOUNTCAPITAL +"\r\n" +
                        "Words in the file (stop words are not included in count): " + WORDCOUNTCAPITAL + "\r\n" +
                        "Words in the file with first capital letter (stop words are not included in count): "
                        + CAPITALCOUNT,
                outputStreamCaptor.toString().trim()
        );
    }

    @Test
    public void testVerifyCapitalLetterCounterShouldHaveZero() throws IOException {
        String filePath = configureFileInArg(NOCAPITALFILE);

        Analyzer capitalAnalyzer = new CapitalAnalyzer(cmd);
        capitalAnalyzer.analyzeText(filePath);

        Assert.assertEquals(
                "The " + filePath + " has the following analyzed data: " + "\r\n" +
                        "Characters in the file (stop words are not included in count): " + CHARCOUNTCAPITAL +"\r\n" +
                        "Words in the file (stop words are not included in count): " + WORDCOUNTCAPITAL + "\r\n" +
                        "Words in the file with first capital letter (stop words are not included in count): "
                        + ZERO,
                outputStreamCaptor.toString().trim()
        );
    }

    @Test(expected = FileNotFoundException.class)
    public void testAnalyzeUnexistingFile() throws IOException {

        String filePath = configureFileInArg(NONEXISTING);
        Analyzer commonAnalyzer = new CommonAnalyzer(cmd);

        commonAnalyzer.analyzeText(filePath);

    }

    private String configureFileInArg(String name) {
        Path resourceDir = Paths.get("src", "test", "resources", name);
        String absolutePath = resourceDir.toFile().getAbsolutePath();
        String[] args = {"-C", "-L", "-S=at,the,on", "-F=" + absolutePath + ""};
        this.cmd = Main.configureCommandLine(args);
        return absolutePath;
    }

}
