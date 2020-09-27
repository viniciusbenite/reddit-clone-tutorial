package org.example.redditclone.data_transfer_objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignIn {

    /**
     * * DTO class for user signin response.
     */
    private String userName;
    private String userEmail;
    private String password;
    
}
