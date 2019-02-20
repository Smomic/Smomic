package com.smomic.exception;

import javafx.util.Pair;

public class OverridePrintFunctionException extends Exception {
    public OverridePrintFunctionException(Pair<Integer, Integer> position) {
        this.position = position;
    }

    @Override
    public void show() {
        System.err.printf("[%d:%d] Cannot override print() function\n",
                position.getKey(),
                position.getValue());
        System.exit(0);
    }
}
