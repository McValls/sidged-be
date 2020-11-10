package com.mvalls.sidged.core.utils;

import java.util.UUID;
import java.util.stream.Stream;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

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
public final class EncryptionUtils {

	public static String encryptSHA256(String password) {
		if(StringUtils.isEmpty(password)) {
			throw new IllegalArgumentException("The password must be non-null and non-empty!");
		}
		return DigestUtils.sha256Hex(password);
	}

	public static String generateRandomPassword() {
		String randomUUID = UUID.randomUUID().toString();
		return Stream
				.of(randomUUID.split("-"))
				.reduce("", (a, b) -> {return a.concat(b);})
				.substring(0, 16);
	}
	
}
