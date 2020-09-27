package org.example.redditclone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.example.redditclone.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    
}