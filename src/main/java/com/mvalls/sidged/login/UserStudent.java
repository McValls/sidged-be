package com.mvalls.sidged.login;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.mvalls.sidged.model.Student;

import lombok.Data;

@Entity
@Table(name = "user_student", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"user_id", "student_id"})
})
@Data
public class UserStudent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id", nullable = false, unique = true)
	private Student student;
	
	public UserStudent() {}

	public UserStudent(User user, Student student) {
		super();
		this.user = user;
		this.student = student;
	}

}
