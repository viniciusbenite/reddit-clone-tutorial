package org.example.redditclone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.example.redditclone.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{
    
}
