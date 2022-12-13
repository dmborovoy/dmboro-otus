package com.dimas.profile.data.repository;

import com.dimas.profile.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByKeycloakId(UUID keycloakId);
}
