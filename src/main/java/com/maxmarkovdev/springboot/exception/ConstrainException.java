package com.maxmarkovdev.springboot.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ConstrainException extends RuntimeException {
    private static final long serialVersionUID = 5723952907135446546L;

    public ConstrainException(String message) {
        super(message);
    }
}
