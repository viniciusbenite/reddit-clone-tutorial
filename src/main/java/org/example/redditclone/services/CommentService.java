package org.example.redditclone.services;

import java.util.List;

import org.example.redditclone.data_transfer_objects.CommentsDto;
import org.example.redditclone.exceptions.PostNotFoundException;
import org.example.redditclone.exceptions.UserNotFoundException;
import org.example.redditclone.mappers.CommentsMapper;
import org.example.redditclone.models.Comment;
import org.example.redditclone.models.NotificationEmail;
import org.example.redditclone.models.Post;
import org.example.redditclone.models.User;
import org.example.redditclone.repositories.CommentRepository;
import org.example.redditclone.repositories.PostRepository;
import org.example.redditclone.repositories.UserRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentsMapper commentsMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthnService authnService;
    private final MailBuilder mailBuilder;
    private final EmailService mService;

    public static final String POST_URL = "";

    /**
     * Save new comment
     * 
     * @param commentsDto
     */
    public void save(final CommentsDto commentsDto) {
        final Post post = postRepository.findById(commentsDto.getPostId()).orElseThrow(
                () -> new PostNotFoundException(String.format("Post % not found", commentsDto.getPostId())));
        final Comment comment = commentsMapper.map(commentsDto, authnService.getCurrentUser(), post);
        commentRepository.save(comment);
        notifyUserNewComment(post);
    }

    /**
     * Build email notification for new comment on user post
     * 
     * @param post
     */
    private void notifyUserNewComment(final Post post) {
        final String message = mailBuilder
                .build(authnService.getCurrentUser() + " posted a comment on your post." + POST_URL);
        sendCommentNotification(message, post.getUser());
    }

    /**
     * Send the user the notification
     * 
     * @param message
     * @param user
     */
    private void sendCommentNotification(final String message, final User user) {
        mService.sendEmail(
                new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    /**
     * Retrieve all comments by user
     * 
     * @param userName
     * @return
     */
    public List<CommentsDto> getAllCommentsByUser(final String userName) {
        final User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UserNotFoundException(String.format("User %s not found", userName)));
        return commentRepository.findByUser(user).stream().map(commentsMapper::mapToDto).collect(toList());
    }

    /**
     * Retrieve all comments by post
     * 
     * @param id
     * @return
     */
    public List<CommentsDto> getAllCommentsByPost(final long id) {
        final Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(String.format("Post %s not found", id)));
        return commentRepository.findByPost(post).stream().map(commentsMapper::mapToDto).collect(toList());
    }

}
