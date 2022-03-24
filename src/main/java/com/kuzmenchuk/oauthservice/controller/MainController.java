package com.kuzmenchuk.oauthservice.controller;

import com.kuzmenchuk.oauthservice.exception.UserAlreadyExistException;
import com.kuzmenchuk.oauthservice.repository.entities.AppUser;
import com.kuzmenchuk.oauthservice.service.AppUserService;
import com.kuzmenchuk.oauthservice.util.CustomResponse;
import com.kuzmenchuk.oauthservice.util.RegistrationUserRequest;
import com.kuzmenchuk.oauthservice.util.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/user-account")
public class MainController {
    private final AppUserService appUserService;
    private final HttpServletRequest request;

    private CustomResponse successResponseBody = new CustomResponse();
    private CustomResponse errorResponseBody = new CustomResponse();

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

    @PostMapping("/auth/registration")
    public ResponseEntity<CustomResponse> reg(@RequestBody RegistrationUserRequest user) {
        try {

            AppUser newUser = appUserService.addNewUser(user);
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
    public ResponseEntity<CustomResponse> updateUser(@RequestBody UpdateUserRequest appUser) {
        try {
            AppUser updUser = appUserService.update(appUser);
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

    @GetMapping("/get-all-accounts")
    private ResponseEntity<CustomResponse> getAllUsers() {
        try {
            List<AppUser> appUserList = appUserService.getAll();
            successResponseBody = CustomResponse.successResponse("", appUserList, request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(successResponseBody);
        } catch (Exception exception) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponseBody);
        }
    }

    @GetMapping("/get-account-by-id")
    public ResponseEntity<CustomResponse> getUserById(@RequestParam("id") Long id) {
        try {
            AppUser user = appUserService.getAccountById(id);
            successResponseBody = CustomResponse.successResponse("", user, request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(successResponseBody);
        } catch (UsernameNotFoundException e) {
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
