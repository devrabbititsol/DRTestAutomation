package com.servlet.pageinfo;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.pageinfo.ScreenDAO;
import com.dao.pageinfo.TestCaseScreensDAO;
import com.dto.ScreeenNames;
import com.dto.TestCaseElementsVo;
import com.dto.TestCaseOutputVo;
import com.google.gson.Gson;

/**
 * Servlet implementation class ScreenNames
 */
@WebServlet("/TestCaseScreens")
public class TestCaseScreens extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String httpCall = request.getParameter("httpCall");
		String testcaseIdSt = request.getParameter("testcaseId");
		int testCaseId = Integer.parseInt(testcaseIdSt);
		TestCaseElementsVo screenNamesList = new TestCaseElementsVo();
		if(testCaseId !=0) {
			 screenNamesList =  TestCaseScreensDAO.getScreensNames(testCaseId);		
		}
		
		new Gson().toJson(screenNamesList, response.getWriter());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	
		}

}
