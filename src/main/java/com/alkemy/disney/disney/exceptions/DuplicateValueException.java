package com.alkemy.disney.disney.exceptions;

public class DuplicateValueException extends RuntimeException{
    private String message;
    public DuplicateValueException(String message){
        super(message);
    }
}
