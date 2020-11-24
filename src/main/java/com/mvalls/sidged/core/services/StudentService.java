package com.mvalls.sidged.core.services;

import java.util.List;

import com.mvalls.sidged.core.enums.UpdateAction;
import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.core.model.users.User;
import com.mvalls.sidged.core.model.users.UserStudent;
import com.mvalls.sidged.core.repositories.StudentRepository;
import com.mvalls.sidged.core.repositories.UserStudentRepository;

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
	
	public StudentService(StudentRepository repository,
			UserStudentRepository userStudentRepository) {
		super(repository);
		this.userStudentRepository = userStudentRepository;
	}
	
	
	@Override
	public Student update(Student student) {
		UserStudent userStudent = userStudentRepository.findByStudentId(student.getId());
		User user = userStudent.getUser();
		user.setEmail(student.getContactData().getDefaultEmail());

		Student persistedStudent = userStudent.getStudent();
		persistedStudent.setNames(student.getNames());
		persistedStudent.setLastname(student.getLastname());
		persistedStudent.getContactData().setEmails(student.getContactData().getEmails());
		persistedStudent.setIdentificationNumber(student.getIdentificationNumber());
		
		this.userStudentRepository.update(userStudent);
		return student;
	}


	public List<Student> findByCourseCode(String courseCode) {
		return this.repository.findByCourseCode(courseCode);
	}


	public List<Student> updateCourseStudent(String courseCode, Long studentId, UpdateAction action) {
		switch (action) {
			case REMOVE:
				this.repository.removeCourseStudent(courseCode, studentId);
				break;
			case ADD:
				this.repository.addCourseStudent(courseCode, studentId);
				break;
			default:
				throw new IllegalArgumentException("Action " + action + " is not supported");
		}
		return this.findByCourseCode(courseCode);
	}
	
	@Override
	public List<Student> findAll() {
		return this.repository.findAll();
	}
	
}
