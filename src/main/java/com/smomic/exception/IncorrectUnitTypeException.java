package com.smomic.exception;

import javafx.util.Pair;

public class IncorrectUnitTypeException extends Exception {
    private String name;

    public IncorrectUnitTypeException(String name, Pair<Integer, Integer> position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public void show() {
        System.err.printf("[%d:%d] Incorrect type of unit in expression: %s\n",
                position.getKey(),
                position.getValue(),
                name);
        System.exit(0);
    }
}
