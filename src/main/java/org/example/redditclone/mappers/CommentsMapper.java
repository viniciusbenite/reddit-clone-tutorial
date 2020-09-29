package org.example.redditclone.mappers;

import org.example.redditclone.data_transfer_objects.CommentsDto;
import org.example.redditclone.models.Comment;
import org.example.redditclone.models.Post;
import org.example.redditclone.models.User;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentsMapper {

    public static final String JAVA_JAVA_TIME_INSTANT_NOW = "java(java.time.Instant.now())";

    /**
     * Map CommentsDto object into Comment object
     * @param commentsDto
     * @param user
     * @param post
     * @return Comment object
     */
    @Mapping(target = "post.postId", ignore = true)
    @Mapping(target = "body", source = "commentsDto.body")
    @Mapping(target = "createdDate", expression = JAVA_JAVA_TIME_INSTANT_NOW)
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment map(CommentsDto commentsDto, User user, Post post);
    
    /**
     * Map Comment object into CommentsDto object
     * @param comment
     * @return comment dto object
     */
    @Mapping(target = "commentId", source = "commentId")
    @Mapping(target = "body", source = "body")
    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "user", expression = "java(comment.getUser().getUsername())")
    @Mapping(target = "createdDate", expression = JAVA_JAVA_TIME_INSTANT_NOW)
    CommentsDto mapToDto(Comment comment); 
}
