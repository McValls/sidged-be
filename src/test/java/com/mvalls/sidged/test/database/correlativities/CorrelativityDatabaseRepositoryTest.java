package com.mvalls.sidged.test.database.correlativities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.core.model.Subject;
import com.mvalls.sidged.core.model.correlativity.Correlativity;
import com.mvalls.sidged.core.repositories.CorrelativityRepository;
import com.mvalls.sidged.database.dtos.CareerDTO;
import com.mvalls.sidged.database.dtos.SubjectDTO;
import com.mvalls.sidged.database.dtos.SubjectDependenciesDTO;
import com.mvalls.sidged.database.mybatis.mappers.SubjectDependenciesMapper;
import com.mvalls.sidged.database.mybatis.mappers.SubjectMapper;
import com.mvalls.sidged.database.repositories.CorrelativityDatabaseRepository;

@RunWith(MockitoJUnitRunner.class)
public class CorrelativityDatabaseRepositoryTest {
	
	private CorrelativityRepository correlativityRepository;
	
	@Mock private SubjectDependenciesMapper subjectDependenciesMapper;
	@Mock private SubjectMapper subjectMapper;
	
	@Before
	public void setup() {
		var mockedCareer = CareerDTO.builder().name("MOCKED")
				.code("MOCKED")
				.id(1L)
				.build();
		var english1 = SubjectDTO.builder().id(1L).name("English A").code("en1").career(mockedCareer).build();
		var english2 = SubjectDTO.builder().id(2L).name("English B").code("en2").career(mockedCareer).build();
		var sd1 = new SubjectDependenciesDTO(english1, List.of());
		var sd2 = new SubjectDependenciesDTO(english2, List.of(english1), "1");
		
		when(subjectDependenciesMapper.findByCareerCode(eq("MOCKED")))
			.thenReturn(List.of(sd1, sd2));
		
		when(subjectDependenciesMapper.findBySubjectId(eq(english1)))
			.thenReturn(Optional.empty());
		
		when(subjectDependenciesMapper.findBySubjectId(eq(english2)))
			.thenReturn(Optional.of(sd2));
		
		when(subjectDependenciesMapper.findBySubjectCode("en2"))
			.thenReturn(Optional.of(sd2));
		
		when(subjectDependenciesMapper.findBySubjectCode("en1"))
			.thenReturn(Optional.empty());
		
		when(subjectMapper.findAllByIds(eq(new long[] {1L}))).thenReturn(List.of(english1));
		when(subjectMapper.findAllByIds(eq(new long[] {2L}))).thenReturn(List.of(english2));
		when(subjectMapper.findAllByIds(eq(new long[] {1L, 2L}))).thenReturn(List.of(english1, english2));
		when(subjectMapper.findAllByIds(eq(new long[] {2L, 1L}))).thenReturn(List.of(english2, english1));
		
		this.correlativityRepository = new CorrelativityDatabaseRepository(subjectDependenciesMapper, subjectMapper);
	}
	
	@Test
	public void findAllByCareer() {
		List<Correlativity> correlativities = this.correlativityRepository.findAllByCareerCode("MOCKED");
		
		var aCareer = Career.builder().name("MOCKED").code("MOCKED").build();
		var correlativityEnglish1 = new Correlativity(new Subject("English A", "en1", 
				aCareer), List.of());
		var correlativityEnglish2 = new Correlativity(new Subject("English B", "en2",
				aCareer), List.of(correlativityEnglish1));
		
		assertNotNull(correlativities);
		assertEquals(2, correlativities.size());
		assertTrue(correlativities.contains(correlativityEnglish1));
		assertTrue(correlativities.contains(correlativityEnglish2));
	}

	@Test
	public void findBySubjectCode() {
		var aCareer = Career.builder().name("MOCKED").code("MOCKED").build();
		var correlativityEnglish1 = new Correlativity(new Subject("English A", "en1", 
				aCareer), List.of());
		var correlativityEnglish2 = new Correlativity(new Subject("English B", "en2",
				aCareer), List.of(correlativityEnglish1));
		
		Optional<Correlativity> correlativity = this.correlativityRepository.findBySubjectCode("en2");
		assertTrue(correlativity.isPresent());
		assertEquals(correlativityEnglish2, correlativity.get());
	}
	
	@Test
	public void addCorrelativity() {
		var aCareer = Career.builder().name("MOCKED").code("MOCKED").build();
		var en1 = new Subject("English A", "en1", aCareer);
		var en2 = new Subject("English B", "en2", aCareer);
		var correlativityEnglish1 = new Correlativity(en1, List.of());
		var correlativityEnglish2 = new Correlativity(en2, List.of(correlativityEnglish1));
		
		Correlativity correlativity = this.correlativityRepository.addCorrelativity(en2, en1);
		assertEquals(correlativityEnglish2, correlativity);
	}
	
	@Test
	public void deleteCorrelativity() {
		var aCareer = Career.builder().name("MOCKED").code("MOCKED").build();
		var en1 = new Subject("English A", "en1", aCareer);
		var en2 = new Subject("English B", "en2", aCareer);
		
		Correlativity aCorrelativity =
				this.correlativityRepository.findBySubjectCode("en2").get(); 
		
		Correlativity updateCorrelativity =
				this.correlativityRepository.deleteCorrelativity(en2, en1);
		
		assertEquals(aCorrelativity.getSubject(), updateCorrelativity.getSubject());
		assertEquals(aCorrelativity.getDependencies().size() - 1, updateCorrelativity.getDependencies().size());
		assertTrue(aCorrelativity.dependsOn(en1));
		assertFalse(updateCorrelativity.dependsOn(en1));
	}
}
