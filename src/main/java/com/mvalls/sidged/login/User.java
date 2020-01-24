package com.mvalls.sidged.login;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String email;
	
	@Column(name = "user_type")
	@Enumerated(EnumType.STRING)
	private UserType userType;
	
	@Column(name = "user_status")
	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;
	
	@Column(name = "last_time_logged")
	private LocalDateTime lastTimeLogged;
	
	@Column(name = "last_time_modified")
	private LocalDateTime lastTimeModified;

}
