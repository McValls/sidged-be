package com.mvalls.sidged.core.services;

import com.mvalls.sidged.core.model.users.UserTeacher;
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
public class UserTeacherService extends GenericService<UserTeacher, UserTeacherRepository>{


	public UserTeacherService(UserTeacherRepository repository) {
		super(repository);
	}

	public UserTeacher findByUsername(String username) {
		return this.repository.findByUserUsername(username);
	}
		
}
