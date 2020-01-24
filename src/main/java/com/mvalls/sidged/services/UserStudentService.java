package com.mvalls.sidged.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvalls.sidged.login.UserStudent;
import com.mvalls.sidged.repositories.UserStudentRepository;

@Service
public class UserStudentService extends GenericService<UserStudent, UserStudentRepository>{

	@Autowired
	private UserStudentRepository userStudentRepository;
	
	public UserStudent findByUsername(String username) {
		return userStudentRepository.findByUserUsername(username);
	}
	
	@Override
	protected UserStudentRepository getRepository() {
		return userStudentRepository;
	}

}
