Feature: Get percentage of present students
	
	Scenario: Presentism case 1
		Given a class with 30 students, 21 of them are present, 3 of them are absents 6 of them are late
		When I ask for the percentage of presentism
		Then I get a 70 percent of presents
		And I get a 10 percent of absents
		And I get a 20 percent of late 
		
	Scenario: Presentism by course
		Given A Course with id 10
		Given 2 classes dated in January
		Given 2 classes dated in February
		Given 2 classes dated in March
		Given That all classes have 10 students
		Given That 5 are present, 3 are late and 2 are absent in each class
		When I ask for the presentism of the course
		Then I get a non empty list
		And the list have the months 1, 2 and 3
		And for each of that lists there are items with values 50, 30 and 20.
		