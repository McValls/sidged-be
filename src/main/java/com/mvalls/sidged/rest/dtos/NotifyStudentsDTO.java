package com.mvalls.sidged.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifyStudentsDTO {

	private Long courseId;
	private String subject;
	private String message;
	
}
