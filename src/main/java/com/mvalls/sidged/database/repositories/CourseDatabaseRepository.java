package com.mvalls.sidged.database.repositories;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.model.CourseClass;
import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.core.model.Teacher;
import com.mvalls.sidged.core.repositories.CourseClassRepository;
import com.mvalls.sidged.core.repositories.CourseRepository;
import com.mvalls.sidged.core.repositories.StudentRepository;
import com.mvalls.sidged.core.repositories.TeacherRepository;
import com.mvalls.sidged.database.dtos.CourseDTO;
import com.mvalls.sidged.database.mybatis.mappers.CourseMapper;
import com.mvalls.sidged.database.repositories.mappers.CourseRepositoryDTOMapper;

public class CourseDatabaseRepository implements CourseRepository {

	private final CourseMapper courseMapper;
	private final CourseRepositoryDTOMapper dtoMapper = new CourseRepositoryDTOMapper();
	
	private final CourseClassRepository courseClassRepository;
	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;
	
	public CourseDatabaseRepository(CourseMapper courseMapper, CourseClassRepository courseClassRepository,
			StudentRepository studentRepository, TeacherRepository teacherRepository) {
		super();
		this.courseMapper = courseMapper;
		this.courseClassRepository = courseClassRepository;
		this.studentRepository = studentRepository;
		this.teacherRepository = teacherRepository;
	}

	@Override
	public Course create(Course course) {
		CourseDTO dto = dtoMapper.modelToDto(course);
		this.courseMapper.insert(dto);
		return course;
	}
	
	@Override
	public List<Course> findByTeacherId(Long teacherId) {
		return this.processDtos(() -> this.courseMapper.findByTeacherId(teacherId));
	}
	
	@Override
	public List<Course> findByStudentsId(Long studentId) {
		return this.processDtos(() -> this.courseMapper.findByStudentId(studentId));
	}

	@Override
	public Optional<Course> findByCourseClassId(Long courseClassId) {
		Optional<CourseDTO> dto = this.courseMapper.findByCourseClassId(courseClassId);
		Optional<Course> course = dto.map(dtoMapper::dtoToModel);
		course.ifPresent(c -> this.fillInCourse(c));
		return course;
	}
	
	@Override
	public List<Course> findByYear(Integer year) {
		return this.processDtos(() -> this.courseMapper.findByYear(year));
	}
	
	@Override
	public Optional<Course> findByCode(String code) {
		Optional<CourseDTO> dto = this.courseMapper.findByCode(code);
		Optional<Course> course = dto.map(dtoMapper::dtoToModel);
		course.ifPresent(c -> this.fillInCourse(c));
		return course;
	}
	
	@Override
	public List<Course> findAll() {
		return this.processDtos(() -> this.courseMapper.findAll());
	}
	
	private List<Course> processDtos(Supplier<List<CourseDTO>> supplier) {
		List<Course> courses = supplier.get().stream()
				.map(dtoMapper::dtoToModel)
				.collect(Collectors.toList());
		this.fillInCourses(courses);
		return courses;
	}
	
	private void fillInCourses(List<Course> courses) {
		for (Course course : courses) {
			this.fillInCourse(course);
		}
	}
	
	private void fillInCourse(Course course) {
		this.addClasses(course);
		this.addStudents(course);
		this.addTeachers(course);
	}
	
	private void addClasses(Course course) {
		List<CourseClass> classes = this.courseClassRepository.findByCourseCode(course.getCode());
		course.getClasses().addAll(classes);
	}
	
	private void addStudents(Course course) {
		List<Student> students = this.studentRepository.findByCourseCode(course.getCode());
		course.getStudents().addAll(students);
	}
	
	private void addTeachers(Course course) {
		List<Teacher> teachers = this.teacherRepository.findByCourseCode(course.getCode());
		course.getTeachers().addAll(teachers);
	}

}
