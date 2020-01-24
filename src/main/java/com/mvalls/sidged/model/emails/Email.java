package com.mvalls.sidged.model.emails;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Email {
	
	private String to;
	private String cc;
	private String subject;
	private String message;

}
