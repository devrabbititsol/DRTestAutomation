package com.servlet.pageinfo;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.PageInfoDAO;
import com.dto.PageDetailsVo;
import com.google.gson.Gson;

/**
 * Servlet implementation class PageInfo
 */
@WebServlet("/PageInfo")
public class PageInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PageInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<PageDetailsVo> pageList = new ArrayList<>();
		
		//CheckInsert.insertPage();
		
		
		pageList = PageInfoDAO.getPagesList();

		new Gson().toJson(pageList, response.getWriter());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
	}

}
