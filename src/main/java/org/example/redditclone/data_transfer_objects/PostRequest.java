package org.example.redditclone.data_transfer_objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {

    private Long postId;
    private String subredditName;
    private String postName;
    private String url;
    private String description;

}
