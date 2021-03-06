package com.project.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.entities.Role;
@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {
Role findByRole(String role); 
}
