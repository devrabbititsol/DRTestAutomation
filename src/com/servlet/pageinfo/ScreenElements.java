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

import com.dao.pageinfo.ScreenDAO;
import com.dao.pageinfo.ScreenElementsDAO;
import com.dao.pageinfo.SelectedElementsDAO;
import com.dto.PageDetailsVo;
import com.dto.ScreenDTO;
import com.dto.ScreenElementDTO;
import com.google.gson.Gson;

/**
 * Servlet implementation class PageElements
 */
@WebServlet("/ScreenElements")
public class ScreenElements extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String status ;
		String httpCall = request.getParameter("httpCall");
		
		if(httpCall.equalsIgnoreCase("getlist")){
			 
			ArrayList<ScreenElementDTO> pageList = new ArrayList<>();
			int screenId = Integer.parseInt(request.getParameter("screenId"));
			int offsetvalue = Integer.parseInt(request.getParameter("offSetVal"));
			System.out.println("screenId: " + screenId + "    offset: " + offsetvalue);
			pageList = ScreenElementsDAO.getSelectedElementsList(screenId, offsetvalue);

			new Gson().toJson(pageList, response.getWriter());
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
		}

	}

}
