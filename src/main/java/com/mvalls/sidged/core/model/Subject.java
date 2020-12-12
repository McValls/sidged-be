package com.mvalls.sidged.core.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false, of = {"name", "code", "career"})
@ToString(of = {"name", "code"})
public class Subject implements Model {
	
	@Setter private Long id;
	@NonNull private final String name;
	@NonNull private final String code;
	@NonNull private final Career career;

}
