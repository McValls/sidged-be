package com.mvalls.sidged.rest.dtos;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class ContactDataDTO extends DataTransferObject {
	
	private static final long serialVersionUID = 5059544740697086717L;
	
	@Builder.Default
	private Collection<String> emails = new ArrayList<>();
	
	@Builder.Default
	private Collection<String> phones = new ArrayList<>();

}
