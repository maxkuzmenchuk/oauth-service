package com.kuzmenchuk.oauthservice.repository.entities;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonalData {
    private Long personalDataID;
    private Long accountID;
    private String name;
    private String surname;
    private Address address;
}
