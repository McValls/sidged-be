package com.mvalls.sidged.strategies.userCreation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mvalls.sidged.login.User;
import com.mvalls.sidged.login.UserStudent;
import com.mvalls.sidged.model.ContactData;
import com.mvalls.sidged.model.Student;
import com.mvalls.sidged.services.StudentService;
import com.mvalls.sidged.services.UserStudentService;
import com.mvalls.sidged.valueObjects.SignUpVO;

@Component
public class StudentCreationStrategy implements UserCreationStrategy {
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private UserStudentService userStudentService;

	@Override
	public void execute(User user, SignUpVO vo) {
		ContactData contactData = new ContactData(vo.getEmail());
		Student student = Student.builder().names(vo.getNames())
				.lastname(vo.getLastname())
				.identificationNumber(vo.getIdentificationNumber())
				.contactData(contactData)
				.build();
				
		studentService.create(student);
		userStudentService.create(new UserStudent(user, student));
	}

}
