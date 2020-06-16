package com.stackroute.datamunger.query.parser;

/*
 * This class is used for storing name of field, condition and value for 
 * each conditions
 * generate getter and setter for this class,
 * Also override toString method
 * */

public class Restriction {

	private String name, value, condition;
	
	// Write logic for constructor
	public Restriction(String name, String value, String condition) {
		this.name = name;
		this.value = value;
		this.condition = condition;
	}

	public String getName() {
		return this.name;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public String getCondition() {
		return this.condition;
	}
	
	@Override
	public String toString() {
		return (this.name+" "+this.condition+" "+this.value);
	}

}