package com.smomic.exception;

import javafx.util.Pair;

public class IncorrectFunNameException extends Exception {
    private String name;

    public IncorrectFunNameException(String name, Pair<Integer, Integer> position) {
        this.name = name;
        this.position = position;
    }
    @Override
    public void show() {
        System.err.printf("[%d:%d] Incorrect name of function: %s\n",
                position.getKey(),
                position.getValue(),
                name);
        System.exit(0);
    }
}
