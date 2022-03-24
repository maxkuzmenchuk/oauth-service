package com.kuzmenchuk.oauthservice.repository;

import com.kuzmenchuk.oauthservice.repository.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);

    @Modifying
    @Query("UPDATE AppUser u SET u.active = false WHERE u.id = :id")
    void deactivateUser(@Param("id") Long id);
}
