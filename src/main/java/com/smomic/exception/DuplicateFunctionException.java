package com.smomic.exception;

import javafx.util.Pair;

public class DuplicateFunctionException extends Exception {
    private String funName;

    public DuplicateFunctionException(String funName, Pair<Integer, Integer> position) {
        this.funName = funName;
        this.position = position;
    }

    @Override
    public void show() {
        System.err.printf("[%d:%d] Duplicate function name: %s \n",
                position.getKey(),
                position.getValue(),
                funName);
    }
}
