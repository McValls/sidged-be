package com.mvalls.sidged;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mvalls.sidged.batch.DesertorBatch;
import com.mvalls.sidged.core.analysis.PresentAnalysisCalculator;
import com.mvalls.sidged.core.repositories.CareerRepository;
import com.mvalls.sidged.core.repositories.ClassFileDocumentRepository;
import com.mvalls.sidged.core.repositories.ClassStudentPresentRepository;
import com.mvalls.sidged.core.repositories.CourseClassRepository;
import com.mvalls.sidged.core.repositories.CourseRepository;
import com.mvalls.sidged.core.repositories.PeriodRepository;
import com.mvalls.sidged.core.repositories.StudentLinkRepository;
import com.mvalls.sidged.core.repositories.StudentRepository;
import com.mvalls.sidged.core.repositories.TeacherRepository;
import com.mvalls.sidged.core.repositories.TimeRepository;
import com.mvalls.sidged.core.repositories.UserRepository;
import com.mvalls.sidged.core.repositories.UserStudentRepository;
import com.mvalls.sidged.core.repositories.UserTeacherRepository;
import com.mvalls.sidged.core.services.CareerService;
import com.mvalls.sidged.core.services.ClassFileDocumentService;
import com.mvalls.sidged.core.services.ClassStudentPresentService;
import com.mvalls.sidged.core.services.CourseClassService;
import com.mvalls.sidged.core.services.CourseService;
import com.mvalls.sidged.core.services.DesertorService;
import com.mvalls.sidged.core.services.EmailsService;
import com.mvalls.sidged.core.services.LoginService;
import com.mvalls.sidged.core.services.PeriodService;
import com.mvalls.sidged.core.services.StudentLinkService;
import com.mvalls.sidged.core.services.StudentService;
import com.mvalls.sidged.core.services.TeacherService;
import com.mvalls.sidged.core.services.TimeService;
import com.mvalls.sidged.core.services.UserStudentService;
import com.mvalls.sidged.core.services.UserTeacherService;
import com.mvalls.sidged.core.strategies.userCreation.StudentCreationStrategy;
import com.mvalls.sidged.core.strategies.userCreation.TeacherCreationStrategy;
import com.mvalls.sidged.core.strategies.userCreation.UserCreationStrategyService;
import com.mvalls.sidged.database.mappers.CareerRepositoryDTOMapper;
import com.mvalls.sidged.database.mappers.ClassFileDocumentRepositoryDTOMapper;
import com.mvalls.sidged.database.mappers.ClassStudentPresentRepositoryDTOMapper;
import com.mvalls.sidged.database.mappers.CourseClassRepositoryDTOMapper;
import com.mvalls.sidged.database.mappers.CourseRepositoryDTOMapper;
import com.mvalls.sidged.database.mappers.PeriodRepositoryDTOMapper;
import com.mvalls.sidged.database.mappers.StudentLinkRepositoryDTOMapper;
import com.mvalls.sidged.database.mappers.StudentRepositoryDTOMapper;
import com.mvalls.sidged.database.mappers.TeacherRepositoryDTOMapper;
import com.mvalls.sidged.database.mappers.TimeRepositoryDTOMapper;
import com.mvalls.sidged.database.mappers.UserRepositoryDTOMapper;
import com.mvalls.sidged.database.mappers.UserStudentRepositoryDTOMapper;
import com.mvalls.sidged.database.mappers.UserTeacherRepositoryDTOMapper;
import com.mvalls.sidged.database.repositories.CareerDatabaseRepository;
import com.mvalls.sidged.database.repositories.ClassFileDocumentDatabaseRepository;
import com.mvalls.sidged.database.repositories.ClassStudentPresentDatabaseRepository;
import com.mvalls.sidged.database.repositories.CourseClassDatabaseRepository;
import com.mvalls.sidged.database.repositories.CourseDatabaseRepository;
import com.mvalls.sidged.database.repositories.PeriodDatabaseRepository;
import com.mvalls.sidged.database.repositories.StudentDatabaseRepository;
import com.mvalls.sidged.database.repositories.StudentLinkDatabaseRepository;
import com.mvalls.sidged.database.repositories.TeacherDatabaseRepository;
import com.mvalls.sidged.database.repositories.TimeDatabaseRepository;
import com.mvalls.sidged.database.repositories.UserDatabaseRepository;
import com.mvalls.sidged.database.repositories.UserStudentDatabaseRepository;
import com.mvalls.sidged.database.repositories.UserTeacherDatabaseRepository;
import com.mvalls.sidged.database.repositories.jpa.CareerJpaRepository;
import com.mvalls.sidged.database.repositories.jpa.ClassFileDocumentJpaRepository;
import com.mvalls.sidged.database.repositories.jpa.ClassStudentPresentJpaRepository;
import com.mvalls.sidged.database.repositories.jpa.CourseClassJpaRepository;
import com.mvalls.sidged.database.repositories.jpa.CourseJpaRepository;
import com.mvalls.sidged.database.repositories.jpa.PeriodJpaRepository;
import com.mvalls.sidged.database.repositories.jpa.StudentJpaRepository;
import com.mvalls.sidged.database.repositories.jpa.StudentLinkJpaRepository;
import com.mvalls.sidged.database.repositories.jpa.TeacherJpaRepository;
import com.mvalls.sidged.database.repositories.jpa.TimeJpaRepository;
import com.mvalls.sidged.database.repositories.jpa.UserJpaRepository;
import com.mvalls.sidged.database.repositories.jpa.UserStudentJpaRepository;
import com.mvalls.sidged.database.repositories.jpa.UserTeacherJpaRepository;
import com.mvalls.sidged.mappers.CourseVOtoModelMapper;

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
@PropertySource("classpath:email.properties")
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.mvalls.sidged.database.repositories.jpa"})
@Configuration
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	@Autowired private Environment env;
	@Autowired private CourseJpaRepository courseJpaRepository;
	@Autowired private CourseClassJpaRepository courseClassJpaRepository;
	@Autowired private StudentJpaRepository studentJpaRepositry;
	@Autowired private TeacherJpaRepository teacherJpaRepository;
	@Autowired private UserStudentJpaRepository userStudentJpaRepository;
	@Autowired private UserTeacherJpaRepository userTeacherJpaRepository;
	@Autowired private UserJpaRepository userJpaRepository;
	@Autowired private TimeJpaRepository timeJpaRepository;
	@Autowired private PeriodJpaRepository periodJpaRepository;
	@Autowired private CareerJpaRepository careerJpaRepository;
	@Autowired private ClassFileDocumentJpaRepository classFileDocumentJpaRepository;
	@Autowired private ClassStudentPresentJpaRepository classStudentPresentJpaRepository;
	@Autowired private StudentLinkJpaRepository studentLinkJpaRepository;
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
					.addMapping("/**")
					.allowedOrigins("*")
					.allowedHeaders("*")
					.allowedMethods("*")
					.exposedHeaders("Authorization", "Content-Type");
			}
		};
	}
	
	/**
	 * DEPENDENCIES CONFIG
	 */
	
	@Bean
	public PresentAnalysisCalculator presentAnalysisCalculator() {
		return new PresentAnalysisCalculator();
	}
	
	@Bean
	public DesertorBatch desertorBatch() {
		return new DesertorBatch(desertorService());
	}
	
	@Bean
	public DesertorService desertorService() {
		return new DesertorService(courseClassService(), emailsService(), this.env);
	}
	
	@Bean
	public CourseClassService courseClassService() {
		return new CourseClassService(courseClassRepository(), courseRepository());
	}
	
	@Bean
	public CourseService courseService() {
		return new CourseService(courseRepository(), 
				teacherService(),
				studentService(),
				timeService(),
				periodService(),
				courseVOtoModelMapper(),
				presentAnalysisCalculator(),
				emailsService());
	}
	
	@Bean
	public StudentService studentService() {
		return new StudentService(studentRepository(), 
				userStudentRepository(), 
				userRepository());
	}
	
	@Bean
	public TeacherService teacherService() {
		return new TeacherService(teacherRepository(),
				userTeacherRepository(),
				userRepository());
	}
	
	@Bean
	public TimeService timeService() {
		return new TimeService(timeRepository());
	}
	
	@Bean
	public PeriodService periodService() {
		return new PeriodService(periodRepository());
	}
	
	@Bean
	public CareerService careerService() {
		return new CareerService(careerRepository());
	}
	
	@Bean
	public CourseVOtoModelMapper courseVOtoModelMapper() {
		return new CourseVOtoModelMapper();
	}
	
	@Bean
	public EmailsService emailsService() {
		Properties props = new Properties();
		props.put("mail.smtp.host", this.env.getProperty("mail.smtp.host"));
		props.put("mail.smtp.port", this.env.getProperty("mail.smtp.port"));
		props.put("mail.smtp.auth", this.env.getProperty("mail.smtp.auth", "true"));
		props.put("mail.smtp.socketFactory.port", this.env.getProperty("mail.smtp.socketFactory.port", "465"));
        props.put("mail.smtp.socketFactory.class", this.env.getProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"));
        props.put("email.account", this.env.getProperty("email.account"));
        props.put("email.secured.password", this.env.getProperty("email.secured.password"));
        props.put("email.from", this.env.getProperty("email.from"));

		return new EmailsService(props);
	}
	
	@Bean
	public LoginService loginService() {
		return new LoginService(userRepository(), userCreationStrategyService(), emailsService());
	}
	
	@Bean
	public UserCreationStrategyService userCreationStrategyService() {
		return new UserCreationStrategyService(teacherCreationStrategy(), studentCreationStrategy());
	}
	
	@Bean
	public TeacherCreationStrategy teacherCreationStrategy() {
		return new TeacherCreationStrategy(userTeacherService());
	}
	
	@Bean
	public StudentCreationStrategy studentCreationStrategy() {
		return new StudentCreationStrategy(userStudentService());
	}
	
	@Bean
	public ClassFileDocumentService classFileDocumentService() {
		return new ClassFileDocumentService(classFileDocumentRepository(), courseClassRepository(), courseRepository());
	}
	
	@Bean
	public ClassStudentPresentService classStudentPresentService() {
		return new ClassStudentPresentService(classStudentPresentRepository(), courseRepository(), courseClassRepository(), presentAnalysisCalculator());
	}

	@Bean
	public UserStudentService userStudentService() {
		return new UserStudentService(userStudentRepository());
	}

	@Bean
	public UserTeacherService userTeacherService() {
		return new UserTeacherService(userTeacherRepository());
	}
	
	@Bean
	public StudentLinkService studentLinkService() {
		return new StudentLinkService(studentLinkRepository());
	}
	
	
	/***** REPOSITORIES *********/
	
	@Bean
	public CourseRepository courseRepository() {
		return new CourseDatabaseRepository(this.courseJpaRepository, new CourseRepositoryDTOMapper());
	}
	
	@Bean
	public CourseClassRepository courseClassRepository() {
		return new CourseClassDatabaseRepository(this.courseClassJpaRepository, new CourseClassRepositoryDTOMapper());
	}
	
	@Bean
	public StudentRepository studentRepository() {
		return new StudentDatabaseRepository(this.studentJpaRepositry, new StudentRepositoryDTOMapper());
	}
	
	@Bean
	public TeacherRepository teacherRepository() {
		return new TeacherDatabaseRepository(this.teacherJpaRepository, new TeacherRepositoryDTOMapper());
	}
	
	@Bean
	public UserRepository userRepository() {
		return new UserDatabaseRepository(this.userJpaRepository, new UserRepositoryDTOMapper());
	}
	
	@Bean
	public UserStudentRepository userStudentRepository() {
		return new UserStudentDatabaseRepository(this.userStudentJpaRepository, new UserStudentRepositoryDTOMapper());
	}
	
	@Bean
	public UserTeacherRepository userTeacherRepository() {
		return new UserTeacherDatabaseRepository(this.userTeacherJpaRepository, new UserTeacherRepositoryDTOMapper());
	}
	
	@Bean
	public TimeRepository timeRepository() {
		return new TimeDatabaseRepository(this.timeJpaRepository, new TimeRepositoryDTOMapper());
	}
	
	@Bean
	public PeriodRepository periodRepository() {
		return new PeriodDatabaseRepository(this.periodJpaRepository, new PeriodRepositoryDTOMapper());
	}
	
	@Bean
	public CareerRepository careerRepository() {
		return new CareerDatabaseRepository(this.careerJpaRepository, new CareerRepositoryDTOMapper());
	}
	
	@Bean
	public ClassFileDocumentRepository classFileDocumentRepository() {
		return new ClassFileDocumentDatabaseRepository(this.classFileDocumentJpaRepository, new ClassFileDocumentRepositoryDTOMapper());
	}
	
	@Bean
	public ClassStudentPresentRepository classStudentPresentRepository() {
		return new ClassStudentPresentDatabaseRepository(this.classStudentPresentJpaRepository, new ClassStudentPresentRepositoryDTOMapper());
	}
	
	@Bean
	public StudentLinkRepository studentLinkRepository() {
		return new StudentLinkDatabaseRepository(this.studentLinkJpaRepository, new StudentLinkRepositoryDTOMapper());
	}
	
}