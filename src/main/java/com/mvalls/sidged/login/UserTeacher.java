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

import com.mvalls.sidged.model.Teacher;

import lombok.Data;

@Entity
@Table(name = "user_teacher", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"user_id", "teacher_id"})
})
@Data
public class UserTeacher {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "teacher_id", nullable = false, unique = true)
	private Teacher teacher;

	public UserTeacher() {}
	
	public UserTeacher(User user, Teacher teacher) {
		super();
		this.user = user;
		this.teacher = teacher;
	}
	
}
