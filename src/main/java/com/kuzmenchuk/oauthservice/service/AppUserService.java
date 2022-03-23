package com.kuzmenchuk.oauthservice.service;

import com.kuzmenchuk.oauthservice.exception.UserAlreadyExistException;
import com.kuzmenchuk.oauthservice.repository.AppUserRepository;
import com.kuzmenchuk.oauthservice.repository.entities.AppUser;
import com.kuzmenchuk.oauthservice.repository.entities.Role;
import com.kuzmenchuk.oauthservice.util.RegistrationUserRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
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
    public AppUser save(AppUser user) {
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
}
