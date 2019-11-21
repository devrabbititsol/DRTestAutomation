package com.servlet.pageinfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DemoLocalTest.CheckInsert;
import com.dao.pageinfo.PageInfoElementsDTO;
import com.dto.PageDetailsVo;
import com.util.ChromeDriverProvider;

/**
 * Servlet implementation class CreatePages
 */
@WebServlet("/CreatePages")
public class CreatePages extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static ArrayList<PageDetailsVo> pagedetailsList = new ArrayList<>();

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String httpCall = request.getParameter("httpCall");
		if (httpCall.equalsIgnoreCase("createpage")) {
			String checkedElements = request.getParameter("updatedIds");
			String pageName = request.getParameter("pagename");
			String unCheckedIds = request.getParameter("uncheckedIds");
			String projectId = request.getParameter("projectid");
			System.out.println("checked elemtns: " + checkedElements);
			System.out.println("checked id: " + unCheckedIds);
			pagedetailsList = ChromeDriverProvider.getPageList();
			System.out.println("Arraylst: " + ChromeDriverProvider.getPageList());
			int project_Id = Integer.parseInt(projectId);
			if (project_Id != 0) {
				int pageId = CheckInsert.createPage(pageName, project_Id);
				if (pageId == -1) {
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("Page already exists.");
				} else if (pageId == 0) {
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("Something went wrong");
				} else {
					List<String> checkedIds = insertScreenElements(checkedElements); // [1,2,3,24,34]
					for (int i = 0; i < pagedetailsList.size(); i++) {
						pagedetailsList.get(i).setPageid(pageId);
					}
					for (int j = 0; j < checkedIds.size(); j++) {
						int value = Integer.parseInt(checkedIds.get(j));
						pagedetailsList.get(value).setSelected(true);
					}
					PageInfoElementsDTO.insertPageElements(pagedetailsList);
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write("Page has been created successfully.");
				}
			}

		}
	}

	private List<String> insertScreenElements(String arrayids) {
		String status = "";
		List<String> items = Arrays.asList(arrayids.split("\\s*,\\s*"));

		return items;
	}

}
