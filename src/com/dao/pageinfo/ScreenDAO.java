package com.dao.pageinfo;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.db.PostgresDBHelper;
import com.dto.ScreeenNames;
import com.dto.ScreenDTO;
import com.util.QueryConstants;

public class ScreenDAO {
	public static int CreateScreen(ScreenDTO screenvo) {
		String statusMsg = "";
		PostgresDBHelper client = new PostgresDBHelper();
		try {
			if (client.connect()) {
				if (!screenvo.isDuplicateScreen(screenvo)) {
					int insertedId = client.insertGetRelatedId(QueryConstants.CREATE_SCREEN, screenvo.getScreenName(),
							screenvo.getScreenDescription(), screenvo.getPageId());

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

	public static ArrayList<ScreenDTO> getScreensList(int pageid) {
		PostgresDBHelper client = new PostgresDBHelper();
		ArrayList<ScreenDTO> pageList = new ArrayList<>();
		try {
			if (client.connect()) {
				System.out.println("DB connected moduleId id");
				ResultSet rs = client.execQuerywithParams(QueryConstants.GET_SCREENS, pageid);

				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				while (rs.next()) {
					ScreenDTO screenDetail = new ScreenDTO();

					screenDetail.setScreenId(rs.getInt(1));
					screenDetail.setScreenName(rs.getString(2));
					screenDetail.setScreenDescription(rs.getString(3));

					pageList.add(screenDetail);
					System.out.println(rs.getInt(1) + ":::::" + rs.getString(2) + ":::::" + rs.getString(3));
				}
			}
			return pageList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
