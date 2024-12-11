package org.shiwani.technicalquize2.pojo;

import lombok.Data;

@Data
public class UserApiKey {
    private String email;
    private String role;
    private String apiKey;
}

