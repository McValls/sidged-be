package com.mvalls.sidged.converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String>{

	@Override
	public String convertToDatabaseColumn(List<String> attribute) {
		if(attribute == null) {
			return null;
		}
		return attribute.stream().collect(Collectors.joining(","));
	}

	@Override
	public List<String> convertToEntityAttribute(String dbData) {
		if(dbData == null) {
			return new ArrayList<>();
		}
		return Arrays.asList(dbData.split(","));
	}

}
