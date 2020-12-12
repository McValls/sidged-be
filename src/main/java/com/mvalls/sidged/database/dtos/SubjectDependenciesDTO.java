package com.mvalls.sidged.database.dtos;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import static java.util.stream.Collectors.toList;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubjectDependenciesDTO {

	private SubjectDTO subject;
	private List<SubjectDTO> dependencies;
	private String dependenciesIds;

	public SubjectDependenciesDTO(SubjectDTO subject, List<SubjectDTO> dependencies, String dependenciesIds) {
		super();
		this.subject = subject;
		this.dependencies = dependencies;
		this.dependenciesIds = dependenciesIds;
	}

	public SubjectDependenciesDTO(SubjectDTO subject, List<SubjectDTO> dependencies) {
		super();
		this.subject = subject;
		this.dependencies = dependencies;
		this.dependenciesIds = String.join(",", 
				dependencies.stream().map(d -> d.getId().toString()).collect(toList()));
	}

	public SubjectDependenciesDTO(SubjectDTO subject, String dependenciesIds) {
		super();
		this.subject = subject;
		this.dependenciesIds = dependenciesIds;
	}
	
	public long[] getDependenciesIdsAsLongArray() {
		if (StringUtils.isEmpty(dependenciesIds)) return new long[0];
		
		String[] idsStr = dependenciesIds.split(",");
		long[] ids = new long[idsStr.length];
		for (int i = 0; i < idsStr.length; i++) {
			ids[i] = Long.valueOf(idsStr[i]).longValue();
		}
		return ids;
	}

}
