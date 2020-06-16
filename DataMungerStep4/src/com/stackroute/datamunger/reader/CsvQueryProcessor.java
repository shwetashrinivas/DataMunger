package com.stackroute.datamunger.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Header;

public class CsvQueryProcessor extends QueryProcessingEngine {

	protected String fileName;
	BufferedReader bufferedReader;	

	/*
	 * parameterized constructor to initialize filename. As you are trying to
	 * perform file reading, hence you need to be ready to handle the IO Exceptions.
	 */
	public CsvQueryProcessor(String fileName) throws FileNotFoundException {
		this.fileName = fileName;
		bufferedReader = new BufferedReader(new FileReader(fileName));
	}

	/*
	 * implementation of getHeader() method. We will have to extract the headers
	 * from the first line of the file.
	 */
	@Override
	public Header getHeader() throws IOException {
		bufferedReader = new BufferedReader(new FileReader(fileName));
		// read the first line				
		String []headercoloumnData = bufferedReader.readLine().split(",");
		
		// populate the header object with the String array containing the header names
		Header h = new Header();
		h.setHeaders(headercoloumnData);	
		h.getHeaders();		
		return h;
	}
	

	/**
	 * This method will be used in the upcoming assignments
	 */
	@Override
	public void getDataRow() {
	}

	/*
	 * implementation of getColumnType() method. To find out the data types, we will
	 * read the first line from the file and extract the field coloumnData from it. In
	 * the previous assignment, we have tried to convert a specific field value to
	 * Integer or Double. However, in this assignment, we are going to use Regular
	 * Expression to find the appropriate data type of a field. Integers: should
	 * contain only digits without decimal point Double: should contain digits as
	 * well as decimal point Date: Dates can be written in many formats in the CSV
	 * file. However, in this assignment,we will test for the following date
	 * formats('dd/mm/yyyy',
	 * 'mm/dd/yyyy','dd-mon-yy','dd-mon-yyyy','dd-month-yy','dd-month-yyyy','yyyy-mm-dd')
	 */
	@Override
	public DataTypeDefinitions getColumnType() throws IOException {
	BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        br.readLine();
        String str=br.readLine();
        String[] newcolumns = str.split(",",18);
       int len=newcolumns.length;
       String[] result=new String[len];
      
       for(int i=0;i<len;i++) {
           if(newcolumns[i].matches("[0-9]+")) 
           {
               Integer a=Integer.parseInt(newcolumns[i]);
               result[i]=a.getClass().getName().toString(); 
           }
           else 
               if(newcolumns[i].matches("^[a-zA-Z ]+$")) 
               {
               result[i]=newcolumns[i].getClass().getName().toString();
        
               }
            else 
            	   if((newcolumns[i].matches("^\\d{4}-\\d{2}-\\d{2}$")))
            	   {
            		   SimpleDateFormat d=new SimpleDateFormat("yyyy-MM-dd");
            		   Date d1=new Date();
            		   try
            		   {
            			   d1=d.parse(newcolumns[i]);
            			   
            		   }
            		   catch(Exception e)
            		   {
            	
            		   }
            		   result[i]=d1.getClass().getName().toString();
            	   }
            	   //else {
//            		   if(newcolumns[i].matches("")) {
//            			   result[i]=newcolumns[i].getClass().getName();
//            		   }
//            		   
//            	   }
            	   if(newcolumns[i].isEmpty())
            	   {
            		   Object ob=new Object();
            		   result[i]=ob.getClass().getName();
            	   }
       }
       //=========================================================
		
		DataTypeDefinitions dataTypeDefinitions = new DataTypeDefinitions();
		dataTypeDefinitions.setDataTypes(result);
		dataTypeDefinitions.getDataTypes();
		return dataTypeDefinitions;
	}
	
}
