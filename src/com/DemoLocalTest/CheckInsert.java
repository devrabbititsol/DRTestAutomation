package com.DemoLocalTest;

import com.dao.PageInfoDAO;
import com.dto.PageDetailsVo;
import com.dto.ProjectVo;

public class CheckInsert {

	public static int createPage(String pageName, int projectId) {
		PageDetailsVo pageDetail = new PageDetailsVo();
		pageDetail.setPageName(pageName);
		pageDetail.setPageDescription("");
		pageDetail.setProjectId(projectId);
		if (!pageDetail.isDuplicatePage(pageDetail)) {
			int insertedId = PageInfoDAO.insertPage(pageDetail);
			System.out.println("inserted Page Id :" + insertedId);
			return insertedId;
		} else {
			return -1;
		}
	}

	public static int CreateProject(String url, String name) {
		ProjectVo project = new ProjectVo();
		project.setUrl(url);
		if (!project.isDuplicateProject(project)) {
			int projectid = PageInfoDAO.insertProject(project);
			System.out.println("inserted PROJECT iD:" + projectid);
			if(projectid != 0) {
				PageInfoDAO.updateProjectAsDefaultProject(projectid);
			}
			return projectid;
		} else {
			return -1;
		}
	}

}
