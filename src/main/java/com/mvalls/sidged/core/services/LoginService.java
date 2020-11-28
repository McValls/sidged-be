package com.mvalls.sidged.core.services;

import java.util.Optional;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import com.mvalls.sidged.core.model.emails.Email;
import com.mvalls.sidged.core.model.interfaces.UserPerson;
import com.mvalls.sidged.core.model.users.User;
import com.mvalls.sidged.core.model.users.UserType;
import com.mvalls.sidged.core.repositories.UserRepository;
import com.mvalls.sidged.core.repositories.UserStudentRepository;
import com.mvalls.sidged.core.repositories.UserTeacherRepository;
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
	private final UserStudentRepository userStudentRepository;
	private final UserTeacherRepository userTeacherRepository;
	private final UserCreationStrategyService userCreationStrategyService;
	private final EmailsService emailsService;

	public LoginService(UserRepository userRepository,
			UserStudentRepository userStudentRepository,
			UserTeacherRepository userTeacherRepository,
			UserCreationStrategyService userCreationStrategyService,
			EmailsService emailsService) {
		this.userRepository = userRepository;
		this.userStudentRepository = userStudentRepository;
		this.userTeacherRepository = userTeacherRepository;
		this.userCreationStrategyService = userCreationStrategyService;
		this.emailsService = emailsService;
	}

	public Pair<Optional<User>, Optional<UserPerson>> login(String username, String password) {
		Optional<User> optionalUser = this.userRepository.findByUserName(username);
		
		if (optionalUser.filter(user -> isValidPassword(user, password)).isEmpty()) {
			return ImmutablePair.of(Optional.empty(), Optional.empty());
		}
		
		return ImmutablePair.of(optionalUser, optionalUser.map(user -> findUserPerson(username, user.getUserType())));
	}
	
	private UserPerson findUserPerson(String username, UserType userType) {
		UserPerson userPerson = null;
		switch (userType) {
			case TEACHER:
				userPerson = this.userTeacherRepository.findByUserUsername(username);
				break;
			case STUDENT:
				userPerson = this.userStudentRepository.findByUserUsername(username);
				break;
			default:
				break;
		}
		return userPerson;
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
		Optional<User> optionalUser = this.login(username, oldPassword).getLeft();
		
		User user = optionalUser.orElseThrow(WrongPasswordException::new);
		String newPasswordEncrypted = EncryptionUtils.encryptSHA256(newPassword);
		user.setPassword(newPasswordEncrypted);
		this.update(user);
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
