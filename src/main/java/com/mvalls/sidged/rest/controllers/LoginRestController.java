package com.mvalls.sidged.rest.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mvalls.sidged.core.model.interfaces.UserPerson;
import com.mvalls.sidged.core.model.users.User;
import com.mvalls.sidged.core.services.LoginService;
import com.mvalls.sidged.rest.annotations.JwtBackOffice;
import com.mvalls.sidged.rest.dtos.ChangePasswordDTO;
import com.mvalls.sidged.rest.dtos.LoginRequestDTO;
import com.mvalls.sidged.rest.dtos.LoginResponseDTO;
import com.mvalls.sidged.rest.dtos.SignUpRequestDTO;
import com.mvalls.sidged.rest.exceptions.BadCredentialsException;
import com.mvalls.sidged.rest.exceptions.WrongPasswordException;
import com.mvalls.sidged.rest.mappers.LoginResponseMapper;
import com.mvalls.sidged.rest.mappers.SignUpModelMapper;
import com.mvalls.sidged.rest.mappers.SignUpVOMapper;
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
	private final JwtTokenUtils jwtTokenUtils;
	private final SignUpModelMapper signUpModelMapper = new SignUpModelMapper();
	private final LoginResponseMapper loginResponseMapper = new LoginResponseMapper();
	private final SignUpVOMapper signUpVOMapper = new SignUpVOMapper();

	@Autowired
	public LoginRestController(LoginService loginService, JwtTokenUtils jwtTokenUtils) {
		super();
		this.loginService = loginService;
		this.jwtTokenUtils = jwtTokenUtils;
	}

	@PostMapping
	public LoginResponseDTO login(@RequestBody LoginRequestDTO login, HttpServletResponse response) throws BadCredentialsException {
		Pair<Optional<User>, Optional<UserPerson>> loginResponse = 
				loginService.login(login.getUsername(), login.getPassword());
		
		User loggedUser = loginResponse.getLeft().orElseThrow(BadCredentialsException::new);
		jwtTokenUtils.setTokenToResponse(loggedUser, response);
		
		return loginResponseMapper.map(loggedUser, 
				loginResponse.getRight().map(UserPerson::getFullName).orElse(""));	
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
