package com.javanauta.usuario.infrastructure.exceptions;

public class ResourceNotValid extends RuntimeException {
    public ResourceNotValid(String message) {
        super(message);
    }
    public ResourceNotValid(String message, Throwable throwable) {
        super(message, throwable);
    }
}
