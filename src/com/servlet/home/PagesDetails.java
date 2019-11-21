package com.servlet.home;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.PageDetailsDAO;
import com.dto.PageDetailsVo;
import com.google.gson.Gson;
import com.jfx.WebViewMain;

import javafx.application.Application;

/**
 * Servlet implementation class ClientsList
 */
@WebServlet("/PageDetail")
public class PagesDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PagesDetails() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			String httpCall=request.getParameter("httpCall");
			//  Application.launch(WebViewMain.class);
		if(httpCall.equalsIgnoreCase("create_PageDetails") ) {
			String status = PageDetailsDAO.insertPageDetails();
			 response.setCharacterEncoding("UTF-8");
			  response.getWriter().write(status);
		}else {
			ArrayList<PageDetailsVo> pageList =  new ArrayList<>();    
			int pageId = Integer.parseInt(request.getParameter("pageId"));
			pageList = PageDetailsDAO.getPageDetailsList(pageId);
			
		   new Gson().toJson(pageList, response.getWriter());
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		}
		 
	}

}
