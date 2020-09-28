package org.example.redditclone.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.example.redditclone.data_transfer_objects.AuthnResponse;
import org.example.redditclone.data_transfer_objects.UserSignIn;
import org.example.redditclone.data_transfer_objects.UserSignup;
import org.example.redditclone.exceptions.UserNotFoundException;
import org.example.redditclone.exceptions.VerificationTokenException;
import org.example.redditclone.models.NotificationEmail;
import org.example.redditclone.models.User;
import org.example.redditclone.models.VerificationToken;
import org.example.redditclone.repositories.UserRepository;
import org.example.redditclone.repositories.VerificationTokenRepository;

import static org.example.redditclone.static_vars.Paths.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AuthnService {

    // TODO: Field injection .... refactor this
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VerificationTokenRepository verificationTokenRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    /**
     * This method handles user registration. Store his name, email, encrypted
     * password and registration time. Than creates a token for email user
     * validation and send it to the user
     * @param userSignup
     */
    @Transactional
    public void signUp(UserSignup userSignup) {
        User user = new User();
        user.setUsername(userSignup.getUserName());
        user.setEmail(userSignup.getEmail());
        user.setPassword(passwordEncoder.encode(userSignup.getPassword()));
        user.setCreatedOn(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);
        String token = verificationToken(user);
        emailService
                .sendEmail(new NotificationEmail("Reddit Close", user.getEmail(), EMAIL_SUBJECT, (EMAIL_BODY + token)));
    }

    /**
     * Generate an user token upon registration for user activation propouses.
     * 
     * @param User
     * @return verification token as a string
     */
    private String verificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);

        return token;
    }

    /**
     * User verification by email token
     * 
     * @param token
     */
    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new VerificationTokenException("Invalid user token"));
        // If token is valid ...
        enableUser(verificationToken.get());
    }

    /**
     * Search for the user in database and enable it
     * 
     * @param verificationToken
     */
    private void enableUser(VerificationToken verificationToken) {
        Long userId = verificationToken.getUser().getUserId();
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(
                String.format("User %s not found", verificationToken.getUser().getUsername())));
        user.setEnabled(true);
        userRepository.save(user);
    }

    /**
     * Handles the user signin
     * 
     * @param userSignIn DTO
     * @return Authentication response object
     */
    public AuthnResponse signIn(UserSignIn userSignIn) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userSignIn.getUserName(), userSignIn.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new AuthnResponse(jwtProvider.generateToken(authentication), userSignIn.getUserName());
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }
}
