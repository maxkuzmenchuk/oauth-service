package com.kuzmenchuk.oauthservice.util.requests;

import com.kuzmenchuk.oauthservice.repository.entities.AppUser;
import com.kuzmenchuk.oauthservice.repository.entities.PersonalData;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AddCredentialWithPersonalDataRequest {
    private AppUser credentials;
    private PersonalData personalData;
}
