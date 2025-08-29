package com.guidely.exhibitionservice.exception;

public class ExhibitionNotFoundException extends RuntimeException {
    
    public ExhibitionNotFoundException(String message) {
        super(message);
    }
    
    public ExhibitionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
} 