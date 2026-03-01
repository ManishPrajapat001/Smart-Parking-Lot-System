package org.manish.exception;

public class InvalidTime extends RuntimeException {
    public InvalidTime(String message) {
        super(message);
    }
}
