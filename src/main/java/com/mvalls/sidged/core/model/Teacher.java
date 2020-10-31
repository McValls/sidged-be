package com.mvalls.sidged.core.model;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@AllArgsConstructor
public class Teacher {

	private Long id;
	private String legacyNumber;
	private String names;
	private String lastname;
	private ContactData contactData;
	private Collection<Course> courses;
	
	public Teacher() {}
	
	public Teacher(String names, String lastname, ContactData contactData) {
		super();
		this.names = names;
		this.lastname = lastname;
		this.contactData = contactData;
	}

}
