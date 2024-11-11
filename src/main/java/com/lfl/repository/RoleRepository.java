package com.lfl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lfl.security.Role;



public interface RoleRepository extends JpaRepository<Role, String>{
	public Role findByName(String name);
}
