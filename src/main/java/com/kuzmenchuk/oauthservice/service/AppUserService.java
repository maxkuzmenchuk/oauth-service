package com.kuzmenchuk.oauthservice.service;

import com.kuzmenchuk.oauthservice.exception.UserAlreadyExistException;
import com.kuzmenchuk.oauthservice.repository.AppUserRepository;
import com.kuzmenchuk.oauthservice.repository.entities.AppUser;
import com.kuzmenchuk.oauthservice.repository.entities.Role;
import com.kuzmenchuk.oauthservice.util.RegistrationUserRequest;
import com.kuzmenchuk.oauthservice.util.UpdateUserRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository repository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(dontRollbackOn = Exception.class)
    public AppUser update(UpdateUserRequest user) {
        Optional<AppUser> appUserDB = appUserRepository.findById(user.getId());
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
