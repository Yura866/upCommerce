package com.bcn.startupers.upcommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bcn.startupers.upcommerce.model.Role;
import com.bcn.startupers.upcommerce.model.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	 Optional<Role> findByRoleName(RoleName roleName);
	 boolean existsByRoleName(RoleName roleName);
}
