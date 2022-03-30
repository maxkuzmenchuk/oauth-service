package com.kuzmenchuk.oauthservice.util.requests;

import com.kuzmenchuk.oauthservice.repository.entities.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class AddNewUserByAdminRequest {
    private String username;
    private String password;
    private Set<Role> roles;
    private boolean active;
}
