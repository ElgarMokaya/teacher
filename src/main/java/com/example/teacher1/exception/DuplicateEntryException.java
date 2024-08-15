package com.example.teacher1.exception;

public class DuplicateEntryException extends RuntimeException{

    public DuplicateEntryException(String message){
        super(message);
    }
}
