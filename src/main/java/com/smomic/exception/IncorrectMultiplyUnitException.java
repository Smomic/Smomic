package com.smomic.exception;

import javafx.util.Pair;

public class IncorrectMultiplyUnitException extends Exception {
    private String name;

    public IncorrectMultiplyUnitException(String name, Pair<Integer, Integer> position) {
        this.position = position;
        this.name = name;
    }

    @Override
    public void show() {
        System.err.printf("[%d:%d] Incorrect unit in multiply expression: %s\n",
                position.getKey(),
                position.getValue(),
                name);
        System.exit(0);
    }
}
