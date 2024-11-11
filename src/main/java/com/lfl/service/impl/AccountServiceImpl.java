package com.lfl.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.lfl.passwordreset.PasswordResetToken;
import com.lfl.repository.PasswordTokenRepository;
import com.lfl.repository.RoleRepository;
import com.lfl.repository.UserRepository;
import com.lfl.security.Role;
import com.lfl.security.Users;
import com.lfl.service.AccountService;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;




@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService{
	

	public UserRepository userRepository;
	

	public RoleRepository roleRepository;
	

	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	PasswordTokenRepository  passwordTokenRepository;

	@Override
	public Users saveUser(Users user) {
		Users u = userRepository.findByUsername(user.getUsername());
		if(u != null) throw new RuntimeException("this user already exists");
		String hasPW = bCryptPasswordEncoder.encode(user.getPassword());
		
		user.setPassword(hasPW);
		user.isEnabled();
		user.setCreatedAt(new Date());
		return userRepository.save(user);
	}

	@Override
	public Role saveRole(Role role) {
		Role ro = roleRepository.findByName(role.getName());
		if(ro != null) throw new RuntimeException("this role already exists");
		return roleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		Users user = userRepository.findByUsername(username);
		Role role = roleRepository.findByName(roleName);
		if(user != null && role != null)
			user.getRoles().add(role);
		
	}
	
	@Override
	public void removeRoleToUser(String username, String roleName) {
		Users user = userRepository.findByUsername(username);
		Role role = roleRepository.findByName(roleName);
		if(user != null && role != null)
			user.getRoles().remove(role);
		
	}


	@Override
	public List<Users> getAll() {
		return userRepository.findAll();
	}

	@Override
	public Users getOne(String id) {
		Optional<Users> u = userRepository.findById(id);
		return u.get();
	}

	@Override
	public Users update(Users user) {
		Users u = userRepository.findByUsername(user.getUsername());
		u.setFirstname(user.getFirstname());
		u.setLastname(user.getLastname());
		u.setEmail(user.getEmail());
		u.setUpdatedAt(new Date());
		u.getRoles().clear();
		Role role = roleRepository.findByName(user.getRoles().iterator().next().getName().toUpperCase());
		u.getRoles().add(role);

		return userRepository.save(u);
	}

	@Override
	public void delele(String id) {
		Users u = userRepository.findById(id).orElse(null);
		if(u != null) 
			userRepository.delete(u);
		
	}

	@Override
	public void activateUser(String id) {
		Users u = userRepository.getOne(id);
		if(u.isEnabled()){
			u.setEnabled(false);
			userRepository.save(u);
		}else{
			u.setEnabled(true);
			userRepository.save(u);
		}
		
	}

	@Override
	public Users register(Users user) {
		if(user.getId() == null) {
			String hasPW = bCryptPasswordEncoder.encode(user.getPassword());
			user.setPassword(hasPW);
			user.setEnabled(true);
			user.setCreatedAt(new Date());
		}else {
			user.setUpdatedAt(new Date());
		}
		
		
		Role role = roleRepository.findByName(user.getRoles().iterator().next().getName().toUpperCase());
		user.getRoles().clear();
		user.getRoles().add(role);
		return userRepository.save(user);
	}

	@Override
	public long usercout() {
		return userRepository.count();
	}

	@Override
	public Users checkByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void createPasswordResetTokenForUser(Users user, String token) {
		PasswordResetToken myToken = new PasswordResetToken(token, user);
	    passwordTokenRepository.save(myToken);
		
	}

	@Override
	public String validatePasswordResetToken(String token) {
		 final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
		 
		    return !isTokenFound(passToken) ? "invalidToken"
		            : isTokenExpired(passToken) ? "expired"
		            : null;
		}
		 
		private boolean isTokenFound(PasswordResetToken passToken) {
		    return passToken != null;
		}
		 
		private boolean isTokenExpired(PasswordResetToken passToken) {
		    final Calendar cal = Calendar.getInstance();
		    return passToken.getExpiryDate().before(cal.getTime());
		}

		@Override
		public Users findByToken(String token) {
			PasswordResetToken passToken = passwordTokenRepository.findByToken(token);
			Users u = passToken.getUser();
			if(u != null){
				return u;
			}
			return null;
		}
	
		@Override
		public void changeUserPassword(Users user, String password) {
			String hasPW = bCryptPasswordEncoder.encode(password);
			user.setPassword(hasPW);
			userRepository.save(user);
		}

		@Override
		public Users loadUserByUsername(String username) {
			return userRepository.findByUsername(username);
		}
		
		

}
