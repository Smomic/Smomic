package com.smomic;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Interpreter {

    public static void main(String[] args) {

        String pathString;
        if (args.length == 1 && !args[0].equals("-h"))
            pathString = args[0];
        else if (args.length == 1 && args[0].equals("-h")) {
            System.out.print(helpInfo);
            return;
        }
        else {
            System.out.print("Invalid numbers of arguments!\nRun with -h to get help.");
            return;
        }

        if (isDefault(pathString) == null)
            pathString = defaultPath + pathString;

        Executor executor = new Executor();
        executor.execute(pathString);
    }

    private static Path isDefault(String pathString) {
        Path path = Paths.get(pathString);
        return path.getParent();
    }

    private static final String defaultPath = "src/main/resources/";
    private static final String helpInfo = "Argument contains path with file of the program.\n"  +
            "Default path for executing only with program filename is " + defaultPath + ".\n" +
            "Dynamic libraries must be in the same directory as program.";

}


