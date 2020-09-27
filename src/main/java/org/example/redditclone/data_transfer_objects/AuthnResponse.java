package org.example.redditclone.data_transfer_objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthnResponse {
    /**
     * DTO class for authentication response.
     */
    
    private String token;
    private String userName;
}
