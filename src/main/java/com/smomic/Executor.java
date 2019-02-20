package com.smomic;

import com.smomic.analyzer.BuildingParser;
import com.smomic.structure.Program;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.smomic.Helper.connectLinesToString;
import static com.smomic.Helper.swapImports;

public class Executor {
    private Program program;
    private BuildingParser parser;
    private ByteArrayOutputStream errContent;

    public Executor() {
        this.parser = new BuildingParser();
        this.errContent = new ByteArrayOutputStream();
    }

    public void execute(String pathString) {
        parse(pathString);
        exchangeImports(pathString);
        checkErrorStream();
        program.clearImportNames();
        checkSemantic();
        checkErrorStream();
        reverseErrorStream();
        executeProgram();
    }

    private void parse(String pathString) {
        changeErrorStream();
        Path path = Paths.get(pathString);
        try {
            program = parser.parse(getSource(path));
        } catch (IOException e) {
            invalidPath();
        }
    }

    private void executeProgram() {
        program.execute(null, null, null);
    }

    private String getSource(Path path) throws IOException {
        return connectLinesToString(Files.readAllLines(path, StandardCharsets.UTF_8));
    }

    private void changeErrorStream() {
        System.setErr(new PrintStream(errContent));
    }

    private void exchangeImports(String pathString) {
        Path path = Paths.get(pathString);
        Program importProgram = parser.parse(connectLinesToString(swapImports(program.getImportNames(), path.getParent().toString())));
        if (importProgram.getImports() != null)
            program.getImports().addAll(importProgram.getImports());
    }

    private void checkSemantic() {
        SemChecker s = new SemChecker(program);
        s.check();
    }

    private void invalidPath() {
        reverseErrorStream();
        System.err.print("Invalid path, this file does not exist.");
        System.exit(1);
    }

    private void checkErrorStream() {
        if (!errContent.toString().equals("")) {
            reverseErrorStream();
            System.err.print(errContent);
            System.exit(1);
        }
    }

    private void reverseErrorStream() {
        System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err)));
    }
}
