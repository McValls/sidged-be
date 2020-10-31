package com.mvalls.sidged.strategies.actions.students;

import com.mvalls.sidged.core.model.Student;
import com.mvalls.sidged.enums.UpdateAction;
import com.mvalls.sidged.strategies.actions.UpdateActionStrategy;

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
public class StudentUpdateActionStrategyFactory {

	private StudentUpdateActionStrategyFactory() {}
	
	public static UpdateActionStrategy<Student> getInstance(UpdateAction updateAction) throws IllegalArgumentException {
		switch(updateAction) {
		case ADD:
			return new AddStudentUpdateActionStrategy();
		case REMOVE:
			return new RemoveStudentUpdateActionStrategy();
		default:
			throw new IllegalArgumentException("No strategy for student fouded for action: " + updateAction);
		}
	}
	
}
