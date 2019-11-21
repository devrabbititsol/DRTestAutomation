package com.dao.pageinfo;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.db.PostgresDBHelper;
import com.dto.PageDetailsVo;
import com.dto.ScreenDTO;
import com.dto.ScreenElementDTO;
import com.util.IntArray;
import com.util.QueryConstants;

public class ScreenElementsDAO {
	
	public static ArrayList<ScreenElementDTO> getSelectedElementsList(int pageId,int offsetval) {
		PostgresDBHelper client = new PostgresDBHelper();
		 ArrayList<ScreenElementDTO> screenEleList =  new ArrayList<>();
		try {
			if (client.connect()) {
				System.out.println("DB connected moduleId id"+pageId);				
				ResultSet rs = client.execQuerywithParams(QueryConstants.GET_SCREENS_ELEMENTS, pageId,offsetval);	
			
				  ResultSetMetaData rsmd = rs.getMetaData();				 
				   int columnsNumber = rsmd.getColumnCount();
				   while (rs.next()) {
					   ScreenElementDTO screenElements = new ScreenElementDTO();
					   PageDetailsVo pageDetail = new PageDetailsVo();	
					   screenElements.setId(rs.getInt(1));
					   screenElements.setScreenId(rs.getInt(2));
					   pageDetail.setPageid(rs.getInt(4));
				         // client1.setClientName(rs.getString(2));
					   pageDetail.setPropertyName(rs.getString(5));
					   pageDetail.setInputValue(rs.getString(6));
					   pageDetail.setActionName(rs.getString(7));
					   pageDetail.setRelativeXpathbyId(rs.getString(8));
					   pageDetail.setRelativeXpathbyName(rs.getString(9));
					   pageDetail.setxPath(rs.getString(10));
					   pageDetail.setSelected(rs.getBoolean(11));
					   screenElements.setPageDetails(pageDetail);
					   screenEleList.add(screenElements);
				       System.out.println(rs.getInt(1)+":::::"+rs.getString(2)+":::::"+rs.getString(3)+":::::"+rs.getString(4)+":::::"+rs.getString(5));
				     
				   }
				   
				   rs.close();
			}
			
			return screenEleList;
		} catch (ClassNotFoundException | SQLException e) {
			
			
			e.printStackTrace();
			return null;
		}	
	}
	
	

}
