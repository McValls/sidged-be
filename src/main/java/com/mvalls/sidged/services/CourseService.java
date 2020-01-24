package com.mvalls.sidged.services;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mvalls.sidged.analysis.PresentAnalysisCalculator;
import com.mvalls.sidged.enums.UpdateAction;
import com.mvalls.sidged.mappers.CourseVOtoModelMapper;
import com.mvalls.sidged.model.Career;
import com.mvalls.sidged.model.Course;
import com.mvalls.sidged.model.Period;
import com.mvalls.sidged.model.Student;
import com.mvalls.sidged.model.Teacher;
import com.mvalls.sidged.model.Time;
import com.mvalls.sidged.model.analytics.CoursePresentismData;
import com.mvalls.sidged.model.analytics.PresentPercentages;
import com.mvalls.sidged.model.analytics.PresentismAnalysisData;
import com.mvalls.sidged.repositories.CourseRepository;
import com.mvalls.sidged.strategies.actions.UpdateActionStrategy;
import com.mvalls.sidged.strategies.actions.students.StudentUpdateActionStrategyFactory;
import com.mvalls.sidged.strategies.actions.teachers.TeacherUpdateActionStrategyFactory;
import com.mvalls.sidged.valueObjects.CourseVO;

@Service
public class CourseService extends GenericService<Course, CourseRepository> {

	@Autowired private CourseRepository courseRepository;
	@Autowired private TeacherService teacherService;
	@Autowired private StudentService studentService;
	@Autowired private TimeService timeService;
	@Autowired private PeriodService periodService;
	@Autowired private CareerService careerService;
	@Autowired private CourseVOtoModelMapper courseVOtoModelMapper;
	@Autowired private PresentAnalysisCalculator presentAnalysisCalculator;
	
	public Collection<Student> findStudentsByCourse(Long courseId) {
		return courseRepository.getOne(courseId).getStudents();
	}
	
	public Collection<Teacher> findTeachersByCourse(Long courseId) {
		return courseRepository.getOne(courseId).getTeachers();
	}

	@Transactional
	public Collection<Teacher> updateTeacher(Long courseId, Teacher teacher, UpdateAction action) {
		Course course = this.findById(courseId);
		teacher = teacherService.findById(teacher.getId());
		Collection<Teacher> teachersOfTheCourse = course.getTeachers();
		UpdateActionStrategy<Teacher> updateStrategy = TeacherUpdateActionStrategyFactory.getInstance(action);
		updateStrategy.execute(teacher, teachersOfTheCourse);
		
		this.update(course);
		
		return teachersOfTheCourse;
	}
	
	public Collection<Student> updateStudent(Long courseId, Student student, UpdateAction action) {
		Course course = this.findById(courseId);
		student = studentService.findById(student.getId());
		Collection<Student> studentsOfTheCourse = course.getStudents();
		UpdateActionStrategy<Student> updateStrategy = StudentUpdateActionStrategyFactory.getInstance(action);
		updateStrategy.execute(student, studentsOfTheCourse);
		
		this.update(course);
		
		return studentsOfTheCourse;
	}
	
	public void createCourse(CourseVO courseVO) {
		Course course = courseVOtoModelMapper.map(courseVO);
		Time timeSince = timeService.findById(courseVO.getTimeSinceId());
		Time timeUntil = timeService.findById(courseVO.getTimeUntilId());
		Period period = periodService.findByTypeAndNumber(courseVO.getPeriodType(), courseVO.getPeriodNumber());
		Career career = careerService.findById(courseVO.getCareerId());
		
		course.setTimeStart(timeSince);
		course.setTimeEnd(timeUntil);
		course.setPeriod(period);
		course.setCareer(career);
		
		courseRepository.save(course);
	}
	
	public CoursePresentismData getPresentismByCourseGroupedByMonth(Long courseId){
		Course course = findById(courseId);
		return presentAnalysisCalculator.getPresentismByCourseGroupedByMonth(course);
	}
	
	public PresentismAnalysisData getPresentismAnalysis(Long courseId) {
		Course course = findById(courseId);
		List<PresentPercentages> presentPercentagesByClasses = presentAnalysisCalculator.getPresentPercentagesByClasses(course);
		
		PresentismAnalysisData analysisData = new PresentismAnalysisData();
		analysisData.setPercentagesByClassNumber(presentPercentagesByClasses);
		analysisData.setCourseId(courseId);
		analysisData.setCourseName(course.getName());
		return analysisData;
		
	}
	
	@Override
	protected CourseRepository getRepository() {
		return courseRepository;
	}

	public Collection<Course> findByTeacher(Long teacherId) {
		return courseRepository.findByTeachersId(teacherId);
	}

	public Collection<Course> findByStudent(Long studentId) {
		return courseRepository.findByStudentsId(studentId);
	}

}
