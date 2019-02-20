package com.smomic.exception;

import javafx.util.Pair;

public class IncorrectConvertException extends Exception {
    private String name;

    public IncorrectConvertException(String name, Pair<Integer, Integer> position) {
        this.position = position;
        this.name = name;
    }

    @Override
    public void show() {
        System.err.printf("[%d:%d] Incorrect convert operator: %s\n",
                position.getKey(),
                position.getValue(),
                name);
        System.exit(0);
    }
}
