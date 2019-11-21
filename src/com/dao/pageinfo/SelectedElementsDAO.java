package com.dao.pageinfo;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.db.PostgresDBHelper;
import com.dto.PageDetailsVo;
import com.dto.ScreenDTO;
import com.dto.ScreenElementDTO;
import com.util.IntArray;
import com.util.QueryConstants;

public class SelectedElementsDAO {

	public static ArrayList<PageDetailsVo> getSelectedElementsList(int pageId, int offsetval) {
		PostgresDBHelper client = new PostgresDBHelper();
		ArrayList<PageDetailsVo> pageList = new ArrayList<>();
		try {
			if (client.connect()) {
				System.out.println("DB connected moduleId id" + pageId);
				ResultSet rs = client.execQuerywithParams(QueryConstants.SELECTED_ELEMENTS, pageId, offsetval);

				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				while (rs.next()) {
					PageDetailsVo pageDetail = new PageDetailsVo();
					pageDetail.setPageid(rs.getInt(1));
					// client1.setClientName(rs.getString(2));
					pageDetail.setPropertyName(rs.getString(3));
					pageDetail.setInputValue(rs.getString(4));
					pageDetail.setActionName(rs.getString(5));
					pageDetail.setRelativeXpathbyId(rs.getString(6));
					pageDetail.setRelativeXpathbyName(rs.getString(7));
					pageDetail.setxPath(rs.getString(8));
					pageDetail.setSelected(rs.getBoolean(9));

					pageList.add(pageDetail);
					System.out.println(rs.getInt(1) + ":::::" + rs.getString(3) + ":::::" + rs.getString(4) + ":::::"
							+ rs.getString(5));

				}

				rs.close();
			}

			return pageList;
		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
			return null;
		}
	}

	public static String updateCheckedElement(int[] values) {

		PostgresDBHelper client = new PostgresDBHelper();
		try {
			if (client.connect()) {
				IntArray intArray = new IntArray(values);
				client.execQueryParams(QueryConstants.PAGEINFO_SETCHECKED_ELEMENTS, intArray);
			}

			return "";
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			client.close();
		}

	}

	public static void prettyPrinterRequest(HttpServletRequest request) {
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = params.nextElement();
			System.out.println("Parameter Name - " + paramName + ", Value - " + request.getParameter(paramName));
		}
	}

	public static String insertScreenPageElements(ArrayList<ScreenElementDTO> screenElementList) {

		String statusMsg = "";
		PostgresDBHelper client = new PostgresDBHelper();
		try {
			if (client.connect()) {
				System.out.println("DB connected");
				/*
				 * if(screenElementList.get(0).getScreenId() != 0) {
				 * client.execQueryParams(QuiryConstants.DELETE_SCREEN_ELEMENTS_BY_SCREENID,
				 * screenElementList.get(0).getScreenId()); }
				 */

				for (ScreenElementDTO vo : screenElementList) {
					if (vo.getScreenId() == 0) {
						continue;
					}
					client.execQueryParams(QueryConstants.SCREEN_ELEMENTS_INSERT, vo.getScreenId(), vo.getElementId());
				}
				statusMsg = "Screen has been created successfully.";
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			client.close();
		}
		return statusMsg;

	}

	public static String updateElementOrder(ArrayList<ScreenElementDTO> screenElementList) {

		String statusMsg = "";
		PostgresDBHelper client = new PostgresDBHelper();
		try {
			if (client.connect()) {
				System.out.println("DB connected");

				for (ScreenElementDTO vo : screenElementList) {
					if (vo.getScreenId() == 0) {
						continue;
					}
					client.execQueryParams(QueryConstants.SCREEN_ELEMENTS_ORDER, vo.getElementOrder(),
							vo.getElementId(), vo.getScreenId());
				}
				statusMsg = "Updated Successfully";
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			client.close();
		}
		return statusMsg;

	}
}
