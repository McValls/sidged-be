Feature: Get User Creation Strategy by UserType
	Teacher or student must be created depending on what the user type is
	
	Scenario: The UserType is TEACHER
		Given user type is teacher
		When I ask for the user creation strategy
		Then I get a TeacherUserCreationStrategy
		
	Scenario: The UserType is STUDENT
		Given user type is student
		When I ask for the user creation strategy
		Then I get a StudentUserCreationStrategy
	