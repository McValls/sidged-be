package com.mvalls.sidged.test.core.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalMatchers.not;
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
import com.mvalls.sidged.core.repositories.SubjectRepository;
import com.mvalls.sidged.core.services.CorrelativityService;

@RunWith(MockitoJUnitRunner.class)
public class CorrelativityServiceTest {
	
	private CorrelativityService service;
	@Mock private CorrelativityRepository correlativityRepository;
	@Mock private SubjectRepository subjectRepository;
	
	private Career aCareer, brokenCareer;
	private Subject en1, en2, pt1, pt2;
	private Correlativity correlativityEnglish1, correlativityEnglish2,
		correlativityPortugues2;
	
	@Before
	public void setup() {
		aCareer = Career.builder().name("MOCKED").code("MOCKED").build();
		brokenCareer = Career.builder().name("BROKEN").code("BROKEN").build();
		en1 = new Subject("English A", "en1", aCareer);
		en2 = new Subject("English B", "en2", aCareer);
		pt1 = new Subject("Portugues A", "pt1", brokenCareer);
		pt2 = new Subject("Portugues B", "pt2", brokenCareer);
		correlativityEnglish1 = new Correlativity(en1, List.of());
		correlativityEnglish2 = new Correlativity(en2, List.of(correlativityEnglish1));
		correlativityPortugues2 = new Correlativity(pt2, List.of(new Correlativity(pt1, List.of())));
		
		when(correlativityRepository.findAllByCareerCode(eq("MOCKED")))
			.thenReturn(List.of(correlativityEnglish1, correlativityEnglish2));
		
		when(correlativityRepository.findAllByCareerCode(not(eq("MOCKED"))))
			.thenReturn(List.of());
		
		when(correlativityRepository.findBySubjectCode("en1"))
			.thenReturn(Optional.of(correlativityEnglish1));
		
		when(correlativityRepository.findBySubjectCode("en2"))
			.thenReturn(Optional.of(correlativityEnglish2));
		
		when(correlativityRepository.findBySubjectCode("pt1"))
			.thenReturn(Optional.empty());
		
		when(correlativityRepository.findBySubjectCode("pt2"))
			.thenReturn(Optional.empty());
		
		when(correlativityRepository.addCorrelativity(eq(en2), eq(en1)))
			.thenReturn(correlativityEnglish2);
		
		when(correlativityRepository.addCorrelativity(eq(pt2), eq(pt1)))
			.thenReturn(correlativityPortugues2);
		
		when(correlativityRepository.deleteCorrelativity(eq(en2), eq(en1)))
			.thenReturn(new Correlativity(en2, List.of()));
		
		when(subjectRepository.findByCode(eq("en1"))).thenReturn(en1);
		when(subjectRepository.findByCode(eq("en2"))).thenReturn(en2);
		when(subjectRepository.findByCode(eq("pt1"))).thenReturn(pt1);
		when(subjectRepository.findByCode(eq("pt2"))).thenReturn(pt2);
		
		this.service = new CorrelativityService(correlativityRepository, subjectRepository);
	}

	@Test
	public void findAllByCareer() {
		List<Correlativity> correlativities = this.service.findAllByCareerCode("MOCKED");
		
		assertEquals(2, correlativities.size());
		assertTrue(correlativities.contains(correlativityEnglish1));
		assertTrue(correlativities.contains(correlativityEnglish2));
	}
	
	@Test
	public void findAllByCareerNoExisting() {
		assertTrue(this.service.findAllByCareerCode("NONE").isEmpty());
	}
	
	@Test
	public void addCorrelativity() {
		Correlativity newCorrelativity = this.service.addCorrelativity("en2", "en1");
		assertEquals(correlativityEnglish2, newCorrelativity);
	}
	
	@Test
	public void addNonExistingCorrelativity() {
		Correlativity newCorrelativity = this.service.addCorrelativity("pt2", "pt1");
		assertEquals(correlativityPortugues2, newCorrelativity);
	}
	
	@Test(expected = IllegalStateException.class)
	public void addNewCorrelativityWithDistinctsCareersShouldFail() {
		this.service.addCorrelativity("en2", "pt1");
	}
	
	@Test(expected = IllegalStateException.class)
	public void addCircularDependenciesCorrelativitiesShouldFail() {
		this.service.addCorrelativity("en1", "en2");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addCorrelativitiesToNonExistentSubjectsShouldFail() {
		this.service.addCorrelativity("chinnese1", "en2");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void addNonExistentSubjectAsCorrelativityShouldFail() {
		this.service.addCorrelativity("en2", "chinesse1");
	}
	
	@Test
	public void deleteCorrelativity() {
		var allCorrelativities = this.service.findAllByCareerCode("MOCKED");
		var updatedCorrelativity = this.service.deleteCorrelativity("en2", "en1");
		
		assertTrue(allCorrelativities.contains(correlativityEnglish2));
		assertTrue(correlativityEnglish2.dependsOn(en1));
		assertFalse(updatedCorrelativity.dependsOn(en1));
	}
	
}
