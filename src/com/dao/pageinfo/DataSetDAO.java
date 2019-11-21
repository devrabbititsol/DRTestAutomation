package com.dao.pageinfo;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.db.PostgresDBHelper;
import com.dto.DataSetValuesVo;
import com.dto.DatasetVo;
import com.dto.PageDetailsVo;
import com.dto.ScreeenNames;
import com.dto.ScreenDTO;
import com.dto.ScreenElementDTO;
import com.dto.TestCaseDTO;
import com.dto.TestCaseElementsVo;
import com.dto.TestCaseOutputVo;
import com.dto.TestCaseScreensDTO;
import com.util.QueryConstants;

public class DataSetDAO {
	public static int CreateDataSet(DatasetVo dataset) {
		PostgresDBHelper client = new PostgresDBHelper();
		try {
			if (client.connect()) {
				if (!dataset.isDuplicateDataset(dataset)) {
					int insertedId = client.insertGetRelatedId(QueryConstants.CREATE_DATASET, dataset.getTestCaseId(),
							dataset.getDatasetName());

					return insertedId;
				} else {
					return -1;
				}
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String insertDataSetElementValues(DataSetValuesVo[] testcasescreens, int dataSetId) {

		String statusMsg = "";
		PostgresDBHelper client = new PostgresDBHelper();
		try {
			if (client.connect()) {
				System.out.println("DB connected");

				for (DataSetValuesVo vo : testcasescreens) {

					client.execQueryParams(QueryConstants.CREATE_DATASET_ELEMENTS, dataSetId, vo.getScreenId(),
							vo.getElementId(), vo.getElementValue(), vo.isIsvaldate());
				}
				statusMsg = "Dataset has been created successfully.";
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return statusMsg;

	}

	public static ArrayList<DatasetVo> getDataSetByTestCase(int testcaseId) {

		PostgresDBHelper client = new PostgresDBHelper();
		ArrayList<DatasetVo> datasetList = new ArrayList<>();
		try {
			if (client.connect()) {
				System.out.println("DB connected moduleId id");
				ResultSet rs = client.execQuerywithParams(QueryConstants.DATASET_BY_TESTCASE, testcaseId);

				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				while (rs.next()) {
					DatasetVo pagedetails = new DatasetVo();
					pagedetails.setDatasetId(rs.getInt(1));
					pagedetails.setDatasetName(rs.getString(2));

					datasetList.add(pagedetails);
					System.out.println(rs.getInt(1) + ":::::" + rs.getString(2));
				}
			}
			return datasetList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static ArrayList<PageDetailsVo> getLastRecordDataSet(int testcaseId) {

		PostgresDBHelper client = new PostgresDBHelper();
		ArrayList<PageDetailsVo> pageList = new ArrayList<>();
		try {
			if (client.connect()) {
				System.out.println("DB connected");
				ResultSet rs = client.execQuerywithParams(QueryConstants.LAST_DATASET_RECORD, testcaseId);

				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				while (rs.next()) {
					PageDetailsVo pagedetails = new PageDetailsVo();
					pagedetails.setElementId(rs.getInt(1));
					pagedetails.setScreenId(rs.getInt(2));
					pagedetails.setText_for_idtext(rs.getString(3));
					pagedetails.setSelected(rs.getBoolean(4));

					pageList.add(pagedetails);
					System.out.println(rs.getInt(1) + ":::::" + rs.getString(2));
				}
			}
			return pageList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static TestCaseElementsVo getDatasetScreensNames(int testcaseid) {
		PostgresDBHelper client = new PostgresDBHelper();
		ArrayList<TestCaseOutputVo> screenList = new ArrayList<>();
		ArrayList<PageDetailsVo> pageList = new ArrayList<>();
		try {
			if (client.connect()) {

				System.out.println("DB connected");
				ResultSet datasetRS = client.execQuerywithParams(QueryConstants.LAST_DATASET_RECORD, testcaseid);

				while (datasetRS.next()) {
					PageDetailsVo pagedetails = new PageDetailsVo();
					pagedetails.setElementId(datasetRS.getInt(1));
					pagedetails.setScreenId(datasetRS.getInt(2));
					pagedetails.setText_for_idtext(datasetRS.getString(3));
					pagedetails.setSelected(datasetRS.getBoolean(4));
					pageList.add(pagedetails);
					System.out.println(datasetRS.getInt(1) + ":::::" + datasetRS.getString(2));
				}

				ResultSet rs = client.execQuerywithParams(QueryConstants.TESTCASE_SCREENS_ELEMENTS, testcaseid);
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
					vo.setXpath(rs.getString(8));
					vo.setXpathById(rs.getString(9));
					vo.setXpathByName(rs.getString(10));
					screenList.add(vo);
				}

				for (int i = 0; i < screenList.size(); i++) {
					screenList.get(i).setDatasetValue(pageList.get(i).getText_for_idtext());
					screenList.get(i).setSelected(pageList.get(i).isSelected());
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
						element.setDataSetValue(screenList.get(i).getDatasetValue());
						element.setSelected(screenList.get(i).isSelected());

						if (screenList.get(i).getXpathById() != null && !screenList.get(i).getXpathById().isEmpty()) {
							element.setXpath(screenList.get(i).getXpathById());
						} else if (screenList.get(i).getXpathByName() != null
								&& !screenList.get(i).getXpathByName().isEmpty()) {
							element.setXpath(screenList.get(i).getXpathByName());
						} else {
							element.setXpath(screenList.get(i).getXpath());
						}

						if (element.getFieldType().equalsIgnoreCase("button")
								|| element.getFieldType().equalsIgnoreCase("link")) {
							element.setDataSetValue(element.getFieldType());
						}
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
							element.setDataSetValue(screenList.get(i).getDatasetValue());
							element.setSelected(screenList.get(i).isSelected());

							if (screenList.get(i).getXpathById() != null
									&& !screenList.get(i).getXpathById().isEmpty()) {
								element.setXpath(screenList.get(i).getXpathById());
							} else if (screenList.get(i).getXpathByName() != null
									&& !screenList.get(i).getXpathByName().isEmpty()) {
								element.setXpath(screenList.get(i).getXpathByName());
							} else {
								element.setXpath(screenList.get(i).getXpath());
							}

							if (element.getFieldType().equalsIgnoreCase("button")
									|| element.getFieldType().equalsIgnoreCase("link")) {
								element.setDataSetValue(element.getFieldType());
							}
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

	public static TestCaseElementsVo getDatasetByDatasetId(int testcaseid, int datasetid) {
		PostgresDBHelper client = new PostgresDBHelper();
		ArrayList<TestCaseOutputVo> screenList = new ArrayList<>();
		ArrayList<PageDetailsVo> pageList = new ArrayList<>();
		try {
			if (client.connect()) {

				System.out.println("DB connected");
				ResultSet datasetRS = client.execQuerywithParams(QueryConstants.DATASET_RECORD, datasetid);

				while (datasetRS.next()) {
					PageDetailsVo pagedetails = new PageDetailsVo();
					pagedetails.setElementId(datasetRS.getInt(1));
					pagedetails.setScreenId(datasetRS.getInt(2));
					pagedetails.setText_for_idtext(datasetRS.getString(3));
					pagedetails.setSelected(datasetRS.getBoolean(4));
					pageList.add(pagedetails);
					System.out.println(datasetRS.getInt(1) + ":::::" + datasetRS.getString(2));
				}

				ResultSet rs = client.execQuerywithParams(QueryConstants.TESTCASE_SCREENS_ELEMENTS, testcaseid);
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
					vo.setXpath(rs.getString(8));
					vo.setXpathById(rs.getString(9));
					vo.setXpathByName(rs.getString(10));
					screenList.add(vo);
				}

				for (int i = 0; i < screenList.size(); i++) {
					screenList.get(i).setDatasetValue(pageList.get(i).getText_for_idtext());
					screenList.get(i).setSelected(pageList.get(i).isSelected());
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
						element.setDataSetValue(screenList.get(i).getDatasetValue());
						element.setSelected(screenList.get(i).isSelected());

						if (screenList.get(i).getXpathById() != null && !screenList.get(i).getXpathById().isEmpty()) {
							element.setXpath(screenList.get(i).getXpathById());
						} else if (screenList.get(i).getXpathByName() != null
								&& !screenList.get(i).getXpathByName().isEmpty()) {
							element.setXpath(screenList.get(i).getXpathByName());
						} else {
							element.setXpath(screenList.get(i).getXpath());
						}

						if (element.getFieldType().equalsIgnoreCase("button")
								|| element.getFieldType().equalsIgnoreCase("link")) {
							element.setDataSetValue(element.getFieldType());
						}
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
							element.setDataSetValue(screenList.get(i).getDatasetValue());
							element.setSelected(screenList.get(i).isSelected());

							if (screenList.get(i).getXpathById() != null
									&& !screenList.get(i).getXpathById().isEmpty()) {
								element.setXpath(screenList.get(i).getXpathById());
							} else if (screenList.get(i).getXpathByName() != null
									&& !screenList.get(i).getXpathByName().isEmpty()) {
								element.setXpath(screenList.get(i).getXpathByName());
							} else {
								element.setXpath(screenList.get(i).getXpath());
							}

							if (element.getFieldType().equalsIgnoreCase("button")
									|| element.getFieldType().equalsIgnoreCase("link")) {
								element.setDataSetValue(element.getFieldType());
							}
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
