package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/*There are total 4 DataMungerTest file:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 4 methods
 * a)getBaseQuery()  b)getFileName()  c)getOrderByClause()  d)getGroupByFields()
 * 
 * Once you implement the above 4 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 2 methods
 * a)getFields() b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getRestrictions()  b)getLogicalOperators()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

public class QueryParser {

	private QueryParameter queryParameter = new QueryParameter();

	/*
	 * This method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	public QueryParameter parseQuery(String queryString) {
		
		queryParameter.setFileName(fileName1(queryString));
		
		queryParameter.setBaseQuery(baseQuery1(queryString));
		
		queryParameter.setRestrictions(restrictions1(queryString));
		
		queryParameter.setLogicalOperators(logicalOperators1(queryString));
		
		queryParameter.setFields(fields1(queryString));
		
		queryParameter.setAggregateFunctions(aggregateFunctions1(queryString));
		
		queryParameter.setGroupByFields(groupByFields1(queryString));
		
		queryParameter.setOrderByFields(orderByFields1(queryString));
		
		return queryParameter;
		
	}

	/*
	 * Extract the name of the file from the query. File name can be found after the
	 * "from" clause.
	 */
	
	public String fileName1(String queryString) {
		String s = queryString.toLowerCase().split("from")[1].trim().split("\\s+")[0];
		return s;
	}
	
	/*
	 * 
	 * Extract the baseQuery from the query.This method is used to extract the
	 * baseQuery from the query string. BaseQuery contains from the beginning of the
	 * query till the where clause
	 */
	

	public String baseQuery1(String queryString) {
		if(queryString.contains("where") || queryString.contains("order by") || queryString.contains("group by"))
			return queryString.toLowerCase().split("where|order by|group by")[0].trim();
		else
			return queryString;
	}
	
	/*
	 * extract the order by fields from the query string. Please note that we will
	 * need to extract the field(s) after "order by" clause in the query, if at all
	 * the order by clause exists. For eg: select city,winner,team1,team2 from
	 * data/ipl.csv order by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one order by fields.
	 */
	
	public List<String> orderByFields1(String queryString) {
		if(!queryString.contains("order by"))
			return null;
		String [] str = queryString.toLowerCase().split("order by")[1].split("group by")[0].split(",");
		List<String> ls = new ArrayList<>();
		for(int i = 0;i < str.length;i++)
			ls.add(str[i].trim());
		return ls;
	}
	
	/*
	 * Extract the group by fields from the query string. Please note that we will
	 * need to extract the field(s) after "group by" clause in the query, if at all
	 * the group by clause exists. For eg: select city,max(win_by_runs) from
	 * data/ipl.csv group by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one group by fields.
	 */
	
	public List<String> groupByFields1(String queryString) {
		if(!queryString.contains("group by"))
			return null;
		String [] str = queryString.toLowerCase().split("group by")[1].split("order by")[0].split(",");
		List<String> ls = new ArrayList<>();
		for(int i = 0;i < str.length;i++)
			ls.add(str[i].trim());
		return ls;
	}
	
	/*
	 * Extract the selected fields from the query string. Please note that we will
	 * need to extract the field(s) after "select" clause followed by a space from
	 * the query string. For eg: select city,win_by_runs from data/ipl.csv from the
	 * query mentioned above, we need to extract "city" and "win_by_runs". Please
	 * note that we might have a field containing name "from_date" or "from_hrs".
	 * Hence, consider this while parsing.
	 */

	public List<String> fields1(String queryString) {
		String a = queryString.toLowerCase().split("\\s+")[1].trim();
		String [] arr = a.split(",");
		List<String> ls = new ArrayList<>();
		for(int i = 0;i < arr.length;i++) {
			ls.add(arr[i].trim());
		}
		return ls;
	}
	
	/*
	 * Extract the conditions from the query string(if exists). for each condition,
	 * we need to capture the following: 1. Name of field 2. condition 3. value
	 * 
	 * For eg: select city,winner,team1,team2,player_of_match from data/ipl.csv
	 * where season >= 2008 or toss_decision != bat
	 * 
	 * here, for the first condition, "season>=2008" we need to capture: 1. Name of
	 * field: season 2. condition: >= 3. value: 2008
	 * 
	 * the query might contain multiple conditions separated by OR/AND operators.
	 * Please consider this while parsing the conditions.
	 * 
	 */
	
	public List<Restriction> restrictions1(String queryString) {
		if(!queryString.contains("where"))
			return null;
		String [] str = queryString.split("where")[1].split("order by|group by")[0].split("\\s+and\\s+|\\sor");
		List<Restriction> res = new ArrayList<>();
		for(int i = 0;i < str.length;i++) {
			String [] s = str[i].trim().split("\\s+|'");
			Restriction ob = new Restriction(s[0].trim(), s[2].trim(), s[1].trim());
			res.add(ob);
			System.out.println(Arrays.toString(s));
		}
		return res;
	}
	
	/*
	 * Extract the logical operators(AND/OR) from the query, if at all it is
	 * present. For eg: select city,winner,team1,team2,player_of_match from
	 * data/ipl.csv where season >= 2008 or toss_decision != bat and city =
	 * bangalore
	 * 
	 * The query mentioned above in the example should return a List of Strings
	 * containing [or,and]
	 */
	
	public List<String> logicalOperators1(String queryString) {
		if(!queryString.contains("where"))
			return null;
		String [] str = queryString.toLowerCase().split("where")[1].split("order by|group by")[0].split(" ");
		List<String> ls = new ArrayList<>();
		for(int i = 0;i < str.length;i++) {
			if(str[i].equals("and") || str[i].equals("or"))
				ls.add(str[i]);
		}
		return ls;
	}
	
	/*
	 * Extract the aggregate functions from the query. The presence of the aggregate
	 * functions can determined if we have either "min" or "max" or "sum" or "count"
	 * or "avg" followed by opening braces"(" after "select" clause in the query
	 * string. in case it is present, then we will have to extract the same. For
	 * each aggregate functions, we need to know the following: 1. type of aggregate
	 * function(min/max/count/sum/avg) 2. field on which the aggregate function is
	 * being applied.
	 * 
	 * Please note that more than one aggregate function can be present in a query.
	 * 
	 * 
	 */
	
	public List<AggregateFunction> aggregateFunctions1(String queryString) {
		String a = queryString.toLowerCase().split(" ")[1].trim();
		if(a.contains("sum(") || a.contains("count(") || a.contains("min(") || a.contains("max(") || a.contains("avg(")) {
			List<AggregateFunction> af = new ArrayList<>();
			String [] str = a.split(",");
			for(int i = 0;i < str.length;i++) {
				
				if(str[i].contains("sum(") || str[i].contains("count(") || str[i].contains("min(") || str[i].contains("max(") || str[i].contains("avg(")) {
					String [] arr = str[i].split("\\(");
					AggregateFunction ob = new AggregateFunction(arr[1].split("\\)")[0], arr[0]);
					af.add(ob);
				}
			}
			return af;
		}
		else 
			return null;
	}

}