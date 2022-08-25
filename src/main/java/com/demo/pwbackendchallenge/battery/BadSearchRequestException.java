package com.demo.pwbackendchallenge.battery;

public class BadSearchRequestException extends Exception {
    public BadSearchRequestException(String message) {
        super(message);
    }

    public BadSearchRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
