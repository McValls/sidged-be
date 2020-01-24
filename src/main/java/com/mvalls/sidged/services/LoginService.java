package com.mvalls.sidged.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvalls.sidged.login.User;
import com.mvalls.sidged.login.UserType;
import com.mvalls.sidged.model.emails.Email;
import com.mvalls.sidged.repositories.UserRepository;
import com.mvalls.sidged.strategies.userCreation.UserCreationStrategy;
import com.mvalls.sidged.strategies.userCreation.UserCreationStrategyService;
import com.mvalls.sidged.utils.EncryptionUtils;
import com.mvalls.sidged.valueObjects.SignUpVO;

@Service
public class LoginService extends GenericService<User, UserRepository>{
	
	
	private final UserRepository userRepository;
	private final UserCreationStrategyService userCreationStrategyService;
	private final EmailsService emailsService;
	
	@Autowired
	public LoginService(UserRepository userRepository, 
			UserCreationStrategyService userCreationStrategyService,
			EmailsService emailsService) {
		this.userRepository = userRepository;
		this.userCreationStrategyService = userCreationStrategyService;
		this.emailsService = emailsService;
	}
	
	public User login(String username, String password) {
		User user = userRepository.getUserByUsername(username);
		if(user != null && isValidPassword(user, password)) {
			return user;
		}
		return null;
	}

	private boolean isValidPassword(User user, String password) {
		String encryptedPassword = user.getPassword();
		if(EncryptionUtils.encryptSHA256(password).equals(encryptedPassword)) {
			return true;
		}
		return false;
	}

	@Transactional
	public void signUp(User user, SignUpVO signUpVO) {
		user.setPassword(EncryptionUtils.encryptSHA256(user.getPassword()));
		create(user);
		
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
	
	@Override
	protected UserRepository getRepository() {
		return userRepository;
	}
}
