package com.kuzmenchuk.oauthservice.service;

import com.kuzmenchuk.oauthservice.exception.UserAlreadyExistException;
import com.kuzmenchuk.oauthservice.repository.AppUserRepository;
import com.kuzmenchuk.oauthservice.repository.entities.AppUser;
import com.kuzmenchuk.oauthservice.repository.entities.Role;
import com.kuzmenchuk.oauthservice.util.requests.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository repository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(dontRollbackOn = Exception.class)
    public AppUser update(Long id, UpdateUserRequest user) {
        Optional<AppUser> appUserDB = appUserRepository.findById(id);
        AppUser appUser;

        if (!appUserDB.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        } else {
            appUser = appUserDB.get();
            appUser.setUsername(user.getUsername());
            if (!appUser.getPassword().equalsIgnoreCase(passwordEncoder.encode(user.getPassword()))) {
                appUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            appUser.setActive(user.isActive());
            appUser.setRoles(user.getRoles());
        }

        return appUserRepository.saveAndFlush(appUser);
    }

    @Transactional(dontRollbackOn = Exception.class)
    public AppUser addNewUser(RegistrationUserRequest user) {
        Optional<AppUser> userFromDB = appUserRepository.findByUsername(user.getUsername());
        if (userFromDB.isPresent()) {
            throw new UserAlreadyExistException("User " + user.getUsername() + " is exists!");
        }

        AppUser newUser = AppUser.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .roles(Collections.singleton(Role.ROLE_USER))
                .active(true)
                .build();

        return appUserRepository.saveAndFlush(newUser);
    }

    @Transactional
    public List<Long> deleteUserById(DeleteUserRequest deleteUserRequest) {
        List<Long> deletedIDs = new ArrayList<>();
        Long[] ids = deleteUserRequest.getDeleteIDs();
        Long authUserID = deleteUserRequest.getAuthUserID();

        for (Long id : ids) {
            if (!Objects.equals(id, authUserID)) {
                appUserRepository.deleteById(id);
                deletedIDs.add(id);
            }
        }

        return deletedIDs;
    }

    @Transactional
    public Long changeActiveStatus(ChangeActiveStatusRequest changeActiveStatusRequest) throws Exception {
        Long accountID = changeActiveStatusRequest.getAccountID();
        Long authUserID = changeActiveStatusRequest.getAuthUserID();
        boolean status = changeActiveStatusRequest.isStatus();

        if (!Objects.equals(accountID, authUserID)) {
            appUserRepository.changeActiveStatus(status, accountID);
        } else {
            throw new Exception("You can't change status for yourself");
        }

        return accountID;
    }

    @Transactional
    public Map<String, Object> changeRoleById(ChangeRoleRequest changeRoleRequest) {
        Optional<AppUser> userDB = appUserRepository.findById(changeRoleRequest.getUserID());
        AppUser appUser;
        if (!userDB.isPresent()) {
            throw new UsernameNotFoundException("User not found!");
        } else {
            appUser = userDB.get();
            appUser.setRoles(changeRoleRequest.getRoles());
        }

        appUserRepository.saveAndFlush(appUser);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userID", changeRoleRequest.getUserID());
        resultMap.put("roles", changeRoleRequest.getRoles());

        return resultMap;
    }

    public List<AppUser> getAll() {
        return appUserRepository.findAll();
    }

    public AppUser getAccountById(Long id) {
        Optional<AppUser> user = appUserRepository.findById(id);

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("User not found!");
        }
    }
}
