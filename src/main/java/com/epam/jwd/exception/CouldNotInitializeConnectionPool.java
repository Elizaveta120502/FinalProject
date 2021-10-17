package com.epam.jwd.exception;

import java.io.Serial;

public class CouldNotInitializeConnectionPool extends Error {
    @Serial
    private static final long serialVersionUID = 8553014030613840383L;

    public CouldNotInitializeConnectionPool(String message, Throwable cause) {
        super(message, cause);
    }
}



