package com.mvalls.sidged.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class Subject implements Model {
	
	@Setter private Long id;
	@NonNull private final String name;
	@NonNull private final String code;
	@NonNull private final Career career;

}
