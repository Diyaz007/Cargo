package com.finalproject.finalproject.Exceptions;

import java.util.HashMap;
import java.util.Map;

public class SignUpException extends RuntimeException {
    private Map<String, Integer> errors;
    public SignUpException(String message, Map<String, Integer> errors) {
        super(message);
        this.errors = errors;
    }
    public Map<String, Integer> getErrors(){
        return errors;
    }
}
