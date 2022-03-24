package com.kuzmenchuk.oauthservice.util.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteUserRequest {
    private Long authUserID;
    private Long[] deleteIDs;
}
