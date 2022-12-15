package com.example.bot1.exception;

public class NotFoundRecordException extends RuntimeException{

    public NotFoundRecordException(Object param) {
        super("Record was not founded with id: " + param);
    }
}
