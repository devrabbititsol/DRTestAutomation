package com.dto;

import com.db.PostgresDBHelper;

public class DatasetVo {
	int datasetId;
	String datasetName;
	int testCaseId;

	public int getDatasetId() {
		return datasetId;
	}

	public void setDatasetId(int datasetId) {
		this.datasetId = datasetId;
	}

	public String getDatasetName() {
		return datasetName;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}

	public int getTestCaseId() {
		return testCaseId;
	}

	public void setTestCaseId(int testCaseId) {
		this.testCaseId = testCaseId;
	}

	public boolean isDuplicateDataset(DatasetVo dataset) {
		PostgresDBHelper client = new PostgresDBHelper();
		try {
			if (client.connect()) {
				return client.checkForDuplicateDataset(dataset);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}

}
