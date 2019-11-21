package com.dto;

public class ScreenElementDTO {
	int id;
	int screenId;
	String prop_name;
	int elementId;
	String fieldType;
	String dataSetValue;
	String xpath;
	boolean selected;
	String xpathById;
	String xpathByName;
	int elementOrder;
	PageDetailsVo pageDetails;

	public int getElementOrder() {
		return elementOrder;
	}

	public void setElementOrder(int elementOrder) {
		this.elementOrder = elementOrder;
	}

	public int getId() {
		return id;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getElementId() {
		return elementId;
	}

	public void setElementId(int elementId) {
		this.elementId = elementId;
	}

	public PageDetailsVo getPageDetails() {
		return pageDetails;
	}

	public void setPageDetails(PageDetailsVo pageDetails) {
		this.pageDetails = pageDetails;
	}

	public String getProp_name() {
		return prop_name;
	}

	public void setProp_name(String prop_name) {
		this.prop_name = prop_name;
	}

	public int getScreenId() {
		return screenId;
	}

	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}

	public String getDataSetValue() {
		return dataSetValue;
	}

	public void setDataSetValue(String dataSetValue) {
		this.dataSetValue = dataSetValue;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
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
