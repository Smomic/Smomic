package com.smomic.exception;

import javafx.util.Pair;

public class NoNameUnitTypeException extends Exception {
    private String type;

    public NoNameUnitTypeException(String type, Pair<Integer, Integer> position) {
        this.type = type;
        this.position = position;
    }

    @Override
    public void show() {
        System.err.printf("[%d:%d] No name unit type: %s\n",
                position.getKey(),
                position.getValue(),
                type);
    }
}
