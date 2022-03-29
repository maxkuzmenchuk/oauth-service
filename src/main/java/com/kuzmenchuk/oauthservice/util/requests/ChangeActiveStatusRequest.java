package com.kuzmenchuk.oauthservice.util.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeActiveStatusRequest {
    private Long authUserID;
    private Long accountID;
    private boolean status;
}
