package com.mvalls.sidged.test.core.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.core.model.Subject;
import com.mvalls.sidged.core.repositories.CareerRepository;
import com.mvalls.sidged.core.repositories.SubjectRepository;
import com.mvalls.sidged.core.services.SubjectService;
import com.mvalls.sidged.database.dtos.CareerDTO;
import com.mvalls.sidged.database.dtos.SubjectDTO;
import com.mvalls.sidged.database.mybatis.mappers.SubjectDependenciesMapper;
import com.mvalls.sidged.database.mybatis.mappers.SubjectMapper;
import com.mvalls.sidged.database.repositories.SubjectDatabaseRepository;

@RunWith(MockitoJUnitRunner.class)
public class SubjectServiceTest {
	
	private SubjectService subjectService;
	@Mock private SubjectMapper subjectMapper;
	@Mock private SubjectDependenciesMapper subjectDependenciesMapper;
	@Mock private CareerRepository careerRepository;
	
	@Before
	public void setup() {
		this.initSubjectMapper();
		this.initCareerRepository();
		SubjectRepository subjectRepository = new SubjectDatabaseRepository(this.subjectMapper, this.subjectDependenciesMapper);
		this.subjectService = new SubjectService(subjectRepository, careerRepository);
	}
	
	@Test
	public void testFindAll() {
		List<Subject> subjects = this.subjectService.findAll();
		assertNotNull(subjects);
		assertFalse(subjects.isEmpty());
	}
	
	@Test
	public void testFindAllByCareerCode() {
		List<Subject> subjects = this.subjectService.findByCareerCode("Career1");
		assertNotNull(subjects);
		assertFalse(subjects.isEmpty());
	}
	
	@Test
	public void testFindByCode() {
		Subject subject = this.subjectService.findByCode("MOCKED");
		assertNotNull(subject);
		assertEquals(subject.getCode(), "MOCKED");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testFindByCodeWithNullValue() {
		this.subjectService.findByCode(null);
	}
	
	@Test
	public void createSubject() {
		Subject subject = new Subject("NEW SUBJECT", "NEW", mock(Career.class));
		
		Subject created = this.subjectService.create("NEW SUBJECT", "NEW", "Career1");
		assertNotNull(created);
		assertTrue(subject != created); // immutability
		assertNull(subject.getId());
		assertNotNull(created.getId());
		assertEquals(subject.getCode(), created.getCode());
		assertEquals(subject.getName(), created.getName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createSubjectInvalidArgs() {
		this.subjectService.create("", null, null);
	}
	
	@Test
	public void updateSubject() {
		Subject original = this.subjectService.findByCode("MOCKED");
		Subject updated = this.subjectService.update("MOCKED", "Maths UPDATED");
		assertNotNull(original);
		assertNotNull(updated);
		assertTrue(original != updated); // immutability
		assertEquals(original.getId(), updated.getId());
		assertEquals(original.getCareer(), updated.getCareer());
		assertEquals(original.getCode(), updated.getCode());
		assertNotEquals(original.getName(), updated.getName());
	}
	
	@Test
	public void deleteSubject() {
		boolean deleted = this.subjectService.delete("MOCKED");
		assertTrue(deleted);
	}
	
	@Test
	public void deleteSubjectNoExists() {
		boolean deleted = this.subjectService.delete("NOT_EXISTS!!!");
		assertFalse(deleted);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void deleteSubjectFailCodeNull() {
		this.subjectService.delete("");
	}
	
	/** INIT MOCKS **/
	private void initSubjectMapper() {

		var careerDTO = CareerDTO.builder().code("Career1").build();
		var dto1 = SubjectDTO.builder()
				.id(1L)
				.name("Maths1")
				.code("MOCKED")
				.career(careerDTO).build();
		var dto2 = SubjectDTO.builder()
				.id(2L)
				.name("Maths2")
				.code("MOCKED_2")
				.career(careerDTO).build();
		
		when(subjectMapper.findAll()).thenReturn(List.of(dto1, dto2));
		when(subjectMapper.findByCode(eq("MOCKED"))).thenReturn(dto1);
		when(subjectMapper.findByCareerCode(eq("Career1"))).thenReturn(List.of(dto1, dto2));
		when(subjectMapper.findByCareerCode(eq("Career2"))).thenReturn(List.of());
		
		doAnswer(invocation -> {
			SubjectDTO dto = (SubjectDTO) invocation.getArguments()[0];
			dto.setId(3L);
			return null;
		}).when(subjectMapper).create(any(SubjectDTO.class));
		
		when(subjectMapper.delete(eq("MOCKED"))).thenReturn(1);
	}
	
	private void initCareerRepository() {
		when(careerRepository.findByCode("Career1")).thenReturn(Career.builder()
				.id(1L)
				.code("Career1")
				.name("Mocked Careeer")
				.build());
	}
}
