package com.mvalls.sidged.database.dtos;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDependenciesDTO {

	private SubjectDTO subject;
	private List<SubjectDTO> dependencies;
	
	public String getDependenciesIds() {
		return String.join(",", 
				this.dependencies.stream()
				.map(dep -> dep.getId().toString())
				.collect(Collectors.toList()));
	}	

}
