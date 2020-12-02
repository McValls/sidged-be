package com.mvalls.sidged.test.core.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.AdditionalMatchers.*;

import java.util.List;

import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.core.repositories.CareerRepository;
import com.mvalls.sidged.core.services.CareerService;

@RunWith(MockitoJUnitRunner.class)
public class CareerServiceTest {
	
	private CareerService careerService;
	
	@Mock
	private CareerRepository careerRepository;
	
	@Before
	public void setup() {
		this.initMocks();
		this.careerService = new CareerService(careerRepository);
	}
	
	private void initMocks() {
		Career informatic = Career.builder()
				.id(1L)
				.code("infMock")
				.name("informatic")
				.build();
		
		Career mechanics = Career.builder()
				.id(2L)
				.code("mecMock")
				.name("mechanics")
				.build();
		
		when(careerRepository.findAll()).thenReturn(List.of(informatic, mechanics));
		when(careerRepository.findByCode("infMock")).thenReturn(informatic);
		when(careerRepository.findByCode("mecMock")).thenReturn(mechanics);
		when(careerRepository.create(any(Career.class))).thenAnswer((invocation) -> {
			Career arg = (Career) invocation.getArguments()[0];
			arg.setId(3L);
			return arg;
		});
		
		when(careerRepository.update(any(Career.class))).thenAnswer((invocation) -> {
			Career arg = (Career) invocation.getArguments()[0];
			return arg;
		});
	
		when(careerRepository.delete(any(Career.class))).thenReturn(true);
		
		when(careerRepository.findById(1L)).thenReturn(informatic);
		when(careerRepository.findById(not(eq(1L)))).thenReturn(null);
	}
	
	@Test
	public void findAllCareers() {
		List<Career> careers = this.careerService.findAll();
		assertNotNull(careers);
		assertFalse(careers.isEmpty());
		assertEquals(careers.size(), 2);
		assertEquals(careers.get(0).getCode(), "infMock");
	}
	
	@Test
	public void findById() {
		Career career = this.careerService.findById(1L);
		assertNotNull(career);
		assertEquals(career.getCode(), "infMock");
		assertEquals(career.getId(), Long.valueOf(1L));
		assertEquals(career.getName(), "informatic");
	}
	
	@Test
	public void findByIdNull() {
		Career career = this.careerService.findById(1000L);
		assertNull(career);
	}
	
	@Test
	public void createCareer() {
		Career career = Career.builder()
				.id(null)
				.code("mockCode")
				.name("mockCareer")
				.build();
		
		Career persisted = this.careerService.create(career);
		assertNotNull(persisted);
		assertNotNull(persisted.getId());
		assertEquals(career.getCode(), persisted.getCode());
		assertEquals(career.getName(), persisted.getName());
	}
	
	@Test
	public void updateCareer() {
		Career career = this.careerService.findAll().get(0);
		String originalCode = career.getCode();
		String originalName = career.getName();
		Career updated = this.careerService.update(career.getId(), originalName + "someModification");
		
		assertEquals(originalCode, updated.getCode());
		assertEquals(career.getId(), updated.getId());
		assertNotEquals(originalName, updated.getName());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void updateCareerWithoutIdMustFail() {
		Career career = this.careerService.findAll().get(0);
		career.setId(null);
		
		this.careerService.update(null, "mustFailMock");
	}
	
	@Test
	public void deleteCareer() {
		List<Career> careers = this.careerService.findAll();
		boolean deletedFirst = this.careerService.delete(careers.get(0));
		assertTrue(deletedFirst);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void deleteCareerWithoutIdMustFail() {
		this.careerService.delete(Career.builder().build());
	}

}
