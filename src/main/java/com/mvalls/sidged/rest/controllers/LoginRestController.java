package com.mvalls.sidged.rest.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.core.model.users.User;
import com.mvalls.sidged.core.services.LoginService;
import com.mvalls.sidged.core.services.UserStudentService;
import com.mvalls.sidged.core.services.UserTeacherService;
import com.mvalls.sidged.mappers.LoginResponseMapper;
import com.mvalls.sidged.mappers.SignUpModelMapper;
import com.mvalls.sidged.mappers.SignUpVOMapper;
import com.mvalls.sidged.rest.annotations.JwtBackOffice;
import com.mvalls.sidged.rest.dtos.ChangePasswordDTO;
import com.mvalls.sidged.rest.dtos.LoginRequestDTO;
import com.mvalls.sidged.rest.dtos.LoginResponseDTO;
import com.mvalls.sidged.rest.dtos.SignUpRequestDTO;
import com.mvalls.sidged.rest.exceptions.BadCredentialsException;
import com.mvalls.sidged.rest.exceptions.WrongPasswordException;
import com.mvalls.sidged.rest.security.jwt.JwtTokenUtils;
import com.mvalls.sidged.valueObjects.SignUpVO;

/**
 * 
 * @author Marcelo Valls
 * 
* This file is part of SIDGED-Backend.
* 
* SIDGED-Backend is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* SIDGED-Backend is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with SIDGED-Backend.  If not, see <https://www.gnu.org/licenses/>.
 *
 */
@RestController
@RequestMapping("/login")
public class LoginRestController {
	
	private final LoginService loginService;
	private final SignUpModelMapper signUpModelMapper;
	private final SignUpVOMapper signUpVOMapper;
	private final JwtTokenUtils jwtTokenUtils;
	
	//TODO: LoginResponseMapper agnostico de servicios
	private final UserStudentService userStudentService;
	private final UserTeacherService userTeacherService;

	@Autowired
	public LoginRestController(LoginService loginService, JwtTokenUtils jwtTokenUtils,
			UserStudentService userStudentService,
			UserTeacherService userTeacherService) {
		super();
		this.loginService = loginService;
		this.jwtTokenUtils = jwtTokenUtils;
		this.signUpVOMapper = new SignUpVOMapper();
		this.signUpModelMapper = new SignUpModelMapper();
		this.userStudentService = userStudentService;
		this.userTeacherService = userTeacherService;
	}

	@PostMapping
	public LoginResponseDTO login(@RequestBody LoginRequestDTO login, HttpServletResponse response) throws BadCredentialsException {
		User loggedUser = loginService.login(login.getUsername(), login.getPassword());
		if(loggedUser == null) {
			throw new BadCredentialsException();
		}
		jwtTokenUtils.setTokenToResponse(loggedUser, response);
		
		LoginResponseMapper loginResponseMapper = new LoginResponseMapper(this.userStudentService, this.userTeacherService);
		return loginResponseMapper.map(loggedUser);	
	}
	
	@JwtBackOffice
	@PostMapping("/signup")
	public void signUp(HttpServletRequest request, @RequestBody SignUpRequestDTO signup) {
		User user = signUpModelMapper.map(signup);
		SignUpVO signUpVO = signUpVOMapper.map(signup); 
		
		loginService.signUp(user, signUpVO);
	}
	
	@PutMapping("/change-password")
	public void changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) throws WrongPasswordException {
		loginService.changePassword(changePasswordDTO.getUsername(), 
				changePasswordDTO.getOldPassword(), 
				changePasswordDTO.getNewPassword());
	}
	
	@GetMapping("/recovery-password")
	public void recoveryPassword(@RequestParam("username") String username, @RequestParam("email") String email) throws BadCredentialsException {
		loginService.recoveryPassword(username, email);
	}
	
}
