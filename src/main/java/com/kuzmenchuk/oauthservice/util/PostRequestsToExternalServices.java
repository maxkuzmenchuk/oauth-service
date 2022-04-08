package com.kuzmenchuk.oauthservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuzmenchuk.oauthservice.exception.ExternalServiceException;
import com.kuzmenchuk.oauthservice.repository.entities.PersonalData;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PostRequestsToExternalServices {
    private final RestTemplate restTemplate = new RestTemplate();

    public PersonalData addNewPersonalData(Long createdAccountID, PersonalData personalData, String authorization) {
        try {
            PersonalData createdPersonalData = new PersonalData();
            ObjectMapper mapper = new ObjectMapper();

            String url = "http://localhost:8080/api/personal-data/add-new";
            HttpHeaders headers = new HttpHeaders();

            headers.set("Content-Type", "application/json");
            headers.set("Access-Control-Allow-Origin", "*");
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.set("Authorization", authorization);

            Map<String, Object> data = new HashMap<>();

            data.put("accountID", createdAccountID);
            data.put("name", personalData.getName());
            data.put("surname", personalData.getSurname());
            data.put("address", personalData.getAddress());


            HttpEntity<Map<String, Object>> getRequest = new HttpEntity<>(data, headers);

            ResponseEntity<ExternalServiceResponse> response = this.restTemplate
                    .postForEntity(url, getRequest, ExternalServiceResponse.class);

            if (response.getStatusCodeValue() == 200) {
                Object entity = Objects.requireNonNull(response.getBody()).getEntity();
                createdPersonalData = mapper.convertValue(entity, PersonalData.class);
            }

            return createdPersonalData;
        } catch (NullPointerException | HttpClientErrorException exception) {
            throw new ExternalServiceException(exception.getMessage());
        }
    }
}
