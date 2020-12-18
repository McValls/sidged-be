package com.mvalls.sidged.test.database.career;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.List;

import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.core.repositories.CareerRepository;
import com.mvalls.sidged.database.dtos.CareerDTO;
import com.mvalls.sidged.database.mybatis.mappers.CareerMapper;
import com.mvalls.sidged.database.repositories.CareerDatabaseRepository;

@RunWith(MockitoJUnitRunner.class)
public class CareerDatabaseRepositoryTest {

	private CareerRepository repository;
	@Mock private CareerMapper careerMapper;
	
	@Before
	public void setup() {
		when(careerMapper.findByStudentIdentificationNumber(eq("12344321")))
			.thenReturn(List.of(
					CareerDTO.builder().id(1L).code("informatic").name("informatic").build()
					));
		this.repository = new CareerDatabaseRepository(careerMapper);
	}
	
	@Test
	public void findByStudentIdentificationNumber() {
		Career informatic = Career.builder()
				.id(1L)
				.code("informatic")
				.name("informatic")
				.build();
		List<Career> careers = this.repository.findByStudentIdentificationNumber("12344321");
		assertNotNull(careers);
		assertFalse(careers.isEmpty());
		assertEquals(1, careers.size());
		assertEquals(informatic, careers.get(0));
	}
	
}
