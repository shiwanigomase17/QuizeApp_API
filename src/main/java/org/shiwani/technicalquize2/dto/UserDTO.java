package org.shiwani.technicalquize2.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDTO {
    private String name;
    private String email;
    private String oldPassword;
    private String newPassword;
}
