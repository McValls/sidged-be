package com.mvalls.sidged.model;

public enum StudentPresent {
	
	PRESENT("Presente"),
	ABSENT("Ausente"),
	LATE("Tarde");
	
	private String spanishDesc;

	StudentPresent(String spanishDesc) {
		this.spanishDesc = spanishDesc;
	}

	public String getSpanishDesc() {
		return spanishDesc;
	}

}
