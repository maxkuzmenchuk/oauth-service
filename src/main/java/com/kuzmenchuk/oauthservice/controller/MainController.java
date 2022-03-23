package com.kuzmenchuk.oauthservice.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.kuzmenchuk.oauthservice.exception.UserAlreadyExistException;
import com.kuzmenchuk.oauthservice.repository.entities.AppUser;
import com.kuzmenchuk.oauthservice.service.AppUserService;
import com.kuzmenchuk.oauthservice.util.CustomResponse;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class MainController {
    private final AppUserService appUserService;
    private final HttpServletRequest request;

    private CustomResponse successResponseBody = CustomResponse.builder().build();
    private CustomResponse errorResponseBody = CustomResponse.builder().build();

    @Autowired
    public MainController(AppUserService appUserService, HttpServletRequest request) {
        this.appUserService = appUserService;
        this.request = request;
    }

    @GetMapping(value = "/")
    public String index() {
        return "Hello world";
    }

    @GetMapping(value = "/private")
    public String privateArea() {
        return "Private area";
    }

    @PostMapping("/registration")
    public ResponseEntity<CustomResponse> reg(@RequestBody AppUser appUser) {
        try {

            AppUser newUser = appUserService.addNewUser(appUser);
            successResponseBody = CustomResponse.successResponse("New user registered successfully!", newUser, request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(successResponseBody);
        } catch (UserAlreadyExistException e) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.CONFLICT, e.getMessage(), request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(errorResponseBody);
        } catch (Exception exception) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponseBody);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<CustomResponse> updateUser(@RequestBody AppUser appUser) {
        try {

            AppUser updUser = appUserService.save(appUser);
            successResponseBody = CustomResponse.successResponse("User updated successfully!", updUser, request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(successResponseBody);
        } catch (UserAlreadyExistException e) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.CONFLICT, e.getMessage(), request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(errorResponseBody);
        } catch (Exception exception) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponseBody);
        }
    }
}
