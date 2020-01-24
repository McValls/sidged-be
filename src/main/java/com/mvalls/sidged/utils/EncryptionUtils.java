package com.mvalls.sidged.utils;

import java.util.UUID;
import java.util.stream.Stream;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

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
