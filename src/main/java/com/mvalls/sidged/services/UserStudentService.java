package com.mvalls.sidged.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mvalls.sidged.login.UserStudent;
import com.mvalls.sidged.repositories.UserStudentRepository;

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
public class UserStudentService extends GenericService<UserStudent, UserStudentRepository>{

	@Autowired
	private UserStudentRepository userStudentRepository;
	
	public UserStudent findByUsername(String username) {
		return userStudentRepository.findByUserUsername(username);
	}
	
	@Override
	protected UserStudentRepository getRepository() {
		return userStudentRepository;
	}

}
