package com.lfl.service;

import java.util.List;

import com.lfl.security.Role;
import com.lfl.security.Users;




public interface AccountService {
	public Users saveUser(Users user);
	public Users register(Users user);
	public Role saveRole(Role role);
	public void addRoleToUser(String username, String roleName);
	public void removeRoleToUser(String username, String roleName);
	public Users loadUserByUsername(String username);
	public List<Users> getAll();
	public Users getOne(String id);
	public Users update(Users user);
	// Users changeStatus(Long id, Integer status);
	//public Users activate(Long id, boolean activate);
	public void delele(String id);
	public void activateUser(String id);
	
	public Users checkByEmail(String email);
	
	public long usercout();
	
	
	public void createPasswordResetTokenForUser(Users user, String token);
	
	public String validatePasswordResetToken(String token);
	
	public Users findByToken(String token);
	
	public void changeUserPassword(Users user, String password);

	
	

}
