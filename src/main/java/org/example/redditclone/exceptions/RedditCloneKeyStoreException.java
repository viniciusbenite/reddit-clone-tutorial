package org.example.redditclone.exceptions;

public class RedditCloneKeyStoreException extends RuntimeException {

    /**
     * Exception for errors while sending activation emails.
     */
    private static final long serialVersionUID = 1L;

    public RedditCloneKeyStoreException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public RedditCloneKeyStoreException(String exMessage) {
        super(exMessage);
    }
}
