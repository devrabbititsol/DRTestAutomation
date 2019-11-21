package com.dao.pageinfo;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.db.PostgresDBHelper;
import com.dto.PageDetailsVo;
import com.util.IntArray;
import com.util.QueryConstants;

public class PageInfoElementsDTO {
	
	public static ArrayList<PageDetailsVo> getPageDetailsList(int pageId,int offsetval) {
		PostgresDBHelper client = new PostgresDBHelper();
		 ArrayList<PageDetailsVo> pageList =  new ArrayList<>();
		try {
			if (client.connect()) {
				System.out.println("DB connected moduleId id"+pageId);				
				ResultSet rs = client.execQuerywithParams(QueryConstants.LOADMORE_PAGEINFO_GETPAGE_ELEMENTS, pageId,offsetval);	
			
				  ResultSetMetaData rsmd = rs.getMetaData();				 
				   int columnsNumber = rsmd.getColumnCount();
				   while (rs.next()) {
					   PageDetailsVo pageDetail = new PageDetailsVo();				      
					   pageDetail.setPageid(rs.getInt(1));
				         // client1.setClientName(rs.getString(2));
					   pageDetail.setPropertyName(rs.getString(3));
					   pageDetail.setInputValue(rs.getString(4));
					   pageDetail.setActionName(rs.getString(14));
					   pageDetail.setRelativeXpathbyId(rs.getString(6));
					   pageDetail.setRelativeXpathbyName(rs.getString(7));
					   pageDetail.setxPath(rs.getString(8));
					   pageDetail.setSelected(rs.getBoolean(9));
					   
					   pageList.add(pageDetail);
				       System.out.println(rs.getInt(1)+":::::"+rs.getString(3)+":::::"+rs.getString(4)+":::::"+rs.getString(5));
				     
				   }
				   
				   rs.close();
			}
			
			return pageList;
		} catch (ClassNotFoundException | SQLException e) {
			
			
			e.printStackTrace();
			return null;
		}	
	}
	
	public static String updateCheckedElement(int[] checkedids,int[] uncheckedIds) {
           
		PostgresDBHelper client = new PostgresDBHelper();
				try {
			if (client.connect()) {			
				 IntArray checkedintArray = new IntArray(checkedids);
				 IntArray unCheckedintArray = new IntArray(uncheckedIds);
				client.execQueryParams(QueryConstants.PAGEINFO_SETCHECKED_ELEMENTS, checkedintArray);
				client.execQueryParams(QueryConstants.PAGEINFO_SETUNCHECKED_ELEMENTS, unCheckedintArray);	
				
			}
			
			return "Successfully Updated";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}	
			
	}
	
	
	
	public static void prettyPrinterRequest(HttpServletRequest request){
		Enumeration<String> params = request.getParameterNames(); 
		while(params.hasMoreElements()){
		 String paramName = params.nextElement();
		 System.out.println("Parameter Name - "+paramName+", Value - "+request.getParameter(paramName));
		}	
	}
	
	public static String insertPageDetails(){
		PageDetailsVo vo = new PageDetailsVo();
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
				
				if (client.insert("tb_page_elements", vals) == 1) {
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
	public static void insertPageElements(ArrayList<PageDetailsVo> elementsList){
		

		String statusMsg = "";
		PostgresDBHelper client = new PostgresDBHelper();
		try {
			if (client.connect()) {
				System.out.println("DB connected");				
				for(PageDetailsVo vo : elementsList) {
					if(vo.getPageid() == 0) {
						continue;
					}
			client.execQueryParams(QueryConstants.ELEMENTS_INSERT_WITH_SELECTION,
						vo.getPageid(),vo.getPropertyName(),vo.getInputValue(),
						vo.getActionName(),vo.getRelativeXpathbyId(),
						vo.getRelativeXpathbyName(),vo.getxPath(),
						vo.getTagName(),vo.getId(),vo.getText_for_idtext(),vo.getCreatedBy(),vo.isSelected());
				}
			
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
	
	}
}
