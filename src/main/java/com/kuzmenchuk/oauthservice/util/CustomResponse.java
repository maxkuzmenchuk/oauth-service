package com.kuzmenchuk.oauthservice.util;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kuzmenchuk
 * <p>
 * Custom body object for {@link org.springframework.http.ResponseEntity}
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomResponse {
    private Timestamp timestamp;
    private int success;
    private int statusCode;
    private HttpStatus status;
    private String requestUrl;
    private Object body;
    private List<String> error;


    public static Map<String, Object> successResponse(String message, String bodyField, Object bodyValue) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("message", message);
        bodyMap.put(bodyField, bodyValue);

        return bodyMap;
    }

    public static Map<String, Object> errorResponse(String message, Object bodyValue) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("message", message);
        bodyMap.put("error_message", bodyValue);

        return bodyMap;
    }
}
