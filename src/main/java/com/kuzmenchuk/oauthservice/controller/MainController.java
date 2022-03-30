package com.kuzmenchuk.oauthservice.controller;

import com.kuzmenchuk.oauthservice.exception.UserAlreadyExistException;
import com.kuzmenchuk.oauthservice.repository.entities.AppUser;
import com.kuzmenchuk.oauthservice.service.AppUserService;
import com.kuzmenchuk.oauthservice.util.CustomResponse;
import com.kuzmenchuk.oauthservice.util.requests.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/auth/registration")
    public ResponseEntity<CustomResponse> add(@RequestBody RegistrationUserRequest user) {
        try {

            AppUser newUser = appUserService.addNewUser(user);
            successResponseBody = CustomResponse.successResponse("New user registered successfully!", "newUser", newUser, request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(successResponseBody);
        } catch (UserAlreadyExistException e) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponseBody);
        } catch (Exception exception) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponseBody);
        }
    }

    @PostMapping("/add-new-account")
    public ResponseEntity<CustomResponse> addNewByAdmin(@RequestBody AddNewUserByAdminRequest userByAdminRequest) {
        try {
            AppUser newUser = appUserService.addNewUser(userByAdminRequest);
            successResponseBody = CustomResponse.successResponse("User added successfully!", "newUser", newUser, request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(successResponseBody);
        } catch (UserAlreadyExistException e) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponseBody);
        } catch (Exception exception) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponseBody);
        }
    }

    @PostMapping("/update")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<CustomResponse> update(@RequestBody UpdateUserRequest appUser) {
        try {
            AppUser updUser = appUserService.update(appUser.getId(), appUser);
            successResponseBody = CustomResponse.successResponse("User updated successfully!", "updUser", updUser, request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(successResponseBody);
        } catch (UserAlreadyExistException e) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponseBody);
        } catch (Exception exception) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponseBody);
        }
    }

    @DeleteMapping("/delete")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<CustomResponse> delete(@RequestBody DeleteUserRequest deleteUserRequest) {
        try {
            List<Long> deletedIDs = appUserService.deleteUserById(deleteUserRequest);
            successResponseBody = CustomResponse.successResponse("Users are deleted successfully", "IDs", deletedIDs, request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(successResponseBody);
        } catch (DataAccessException e) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.FORBIDDEN, e.getMessage(), request.getRequestURI());
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(errorResponseBody);
        } catch (Exception exception) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponseBody);
        }
    }

    @PutMapping("/change-active-status")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<CustomResponse> changeActiveStatus(@RequestBody ChangeActiveStatusRequest changeActiveStatusRequest) {
        try {
            Long updateUserID = appUserService.changeActiveStatus(changeActiveStatusRequest);
            successResponseBody = CustomResponse.successResponse("Status active changed successfully", "updatedUserID", updateUserID, request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(successResponseBody);
        } catch (DataAccessException e) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.FORBIDDEN, e.getMessage(), request.getRequestURI());
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(errorResponseBody);
        } catch (Exception exception) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponseBody);
        }
    }

    @PutMapping("/change-role")
    @Secured({"ROLE_ADMIN"})
    public ResponseEntity<CustomResponse> changeRole(@RequestBody ChangeRoleRequest changeRoleRequest) {
        try {
            Map<String, Object> result = appUserService.changeRoleById(changeRoleRequest);
            successResponseBody = CustomResponse.successResponse("Users roles are changed successfully", "roles", result, request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(successResponseBody);
        } catch (DataAccessException e) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.FORBIDDEN, e.getMessage(), request.getRequestURI());
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(errorResponseBody);
        } catch (Exception exception) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponseBody);
        }
    }

    @GetMapping("/get-all-accounts")
    public ResponseEntity<CustomResponse> getAllUsers() {
        try {
            List<AppUser> appUserList = appUserService.getAll();
            successResponseBody = CustomResponse.successResponse("Success", "appUserList", appUserList, request.getRequestURI());

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
            successResponseBody = CustomResponse.successResponse("Success", "appUser", user, request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(successResponseBody);
        } catch (UsernameNotFoundException e) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponseBody);
        } catch (Exception exception) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponseBody);
        }
    }

    @GetMapping("/get-account-by-username")
    public ResponseEntity<CustomResponse> getUserByUsername(@RequestParam("username") String username) {
        try {
            AppUser user = appUserService.getAccountByUsername(username);
            successResponseBody = CustomResponse.successResponse("Success", "appUser", user, request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(successResponseBody);
        } catch (UsernameNotFoundException e) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(errorResponseBody);
        } catch (Exception exception) {
            errorResponseBody = CustomResponse.errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage(), request.getRequestURI());

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponseBody);
        }
    }
}
