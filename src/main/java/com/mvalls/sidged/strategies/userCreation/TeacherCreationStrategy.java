package com.mvalls.sidged.strategies.userCreation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mvalls.sidged.login.User;
import com.mvalls.sidged.login.UserTeacher;
import com.mvalls.sidged.model.ContactData;
import com.mvalls.sidged.model.Teacher;
import com.mvalls.sidged.services.TeacherService;
import com.mvalls.sidged.services.UserTeacherService;
import com.mvalls.sidged.valueObjects.SignUpVO;

@Component
public class TeacherCreationStrategy implements UserCreationStrategy {
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private UserTeacherService userTeacherService;
	
	@Override
	public void execute(User user, SignUpVO vo) {
		ContactData contactData = new ContactData(vo.getEmail());
		Teacher teacher = new Teacher(vo.getNames(), vo.getLastname(), contactData);
		teacherService.create(teacher);
		userTeacherService.create(new UserTeacher(user, teacher));
	}
	
	
}
