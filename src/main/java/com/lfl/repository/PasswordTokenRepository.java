package com.lfl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lfl.passwordreset.PasswordResetToken;


public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, String>{

	public PasswordResetToken findByToken(String token);

}
