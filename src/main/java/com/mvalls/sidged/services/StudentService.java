package com.mvalls.sidged.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvalls.sidged.login.User;
import com.mvalls.sidged.login.UserStudent;
import com.mvalls.sidged.model.Student;
import com.mvalls.sidged.repositories.StudentRepository;
import com.mvalls.sidged.repositories.UserRepository;
import com.mvalls.sidged.repositories.UserStudentRepository;
import com.mvalls.sidged.utils.ContactDataUtils;

@Service
public class StudentService extends GenericService<Student, StudentRepository>{

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private UserStudentRepository userStudentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional
	public Student update(Student student) {
		Student persistedStudent = findById(student.getId());
		if(ContactDataUtils.mailsHaveChanged(student.getContactData(), persistedStudent.getContactData())) {
			UserStudent userStudent = userStudentRepository.findByStudentId(student.getId());
			User user = userStudent.getUser();
			user.setEmail(student.getContactData().getDefaultEmail());
			userRepository.save(user);
		}
		
		persistedStudent.setNames(student.getNames());
		persistedStudent.setLastname(student.getLastname());
		persistedStudent.getContactData().setEmails(student.getContactData().getEmails());
		persistedStudent.setIdentificationNumber(student.getIdentificationNumber());
		
		return studentRepository.save(persistedStudent);
	}
	
	@Override
	protected StudentRepository getRepository() {
		return this.studentRepository;
	}

}
