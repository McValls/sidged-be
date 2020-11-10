package com.mvalls.sidged.core.services;

import org.springframework.transaction.annotation.Transactional;

import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.core.model.users.User;
import com.mvalls.sidged.core.model.users.UserStudent;
import com.mvalls.sidged.core.repositories.StudentRepository;
import com.mvalls.sidged.core.repositories.UserRepository;
import com.mvalls.sidged.core.repositories.UserStudentRepository;
import com.mvalls.sidged.core.utils.ContactDataUtils;

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
public class StudentService extends GenericService<Student, StudentRepository> {
	
	private final UserStudentRepository userStudentRepository;
	private final UserRepository userRepository;
	
	public StudentService(StudentRepository repository,
			UserStudentRepository userStudentRepository,
			UserRepository userRepository) {
		super(repository);
		this.userStudentRepository = userStudentRepository;
		this.userRepository = userRepository;
	}
	
	
	@Override
	@Transactional
	public Student update(Student student) {
		Student persistedStudent = findById(student.getId());
		if(ContactDataUtils.mailsHaveChanged(student.getContactData(), persistedStudent.getContactData())) {
			UserStudent userStudent = userStudentRepository.findByStudentId(student.getId());
			User user = userStudent.getUser();
			user.setEmail(student.getContactData().getDefaultEmail());
			userRepository.create(user);
		}
		
		persistedStudent.setNames(student.getNames());
		persistedStudent.setLastname(student.getLastname());
		persistedStudent.getContactData().setEmails(student.getContactData().getEmails());
		persistedStudent.setIdentificationNumber(student.getIdentificationNumber());
		
		return this.repository.update(persistedStudent);
	}
	

}
