package com.blogapp.api.repository;

import com.blogapp.api.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(String name);

    boolean existsByName(String roleUser);
}
