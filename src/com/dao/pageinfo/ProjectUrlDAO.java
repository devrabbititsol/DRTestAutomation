package com.dao.pageinfo;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.db.PostgresDBHelper;
import com.dto.PageDetailsVo;
import com.dto.ProjectUrlVo;
import com.util.QueryConstants;

public class ProjectUrlDAO {
	public static ProjectUrlVo getDefaultUrl() {
		PostgresDBHelper client = new PostgresDBHelper();
		ProjectUrlVo projectDetails = new ProjectUrlVo();
		try {
			if (client.connect()) {
				System.out.println("DB connected");
				ResultSet rs = client.execQuerywithParams(QueryConstants.DEFAULT_URL);

				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				while (rs.next()) {
					projectDetails.setProjectId(rs.getInt(1));
					projectDetails.setUrl(rs.getString(2));
					projectDetails.setProjectName(rs.getString(3));

					System.out.println(rs.getInt(1) + ":::::" + rs.getString(2) + ":::::" + rs.getString(3));
				}
			}
			return projectDetails;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ArrayList<ProjectUrlVo> getUrls() {
		PostgresDBHelper client = new PostgresDBHelper();
		ArrayList<ProjectUrlVo> projectsList = new ArrayList<>();

		try {
			if (client.connect()) {
				System.out.println("DB connected");
				ResultSet rs = client.execQuerywithParams(QueryConstants.URLS);

				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				while (rs.next()) {
					ProjectUrlVo projectDetails = new ProjectUrlVo();
					projectDetails.setProjectId(rs.getInt(1));
					projectDetails.setUrl(rs.getString(2));
					projectDetails.setProjectName(rs.getString(3));
					projectsList.add(projectDetails);
					System.out.println(rs.getInt(1) + ":::::" + rs.getString(2) + ":::::" + rs.getString(3));
				}
			}
			return projectsList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ProjectUrlVo updateProjectAsDefaultProject(int projectId) {
		PostgresDBHelper client = new PostgresDBHelper();
		ProjectUrlVo projectDetails = new ProjectUrlVo();
		try {
			if (client.connect()) {
				client.insertGetRelatedId(QueryConstants.UPDATE_PROJECT_AS_DEFAULT, projectId);
				ResultSet rs = client.execQuerywithParams(QueryConstants.DEFAULT_URL);
				while (rs.next()) {
					projectDetails.setProjectId(rs.getInt(1));
					projectDetails.setUrl(rs.getString(2));
					projectDetails.setProjectName(rs.getString(3));

					System.out.println(rs.getInt(1) + ":::::" + rs.getString(2) + ":::::" + rs.getString(3));
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			//client.close();
		}
		return projectDetails;
	}

}
