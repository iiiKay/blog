package com.kay.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TokenIsNullException extends RuntimeException {

    public TokenIsNullException(String message) {
        super(message);
    }
}
