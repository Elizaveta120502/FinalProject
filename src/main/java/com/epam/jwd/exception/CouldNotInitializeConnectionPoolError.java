package com.epam.jwd.exception;



public class CouldNotInitializeConnectionPoolError extends Error {

    private static final long serialVersionUID = -6767097587635314171L;

    public CouldNotInitializeConnectionPoolError(String message, Throwable cause) {
        super(message, cause);
    }
}



