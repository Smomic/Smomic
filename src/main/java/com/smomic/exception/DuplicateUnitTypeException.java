package com.smomic.exception;

import javafx.util.Pair;

public class DuplicateUnitTypeException extends Exception {
    private String type;

    public DuplicateUnitTypeException(String type, Pair<Integer, Integer> position) {
        this.type = type;
        this.position = position;
    }
    @Override
    public void show() {
        System.err.printf("[%d:%d] Duplicate unit type: %s\n",
                position.getKey(),
                position.getValue(),
                type);
    }
}
