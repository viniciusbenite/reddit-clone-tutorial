package org.example.redditclone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import static org.example.redditclone.static_vars.Paths.*;

import org.example.redditclone.data_transfer_objects.UserSignup;
import org.example.redditclone.services.AuthnService;;

@Api(value = "/api/sigup", description = "Handles user registration, e-mail validation, login, logout and refresh tokens")
@RestController
@RequestMapping(value = AUTHN_URL)
public class AuthnController {

    @Autowired
    AuthnService authnService;
    
    @ApiOperation("Register a new user")
    @PostMapping(value = SIGNUP_URL)
    public ResponseEntity<String> signup(@RequestBody UserSignup userSignup) {
        authnService.signUp(userSignup);
        return new ResponseEntity<String>("User registration OK", HttpStatus.OK);
    }
}
