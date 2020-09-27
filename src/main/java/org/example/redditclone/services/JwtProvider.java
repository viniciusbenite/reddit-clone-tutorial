package org.example.redditclone.services;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.example.redditclone.exceptions.RedditCloneKeyStoreException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

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

    public String generateToken(Authentication authentication) {
        /**
         * Generate an authn token for user signin User is not our model. 
         * User is from Spring core userdetails!
         */
        User principal = (User) authentication.getPrincipal();
        return Jwts.builder().setSubject(principal.getUsername()).signWith(getPrivateKey()).compact();
    }

    private PrivateKey getPrivateKey() {
        /**
         * Asymetric keys.. Get client private key
         */
        try {
            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            throw new RedditCloneKeyStoreException("Something went wrog ... too bad!");
        }
    }
}
