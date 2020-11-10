package com.mvalls.sidged.core.model;

import java.util.ArrayList;
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
public class ContactData implements Identifiable {
	
	private Long id;
	@Builder.Default
	private Collection<String> emails = new ArrayList<>();
	@Builder.Default
	private Collection<String> phones = new ArrayList<>();
	
	public ContactData() {}
	
	public ContactData(String email) {
		super();
		this.emails = new ArrayList<>();
		this.emails.add(email);
		this.phones = new ArrayList<>();
	}
	
	public String getDefaultEmail() {
		if(emails.isEmpty()) {
			return null;
		}
		return emails.iterator().next();
	}


}
