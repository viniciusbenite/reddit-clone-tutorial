package org.example.redditclone.exceptions;

public class VerificationTokenException extends RuntimeException {

    /**
     * Exception for token not found error
     */
    private static final long serialVersionUID = 1L;

    public VerificationTokenException(String message, Exception exception) {
        super(message, exception);
    }

    public VerificationTokenException(String message) {
        super(message);
    }
    
}
