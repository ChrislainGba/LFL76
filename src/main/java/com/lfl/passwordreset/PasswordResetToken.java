package com.lfl.passwordreset;

import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lfl.entity.BaseEntity;
import com.lfl.security.Users;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class PasswordResetToken extends BaseEntity {
 
    private static final int EXPIRATION = 60 * 24;
 
 
    private String token;
 
    @OneToOne(targetEntity = Users.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
	@JsonIgnore
    private Users user;
 
    private Date expiryDate;

	public PasswordResetToken(String token, Users user) {
		super();
		this.token = token;
		this.user = user;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	public static int getExpirationDate() {
		return EXPIRATION;
	}
	
	
	
	 private Date calculateExpiryDate(final int expiryTimeInMinutes) {
	        final Calendar cal = Calendar.getInstance();
	        cal.setTimeInMillis(new Date().getTime());
	        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
	        return new Date(cal.getTime().getTime());
	}
	 
	 public void updateToken(final String token) {
	        this.token = token;
	        this.expiryDate = calculateExpiryDate(EXPIRATION);
	}
	
	
    
    
}
