package com.kuzmenchuk.oauthservice.util.requests;

import com.kuzmenchuk.oauthservice.repository.entities.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ChangeRoleRequest {
    private Long userID;
    private Set<Role> roles;
}
