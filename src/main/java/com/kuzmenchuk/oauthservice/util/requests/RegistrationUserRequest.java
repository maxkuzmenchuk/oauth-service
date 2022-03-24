package com.kuzmenchuk.oauthservice.util.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationUserRequest {
    String username;
    String password;
}
