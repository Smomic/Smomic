package com.smomic;

import com.smomic.analyzer.UnitLanguageParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.smomic.analyzer.UnitLanguageLexer.*;

public class Helper {
    public static String makeOperator(int operator) {
        String name = UnitLanguageParser.tokenNames[operator];
        return name.substring(1, name.length() - 1);
    }

    public static String connectLinesToString(List<String> lines) {
        StringBuilder builder = new StringBuilder();
        String separator = "\n";

        for (String line : lines)
            builder.append(line)
                    .append(separator);

        return builder.toString();
    }

    public static List<String> swapImports(List<String> importNames, String path) {
        List<String> importLines = new ArrayList<>();

        for (String iName : importNames) {
            String filename = new StringBuilder().append(iName)
                    .append(".txt")
                    .toString();

            Path p = Paths.get(path, filename);
            List<String> line = null;

            try {
                line = Files.readAllLines(p, StandardCharsets.UTF_8);
            } catch (IOException e) {
                System.err.printf("Incorrect filename: %s", filename);
                System.exit(0);
            }

            if (line.isEmpty()) {
                System.err.printf("File cannot be empty!", filename);
                System.exit(0);
            }

            importLines.add(new StringBuilder().append(makeOperator(Import))
                    .append(makeOperator(LeftBracket))
                    .append(line.get(0))
                    .append(makeOperator(RightBracket))
                    .append(makeOperator(Semi))
                    .toString());
        }

        return importLines;
    }
}
