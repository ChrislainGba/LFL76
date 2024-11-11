package com.lfl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lfl.security.Users;


public interface UserRepository extends JpaRepository<Users, String>{
	public Users findByUsername(String username);
	public Users findByEmail(String email);
	public long count();
}