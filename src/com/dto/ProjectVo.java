package com.dto;

import java.sql.SQLException;

import com.db.PostgresDBHelper;
import com.util.QueryConstants;

public class ProjectVo {
	int id;
	String url;
	String projectName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public boolean isDuplicateProject(ProjectVo project) {
		PostgresDBHelper client = new PostgresDBHelper();
		try {
			if (client.connect()) {
				return client.checkForDuplicateProject(project);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}

}
