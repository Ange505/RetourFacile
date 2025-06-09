package com.RetourFacile.Exceptions;

public class InvalidRequestException extends RuntimeException {

    // Constructeur par défaut
    public InvalidRequestException() {
        super();
    }

    // Constructeur avec un message d'erreur personnalisé
    public InvalidRequestException(String message) {
        super(message);
    }

    // Constructeur avec un message d'erreur et une cause
    public InvalidRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructeur avec une cause
    public InvalidRequestException(Throwable cause) {
        super(cause);
    }
}
