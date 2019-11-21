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

import com.dao.PageDetailsDAO;
import com.dao.pageinfo.PageInfoElementsDTO;
import com.dto.PageDetailsVo;
import com.google.gson.Gson;

/**
 * Servlet implementation class PageElements
 */
@WebServlet("/PageElements")
public class PageElements extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PageElements() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String httpCall=request.getParameter("httpCall");
		//  Application.launch(WebViewMain.class);
	if(httpCall.equalsIgnoreCase("create_PageDetails") ) {
		String status = PageInfoElementsDTO.insertPageDetails();
		 response.setCharacterEncoding("UTF-8");
		  response.getWriter().write(status);
	}else if(httpCall.equalsIgnoreCase("update_chekedStatus") ) {
		
		String  checkedIds=request.getParameter("updatedIds");
		String unCheckedIds = request.getParameter("uncheckedIds");
		
		int[] checkedList = convertStringToArray(checkedIds);	
		int[] uncheckedList = convertStringToArray(unCheckedIds);	
		
		String status = PageInfoElementsDTO.updateCheckedElement(checkedList,uncheckedList);
		
		 response.setCharacterEncoding("UTF-8");
		  response.getWriter().write(status);
	}else {
		ArrayList<PageDetailsVo> pageList =  new ArrayList<>();    
		int pageId = Integer.parseInt(request.getParameter("pageId"));
		int offsetvalue = Integer.parseInt(request.getParameter("offSetVal"));
		System.out.println("pageId: "+pageId+"    offset: "+offsetvalue);
		pageList = PageInfoElementsDTO.getPageDetailsList(pageId,offsetvalue);
		
	   new Gson().toJson(pageList, response.getWriter());
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	}
	 
}
	
	private int[] convertStringToArray(String arrayids){
		List<String> items = new ArrayList<String>();
		items = Arrays.asList(arrayids.split("\\s*,\\s*"));
		int[] list = new int[items.size()];
		for(int i = 0;i < items.size();i++)
		{
			list[i] = Integer.parseInt(items.get(i));
		}
		return list;
	}

}
