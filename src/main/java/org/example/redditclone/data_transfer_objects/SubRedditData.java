package org.example.redditclone.data_transfer_objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubRedditData {

    /**
     * * DTO class for Subreddits.
     */
    private long id;
    private String name;
    private String description;
    private int numberOfPosts;
    
}
