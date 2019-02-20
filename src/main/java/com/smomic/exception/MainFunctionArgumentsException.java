package com.smomic.exception;

import javafx.util.Pair;

public class MainFunctionArgumentsException extends Exception {

    public MainFunctionArgumentsException(Pair<Integer, Integer> position) {
        this.position = position;
    }

    @Override
    public void show() {
        System.err.printf("[%d:%d] Unexpected parameters for \"main\" function!\n",
                position.getKey(),
                position.getValue());
    }
}
