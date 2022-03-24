package com.kuzmenchuk.oauthservice.util.requests;

import com.kuzmenchuk.oauthservice.repository.entities.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UpdateUserRequest {
    private String username;
    private String password;
    private Set<Role> roles;
    private boolean active;
}
