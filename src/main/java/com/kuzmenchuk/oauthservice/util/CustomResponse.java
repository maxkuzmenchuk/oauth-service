package com.kuzmenchuk.oauthservice.util;

import java.util.HashMap;
import java.util.Map;

public class CustomResponse {
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
