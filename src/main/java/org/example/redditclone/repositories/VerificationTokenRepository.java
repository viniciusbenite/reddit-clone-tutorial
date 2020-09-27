package org.example.redditclone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.example.redditclone.models.VerificationToken;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{

    /**
     * Verify if the user token is in our database
     * @param token
     * @return isPresent() -> true -> token 
     */
	Optional<VerificationToken> findByToken(String token);
    
}
