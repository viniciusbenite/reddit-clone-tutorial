package org.example.redditclone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

import static org.example.redditclone.static_vars.Paths.*;
import java.util.List;

import org.example.redditclone.data_transfer_objects.PostRequest;
import org.example.redditclone.data_transfer_objects.PostResponse;
import org.example.redditclone.services.PostService;

@RestController
@RequestMapping(value = POST_URL)
public class PostController {

    @Autowired
    PostService postService;

    @ApiOperation("Create new post")
    @PostMapping
    public ResponseEntity<PostRequest> createNewPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation("Retrive information about all posts")
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @ApiOperation("Retrive information about a single post by ID")
    @GetMapping("{id}")
    public ResponseEntity<PostResponse> getSinglePost(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getSinglePost(id));
    }

    @ApiOperation("Retrive information about all posts in a subreddit")
    @GetMapping("by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getAllPostsBySubReddit(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsBySubReddit(id));
    }

    @ApiOperation("Retrive information about all posts made by an user")
    @GetMapping("by-users/{user}")
    public ResponseEntity<List<PostResponse>> getAllPostsByUsername(@PathVariable String user) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUser(user));
    }
}
