package com.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.db.PostgresDBHelper;
import com.dto.PageDetailsVo;
import com.dto.ProjectVo;
import com.util.QueryConstants;

public class PageInfoDAO {
	
	public static ArrayList<PageDetailsVo> getPagesList() {
		PostgresDBHelper client = new PostgresDBHelper();
		 ArrayList<PageDetailsVo> pageList =  new ArrayList<>();
		try {
			if (client.connect()) {
				System.out.println("DB connected moduleId id");				
				ResultSet rs = client.execQuerywithParams(QueryConstants.PAGEINFO_GETPAGES);	
			
				  ResultSetMetaData rsmd = rs.getMetaData();				 
				   int columnsNumber = rsmd.getColumnCount();
				   while (rs.next()) {
					   PageDetailsVo pageDetail = new PageDetailsVo();	
					   
					   pageDetail.setPageid(rs.getInt(1));
					   pageDetail.setPageName(rs.getString(2));
					   pageDetail.setPageDescription(rs.getString(3));
					   
					   pageList.add(pageDetail);
				       System.out.println(rs.getInt(1)+":::::"+rs.getString(2)+":::::"+rs.getString(3));
				   }				
			}
			return pageList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	public static int insertPage(PageDetailsVo pageDetail){
		PostgresDBHelper client = new PostgresDBHelper();
		try {
			if (client.connect()) {
				System.out.println("DB connected");				
			
				int insertedId = client.insertGetRelatedId(QueryConstants.PAGEINFO_INSERTPAGE, pageDetail.getPageName(),
                        pageDetail.getPageDescription(),pageDetail.getProjectId());
				
				return insertedId;				
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int insertProject(ProjectVo project){
		PostgresDBHelper client = new PostgresDBHelper();
		try {
			if (client.connect()) {
				System.out.println("DB connected");				
			
				int insertedId = client.insertGetRelatedId(QueryConstants.PROJECT_CREATE, project.getUrl());
				
				return insertedId;				
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		return 0;
	}

	public static void updateProjectAsDefaultProject(int projectId) {
		PostgresDBHelper client = new PostgresDBHelper();
		try {
			if (client.connect()) {
				client.execQuerywithParams(QueryConstants.UPDATE_PROJECT_AS_DEFAULT, projectId);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
	}

}
