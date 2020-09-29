package org.example.redditclone.controllers;

import static org.example.redditclone.static_vars.Paths.*;

import java.util.List;

import org.example.redditclone.data_transfer_objects.CommentsDto;
import org.example.redditclone.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = COMMENTS_URL)
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * Create new comment
     * 
     * @param cDto
     * @return Response Entity http.CREATED
     */
    @ApiOperation(value = "Create new comment")
    @PostMapping
    public ResponseEntity<Void> createNewPost(@RequestBody CommentsDto cDto) {
        commentService.save(cDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Get all comments made by user
     * 
     * @param userName
     * @return Response Entitiy http.OK -> all comments by user
     */
    @ApiOperation(value = "Get all comments by user")
    @GetMapping(value = "by-user/{userName}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsByUser(@PathVariable String userName) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsByUser(userName));
    }

    /**
     * Get all comments on post
     * 
     * @param postId
     * @return Response Entitiy http.OK -> all comments in that post
     */
    @ApiOperation(value = "Get all comments on post")
    @GetMapping(value = "by-post/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsByPost(@PathVariable long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllCommentsByPost(postId));
    }
}
