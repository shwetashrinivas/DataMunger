package com.stackroute.datamunger.query;

import java.util.Arrays;

public class DataTypeDefinitions {

	/*
	 * This class should contain a member variable which is a String array, to hold
	 * the data type for all columns for all data types
	 */
	String DataTypes[];

    public String[] getDataTypes() {
        return DataTypes;
    }

    public void setDataTypes(String[] datatype) {
        this.DataTypes = datatype;
    }

    public DataTypeDefinitions(String[] datatype) {
        super();
        this.DataTypes = datatype;
    }



}
