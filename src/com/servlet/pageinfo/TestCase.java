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
import com.dao.pageinfo.TestCaseDAO;
import com.dao.pageinfo.TestCaseScreensDAO;
import com.dto.ScreenDTO;
import com.dto.ScreenElementDTO;
import com.dto.TestCaseDTO;
import com.dto.TestCaseScreensDTO;
import com.google.gson.Gson;

/**
 * Servlet implementation class TestCase
 */
@WebServlet("/TestCase")
public class TestCase extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String httpCall = request.getParameter("httpCall");
		String status;
		if (httpCall.equalsIgnoreCase("create")) {
			String testcaseName = request.getParameter("testcase_name");
			String arrayids = request.getParameter("updatedIds");
			String screenorder = request.getParameter("order");

			TestCaseDTO testcase = new TestCaseDTO();
			testcase.setTestCaseName(testcaseName);

			int createdTestcaseId = TestCaseDAO.CreateTestCase(testcase);
			if (createdTestcaseId == -1) {
				status = "Testcase already exists.";
			} else if (createdTestcaseId == 0) {
				status = "Screen not created Please contact admin";
			} else {
				status = insertScreenElements(arrayids, createdTestcaseId, screenorder);
			}

			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(status);
		}else if(httpCall.equalsIgnoreCase("update")) {
			String updateStatus = "";
			String testcaseid = request.getParameter("testcase_id");
			int testcaseId = Integer.parseInt(testcaseid);
			if(testcaseId != 0) {
				String screenorder = request.getParameter("order");
				updateStatus = setScreenOrder(screenorder, testcaseId);
			}else {
				updateStatus = "please try after some time";
			}
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(updateStatus);
			
		} else if (httpCall.equalsIgnoreCase("gettestcases")) {
			ArrayList<TestCaseDTO> pageList = new ArrayList<>();
			pageList = TestCaseDAO.getTestcaseList(); // pageid
			new Gson().toJson(pageList, response.getWriter());
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
		}
	}

	private String insertScreenElements(String arrayids, int testcaseId, String order) {
		String status = "";
		List<String> items = Arrays.asList(arrayids.split("\\s*,\\s*"));
		ArrayList<TestCaseScreensDTO> screenList = new ArrayList<>();
		for (int i = 0; i < items.size(); i++) {
			TestCaseScreensDTO sceen = new TestCaseScreensDTO();
			sceen.setScreenid(Integer.parseInt(items.get(i)));
			sceen.setTestcaseid(testcaseId);
			screenList.add(sceen);

		}
		if (screenList.size() > 0) {
			status = TestCaseScreensDAO.insertTestCaseScreens(screenList);
			if (status.equalsIgnoreCase("Testcase has been created successfully.")) {
				if (order != null && !order.isEmpty()) {
					setScreenOrder(order, testcaseId);
				}

			}
		} else {
			status = "no elements are selected";
		}

		return status;
	}

	private String setScreenOrder(String order, int testcaseId) {
		String status = "";
		List<String> items = Arrays.asList(order.split("\\s*,\\s*"));
		ArrayList<TestCaseScreensDTO> testcaseList = new ArrayList<>();
		for (int i = 0; i < items.size(); i++) {
			TestCaseScreensDTO testcasescreen = new TestCaseScreensDTO();
			testcasescreen.setScreenid(Integer.parseInt(items.get(i)));
			testcasescreen.setOrder(i);
			testcasescreen.setTestcaseid(testcaseId);
			testcaseList.add(testcasescreen);

		}
		if (testcaseList.size() > 0) {
			status = TestCaseScreensDAO.updateScreensOrder(testcaseList);
		} else {
			status = "no elements are selected";
		}

		return status;
	}
}
