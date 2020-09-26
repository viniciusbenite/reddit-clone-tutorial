package org.example.redditclone.services;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import org.example.redditclone.data_transfer_objects.UserSignup;
import org.example.redditclone.models.NotificationEmail;
import org.example.redditclone.models.User;
import org.example.redditclone.models.VerificationToken;
import org.example.redditclone.repositories.UserRepository;
import org.example.redditclone.repositories.VerificationTokenRepository;
import static org.example.redditclone.static_vars.Paths.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
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

    @Transactional
    public void signUp(UserSignup userSignup) {
        /**
         * This method handles user registration.
         * Store his name, email, encrypted password and registration time.
         * Than creates a token for email user validation and send it to the user
         */
        User user = new User();
        user.setUsername(userSignup.getUserName());
        user.setEmail(userSignup.getEmail());
        user.setPassword(passwordEncoder.encode(userSignup.getPassword()));
        user.setCreatedOn(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);
        String token = verificationToken(user);
        emailService.sendEmail(new NotificationEmail("Reddit Close", user.getEmail(), EMAIL_SUBJECT, (EMAIL_BODY + token)));
    }

    private String verificationToken(User user) {
        /**
         * Generate an user token upon registration for user activation propouses.
         */
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);

        return token;
    }
    
}
