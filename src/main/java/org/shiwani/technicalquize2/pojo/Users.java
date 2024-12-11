package org.shiwani.technicalquize2.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Schema(description = "User Details")
public class Users {
    @Id
    private String email;
    private String name;
    private String password;
    @Schema(hidden = true)
    private String salt;
    @Schema(hidden = true)
    private String role;
}
