package com.lfl.passwordreset;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lfl.security.Users;
import com.lfl.service.AccountService;
import com.lfl.utils.MailConstructor;

import lombok.extern.java.Log;



@RestController
public class ResetPasswordController {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private MailConstructor mailconstructor;
	
	private String dirClienthost;
	

	@PostMapping("/other/resetPasswordMail/{email}")
	public ResponseEntity<?> resetPassword(@PathVariable String email) {
		
		Users user = accountService.checkByEmail(email);
	    if (user == null) {
	    	 return new ResponseEntity("user not found", HttpStatus.BAD_REQUEST);
	    }
	    String token = UUID.randomUUID().toString();
	    accountService.createPasswordResetTokenForUser(user, token);
	    SimpleMailMessage mail = mailconstructor.constructResetTokenEmail(this.dirClienthost , token, user);
	    mailSender.send(mail);
	    return new ResponseEntity("mail sent", HttpStatus.OK);
	}
	

	@GetMapping("/other/resetPasswordCheckToken/{token}")
	public ResponseEntity<?> showChangePasswordPage(@PathVariable String token) {
	    String result = accountService.validatePasswordResetToken(token);
	    if(result == null) {
	    	return new ResponseEntity("good token", HttpStatus.OK);
	    } else {
	    	return new ResponseEntity("bad token", HttpStatus.BAD_REQUEST);
	    }
	}
	
	
	@GetMapping("/other/savePassword/{token}/{pwd}")
	public ResponseEntity<?> savePassword(@PathVariable String token,@PathVariable String pwd) {
	    
		Users u = accountService.findByToken(token);
		
		if(u != null){
			accountService.changeUserPassword(u, pwd);
			return new ResponseEntity("good token", HttpStatus.OK);
		}else {
			return new ResponseEntity("bad token", HttpStatus.BAD_REQUEST);
	    	
	    }
	}

}
