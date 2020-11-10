package com.mvalls.sidged.core.services;

import org.springframework.transaction.annotation.Transactional;

import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.model.users.User;
import com.mvalls.sidged.core.model.users.UserTeacher;
import com.mvalls.sidged.core.repositories.TeacherRepository;
import com.mvalls.sidged.core.repositories.UserRepository;
import com.mvalls.sidged.core.repositories.UserTeacherRepository;
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
public class TeacherService extends GenericService<Teacher, TeacherRepository>{
	
	private final UserTeacherRepository userTeacherRepository;
	private final UserRepository userRepository;

	public TeacherService(TeacherRepository repository,
			UserTeacherRepository userTeacherRepository, UserRepository userRepository) {
		super(repository);
		this.userTeacherRepository = userTeacherRepository;
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public Teacher update(Teacher teacher) {
		Teacher persistedTeacher = findById(teacher.getId());
		if(ContactDataUtils.mailsHaveChanged(teacher.getContactData(), persistedTeacher.getContactData())) {
			UserTeacher userTeacher = userTeacherRepository.findByTeacherId(teacher.getId());
			User user = userTeacher.getUser();
			user.setEmail(teacher.getContactData().getDefaultEmail());
			userRepository.create(user);
		}
		
		persistedTeacher.setNames(teacher.getNames());
		persistedTeacher.setLastname(teacher.getLastname());
		persistedTeacher.getContactData().setEmails(teacher.getContactData().getEmails());
		
		return this.repository.create(persistedTeacher);
	}
	
}
