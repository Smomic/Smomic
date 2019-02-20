package com.smomic.exception;

public class NoMainFunctionException extends Exception {
    @Override
    public void show() {
        System.err.println("Fault of \"main\" function !");
    }
}
