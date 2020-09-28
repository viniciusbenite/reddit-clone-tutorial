package org.example.redditclone.exceptions;

public class PostNotFoundException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public PostNotFoundException(String msg) {
        super(msg);
    }
    public PostNotFoundException(String msg, Exception e) {
        super(msg, e);
    }
    
}
