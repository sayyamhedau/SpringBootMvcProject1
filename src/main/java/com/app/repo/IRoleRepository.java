package com.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
	Role findByRole(String role);
}
