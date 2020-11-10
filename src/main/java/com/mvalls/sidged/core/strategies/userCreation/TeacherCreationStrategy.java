package com.mvalls.sidged.core.strategies.userCreation;

import com.mvalls.sidged.core.model.ContactData;
import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.model.users.User;
import com.mvalls.sidged.core.model.users.UserTeacher;
import com.mvalls.sidged.core.services.UserTeacherService;
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
public class TeacherCreationStrategy implements UserCreationStrategy {
	
	private final UserTeacherService userTeacherService;
	
	public TeacherCreationStrategy(UserTeacherService userTeacherService) {
		super();
		this.userTeacherService = userTeacherService;
	}

	@Override
	public void execute(User user, SignUpVO vo) {
		ContactData contactData = new ContactData(vo.getEmail());
		Teacher teacher = new Teacher(vo.getNames(), vo.getLastname(), contactData);
		userTeacherService.create(new UserTeacher(user, teacher));
	}
	
	
}
