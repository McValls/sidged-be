package com.mvalls.sidged.core.model.users;

import com.mvalls.sidged.core.model.Student;

import lombok.Data;
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
@Data
public class UserStudent {
	
	private Long id;
	private User user;
	private Student student;
	
	public UserStudent() {}

	public UserStudent(User user, Student student) {
		super();
		this.user = user;
		this.student = student;
	}

}
