package org.example.redditclone.exceptions;

public class SubredditNotFoundException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public SubredditNotFoundException(String err, Exception e) {
        super(err, e);
    }
    
    public SubredditNotFoundException(String err) {
        super(err);
    }

}
