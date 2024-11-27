package com.finalproject.finalproject.Exceptions;

import java.util.Map;

public class LoginException extends RuntimeException {
    private Map<String, Integer> errors;
    public LoginException(String message, Map<String, Integer> errors) {
        super(message);
        this.errors = errors;
    }
    public Map<String, Integer> getErrors() {
        return errors;
    }
}
