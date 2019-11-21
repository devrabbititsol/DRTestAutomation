package com.dao.pageinfo;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.db.PostgresDBHelper;
import com.dto.ScreeenNames;
import com.dto.ScreenDTO;
import com.dto.ScreenElementDTO;
import com.dto.TestCaseElementsVo;
import com.dto.TestCaseOutputVo;
import com.dto.TestCaseScreensDTO;
import com.util.QueryConstants;

public class TestCaseScreensDAO {

	public static String insertTestCaseScreens(ArrayList<TestCaseScreensDTO> testcasescreens) {

		String statusMsg = "";
		PostgresDBHelper client = new PostgresDBHelper();
		try {
			if (client.connect()) {
				System.out.println("DB connected");

				for (TestCaseScreensDTO vo : testcasescreens) {
					if (vo.getScreenid() == 0) {
						continue;
					}
					client.execQueryParams(QueryConstants.CREATE_TESTCASE_SCREENS, vo.getScreenid(),
							vo.getTestcaseid());
				}
				statusMsg = "Testcase has been created successfully.";
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return statusMsg;

	}
	

	public static String updateScreensOrder(ArrayList<TestCaseScreensDTO> testcasescreens) {

		String statusMsg = "";
		PostgresDBHelper client = new PostgresDBHelper();
		try {
			if (client.connect()) {
				System.out.println("DB connected");

				for (TestCaseScreensDTO vo : testcasescreens) {
					if (vo.getTestcaseid() == 0) {
						continue;
					}else {
						client.execQueryParams(QueryConstants.TESTCASE_SCREENS_ORDER, vo.getOrder(),
								vo.getTestcaseid(),vo.getScreenid());
					
					}				
				
				}
				statusMsg = "Updated Successfully";
			
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return statusMsg;

	}

	public static TestCaseElementsVo getScreensNames(int testcaseId) {
		PostgresDBHelper client = new PostgresDBHelper();
		ArrayList<TestCaseOutputVo> screenList = new ArrayList<>();

		try {
			if (client.connect()) {
				ResultSet rs = client.execQuerywithParams(QueryConstants.TESTCASE_SCREENS_ELEMENTS,testcaseId);
				while (rs.next()) {
					ScreeenNames screens = new ScreeenNames();
					screens.setScreenId(rs.getInt(1));
					screens.setScreenName(rs.getString(2));
					TestCaseOutputVo vo = new TestCaseOutputVo();
					vo.setTestcaseId(rs.getInt(1));
					vo.setTestcaseName(rs.getString(2));
					vo.setScreenId(rs.getInt(3));
					vo.setScreenName(rs.getString(4));
					vo.setPageElementsId(rs.getInt(5));
					vo.setPropName(rs.getString(6));
					vo.setPropInputValue(rs.getString(7));
					screenList.add(vo);
				}
				TestCaseElementsVo testcaseElements = new TestCaseElementsVo();
				if (!screenList.isEmpty()) {
					testcaseElements.setTestcaseId(screenList.get(0).getTestcaseId());
					testcaseElements.setTestcaseName(screenList.get(0).getTestcaseName());
				}
				ArrayList<ScreenDTO> screens = new ArrayList<>();
				for (int i = 0; i < screenList.size(); ++i) {
					ArrayList<ScreenElementDTO> elements = new ArrayList<>();
					if (!isDuplicateScreen(screenList.get(i).getScreenId(), screens)) {
						ScreenDTO screen = new ScreenDTO();
						screen.setScreenId(screenList.get(i).getScreenId());
						screen.setScreenName(screenList.get(i).getScreenName());

						ScreenElementDTO element = new ScreenElementDTO();
						element.setProp_name(screenList.get(i).getPropName());
						element.setElementId(screenList.get(i).getPageElementsId());
						element.setFieldType(screenList.get(i).getPropInputValue());
						elements.add(element);

						screen.setScreenElements(elements);
						screens.add(screen);
					} else {
						ScreenDTO screen = getDuplicateScreen(screenList.get(i).getScreenId(), screens);
						if (null != screen) {
							ArrayList<ScreenElementDTO> elementsList = screen.getScreenElements();
							ScreenElementDTO element = new ScreenElementDTO();
							element.setElementId(screenList.get(i).getPageElementsId());
							element.setProp_name(screenList.get(i).getPropName());
							element.setFieldType(screenList.get(i).getPropInputValue());
							elementsList.add(element);
						}
					}
				}
				testcaseElements.setScreens(screens);
				return testcaseElements;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static boolean isDuplicateScreen(int screenId, ArrayList<ScreenDTO> screens) {
		for (ScreenDTO screen : screens) {
			if (screen.getScreenId() == screenId) {
				return true;
			}
		}
		return false;
	}

	private static ScreenDTO getDuplicateScreen(int screenId, ArrayList<ScreenDTO> screens) {
		for (ScreenDTO screen : screens) {
			if (screen.getScreenId() == screenId) {
				return screen;
			}
		}
		return null;
	}
}
