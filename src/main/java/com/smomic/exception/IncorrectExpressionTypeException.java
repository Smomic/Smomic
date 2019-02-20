package com.smomic.exception;

import javafx.util.Pair;

public class IncorrectExpressionTypeException extends Exception {
    public IncorrectExpressionTypeException(Pair<Integer, Integer> position) {
        this.position = position;
    }

    @Override
    public void show() {
        System.err.printf("[%d:%d] Incorrect value of unit type in expression\n",
                position.getKey(),
                position.getValue());
        System.exit(0);
    }
}
