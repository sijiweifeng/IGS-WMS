package com.esquel.wh.dao;

import java.sql.Date;
import java.util.List;

import com.esquel.wh.utils.*;

public class WMSMasterDao {
	public boolean checkTransDateFrozen(String type,String factoryCode,Date transDate) throws Exception {
		try {
			String status="";
			MySqlUtil db = MySqlUtil.getInstance("localhost", "esqwms", "root", "123456") ;
			List<Object[]> result_list = db.getBySql(  
	                "select status from inv_month_end_hd where type=? and factoryCode=? and ? between startDate and endDate ", new Object[] {type,factoryCode,transDate});  
			if(result_list==null || result_list.size()<=0) {
				throw new Exception( "The transaction date is not in open period. Please check/amend the transaction date!");
			}
	        for (int i = 0; i < result_list.size(); i++) {  
	            Object[] row = result_list.get(i); 
	            status=row[0] + "";  
	        }  
	        if (status.equalsIgnoreCase("C")){
	        	throw new Exception( "Transaction date is in a closed period. Cannot continue.");
	        }
	        else if(status.equalsIgnoreCase("S")){
	        	throw new Exception( "The period is suspended. Cannot continue. ");
	        }
	        else if(status.equalsIgnoreCase("P")){
	        	throw new Exception( "The period is disabled for transaction entry because the month-end closing is in process. Cannot continue.");
	        }
	        else if(status.equalsIgnoreCase("L")){
	        	throw new Exception( "The period is locked. Cannot continue.");
	        }
	        else if(status.equalsIgnoreCase("O")){
	        	return true;
	        }
	        else {
	        	throw new Exception( "The period is not open. Cannot continue.");
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
