package com.alpine.dairy;

public class UnknownPageException extends RuntimeException {
    public UnknownPageException() {
        super("Could not find the page");
    }
}
