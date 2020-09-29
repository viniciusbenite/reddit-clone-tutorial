package org.example.redditclone.services;

import java.util.List;

import org.example.redditclone.data_transfer_objects.PostRequest;
import org.example.redditclone.data_transfer_objects.PostResponse;
import org.example.redditclone.exceptions.PostNotFoundException;
import org.example.redditclone.exceptions.SubRedditNotFoundException;
import org.example.redditclone.exceptions.UserNotFoundException;
import org.example.redditclone.mappers.PostMapper;
import org.example.redditclone.models.Post;
import org.example.redditclone.models.SubReddit;
import org.example.redditclone.models.User;
import org.example.redditclone.repositories.PostRepository;
import org.example.redditclone.repositories.SubRedditRepository;
import org.example.redditclone.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
@AllArgsConstructor
public class PostService {

    @Autowired
    private SubRedditRepository subRedditRepository;
    @Autowired
    private AuthnService authnService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private UserRepository userRepository;

    /**
     * 
     * @param postRequest
     */
    public void save(PostRequest postRequest) {
        SubReddit subReddit = subRedditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubRedditNotFoundException(
                        String.format("Subreddit %s not found", postRequest.getSubredditName())));
        postRepository.save(postMapper.map(postRequest, subReddit, authnService.getCurrentUser()));
    }

    /**
     * Get single post by post ID
     * 
     * @param id
     * @return PostResponse object
     */
    @Transactional(readOnly = true)
    public PostResponse getSinglePost(Long id) {
        return postMapper.mapToData(postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(String.format("Post %d not found", id))));
    }

    /**
     * Get all posts
     * 
     * @return List of Post response objects
     */
    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream().map(postMapper::mapToData).collect(toList());
    }

    /**
     * Get all posts by user
     * 
     * @return List of PostResponse objects
     */
    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s not found", userName)));
        return postRepository.findByUser(user.getUsername()).stream().map(postMapper::mapToData).collect(toList());
    }

    /**
     * Get all posts from sub reddit
     * 
     * @param id
     * @return List of PostResponse objects
     */
    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubReddit(long id) {
        SubReddit sr = subRedditRepository.findById(id)
                .orElseThrow(() -> new SubRedditNotFoundException(String.format("Subreddit %d not found", id)));
        List<Post> posts = postRepository.findAllBySubreddit(sr.getName());
        return posts.stream().map(postMapper::mapToData).collect(toList());
    }
}
