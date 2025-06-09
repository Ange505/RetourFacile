package com.RetourFacile.Exceptions;

public class FileUploadException extends RuntimeException {

    // Constructeur par défaut
    public FileUploadException() {
        super();
    }

    // Constructeur avec un message d'erreur personnalisé
    public FileUploadException(String message) {
        super(message);
    }

    // Constructeur avec un message d'erreur et une cause
    public FileUploadException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructeur avec une cause
    public FileUploadException(Throwable cause) {
        super(cause);
    }
}
