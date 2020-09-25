package org.example.redditclone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.example.redditclone.models.SubReddit;

@Repository
public interface SubRedditRepository extends JpaRepository<SubReddit, Long>{
    
}
