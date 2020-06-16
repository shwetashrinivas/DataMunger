package com.stackroute.datamunger.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.stackroute.datamunger.query.DataTypeDefinitions;
import com.stackroute.datamunger.query.Header;

public class CsvQueryProcessor extends QueryProcessingEngine {

	protected String fileName;
	BufferedReader bufferReader;

	// Parameterized constructor to initialize filename
	public CsvQueryProcessor(String fileName) throws FileNotFoundException {
		this.fileName = fileName;
		bufferReader = new BufferedReader(new FileReader(this.fileName));

	}

	/*
	 * Implementation of getHeader() method. We will have to extract the headers
	 * from the first line of the file.
	 * Note: Return type of the method will be Header
	 */
	
	@Override
	public Header getHeader() throws IOException {

		/// read the first line
		String header[] = {""};
		bufferReader = new BufferedReader(new FileReader(this.fileName));
		header = bufferReader.readLine().toString().split(",");
		// populate the header object with the String array containing the header names
		Header headers = new Header(header);
		return headers;

	}

	/**
	 * getDataRow() method will be used in the upcoming assignments
	 */

		/*
	 * Implementation of getColumnType() method. To find out the data types, we will
	 * read the first line from the file and extract the field values from it. If a
	 * specific field value can be converted to Integer, the data type of that field
	 * will contain "java.lang.Integer", otherwise if it can be converted to Double,
	 * then the data type of that field will contain "java.lang.Double", otherwise,
	 * the field is to be treated as String. 
	 * Note: Return Type of the method will be DataTypeDefinitions
	 */
	
	@Override
	public DataTypeDefinitions getColumnType() throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        br.readLine();
        String str=br.readLine();
        String[] newcolumns = str.split(",",-1);
       int len=newcolumns.length;
       String[] result=new String[len];
      
       for(int i=0;i<len;i++) {
           if(newcolumns[i].matches("[0-9]+")) {
               Integer a=Integer.parseInt(newcolumns[i]);
               result[i]=a.getClass().getName().toString(); 
           }
           else 
               if(newcolumns[i].matches("^[a-zA-Z ]+$|^\\d{4}-\\d{2}-\\d{2}$")) {
               result[i]=newcolumns[i].getClass().getName().toString();
        }
               else if (newcolumns[i].matches("")) {
                   result[i]=newcolumns[i].getClass().getName().toString();
                }
           }
           DataTypeDefinitions dtd= new DataTypeDefinitions(result);
           return dtd;
		
		

	}

	@Override
	public void getDataRow() {
			
	}
}

