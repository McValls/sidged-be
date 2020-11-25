package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.Time;
import com.mvalls.sidged.core.repositories.TimeRepository;
import com.mvalls.sidged.database.dtos.TimeDTO;
import com.mvalls.sidged.database.mybatis.mappers.TimeMapper;

public class TimeDatabaseRepository	implements TimeRepository {

	private final TimeMapper timeMapper;
	
	public TimeDatabaseRepository(TimeMapper timeMapper) {
		super();
		this.timeMapper = timeMapper;
	}

	@Override
	public Time findById(Long id) {
		TimeDTO dto = this.timeMapper.findTimeById(id);
		return Time.builder()
				.id(dto.getId())
				.since(dto.getSince())
				.until(dto.getUntil())
				.build();
	}

	@Override
	public List<Time> findAll() {
		List<TimeDTO> dtos = this.timeMapper.findAll();
		return dtos.stream()
				.map(dto -> Time.builder()
						.id(dto.getId())
						.since(dto.getSince())
						.until(dto.getUntil())
						.build())
				.collect(Collectors.toList());
	}
	
}
