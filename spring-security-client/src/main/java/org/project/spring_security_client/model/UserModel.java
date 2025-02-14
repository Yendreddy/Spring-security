package org.project.spring_security_client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private String name;
    private String email;
    private String password;
    private String matchingPassword;
}
