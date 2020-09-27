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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.example.redditclone.static_vars.Paths.*;

import java.util.List;

import org.example.redditclone.data_transfer_objects.SubRedditData;
import org.example.redditclone.services.SubRedditService;

@RestController
@RequestMapping(value = SUBREDDIT_URL)
@AllArgsConstructor
@Slf4j
public class SubredditController {

    @Autowired
    SubRedditService subRedditService;

    /**
     * Secured end point to create a new subreddit.
     * @param subRedditData
     */
    @ApiOperation("Create a new subreddit")
    @PostMapping
    public ResponseEntity<SubRedditData> createSubReddit(@RequestBody SubRedditData subRedditData) {
        log.info(String.format("SubReddit %s created", subRedditData.getName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(subRedditService.save(subRedditData));
    }
    
    /**
     * 
     * @return all subreddits
     */
    @ApiOperation("Retrive info about all subreddits")
    @GetMapping
    public ResponseEntity<List<SubRedditData>> getSubReddits() {
        log.info("List of subreddits retrieved");
        return ResponseEntity.status(HttpStatus.OK).body(subRedditService.getAll());
    }

    /**
     * 
     * @param Subreddit id
     * @return Single subreddit
     */
    @ApiOperation("Retrive info about all subreddits")
    @GetMapping(path = "{id}")
    public ResponseEntity<SubRedditData> getReddit(@PathVariable long id) {
        log.info("Single subreddit retrieved");
        return ResponseEntity.status(HttpStatus.OK).body(subRedditService.getSingleSubReddit(id));
    }
}
