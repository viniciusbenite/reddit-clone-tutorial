package org.example.redditclone.static_vars;

public final class Paths {

    public static final String AUTHN_URL = "/api/authn";
    public static final String SIGNUP_URL = "/signup/";
    public static final String SIGNIN_URL = "/signin/";
    public static final String TOKEN_VERIFICATION_URL = "/account-verification/";

    public static final String SUBREDDIT_URL = "/api/subreddit/";

    public static final String VALIDATION_URL = "http://localhost:8080/api/authn/account-verification/";
    public static final String EMAIL_BODY = "Click the link to validade your account:" + VALIDATION_URL;
    public static final String EMAIL_SUBJECT = "Please validate your account at Reddit Clone";
    
}
