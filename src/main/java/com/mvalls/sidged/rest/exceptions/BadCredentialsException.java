package com.mvalls.sidged.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Invalid User, Password or combination of both")
public class BadCredentialsException extends Exception{

}
