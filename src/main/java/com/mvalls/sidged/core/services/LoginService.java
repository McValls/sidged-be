package com.mvalls.sidged.core.services;

import java.util.Optional;

import com.mvalls.sidged.core.model.emails.Email;
import com.mvalls.sidged.core.model.users.User;
import com.mvalls.sidged.core.model.users.UserType;
import com.mvalls.sidged.core.repositories.UserRepository;
import com.mvalls.sidged.core.strategies.userCreation.UserCreationStrategy;
import com.mvalls.sidged.core.strategies.userCreation.UserCreationStrategyService;
import com.mvalls.sidged.core.utils.EncryptionUtils;
import com.mvalls.sidged.rest.exceptions.BadCredentialsException;
import com.mvalls.sidged.rest.exceptions.WrongPasswordException;
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
public class LoginService {
	
	private final UserRepository userRepository;
	private final UserCreationStrategyService userCreationStrategyService;
	private final EmailsService emailsService;

	public LoginService(UserRepository userRepository, UserCreationStrategyService userCreationStrategyService,
			EmailsService emailsService) {
		this.userRepository = userRepository;
		this.userCreationStrategyService = userCreationStrategyService;
		this.emailsService = emailsService;
	}

	public User login(String username, String password) {
		Optional<User> optionalUser = this.userRepository.findByUserName(username);
		return optionalUser
				.filter(user -> isValidPassword(user, password))
				.orElse(null);
	}

	private boolean isValidPassword(User user, String password) {
		String encryptedPassword = user.getPassword();
		if(EncryptionUtils.encryptSHA256(password).equals(encryptedPassword)) {
			return true;
		}
		return false;
	}

	public void signUp(User user, SignUpVO signUpVO) {
		user.setPassword(EncryptionUtils.encryptSHA256(user.getPassword()));
		UserType userType = user.getUserType();
		UserCreationStrategy creationStrategy = userCreationStrategyService.getStrategy(userType);
		
		creationStrategy.execute(user, signUpVO);
		
		Email email = Email.builder()
				.to(user.getEmail())
				.subject(getSubject(userType))
				.message(getMessage(user, signUpVO))
				.build();
		emailsService.sendEmail(email);
	}
	
	public void changePassword(String username, String oldPassword, String newPassword) throws WrongPasswordException {
		User user = this.login(username, oldPassword);
		if(user != null) {
			String newPasswordEncrypted = EncryptionUtils.encryptSHA256(newPassword);
			user.setPassword(newPasswordEncrypted);
			this.update(user);
		} else {
			throw new WrongPasswordException();
		}
	}
	
	public void recoveryPassword(String username, String email) throws BadCredentialsException {
		Optional<User> optionalUser = this.userRepository.findByUserName(username);
		User user = optionalUser.orElseThrow();
		if(user.getEmail().equalsIgnoreCase(email)) {
			String randomPassword = EncryptionUtils.generateRandomPassword();
			String randomPasswordEncrypted = EncryptionUtils.encryptSHA256(randomPassword);
			
			this.emailsService.sendRecoveryPasswordEmail(email, randomPassword);
			user.setPassword(randomPasswordEncrypted);
			this.update(user);
		} else {
			throw new BadCredentialsException();
		}
	}
	
	private User update(User user) {
		return this.userRepository.update(user);
	}

	private String getSubject(UserType userType) {
		return "Nuevo usuario registrado " + (userType == UserType.STUDENT? "[Estudiante]" : "[Docente]");
	}

	private String getMessage(User user, SignUpVO signUpVO) {
		StringBuilder sb = new StringBuilder();
		sb.append("Hola ").append(signUpVO.getNames()).append(".")
			.append("\n")
			.append("Se ha registrado un nuevo usuario con este correo, bajo el nombre de usuario ").append(user.getUsername())
			.append("\n\n")
			.append("Para ingresar al sistema, haga click aquí. <a href=\"www.google.com\"> google.com </a>");
		return sb.toString();
	}
	
}
