package com.mvalls.sidged.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mvalls.sidged.converters.StringListConverter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "contact_data")
@Data
@Builder
@AllArgsConstructor
public class ContactData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Convert(converter = StringListConverter.class)
	@Builder.Default
	private Collection<String> emails = new ArrayList<>();
	
	@Convert(converter = StringListConverter.class)
	@Builder.Default
	private Collection<String> phones = new ArrayList<>();
	
	public ContactData() {}
	
	public ContactData(String email) {
		super();
		this.emails = new ArrayList<>();
		this.emails.add(email);
	}
	
	public String getDefaultEmail() {
		if(emails.isEmpty()) {
			return null;
		}
		return emails.iterator().next();
	}


}
