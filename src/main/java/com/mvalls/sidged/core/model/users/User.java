package com.mvalls.sidged.core.model.users;

import java.time.LocalDateTime;

import com.mvalls.sidged.core.model.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Model {

	private Long id;
	private String username;
	private String password;
	private String email;
	private UserType userType;
	private UserStatus userStatus;
	private LocalDateTime lastTimeLogged;
	private LocalDateTime lastTimeModified;

}
