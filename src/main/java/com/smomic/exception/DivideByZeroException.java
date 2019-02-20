package com.smomic.exception;

import javafx.util.Pair;

public class DivideByZeroException extends Exception {
    public DivideByZeroException(Pair<Integer, Integer> position) {
        this.position = position;
    }

    @Override
    public void show() {
        System.err.printf("[%d, %d] Cannot divide by zero!\n", position.getKey(), position.getValue());
        System.exit(0);
    }
}
