package com.mvalls.sidged.strategies.userCreation;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvalls.sidged.login.UserType;

@Service
public class UserCreationStrategyService {
	
	private final TeacherCreationStrategy teacherCreationStrategy;
	private final StudentCreationStrategy studentCreationStrategy;
	
	@Autowired
	public UserCreationStrategyService(TeacherCreationStrategy teacherCreationStrategy, StudentCreationStrategy studentCreationStrategy) {
		this.teacherCreationStrategy = teacherCreationStrategy;
		this.studentCreationStrategy = studentCreationStrategy;
	}
	
	private final Map<UserType, UserCreationStrategy> strategies = new HashMap<>();
	
	@PostConstruct
	public void postConstruct() {
		strategies.put(UserType.TEACHER, teacherCreationStrategy);
		strategies.put(UserType.STUDENT, studentCreationStrategy);
	}
	
	public UserCreationStrategy getStrategy(UserType userType) {
		return strategies.get(userType);
	}
	
}
