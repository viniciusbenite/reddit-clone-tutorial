package org.example.redditclone.data_transfer_objects;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {
    
    private long commentId;
    private long postId;
    private Instant createdOn;
    private String body;
    private String user;
}
