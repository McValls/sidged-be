package com.mvalls.sidged.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvalls.sidged.login.User;
import com.mvalls.sidged.login.UserTeacher;
import com.mvalls.sidged.model.Teacher;
import com.mvalls.sidged.repositories.TeacherRepository;
import com.mvalls.sidged.repositories.UserRepository;
import com.mvalls.sidged.repositories.UserTeacherRepository;
import com.mvalls.sidged.utils.ContactDataUtils;

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
@Service
public class TeacherService extends GenericService<Teacher, TeacherRepository>{
	
	@Autowired
	private TeacherRepository teacherRepository;
	
	@Autowired
	private UserTeacherRepository userTeacherRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public Teacher update(Teacher teacher) {
		Teacher persistedTeacher = findById(teacher.getId());
		if(ContactDataUtils.mailsHaveChanged(teacher.getContactData(), persistedTeacher.getContactData())) {
			UserTeacher userTeacher = userTeacherRepository.findByTeacherId(teacher.getId());
			User user = userTeacher.getUser();
			user.setEmail(teacher.getContactData().getDefaultEmail());
			userRepository.save(user);
		}
		
		persistedTeacher.setNames(teacher.getNames());
		persistedTeacher.setLastname(teacher.getLastname());
		persistedTeacher.getContactData().setEmails(teacher.getContactData().getEmails());
		
		return teacherRepository.save(persistedTeacher);
	}
	
	@Override
	protected TeacherRepository getRepository() {
		return teacherRepository;
	}

}
