package com.mvalls.sidged.test.dataAnalisis;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvalls.sidged.core.model.analytics.CoursePresentismByMonthData;
import com.mvalls.sidged.core.analysis.PresentAnalysisCalculator;
import com.mvalls.sidged.core.model.ClassStudentPresent;
import com.mvalls.sidged.core.model.Course;
import com.mvalls.sidged.core.model.CourseClass;
import com.mvalls.sidged.core.model.StudentPresent;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

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
public class PresentAnalisisScenario2Test {

	@Autowired
	private PresentAnalysisCalculator presentAnalysisCalculator;
	private Course course;
	private List<CoursePresentismByMonthData> presentismByCourse;
	
	@Given("A Course with id {int}")
	public void a_Course_with_id(Integer int1) {
	    this.course = mock(Course.class);
	    Set<CourseClass> classes = new HashSet<>();
	    when(this.course.getId()).thenReturn(Integer.toUnsignedLong(int1));
	    when(this.course.getClasses()).thenReturn(classes);
	}

	@Given("{int} classes dated in January")
	public void classes_dated_in_January(Integer int1) {
		for(int i = 0; i < int1; i++) {
			CourseClass courseClass = mock(CourseClass.class);
			when(courseClass.getDate()).thenReturn(LocalDate.of(2019, 1, int1+1));
			this.course.getClasses().add(courseClass);
		}
	}

	@Given("{int} classes dated in February")
	public void classes_dated_in_February(Integer int1) {
		for(int i = 0; i < int1; i++) {
			CourseClass courseClass = mock(CourseClass.class);
			when(courseClass.getDate()).thenReturn(LocalDate.of(2019, 2, int1+1));
			this.course.getClasses().add(courseClass);
		}
	}

	@Given("{int} classes dated in March")
	public void classes_dated_in_March(Integer int1) {
		for(int i = 0; i < int1; i++) {
			CourseClass courseClass = mock(CourseClass.class);
			when(courseClass.getDate()).thenReturn(LocalDate.of(2019, 3, int1+1));
			this.course.getClasses().add(courseClass);
		}
	}

	@Given("That all classes have {int} students")
	public void that_all_classes_have_students(Integer int1) {
	    for(CourseClass courseClass : this.course.getClasses()) {
	    	List<ClassStudentPresent> studentPresents = new ArrayList<ClassStudentPresent>();
	    	for(int i = 0; i < int1; i++) {
	    		ClassStudentPresent classStudentPresent = mock(ClassStudentPresent.class);
	    		studentPresents.add(classStudentPresent);
	    	}
	    	when(courseClass.getStudentPresents()).thenReturn(studentPresents);
	    }
	}

	@Given("That {int} are present, {int} are late and {int} are absent in each class")
	public void that_is_present_is_late_and_is_absent_in_each_class(Integer int1, Integer int2, Integer int3) {
	    for(CourseClass courseClass : this.course.getClasses()) {
	    	int i = 0;
	    	Iterator<ClassStudentPresent> iterator = courseClass.getStudentPresents().iterator();
	    	while(i < int1) {
	    		ClassStudentPresent studentPresent = iterator.next();
	    		when(studentPresent.getPresent()).thenReturn(StudentPresent.PRESENT);
	    		i++;
	    	}
	    	
	    	while(i < int1+int2) {
	    		ClassStudentPresent studentPresent = iterator.next();
	    		when(studentPresent.getPresent()).thenReturn(StudentPresent.LATE);
	    		i++;
	    	}
	    	
	    	while(i < int1+int2+int3) {
	    		ClassStudentPresent studentPresent = iterator.next();
	    		when(studentPresent.getPresent()).thenReturn(StudentPresent.ABSENT);
	    		i++;
	    	}
	    }
	}

	@When("I ask for the presentism of the course")
	public void i_ask_for_the_presentism_of_the_course() {
	    this.presentismByCourse = presentAnalysisCalculator.getPresentismByCourseGroupedByMonth(this.course).getPresentismByMonth();
	}

	@Then("I get a non empty list")
	public void i_get_a_non_empty_map() {
	    Assert.assertNotNull(presentismByCourse);
	    Assert.assertTrue(!presentismByCourse.isEmpty());
	}
	
	@Then("the list have the months {int}, {int} and {int}")
	public void the_list_have_the_months_and(Integer int1, Integer int2, Integer int3) {
	    Assert.assertTrue(presentismByCourse.stream().filter(cpd -> cpd.getMonth().equals(int1)).count() == 1);
	    Assert.assertTrue(presentismByCourse.stream().filter(cpd -> cpd.getMonth().equals(int2)).count() == 1);
	    Assert.assertTrue(presentismByCourse.stream().filter(cpd -> cpd.getMonth().equals(int3)).count() == 1);
	}

	@Then("for each key there are {int} list")
	public void for_each_key_there_are_list(Integer int1) {
		presentismByCourse.forEach(cpd -> Assert.assertTrue(cpd.getPercentagesByMonth().size() == int1));
	}

	@Then("for each of that lists there are items with values {int}, {int} and {int}.")
	public void for_each_of_that_lists_there_are_items_with_values_and(Integer int2, Integer int3, Integer int4) {
		presentismByCourse.forEach(cpd -> {
			Assert.assertEquals(int2, cpd.getPercentagesByMonth().stream().filter(studentPresent -> studentPresent.getStudentPresent() == StudentPresent.PRESENT)
				.findAny().get().getPercentage());
				
			Assert.assertEquals(int3, cpd.getPercentagesByMonth().stream().filter(studentPresent -> studentPresent.getStudentPresent() == StudentPresent.LATE)
				.findAny().get().getPercentage());
			
			Assert.assertEquals(int4, cpd.getPercentagesByMonth().stream().filter(studentPresent -> studentPresent.getStudentPresent() == StudentPresent.ABSENT)
				.findAny().get().getPercentage());
		});
	}
	
}
