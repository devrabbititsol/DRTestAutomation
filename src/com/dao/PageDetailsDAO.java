package com.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.db.PostgresDBHelper;
import com.dto.PageDetailsVo;
import com.util.QueryConstants;



public class PageDetailsDAO {
	
	public static ArrayList<PageDetailsVo> getPageDetailsList(int pageId) {
		PostgresDBHelper client = new PostgresDBHelper();
		 ArrayList<PageDetailsVo> pageList =  new ArrayList<>();
		try {
			if (client.connect()) {
				System.out.println("DB connected moduleId id"+pageId);				
				ResultSet rs = client.execQuerywithParams(QueryConstants.GETPAGEDETAIL, pageId);	
			
				  ResultSetMetaData rsmd = rs.getMetaData();				 
				   int columnsNumber = rsmd.getColumnCount();
				   while (rs.next()) {
					   PageDetailsVo pageDetail = new PageDetailsVo();				      
					   pageDetail.setPageid(rs.getInt(1));
				         // client1.setClientName(rs.getString(2));
					   pageDetail.setPropertyName(rs.getString(3));
					   pageDetail.setInputValue(rs.getString(4));
					   pageDetail.setActionName(rs.getString(5));
					   pageDetail.setRelativeXpathbyId(rs.getString(6));
					   pageDetail.setRelativeXpathbyName(rs.getString(7));
					   pageDetail.setxPath(rs.getString(8));
					   pageList.add(pageDetail);
				       System.out.println(rs.getInt(1)+":::::"+rs.getString(3)+":::::"+rs.getString(4)+":::::"+rs.getString(5));
				   }				
			}
			return pageList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	public static String insertPageDetails(){
		String statusMsg = "";
		PostgresDBHelper client = new PostgresDBHelper();
		try {
			if (client.connect()) {
				System.out.println("DB connected");				
				Map<String,Object> vals = new HashMap<>();				
				vals.put("pagenames_id", 2);
				vals.put("prop_name", "input");
				vals.put("prop_input_value", "UserName");
				vals.put("prop_action", "input");
				vals.put("relativexpathbyid", "//table/tbody/tr/th");
				vals.put("relativeXpathByName", "//body//section[3]/div/ul/li[3]/a");
				vals.put("xPath", "//*[@id=’social-media’]/ul/li[3]/a");
				
				if (client.insert("tb_page_details", vals) == 1) {
					System.out.println("Record added");
					statusMsg="Created Successfully";
				}else {
					System.out.println("Please Try later.");
					statusMsg="Please Try later.";
				}
				
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return statusMsg;
	}

}
