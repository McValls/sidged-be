package com.mvalls.sidged.core.services;

import java.util.Collection;
import java.util.List;

import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.model.Course.CourseBuilder;
import com.mvalls.sidged.core.model.Period;
import com.mvalls.sidged.core.model.PeriodType;
import com.mvalls.sidged.core.model.Time;
import com.mvalls.sidged.core.repositories.CareerRepository;
import com.mvalls.sidged.core.repositories.CourseRepository;

/**
 * 
 * @author Marcelo Valls
 * 
 *         This file is part of SIDGED-Backend.
 * 
 *         SIDGED-Backend is free software: you can redistribute it and/or
 *         modify it under the terms of the GNU General Public License as
 *         published by the Free Software Foundation, either version 3 of the
 *         License, or (at your option) any later version.
 * 
 *         SIDGED-Backend is distributed in the hope that it will be useful, but
 *         WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *         General Public License for more details.
 * 
 *         You should have received a copy of the GNU General Public License
 *         along with SIDGED-Backend. If not, see
 *         <https://www.gnu.org/licenses/>.
 *
 */
public class CourseService {

	private final CourseRepository courseRepository;
	private final CareerRepository careerRepository;
	private final TimeService timeService;
	private final PeriodService periodService;

	public CourseService(CourseRepository courseRepository, 
			TimeService timeService, 
			PeriodService periodService,
			CareerRepository careerRepository) {
		this.courseRepository = courseRepository;
		this.timeService = timeService;
		this.periodService = periodService;
		this.careerRepository = careerRepository;
	}
	
	public List<Course> findAll() {
		return this.courseRepository.findAll();
	}

	public void createCourse(CourseBuilder courseBuilder, Integer periodNumber, PeriodType periodType, Long timeSinceId,
			Long timeUntilId, String careerCode) {
		Career career = this.careerRepository.findByCode(careerCode);
		Time timeSince = timeService.findById(timeSinceId);
		Time timeUntil = timeService.findById(timeUntilId);
		Period period = periodService.findByTypeAndNumber(periodType, periodNumber);
		
		courseBuilder.career(career)
			.timeStart(timeSince)
			.timeEnd(timeUntil)
			.period(period);

		this.courseRepository.create(courseBuilder.build());
	}

	public Collection<Course> findByTeacher(Long teacherId) {
		return this.courseRepository.findByTeacherId(teacherId);
	}

	public Collection<Course> findByStudent(Long studentId) {
		return this.courseRepository.findByStudentsId(studentId);
	}

}
