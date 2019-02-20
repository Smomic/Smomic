package com.smomic.exception;

import javafx.util.Pair;

public class DuplicateUnitException extends Exception {
    private String name;

    public DuplicateUnitException(String name, Pair<Integer, Integer> position) {
        this.name = name;
        this.position = position;
    }
    @Override
    public void show() {
        System.err.printf("[%d:%d] Duplicate unit: %s\n",
                position.getKey(),
                position.getValue(),
                name);
    }
}
