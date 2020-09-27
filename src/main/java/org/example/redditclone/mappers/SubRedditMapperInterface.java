package org.example.redditclone.mappers;

import java.util.List;

import org.example.redditclone.data_transfer_objects.SubRedditData;
import org.example.redditclone.models.Post;
import org.example.redditclone.models.SubReddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;

/**
 * this interface is a Mapstruct Mapper and Spring should identify 
 * it as a component and should be able to inject it into other components
 */
@MapperConfig(componentModel = "spring")
public interface SubRedditMapperInterface {

    /**
     * we are mapping from List<Posts> to an Integer, this kind of mapping 
     * is not straight forward and we need to write our logic. 
     * We can do that by using the expression field and pass the method 
     * definition for mapPosts() which returns an Integer
     * @param subReddit
     * @return sub reddit DTO
     */
    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubRedditData mapSubRedditToData(SubReddit subReddit);
    
    default int mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    SubReddit mapDataToSubReddit(SubRedditData subRedditData);
}
