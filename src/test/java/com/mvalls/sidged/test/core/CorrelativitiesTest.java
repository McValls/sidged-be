package com.mvalls.sidged.test.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.mvalls.sidged.core.model.Career;
import com.mvalls.sidged.core.model.Subject;
import com.mvalls.sidged.core.model.correlativity.Correlativity;

/*
 * TODO Test List
 * 
 * english 1 has no dependencies -> OK
 * programation 1 has no dependencies -> OK
 * english 2 depends on english 1 -> OK
 * programation 3 depends on programation 2 and programation 2 depends on programation 1 -> OK
 * programation 3 does not depends directly on programation 1 -> OK
 * finalProject depends on programation 3 and english 2 -> OK
 * finalProject does not depends directly on english 1 or programation 2 or programation 1 -> OK
 * 
 * english1.height == 0 -> OK
 * english2.height == 1 -> OK
 * programation1.height == 0 -> OK
 * programation2.height == 1 -> OK
 * programation3.height == 2 -> OK
 * finalProject.height == 3 -> OK
 * 
 * subject.career == dependency.career -> OK
 * 
 * if english2 depends on english1 then english1 cannot depend on english2 (circular dependency) -> OK
 * 
 */
public class CorrelativitiesTest {
	
	private Subject english1, english2, programation1, programation2, programation3, finalProject;
	private Correlativity en1, en2, pr1, pr2, pr3, fp;
	
	@Before
	public void setup() {
		Career career = mock(Career.class);
		english1 = new Subject("english a", "en1", career);
		english2 = new Subject("english b", "en2", career);
		programation1 = new Subject("programation a", "pr1", career);
		programation2 = new Subject("programation b", "pr2", career);
		programation3 = new Subject("programation c", "pr3", career);
		finalProject = new Subject("finalProject", "fp", career);
		
		pr1 = new Correlativity(programation1, List.of());
		pr2 = new Correlativity(programation2, List.of(pr1));
		pr3 = new Correlativity(programation3, List.of(pr2));
		en1 = new Correlativity(english1, List.of());
		en2 = new Correlativity(english2, List.of(en1));
		fp = new Correlativity(finalProject, List.of(pr1, pr2, pr3, en1, en2));
	}
	
	@Test
	public void subjectWithNoDependencies() {
		var correlativity = new Correlativity(english1, List.of());
		
		assertTrue(correlativity.getDependencies().isEmpty());
		assertEquals(0, correlativity.getLevel());
	}
	
	@Test
	public void subjectWithOneDependency() {
		assertEquals(1, en2.getDependencies().size());
		assertEquals(1, en2.getLevel());
	}
	
	@Test
	public void subjectWithTwoNestedDependencies() {
		assertEquals(1, pr2.getDependencies().size());
		assertEquals(1, pr2.getLevel());
		assertEquals(1, pr3.getDependencies().size());
		assertEquals(2, pr3.getLevel());
	}
	
	@Test
	public void subjectWithNestedDependenciesOfDifferentLevels() {
		assertEquals(2, fp.getDependencies().size());
		assertEquals(3, fp.getLevel());
	}
	
	@Test
	public void subjectWithNestedDependenciesDependsOn() {
		assertTrue(pr3.dependsOn(programation1));
		assertTrue(fp.dependsOn(english1));
		assertTrue(fp.dependsOn(english2));
		assertTrue(fp.dependsOn(programation1));
		assertTrue(fp.dependsOn(programation2));
		assertTrue(fp.dependsOn(programation3));
		assertFalse(pr3.dependsOn(english1));
	}
	
	@Test
	public void subjectWithNestedDependenciesRemovesDuplicated() {
		var pr1 = new Correlativity(programation1, List.of());
		var pr2 = new Correlativity(programation2, List.of(pr1));
		var pr3 = new Correlativity(programation3, List.of(pr1, pr2));
		
		var fp = new Correlativity(finalProject, List.of(pr1, pr2, pr3));
		
		assertEquals(1, pr3.getDependencies().size());
		assertEquals(2, pr3.getLevel());
		assertEquals(1, fp.getDependencies().size());
	}
	
	@Test
	public void correlativitiesWithoutDuplicatedSortedByLevelAsc() {
		assertEquals(2, fp.getDependencies().size());
		assertEquals(3, fp.getLevel());
		assertEquals(programation3, fp.getDependencies().get(0));
		assertEquals(english2, fp.getDependencies().get(1));
	}
	
	@Test(expected = IllegalStateException.class)
	public void circularDependencyMustFail() {
		var pr1 = new Correlativity(programation1, List.of());
		var pr2 = new Correlativity(programation2, List.of(pr1));
		@SuppressWarnings("unused")
		var circular = new Correlativity(programation1, List.of(pr2));
	}

	@Test(expected = IllegalStateException.class)
	public void differentCareerMustFail() {
		Subject brokenSubject = new Subject("english1", "en1", mock(Career.class));
		var broken = new Correlativity(brokenSubject, List.of());
		new Correlativity(english2, List.of(broken)); // must crash, different careers
	}
	
	@Test
	public void correlativityComparation() {
		assertTrue(en1.compareTo(en2) > 0);
		assertTrue(en2.compareTo(en1) < 0);
		assertTrue(pr1.compareTo(pr2) > 0);
		assertTrue(pr2.compareTo(pr1) < 0);
		assertTrue(en1.compareTo(en1) == 0);
		assertTrue(pr1.compareTo(pr1) == 0);
		assertTrue(en1.compareTo(pr1) < 0);
		assertTrue(pr1.compareTo(en1) > 0);
	}
	
	@Test
	public void dependenciesSortedByLevelAndSubjectName() {
		List<Subject> dependencies = fp.getDependencies();
		assertEquals(programation3, dependencies.get(0));
		assertEquals(english2, dependencies.get(1));
		assertEquals(2, dependencies.size());
		System.out.println(dependencies);
	}
	
	@Test
	public void dependencyTree() {
		List<Subject> dependenciesTree = fp.getDependenciesTree();
		assertEquals(programation3, dependenciesTree.get(0));
		assertEquals(english2, dependenciesTree.get(1));
		assertEquals(programation2, dependenciesTree.get(2));
		assertEquals(english1, dependenciesTree.get(3));
		assertEquals(programation1, dependenciesTree.get(4));
	}

}
