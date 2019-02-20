package com.smomic.exception;

import javafx.util.Pair;

public class IncorrectCountOfParamsException extends Exception {
    public IncorrectCountOfParamsException(Pair<Integer, Integer> position) {
        this.position = position;
    }
    @Override
    public void show() {
        System.err.printf("[%d:%d] Incorrect count of parameters\n",
                position.getKey(),
                position.getValue());
        System.exit(0);
    }
}
