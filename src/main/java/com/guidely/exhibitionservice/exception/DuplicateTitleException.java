package com.guidely.exhibitionservice.exception;

public class DuplicateTitleException extends RuntimeException {
    public DuplicateTitleException(String message) {
        super(message);
    }
} 