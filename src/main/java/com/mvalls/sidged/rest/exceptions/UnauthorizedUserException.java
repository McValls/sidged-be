package com.mvalls.sidged.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "This user has not access to the required resource")
public class UnauthorizedUserException extends Exception {
	
}
