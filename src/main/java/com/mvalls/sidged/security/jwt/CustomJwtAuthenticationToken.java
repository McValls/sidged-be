package com.mvalls.sidged.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * 
 * @author mcvalls
 *
 * Custom authentication token with a "token" property.
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
@SuppressWarnings("serial")
public class CustomJwtAuthenticationToken extends UsernamePasswordAuthenticationToken{

	
	public CustomJwtAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}
	
	public CustomJwtAuthenticationToken(String token) {
		super(token, token);
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	private String token;

}
