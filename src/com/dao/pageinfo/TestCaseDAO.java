package com.dao.pageinfo;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import com.db.PostgresDBHelper;
import com.dto.ScreenDTO;
import com.dto.TestCaseDTO;
import com.util.QueryConstants;

public class TestCaseDAO {
	public static int CreateTestCase(TestCaseDTO testcase) {
		PostgresDBHelper client = new PostgresDBHelper();
		try {
			if (client.connect()) {
				if (!testcase.isDuplicateTestCase(testcase)) {
					int insertedId = client.insertGetRelatedId(QueryConstants.CREATE_TESTCASE,
							testcase.getTestCaseName());

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

	public static ArrayList<TestCaseDTO> getTestcaseList() {
		PostgresDBHelper client = new PostgresDBHelper();
		ArrayList<TestCaseDTO> testcaseList = new ArrayList<>();
		try {
			if (client.connect()) {
				System.out.println("DB connected moduleId id");
				ResultSet rs = client.execQuerywithParams(QueryConstants.TESTCASE_NAMES);

				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				while (rs.next()) {
					TestCaseDTO testcase = new TestCaseDTO();

					testcase.setTestCaseId(rs.getInt(1));
					testcase.setTestCaseName(rs.getString(2));

					testcaseList.add(testcase);
					System.out.println(rs.getInt(1) + ":::::" + rs.getString(2));
				}
			}
			return testcaseList;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
