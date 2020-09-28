package org.example.redditclone.exceptions;

public class SubRedditNotFoundException extends RuntimeException{

    /**
     * Exception for Subreddit not found.
     */
    private static final long serialVersionUID = 1L;

    public SubRedditNotFoundException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SubRedditNotFoundException(String exMessage) {
        super(exMessage);
    }
    
}
