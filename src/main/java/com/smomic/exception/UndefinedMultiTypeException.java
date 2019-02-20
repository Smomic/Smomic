package com.smomic.exception;

import javafx.util.Pair;

public class UndefinedMultiTypeException extends Exception {
    public UndefinedMultiTypeException(Pair<Integer, Integer> position) {
        this.position = position;
    }

    @Override
    public void show() {
        System.err.printf("[%d:%d] Undefined multi type for a such level of expression\n",
                position.getKey(),
                position.getValue());
        System.exit(0);
    }


}
