package com.epam.lab.exception;

public class DAOException extends Exception {

    public DAOException(String message, Exception e) {
        super(message, e);
    }

    public DAOException(String message, InterruptedException e) {
        super(message, e);
    }

    public DAOException(String message) {

    }
}
