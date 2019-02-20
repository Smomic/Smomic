package com.smomic.exception;

import javafx.util.Pair;

public class IncorrectConditionException extends Exception {
    public IncorrectConditionException(Pair<Integer, Integer> position) {
        this.position = position;
    }

    @Override
    public void show() {
        System.err.printf("[%d:%d] Incorrect construction of condition\n",
                position.getKey(),
                position.getValue());
        System.exit(0);
    }
}
