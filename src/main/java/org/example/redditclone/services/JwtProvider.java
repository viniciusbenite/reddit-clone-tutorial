package org.example.redditclone.services;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.example.redditclone.exceptions.RedditCloneKeyStoreException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        // TODO: REFACTOR THIS CATCHS
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
            keyStore.load(resourceAsStream, "secret".toCharArray());
        } catch (KeyStoreException e) {
            throw new RedditCloneKeyStoreException("Oops. Exception ocurred while retrieving keystore.");
        } catch (CertificateException e) {
            throw new RedditCloneKeyStoreException("Oops. Exception ocurred while loading certificate.");
        } catch (NoSuchAlgorithmException e) {
            throw new RedditCloneKeyStoreException("Oops. Something went wrong.No such algorithm");
        } catch (IOException e) {
            throw new RedditCloneKeyStoreException("Oops. resource not found.");
        }
    }

    /**
     * Import static io.jsonwebtoken.Jwts.parser; Generate an authn token for user
     * signin User is not our model. User is from Spring core userdetails!
     * 
     * @param Authentication object
     * @return
     */
    public String generateToken(Authentication authentication) {
        /**
         * Import static io.jsonwebtoken.Jwts.parser; Generate an authn token for user
         * signin User is not our model. User is from Spring core userdetails!
         */
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder().setSubject(principal.getUsername()).signWith(getPrivateKey()).compact();
    }

    /**
     * Asymetric keys.. Get client private key
     * 
     * @return PrivateKey object
     */
    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new RedditCloneKeyStoreException("Something went wrog ... too bad!");
        }
    }

    /**
     * This method uses the JwtParser class to validate our JWT. Validate the token
     * using the public key
     * 
     * @param String jwt
     * @return boolean
     */
    public boolean validateToken(String jwt) {
        parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        return true;
    }

    /**
     * Retrieve the username from the token by calling the getUsernameFromJWT()
     * method.
     * @param String token
     * @return String subject
     */
    public String getUserNameFromJwt(String token) {
        return parser().setSigningKey(getPublicKey()).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Get public key
     * 
     * @return Publick key (certificate) object
     */
    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("springblog").getPublicKey();
        } catch (KeyStoreException e) {
            throw new RedditCloneKeyStoreException("Something went wrog while retrieving the public key... too bad!");
        }
    }
}
