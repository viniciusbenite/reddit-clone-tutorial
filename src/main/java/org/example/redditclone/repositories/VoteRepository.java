package org.example.redditclone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.example.redditclone.models.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>{
    
}
