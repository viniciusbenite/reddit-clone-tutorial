package org.example.redditclone.mappers;

import org.example.redditclone.data_transfer_objects.PostRequest;
import org.example.redditclone.data_transfer_objects.PostResponse;
import org.example.redditclone.models.Post;
import org.example.redditclone.models.SubReddit;
import org.example.redditclone.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    /**
     * Create a new post from post request object.
     * @param postRequest
     * @param subReddit
     * @param user
     * @return new Post.
     */
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    Post map(PostRequest postRequest, SubReddit subReddit, User user);

    /**
     * Mapping from post to DTO
     * @param post object
     * @return post response object
     */
    @Mapping(target = "postName", source = "postName")
    @Mapping(target = "url", source = "url")
    @Mapping(target = "id", source = "postId")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "userName", source = "user.username")
    PostResponse mapToData(Post post);
}
