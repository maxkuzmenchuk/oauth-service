package com.kuzmenchuk.oauthservice.util;

import com.kuzmenchuk.oauthservice.repository.entities.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdateUserRequest {
    private Long id;
    private String username;
    private String password;
    private Set<Role> roles;
    private boolean active;
}
