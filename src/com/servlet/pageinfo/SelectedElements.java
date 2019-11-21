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
import com.dao.pageinfo.SelectedElementsDAO;
import com.dto.PageDetailsVo;
import com.dto.ScreenDTO;
import com.dto.ScreenElementDTO;
import com.google.gson.Gson;

/**
 * Servlet implementation class PageElements
 */
@WebServlet("/SelectedElements")
public class SelectedElements extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SelectedElements() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String status;
		String httpCall = request.getParameter("httpCall");
		if (httpCall.equalsIgnoreCase("create")) {
			String screenName = request.getParameter("screen_name");
			String arrayids = request.getParameter("updatedIds");
			String pageId = request.getParameter("pageId");
			String eleOrder = request.getParameter("elementsOrder");
			ScreenDTO screenvo = new ScreenDTO();
			screenvo.setScreenName(screenName);
			screenvo.setScreenDescription("");
			screenvo.setPageId(Integer.parseInt(pageId));
			int createdScreenId = ScreenDAO.CreateScreen(screenvo);
			if (createdScreenId == -1) {
				status = "Screen already exists.";
			} else if (createdScreenId == 0) {
				status = "Screen not created Please contact admin.";
			} else {
				status = insertScreenElements(arrayids, createdScreenId, eleOrder);
			}

			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(status);
		} else if (httpCall.equalsIgnoreCase("updateElements")) {

			ArrayList<PageDetailsVo> pageList = new ArrayList<>();
			int pageId = Integer.parseInt(request.getParameter("pageId"));
			int offsetvalue = Integer.parseInt(request.getParameter("offSetVal"));
			System.out.println("pageId: " + pageId + "    offset: " + offsetvalue);
			pageList = SelectedElementsDAO.getSelectedElementsList(pageId, offsetvalue);

			new Gson().toJson(pageList, response.getWriter());
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
		} else if (httpCall.equalsIgnoreCase("getScreens")) {

			ArrayList<ScreenDTO> pageList = new ArrayList<>();
			pageList = ScreenDAO.getScreensList(4);
			new Gson().toJson(pageList, response.getWriter());
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

		} else {
			ArrayList<PageDetailsVo> pageList = new ArrayList<>();
			int pageId = Integer.parseInt(request.getParameter("pageId"));
			int offsetvalue = Integer.parseInt(request.getParameter("offSetVal"));
			System.out.println("pageId: " + pageId + "    offset: " + offsetvalue);
			pageList = SelectedElementsDAO.getSelectedElementsList(pageId, offsetvalue);

			new Gson().toJson(pageList, response.getWriter());
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
		}

	}

	private String setElementOrder(String arrayids, int screenId) {
		String status = "";
		List<String> items = Arrays.asList(arrayids.split("\\s*,\\s*"));
		ArrayList<ScreenElementDTO> screenElementList = new ArrayList<>();
		for (int i = 0; i < items.size(); i++) {
			ScreenElementDTO sceenelement = new ScreenElementDTO();
			sceenelement.setElementId(Integer.parseInt(items.get(i)));
			sceenelement.setElementOrder(i);
			sceenelement.setScreenId(screenId);
			screenElementList.add(sceenelement);

		}
		if (screenElementList.size() > 0) {
			status = SelectedElementsDAO.updateElementOrder(screenElementList);
		} else {
			status = "no elements are selected";
		}

		return status;
	}

	private String insertScreenElements(String arrayids, int screenId, String order) {
		String status = "";
		List<String> items = Arrays.asList(arrayids.split("\\s*,\\s*"));
		ArrayList<ScreenElementDTO> screenElementList = new ArrayList<>();
		for (int i = 0; i < items.size(); i++) {
			ScreenElementDTO sceenelement = new ScreenElementDTO();
			sceenelement.setElementId(Integer.parseInt(items.get(i)));
			sceenelement.setScreenId(screenId);
			screenElementList.add(sceenelement);

		}
		if (screenElementList.size() > 0) {
			status = SelectedElementsDAO.insertScreenPageElements(screenElementList);
			if (status.equalsIgnoreCase("Screen has been created successfully.")) {
				if (order != null && !order.isEmpty()) {
					setElementOrder(order, screenId);
				}

			}
		} else {
			status = "no elements are selected";
		}

		return status;
	}
}
