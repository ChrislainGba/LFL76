package com.lfl.security;

import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lfl.service.AccountService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
	private AccountService accountService;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users u = accountService.loadUserByUsername(username);
		if(u == null) throw new UsernameNotFoundException(String.format("user %s not found",username));
		String[] roles = u.getRoles().stream().map(r -> r.getName()).toArray(String[]::new);
		UserDetails userDetails = User
				.withUsername(u.getUsername())
				.password(u.getPassword())
				.roles(roles)
				.build();
		return userDetails;
	}

}
