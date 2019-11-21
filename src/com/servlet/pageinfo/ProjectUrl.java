package com.servlet.pageinfo;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.PageInfoDAO;
import com.dao.pageinfo.ProjectUrlDAO;
import com.dto.PageDetailsVo;
import com.dto.ProjectUrlVo;
import com.google.gson.Gson;

/**
 * Servlet implementation class ProjectUrl
 */
@WebServlet("/ProjectUrl")
public class ProjectUrl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String httpCall = request.getParameter("httpCall");
		
		if(httpCall.equalsIgnoreCase("defaultURL")){	
		ProjectUrlVo projectUrl = new ProjectUrlVo();
		projectUrl = ProjectUrlDAO.getDefaultUrl();
		new Gson().toJson(projectUrl, response.getWriter());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		}
		
		else if(httpCall.equalsIgnoreCase("get")){	
			ArrayList<ProjectUrlVo> projectUrls = new ArrayList<ProjectUrlVo>();
			projectUrls = ProjectUrlDAO.getUrls();
			new Gson().toJson(projectUrls, response.getWriter());
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			}
		else if(httpCall.equalsIgnoreCase("updateUrl")){	
			ProjectUrlVo projectvo = new ProjectUrlVo();
				int urlId = Integer.parseInt(request.getParameter("selectedUrl"));
				 projectvo = ProjectUrlDAO.updateProjectAsDefaultProject(urlId);
				
				new Gson().toJson(projectvo, response.getWriter());
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				
				}
	}

}
