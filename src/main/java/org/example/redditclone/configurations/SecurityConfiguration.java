package org.example.redditclone.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.example.redditclone.static_vars.Paths.AUTHN_URL;;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    // CSRF protection
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
            .authorizeRequests()
            .antMatchers(AUTHN_URL)
            .permitAll()
            .anyRequest()
            .authenticated();
    }

    // Password encoder interface from Spring
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
