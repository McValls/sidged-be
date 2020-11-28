package com.mvalls.sidged.core.services;

import java.util.List;

import com.mvalls.sidged.core.enums.UpdateAction;
import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.model.users.User;
import com.mvalls.sidged.core.model.users.UserTeacher;
import com.mvalls.sidged.core.repositories.TeacherRepository;
import com.mvalls.sidged.core.repositories.UserTeacherRepository;

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
public class TeacherService {
	
	private final TeacherRepository teacherRepository;
	private final UserTeacherRepository userTeacherRepository;

	public TeacherService(TeacherRepository teacherRepository,
			UserTeacherRepository userTeacherRepository) {
		this.teacherRepository = teacherRepository;
		this.userTeacherRepository = userTeacherRepository;
	}

	public Teacher update(Teacher aTeacher) {
		UserTeacher userTeacher = this.userTeacherRepository.findByTeacherId(aTeacher.getId());

		User user = userTeacher.getUser();
		user.setEmail(aTeacher.getContactData().getDefaultEmail());
		
		Teacher teacher = userTeacher.getTeacher();
		teacher.setNames(aTeacher.getNames());
		teacher.setLastname(aTeacher.getLastname());
		teacher.getContactData().setEmails(aTeacher.getContactData().getEmails());
		
		this.userTeacherRepository.update(userTeacher);
		return teacher;
	}

	public List<Teacher> findByCourseCode(String courseCode) {
		return this.teacherRepository.findByCourseCode(courseCode);
	}

	public List<Teacher> updateCourseTeacher(String courseCode, Long teacherId, UpdateAction action) {
		switch (action) {
			case REMOVE:
				this.teacherRepository.removeCourseTeacher(courseCode, teacherId);
				break;
			case ADD:
				this.teacherRepository.addCourseTeacher(courseCode, teacherId);
				break;
			default:
				throw new IllegalArgumentException("Action " + action + " not supported");
		}
		return this.findByCourseCode(courseCode);
	}
	
	public List<Teacher> findAll() {
		return this.teacherRepository.findAll();
	}
	
}
