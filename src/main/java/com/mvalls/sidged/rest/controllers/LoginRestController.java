package com.mvalls.sidged.rest.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.annotations.JwtBackOffice;
import com.mvalls.sidged.login.User;
import com.mvalls.sidged.mappers.LoginResponseMapper;
import com.mvalls.sidged.mappers.SignUpModelMapper;
import com.mvalls.sidged.mappers.SignUpVOMapper;
import com.mvalls.sidged.rest.dtos.LoginRequestDTO;
import com.mvalls.sidged.rest.dtos.LoginResponseDTO;
import com.mvalls.sidged.rest.dtos.SignUpRequestDTO;
import com.mvalls.sidged.rest.exceptions.BadCredentialsException;
import com.mvalls.sidged.security.jwt.JwtTokenUtils;
import com.mvalls.sidged.services.LoginService;
import com.mvalls.sidged.valueObjects.SignUpVO;

@RestController
@RequestMapping("/login")
public class LoginRestController {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private SignUpModelMapper signUpModelMapper;
	
	@Autowired
	private LoginResponseMapper loginResponseMapper;
	
	@Autowired
	private SignUpVOMapper signUpVOMapper;
	
	@Autowired
	private JwtTokenUtils jwtTokenUtils;

	@PostMapping
	public LoginResponseDTO login(@RequestBody LoginRequestDTO login, HttpServletResponse response) throws BadCredentialsException {
		User loggedUser = loginService.login(login.getUsername(), login.getPassword());
		if(loggedUser == null) {
			throw new BadCredentialsException();
		}
		jwtTokenUtils.setTokenToResponse(loggedUser, response);
		return loginResponseMapper.map(loggedUser);	
	}
	
	@JwtBackOffice
	@PostMapping("/signup")
	public void signUp(HttpServletRequest request, @RequestBody SignUpRequestDTO signup) {
		User user = signUpModelMapper.map(signup);
		SignUpVO signUpVO = signUpVOMapper.map(signup); 
		
		loginService.signUp(user, signUpVO);
	}
	
}
