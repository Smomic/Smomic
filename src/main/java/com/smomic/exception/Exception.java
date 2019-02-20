package com.smomic.exception;

import javafx.util.Pair;

public abstract class Exception extends java.lang.Exception {
    protected Pair<Integer, Integer> position;
    public abstract void show();
}
