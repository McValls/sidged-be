package com.mvalls.sidged.test.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.mvalls.sidged.database.dtos.CareerDTO;
import com.mvalls.sidged.database.dtos.SubjectDTO;
import com.mvalls.sidged.database.dtos.SubjectDependenciesDTO;

public class SubjectDependenciesDTOTest {
	
	private SubjectDTO englishA, englishB;

	@Before
	public void setup() {
		CareerDTO career = Mockito.mock(CareerDTO.class);
		this.englishA = new SubjectDTO(1L, "English A", "en1", career);
		this.englishB = new SubjectDTO(2L, "English B", "en2", career);
	}

	@Test
	public void createSubjectDependenciesDTO() {
		var dto = new SubjectDependenciesDTO(englishB, List.of(englishA));
		assertNotNull(dto);
		assertEquals("1", dto.getDependenciesIds());
		assertEquals(1, dto.getDependencies().size());
		assertEquals(englishA, dto.getDependencies().get(0));
		assertEquals(1L, dto.getDependenciesIdsAsLongArray()[0]);
	}
	
}
