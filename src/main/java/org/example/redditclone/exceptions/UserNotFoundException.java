package org.example.redditclone.exceptions;

public class UserNotFoundException extends RuntimeException {

    /**
     * Exception for user not found error
     */
    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String message, Exception exception) {
        super(message, exception);
    }

    public UserNotFoundException(String message) {
        super(message);
    }
    
}
