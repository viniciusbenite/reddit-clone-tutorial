package org.example.redditclone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.example.redditclone.models.Comment;
import org.example.redditclone.models.Post;
import org.example.redditclone.models.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

    List<Comment> findByUser(User user);
    List<Comment> findByPost(Post post);
    
}
