package org.example.redditclone.exceptions;

public class EmailException extends RuntimeException{

    /**
     * Exception for erros while sending activation emails.
     */
    private static final long serialVersionUID = 1L;

    public EmailException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public EmailException(String exMessage) {
        super(exMessage);
    }
    
}
