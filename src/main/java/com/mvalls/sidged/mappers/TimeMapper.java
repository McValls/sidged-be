package com.mvalls.sidged.mappers;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.mvalls.sidged.model.Time;
import com.mvalls.sidged.rest.dtos.TimeDTO;

@Component
public class TimeMapper extends GenericMapper<Time, TimeDTO>{

	@Override
	public TimeDTO map(Time time) {
		TimeDTO dto = TimeDTO.builder()
				.id(time.getId())
				.since(format(time.getSince()))
				.until(format(time.getUntil()))
				.build();
		return dto;
	}
	
	private String format(LocalTime localTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		return localTime.format(formatter);
	}
}
