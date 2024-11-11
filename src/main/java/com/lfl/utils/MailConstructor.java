package com.lfl.utils;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.lfl.security.Users;


@Component
public class MailConstructor {
	@Autowired
	private Environment env;
	
	public SimpleMailMessage constructResetTokenEmail(
			String contextPath, String token, Users user) {
			    String url = contextPath + "changePassword/" + token;
//			    String message = "Following your registration to kandomanuscript, please find below your login credentials."+ " \r\n"+"Username: "+user.getUsername()+ " \r\n"+"Password: "+ " \r\n"+"Please store your password safely so that the account remains only accessible to you.";
			    String message = "Hi "+user.getUsername()+ " \r\n"+"Following your password request, please click on or copy and paste the link below into your browser."+ " \r\n";
			    return constructEmail("[Kando Manuscript] Reset Password", message + " \r\n" + url, user);
			}
			 
	public SimpleMailMessage constructEmail(String subject, String body, 
			  Users user) {
			    SimpleMailMessage email = new SimpleMailMessage();
			    email.setSubject(subject);
			    email.setText(body);
			    email.setTo(user.getEmail());
			    email.setFrom(env.getProperty("support.email"));
			    return email;
			}
}
