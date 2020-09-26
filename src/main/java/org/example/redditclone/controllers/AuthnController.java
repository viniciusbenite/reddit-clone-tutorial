package org.example.redditclone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import static org.example.redditclone.static_vars.Paths.*;

import org.example.redditclone.data_transfer_objects.AuthnResponse;
import org.example.redditclone.data_transfer_objects.UserSignIn;
import org.example.redditclone.data_transfer_objects.UserSignup;
import org.example.redditclone.services.AuthnService;;

@Api(value = AUTHN_URL, description = "Handles user registration, e-mail validation, login, logout and refresh tokens")
@RestController
@RequestMapping(value = AUTHN_URL)
public class AuthnController {

    @Autowired
    AuthnService authnService;
    
    @CrossOrigin(origins="*")
    @ApiOperation("Register a new user")
    @PostMapping(value = SIGNUP_URL)
    public ResponseEntity<String> signup(@RequestBody UserSignup userSignup) {
        authnService.signUp(userSignup);
        return new ResponseEntity<String>(String.format("User %s registration OK", userSignup.getUserName()), HttpStatus.OK);
    }

    @ApiOperation("Validate user with token")
    @GetMapping(value = TOKEN_VERIFICATION_URL + "{token}")
    public ResponseEntity<String> validateUser(@PathVariable String token) {
        authnService.verifyAccount(token);
        return new ResponseEntity<String>("User verification successfull", HttpStatus.OK);
    }

    @ApiOperation("Authenticate the user")
    @PostMapping(value = SIGNIN_URL)
    public AuthnResponse authnUser(@RequestBody UserSignIn userSignIn) {
        return authnService.signIn(userSignIn);
    }
}
