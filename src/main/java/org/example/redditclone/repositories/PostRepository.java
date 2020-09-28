package org.example.redditclone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.example.redditclone.models.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

    Optional<Post> findByUser(String user);
    List<Post> findAllBySubreddit(String name);
    
}