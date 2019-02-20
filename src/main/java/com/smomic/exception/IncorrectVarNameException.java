package com.smomic.exception;

import javafx.util.Pair;

public class IncorrectVarNameException extends Exception {
    private String name;

    public IncorrectVarNameException(String name, Pair<Integer, Integer> position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public void show() {
        System.err.printf("[%d:%d] Incorrect variable name: %s\n",
                position.getKey(),
                position.getValue(),
                name);
        System.exit(0);
    }
}
