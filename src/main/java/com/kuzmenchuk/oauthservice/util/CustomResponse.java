package com.kuzmenchuk.oauthservice.util;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * @author kuzmenchuk
 * <p>
 * Custom body object for {@link org.springframework.http.ResponseEntity}
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class CustomResponse {
    private Timestamp timestamp;
    private int success;
    private int statusCode;
    private HttpStatus status;
    private String requestUrl;
    private String message;
    private Object body;
    private List<String> error;

    /**
     * Create success response body
     * <p>
     *
     * @param body       {@link Object}  to be installed in the response {@code body} field
     * @param requestUrl {@code RequestMapping} value from the controller being called
     * @return {@link CustomResponse} object value for the body in the {@link org.springframework.http.ResponseEntity}
     */
    public static CustomResponse successResponse(String message, Object body, String requestUrl) {
        return CustomResponse.builder()
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .success(1)
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .requestUrl(requestUrl)
                .message(message)
                .body(body)
                .error(Collections.emptyList())
                .build();
    }

    /**
     * Create {@code INTERNAL_SERVER_ERROR} error response body
     * <p>
     *
     * @param error      exception message
     * @param requestUrl {@code RequestMapping} value from the controller being called
     * @return {@link CustomResponse} object value for the body in the {@link org.springframework.http.ResponseEntity}
     */
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

    /**
     * Create {@code FORBIDDEN} error response body
     * <p>
     *
     * @param error      exception messages list
     * @param requestUrl {@code RequestMapping} value from the controller being called
     * @return {@link CustomResponse} object value for the body in the {@link org.springframework.http.ResponseEntity}
     */
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

    /**
     * Create {@code BAD_REQUEST} error response body for validation errors
     *
     * @param errors     exception messages list
     * @param requestUrl request url
     * @return {@link CustomResponse} object value for the body in the {@link org.springframework.http.ResponseEntity}
     */
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
