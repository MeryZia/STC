package com.example.STCSpringBoot.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.STCSpringBoot.Entities.Role.Role;
import com.example.STCSpringBoot.Entities.Role.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RoleName name);
}
