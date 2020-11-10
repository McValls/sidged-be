package com.mvalls.sidged.test.dataAnalisis;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.mvalls.sidged.core.analysis.PresentAnalysisCalculator;
import com.mvalls.sidged.core.model.ClassStudentPresent;
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
public class PresentAnalisisTest {
	
	@Autowired
	private PresentAnalysisCalculator presentAnalysisCalculator;
	
	private CourseClass courseClass;
	private Map<StudentPresent, Double> presentPercentages;
	
	@Given("a class with {int} students, {int} of them are present, {int} of them are absents {int} of them are late")
	public void a_class_with_students_of_them_are_present_of_them_are_absents_of_them_are_late(Integer int1, Integer int2, Integer int3, Integer int4) {
		List<ClassStudentPresent> list = new ArrayList<>();
		assert int1 == (int2 + int3 + int4) : "Bad arguments!";
		IntStream.range(0, int2).forEach(index -> {
			ClassStudentPresent student = mock(ClassStudentPresent.class);
			doReturn(StudentPresent.PRESENT).when(student).getPresent();
			list.add(student);
		});
		
		IntStream.range(0, int3).forEach(index -> {
			ClassStudentPresent student = mock(ClassStudentPresent.class);
			doReturn(StudentPresent.ABSENT).when(student).getPresent();
			list.add(student);
		});
		
		IntStream.range(0, int4).forEach(index -> {
			ClassStudentPresent student = mock(ClassStudentPresent.class);
			doReturn(StudentPresent.LATE).when(student).getPresent();
			list.add(student);
		});
		
		courseClass = mock(CourseClass.class);
		doReturn(list).when(courseClass).getStudentPresents();
	}
	
	@When("I ask for the percentage of presentism")
	public void i_ask_for_the_percentage_of_presentism() {
		presentPercentages = presentAnalysisCalculator.getPresentPercentage(courseClass);
	}

	@Then("I get a {int} percent of presents")
	public void i_get_a_percent_of_presents(Integer int1) {
	    Assert.assertTrue(presentPercentages.get(StudentPresent.PRESENT).intValue() == int1);
	}

	@Then("I get a {int} percent of absents")
	public void i_get_a_percent_of_absents(Integer int1) {
		Assert.assertTrue(presentPercentages.get(StudentPresent.ABSENT).intValue() == int1);
	}

	@Then("I get a {int} percent of late")
	public void i_get_a_percent_of_late(Integer int1) {
		Assert.assertTrue(presentPercentages.get(StudentPresent.LATE).intValue() == int1);
	}

	
}
