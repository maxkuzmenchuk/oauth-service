package com.kuzmenchuk.oauthservice.repository.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    private Long addressID;
    private String country;
    private String region;
    private String city;
    private String street;
    private String building;
    private int flat;
    private int postCode;
}
