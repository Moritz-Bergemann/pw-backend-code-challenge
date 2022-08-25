package com.demo.pwbackendchallenge.battery;

public class BadAddRequestException extends Exception {
    public BadAddRequestException(String message) {
        super(message);
    }

    public BadAddRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
