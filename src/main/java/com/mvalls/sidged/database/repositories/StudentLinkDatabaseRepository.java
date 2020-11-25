package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.StudentLink;
import com.mvalls.sidged.core.repositories.StudentLinkRepository;
import com.mvalls.sidged.database.dtos.StudentLinkDTO;
import com.mvalls.sidged.database.mybatis.mappers.StudentLinkMapper;

public class StudentLinkDatabaseRepository implements StudentLinkRepository {
	
	private final StudentLinkMapper studentLinkMapper;
	
	public StudentLinkDatabaseRepository(StudentLinkMapper studentLinkMapper) {
		this.studentLinkMapper = studentLinkMapper;
	}
	
	@Override
	public List<StudentLink> findAll() {
		List<StudentLinkDTO> dtos = this.studentLinkMapper.findAll();
		return dtos.stream()
				.map(dto -> StudentLink.builder()
						.id(dto.getId())
						.link(dto.getLink())
						.title(dto.getTitle())
						.build())
				.collect(Collectors.toList());
	}

}
