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
