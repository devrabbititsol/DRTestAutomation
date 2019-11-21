package com.dto;

import com.db.PostgresDBHelper;

public class TestCaseDTO {

	int testCaseId;
	String testCaseName;
	String testCaseDesc;
	
	public int getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(int testCaseId) {
		this.testCaseId = testCaseId;
	}
	public String getTestCaseName() {
		return testCaseName;
	}
	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}
	public String getTestCaseDesc() {
		return testCaseDesc;
	}
	public void setTestCaseDesc(String testCaseDesc) {
		this.testCaseDesc = testCaseDesc;
	}
	
	public boolean isDuplicateTestCase(TestCaseDTO testcase) {
		PostgresDBHelper client = new PostgresDBHelper();
		try {
			if (client.connect()) {
				return client.checkForDuplicateTestcase(testcase);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}
	
	
}
