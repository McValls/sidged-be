package com.mvalls.sidged.converters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

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
