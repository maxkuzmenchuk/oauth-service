package com.kuzmenchuk.oauthservice.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ExternalServiceResponse implements Serializable {
    private String message;
    private Object entity;
}
