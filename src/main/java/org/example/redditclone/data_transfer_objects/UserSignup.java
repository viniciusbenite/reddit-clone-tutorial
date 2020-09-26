package org.example.redditclone.data_transfer_objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignup {
    
    private String userName;
    private String email;
    private String password;
}
