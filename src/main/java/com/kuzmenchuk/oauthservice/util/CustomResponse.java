package com.kuzmenchuk.oauthservice.util;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
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


    public static CustomResponse successResponse(String message, String bodyField, Object bodyValue, String requestUrl) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("message", message);
        bodyMap.put(bodyField, bodyValue);
        return CustomResponse.builder()
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .success(1)
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .requestUrl(requestUrl)
                .body(bodyMap)
                .error(Collections.emptyList())
                .build();
    }

    public static CustomResponse errorResponse(HttpStatus status, String error, String requestUrl) {
        return CustomResponse.builder()
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .success(0)
                .statusCode(status.value())
                .status(status)
                .requestUrl(requestUrl)
                .body(null)
                .error(Collections.singletonList(error))
                .build();
    }

    public static CustomResponse forbiddenResponse(String error, String requestUrl) {
        return CustomResponse.builder()
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .success(0)
                .statusCode(HttpStatus.FORBIDDEN.value())
                .status(HttpStatus.FORBIDDEN)
                .requestUrl(requestUrl)
                .body(null)
                .error(Collections.singletonList(error))
                .build();
    }

    public static CustomResponse validationErrorResponse(List<String> errors, String requestUrl) {
        return CustomResponse.builder()
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .success(0)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST)
                .requestUrl(requestUrl)
                .body(null)
                .error(errors)
                .build();
    }
}
