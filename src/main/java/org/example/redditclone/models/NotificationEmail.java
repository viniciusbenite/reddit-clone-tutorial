package org.example.redditclone.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail {
    
    private String from;
    private String to;
    private String subject;
    private String emailBody;
}
