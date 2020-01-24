package com.mvalls.sidged.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvalls.sidged.login.UserTeacher;
import com.mvalls.sidged.repositories.UserTeacherRepository;

@Service
public class UserTeacherService extends GenericService<UserTeacher, UserTeacherRepository>{

	@Autowired
	private UserTeacherRepository userTeacherRepository;

	public UserTeacher findByUsername(String username) {
		return userTeacherRepository.findByUserUsername(username);
	}
	
	@Override
	protected UserTeacherRepository getRepository() {
		return userTeacherRepository;
	}
	
}
