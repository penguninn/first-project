package com.daviddai.retail.exception;

public class DataAccessException extends RuntimeException {

    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(String message, Exception e) {
        super(message, e);
    }

}
