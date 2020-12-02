package com.mvalls.sidged;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mvalls.sidged.batch.DesertorBatch;
import com.mvalls.sidged.core.analysis.PresentAnalysisCalculator;
import com.mvalls.sidged.core.repositories.CareerRepository;
import com.mvalls.sidged.core.repositories.ClassFileDocumentRepository;
import com.mvalls.sidged.core.repositories.ClassStudentPresentRepository;
import com.mvalls.sidged.core.repositories.ContactDataRepository;
import com.mvalls.sidged.core.repositories.CourseClassRepository;
import com.mvalls.sidged.core.repositories.CourseRepository;
import com.mvalls.sidged.core.repositories.PeriodRepository;
import com.mvalls.sidged.core.repositories.StudentLinkRepository;
import com.mvalls.sidged.core.repositories.StudentRepository;
import com.mvalls.sidged.core.repositories.SubjectRepository;
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
import com.mvalls.sidged.core.services.NotificationService;
import com.mvalls.sidged.core.services.PeriodService;
import com.mvalls.sidged.core.services.PresentismDataService;
import com.mvalls.sidged.core.services.StudentLinkService;
import com.mvalls.sidged.core.services.StudentService;
import com.mvalls.sidged.core.services.SubjectService;
import com.mvalls.sidged.core.services.TeacherService;
import com.mvalls.sidged.core.services.TimeService;
import com.mvalls.sidged.core.services.UserStudentService;
import com.mvalls.sidged.core.services.UserTeacherService;
import com.mvalls.sidged.core.strategies.userCreation.StudentCreationStrategy;
import com.mvalls.sidged.core.strategies.userCreation.TeacherCreationStrategy;
import com.mvalls.sidged.core.strategies.userCreation.UserCreationStrategyService;
import com.mvalls.sidged.database.mybatis.mappers.CareerMapper;
import com.mvalls.sidged.database.mybatis.mappers.ClassFileDocumentMapper;
import com.mvalls.sidged.database.mybatis.mappers.ClassStudentPresentMapper;
import com.mvalls.sidged.database.mybatis.mappers.ContactDataMapper;
import com.mvalls.sidged.database.mybatis.mappers.CourseClassMapper;
import com.mvalls.sidged.database.mybatis.mappers.CourseMapper;
import com.mvalls.sidged.database.mybatis.mappers.PeriodMapper;
import com.mvalls.sidged.database.mybatis.mappers.StudentLinkMapper;
import com.mvalls.sidged.database.mybatis.mappers.StudentMapper;
import com.mvalls.sidged.database.mybatis.mappers.SubjectMapper;
import com.mvalls.sidged.database.mybatis.mappers.TeacherMapper;
import com.mvalls.sidged.database.mybatis.mappers.TimeMapper;
import com.mvalls.sidged.database.mybatis.mappers.UserMapper;
import com.mvalls.sidged.database.mybatis.mappers.UserStudentMapper;
import com.mvalls.sidged.database.mybatis.mappers.UserTeacherMapper;
import com.mvalls.sidged.database.repositories.CareerDatabaseRepository;
import com.mvalls.sidged.database.repositories.ClassFileDocumentDatabaseRepository;
import com.mvalls.sidged.database.repositories.ClassStudentPresentDatabaseRepository;
import com.mvalls.sidged.database.repositories.ContactDataDatabaseRepository;
import com.mvalls.sidged.database.repositories.CourseClassDatabaseRepository;
import com.mvalls.sidged.database.repositories.CourseDatabaseRepository;
import com.mvalls.sidged.database.repositories.PeriodDatabaseRepository;
import com.mvalls.sidged.database.repositories.StudentDatabaseRepository;
import com.mvalls.sidged.database.repositories.StudentLinkDatabaseRepository;
import com.mvalls.sidged.database.repositories.SubjectDatabaseRepository;
import com.mvalls.sidged.database.repositories.TeacherDatabaseRepository;
import com.mvalls.sidged.database.repositories.TimeDatabaseRepository;
import com.mvalls.sidged.database.repositories.UserDatabaseRepository;
import com.mvalls.sidged.database.repositories.UserStudentDatabaseRepository;
import com.mvalls.sidged.database.repositories.UserTeacherDatabaseRepository;

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
@EnableTransactionManagement
@Configuration
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	@Autowired private Environment env;
	
	@Autowired private CareerMapper careerMapper;
	@Autowired private ClassStudentPresentMapper classStudentPresentMapper;
	@Autowired private ContactDataMapper contactDataMapper;
	@Autowired private CourseClassMapper courseClassMapper;
	@Autowired private CourseMapper courseMapper;
	@Autowired private StudentMapper studentMapper;
	@Autowired private TeacherMapper teacherMapper;
	@Autowired private UserMapper userMapper;
	@Autowired private UserStudentMapper userStudentMapper;
	@Autowired private UserTeacherMapper userTeacherMapper;
	@Autowired private ClassFileDocumentMapper classFileDocumentMapper;
	@Autowired private StudentLinkMapper studentLinkMapper;
	@Autowired private PeriodMapper periodMapper;
	@Autowired private TimeMapper timeMapper;
	@Autowired private SubjectMapper subjectMapper;
	
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
		return new DesertorService(emailsService(), notificationService(), courseRepository());
	}
	
	@Bean
	public CourseClassService courseClassService() {
		return new CourseClassService(courseClassRepository(), 
				courseRepository(), 
				classStudentPresentRepository(), 
				teacherRepository());
	}
	
	@Bean
	public CourseService courseService() {
		return new CourseService(courseRepository(), 
				timeService(), 
				periodService(), 
				subjectRepository());
	}
	
	@Bean
	public StudentService studentService() {
		return new StudentService(studentRepository(), userStudentRepository());
	}
	
	@Bean
	public TeacherService teacherService() {
		return new TeacherService(teacherRepository(), userTeacherRepository());
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
		return new LoginService(userRepository(), 
				userStudentRepository(), 
				userTeacherRepository(), 
				userCreationStrategyService(), 
				emailsService());
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
		return new ClassFileDocumentService(classFileDocumentRepository(), courseClassRepository());
	}
	
	@Bean
	public ClassStudentPresentService classStudentPresentService() {
		return new ClassStudentPresentService(classStudentPresentRepository(), teacherRepository());
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
	
	@Bean
	public PresentismDataService presentismDataService() {
		return new PresentismDataService(courseRepository(), courseClassRepository(), presentAnalysisCalculator());
	}
	
	@Bean
	public NotificationService notificationService() {
		return new NotificationService(emailsService(), 
				courseRepository(),
				this.env.getProperty("email.institution.account"),
				this.env.getProperty("email.desertors.subject"));
	}
	
	@Bean
	public SubjectService subjectService() {
		return new SubjectService(subjectRepository(), careerRepository());
	}
	
	/***** REPOSITORIES *********/
	
	@Bean
	public CourseRepository courseRepository() {
		return new CourseDatabaseRepository(courseMapper,
				courseClassRepository(),
				studentRepository(),
				teacherRepository());
	}
	
	@Bean
	public CourseClassRepository courseClassRepository() {
		return new CourseClassDatabaseRepository(courseClassMapper, 
				classStudentPresentRepository());
	}
	
	@Bean
	public StudentRepository studentRepository() {
		return new StudentDatabaseRepository(studentMapper, contactDataRepository());
	}
	
	@Bean
	public TeacherRepository teacherRepository() {
		return new TeacherDatabaseRepository(teacherMapper, contactDataRepository());
	}
	
	@Bean
	public ContactDataRepository contactDataRepository() {
		return new ContactDataDatabaseRepository(contactDataMapper);
	}
	
	@Bean
	public UserRepository userRepository() {
		return new UserDatabaseRepository(userMapper);
	}
	
	@Bean
	public UserStudentRepository userStudentRepository() {
		return new UserStudentDatabaseRepository(userRepository(), studentRepository(), userStudentMapper);
	}
	
	@Bean
	public UserTeacherRepository userTeacherRepository() {
		return new UserTeacherDatabaseRepository(userRepository(), teacherRepository(), userTeacherMapper);
	}
	
	@Bean
	public TimeRepository timeRepository() {
		return new TimeDatabaseRepository(timeMapper);
	}
	
	@Bean
	public PeriodRepository periodRepository() {
		return new PeriodDatabaseRepository(periodMapper);
	}
	
	@Bean
	public CareerRepository careerRepository() {
		return new CareerDatabaseRepository(careerMapper);
	}
	
	@Bean
	public ClassFileDocumentRepository classFileDocumentRepository() {
		return new ClassFileDocumentDatabaseRepository(classFileDocumentMapper);
	}
	
	@Bean
	public ClassStudentPresentRepository classStudentPresentRepository() {
		return new ClassStudentPresentDatabaseRepository(classStudentPresentMapper);
	}
	
	@Bean
	public StudentLinkRepository studentLinkRepository() {
		return new StudentLinkDatabaseRepository(studentLinkMapper);
	}
	
	@Bean
	public SubjectRepository subjectRepository() {
		return new SubjectDatabaseRepository(subjectMapper);
	}
	
}