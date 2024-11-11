package com.lfl.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.lfl.entity.Astreinte;
import com.lfl.entity.BaseEntity;
import com.lfl.entity.Event;
import com.lfl.entity.Notification;
import com.lfl.entity.RhFile;
import com.lfl.entity.RhFileValidation;
import com.lfl.passwordreset.PasswordResetToken;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users extends BaseEntity{
	
	@Column(unique=true)
	private String username;
	
	@Column(name = "password_hash", length = 255, nullable = false)
	private String password;

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @Column(name = "firstname", length = 50, nullable = false)
    private String firstname;

    @Column(name = "lastname", length = 50, nullable = false)
    private String lastname;

    @Column(name = "gender", length = 25, nullable = false)
    private String gender;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "sub_role", length = 50)
    private String subRole;

    @Column(name = "onboarding_date", nullable = false)
    private Date onboardingDate;

    @Column(name = "rt_hash", length = 255)
    private String rtHash;

    @Column(name = "phone_number", length = 50)
    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RhFile> rhFiles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RhFileValidation> rhFileValidations;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Astreinte> astreintes;
    
	@ManyToMany(fetch=FetchType.EAGER)
	private Collection<Role> roles = new ArrayList<>();
	
	@OneToOne(mappedBy = "user")
	private PasswordResetToken passwordresettoken;
	
	private boolean enabled;

}
