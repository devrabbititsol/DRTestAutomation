package com.dto;

public class TestCaseOutputVo {
	int id;
	int testcaseId;
	String testcaseName;
	int screenId;
	String screenName;
	int pageElementsId;
	String propName;
	String propInputValue;
	String datasetValue;
	String xpath;
	String xpathById;
	String xpathByName;
	boolean isSelected;

	public int getTestcaseId() {
		return testcaseId;
	}

	public void setTestcaseId(int testcaseId) {
		this.testcaseId = testcaseId;
	}

	public String getTestcaseName() {
		return testcaseName;
	}

	public void setTestcaseName(String testcaseName) {
		this.testcaseName = testcaseName;
	}

	public int getScreenId() {
		return screenId;
	}

	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public int getPageElementsId() {
		return pageElementsId;
	}

	public void setPageElementsId(int pageElementsId) {
		this.pageElementsId = pageElementsId;
	}

	public String getPropName() {
		return propName;
	}

	public void setPropName(String propName) {
		this.propName = propName;
	}

	public String getPropInputValue() {
		return propInputValue;
	}

	public void setPropInputValue(String propInputValue) {
		this.propInputValue = propInputValue;
	}

	public String getDatasetValue() {
		return datasetValue;
	}

	public void setDatasetValue(String datasetValue) {
		this.datasetValue = datasetValue;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public String getXpathById() {
		return xpathById;
	}

	public void setXpathById(String xpathById) {
		this.xpathById = xpathById;
	}

	public String getXpathByName() {
		return xpathByName;
	}

	public void setXpathByName(String xpathByName) {
		this.xpathByName = xpathByName;
	}

}
