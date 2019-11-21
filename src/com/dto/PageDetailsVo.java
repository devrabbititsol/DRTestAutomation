package com.dto;

import com.db.PostgresDBHelper;

public class PageDetailsVo {
	
	int pageid;
	String pageName;
	String pageDescription;
	String propertyName;
	String inputValue;
	String actionName;
	String relativeXpathbyId;
	String relativeXpathbyName;
	String xPath;
	boolean isSelected;
	
	String tagName;
	String id;
	String text_for_idtext;
	String createdBy;
	int elementId;
	int screenId;
	int projectId;
	
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public int getScreenId() {
		return screenId;
	}
	public void setScreenId(int screenId) {
		this.screenId = screenId;
	}
	public int getElementId() {
		return elementId;
	}
	public void setElementId(int elementId) {
		this.elementId = elementId;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText_for_idtext() {
		return text_for_idtext;
	}
	public void setText_for_idtext(String text_for_idtext) {
		this.text_for_idtext = text_for_idtext;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public int getPageid() {
		return pageid;
	}
	public void setPageid(int pageid) {
		this.pageid = pageid;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getInputValue() {
		return inputValue;
	}
	public void setInputValue(String inputValue) {
		this.inputValue = inputValue;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getRelativeXpathbyId() {
		return relativeXpathbyId;
	}
	public void setRelativeXpathbyId(String relativeXpathbyId) {
		this.relativeXpathbyId = relativeXpathbyId;
	}
	public String getRelativeXpathbyName() {
		return relativeXpathbyName;
	}
	public void setRelativeXpathbyName(String relativeXpathbyName) {
		this.relativeXpathbyName = relativeXpathbyName;
	}
	public String getxPath() {
		return xPath;
	}
	public void setxPath(String xPath) {
		this.xPath = xPath;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getPageDescription() {
		return pageDescription;
	}
	public void setPageDescription(String pageDescription) {
		this.pageDescription = pageDescription;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public boolean isDuplicatePage(PageDetailsVo pageDetail) {
		PostgresDBHelper client = new PostgresDBHelper();
		try {
			if (client.connect()) {
				return client.checkForDuplicatePage(pageDetail);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return false;
	}

}
