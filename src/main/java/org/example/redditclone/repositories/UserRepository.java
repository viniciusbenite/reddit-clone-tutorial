package org.example.redditclone.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.example.redditclone.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
}
