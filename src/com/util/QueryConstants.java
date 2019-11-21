package com.util;

public interface QueryConstants {

	public static final String GETCLIENTS = "select * from tb_client";
	public static final String GETPROJECTS = "select * from tb_projects WHERE cient_id_f = ?";
	public static final String GETMODULES = "select * from tb_modules WHERE project_id = ?";
	public static final String GETTESTCASES = "select * from tb_testcases WHERE module_id = ?";
	public static final String GETPAGES = "select * from tb_pagenames WHERE testcase_id = ?";
	public static final String GETPAGEDETAIL = "select * from tb_page_details WHERE pagenames_id = ?";

	public static final String PAGEINFO_GETPAGES = "select * from tb_pagenames where project_url_id = (select project_url_id from tb_projects where default_url = true limit 1)"; // pageinfotable;
	public static final String PAGEINFO_GETPAGE_ELEMENTS = "select * from tb_page_elements WHERE pagenames_id = ? LIMIT 30";
	public static final String LOADMORE_PAGEINFO_GETPAGE_ELEMENTS = "select * from tb_page_elements WHERE pagenames_id = ? LIMIT 30 OFFSET ?";
	public static final String PAGEINFO_SETCHECKED_ELEMENTS = "UPDATE tb_page_elements SET is_selected = TRUE WHERE page_elements_id  = ANY (?::int[])";
	public static final String PAGEINFO_SETUNCHECKED_ELEMENTS = "UPDATE tb_page_elements SET is_selected = FALSE WHERE page_elements_id  = ANY (?::int[])";

	public static final String PAGEINFO_INSERTPAGE = "INSERT INTO tb_pagenames (pagenames,pagedescription,project_url_id) VALUES (?,?,?)";
	public static final String ELEMENTS_INSERT = "INSERT INTO tb_page_elements (pagenames_id,prop_name,prop_input_value,prop_action,relativexpathbyid,relativeXpathByName,xPath,tag_name,id,text_for_id,created_by) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	public static final String ELEMENTS_INSERT_WITH_SELECTION = "INSERT INTO tb_page_elements (pagenames_id,prop_name,prop_input_value,prop_action,relativexpathbyid,relativeXpathByName,xPath,tag_name,id,text_for_id,created_by,is_selected) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

	public static final String SELECTED_ELEMENTS = "select * from tb_page_elements WHERE pagenames_id = ? AND is_selected = TRUE LIMIT 30 OFFSET ?";
	public static final String CREATE_SCREEN = "INSERT INTO tb_screens (screenName,screen_Description,pagenames_id) VALUES (?,?,?)";
	public static final String SCREEN_ELEMENTS_INSERT = "INSERT INTO tb_screens_elements (screen_id,page_elements_id) VALUES (?,?)";
	// set element order
	public static final String SCREEN_ELEMENTS_ORDER = "UPDATE  tb_screens_elements  SET ele_order = ?  WHERE page_elements_id  = ? AND screen_id = ?";

	public static final String DELETE_SCREEN_ELEMENTS_BY_SCREENID = "DELETE FROM tb_screens_elements WHERE screen_id = ?";
	public static final String GET_SCREENS = "select * from tb_screens WHERE pagenames_id = ?"; // pageinfotable;
	public static final String GET_SCREENS_ELEMENTS = "select screen_ele.screen_id,screen_ele.page_elements_id,pg_elements.*	from tb_screens_elements screen_ele, tb_page_elements pg_elements where screen_ele.page_elements_id = pg_elements.page_elements_id and screen_ele.screen_id = ? ORDER BY screen_ele.ele_order asc LIMIT 100 OFFSET ? ";

	public static final String CREATE_TESTCASE_SCREENS = "INSERT INTO tb_testcase_screens (screen_id,testcase_id)"
			+ " VALUES (?,?)";

	// set testcase screens order
	public static final String TESTCASE_SCREENS_ORDER = "UPDATE  tb_testcase_screens  SET screen_order = ?  WHERE testcase_id  = ? AND screen_id = ?";

	public static final String TESTCASE_SCREENS_ELEMENTS = "select testcases.testcase_id,testcases.testcase_name, testcasescreens.screen_id,screenname.screenname, "
			+ " pg_elements.page_elements_id, "
			+ " pg_elements.prop_name,pg_elements.prop_input_value, pg_elements.xpath, pg_elements.relativexpathbyid, pg_elements.relativexpathbyname "
			+ " from " + " tb_testcasenames testcases , " + " tb_testcase_screens testcasescreens, "
			+ " tb_screens screenname, " + " tb_screens_elements screen_ele, " + " tb_page_elements pg_elements"
			+ " where " + " testcases.testcase_id = testcasescreens.testcase_id AND "
			+ " testcasescreens.screen_id  = screenname.screen_id AND "
			+ " testcasescreens.screen_id = screen_ele.screen_id AND "
			+ " screen_ele.page_elements_id = pg_elements.page_elements_id AND "
			+ " testcases.testcase_id = ? ORDER BY testcasescreens.screen_order,screen_ele.ele_order asc";

	// Dataset

	public static final String CREATE_DATASET = "INSERT INTO tb_dataset (testcase_id,datasetname) VALUES (?,?)";
	public static final String CREATE_DATASET_ELEMENTS = "INSERT INTO tb_datasetvalues (dataset_id, screenid, element_id, input_value,isvalidate) VALUES (?,?,?,?,?)";

	// last record data set
	public static final String LAST_DATASET_RECORD = "select element_id, screenid, input_value, isvalidate from tb_datasetvalues where dataset_id = (select dataset_id from tb_dataset WHERE testcase_id = ? ORDER BY dataset_id DESC limit 1)";
	// dataset Id by datasetid
	public static final String DATASET_RECORD = "select element_id, screenid, input_value, isvalidate from tb_datasetvalues where dataset_id = ?";

	// project
	public static final String PROJECT_CREATE = "INSERT INTO tb_projects (project_url) VALUES (?)";
	// get data sets by TestcaseId
	public static final String DATASET_BY_TESTCASE = "select dataset_id, datasetname from tb_dataset WHERE testcase_id = ? ORDER BY dataset_id DESC ";
	// defalut url address
	public static final String DEFAULT_URL = "select * from tb_projects WHERE default_url=TRUE"; // pageinfotable;
	//all urls
	public static final String URLS = "select * from tb_projects"; // pageinfotable;
	
	public static final String CHECK_PROJECT_EXIST = "select COUNT(*) from tb_projects where project_url LIKE ";
	public static final String CHECK_PAGE_EXIST = "select COUNT(*) from tb_pagenames where pagenames LIKE ";
	public static final String CHECK_TESTCASE_EXIST = "select COUNT(*) from tb_testcasenames where testcase_name LIKE ";
	public static final String CHECK_DATASET_EXIST = "select COUNT(*) from tb_dataset where datasetname LIKE ";
	public static final String CHECK_SCREEN_EXIST = "select COUNT(*) from tb_screens where screenname LIKE ";
	public static final String TESTCASE_NAMES = "SELECT * FROM tb_testcasenames  where project_url_id = (select project_url_id from tb_projects where default_url = true limit 1)";
	public static final String CREATE_TESTCASE = "INSERT INTO tb_testcasenames (testcase_name,project_url_id) VALUES (?,(select project_url_id from tb_projects where default_url = true limit 1))";
	public static final String UPDATE_PROJECT_AS_DEFAULT = "UPDATE tb_projects SET default_url = CASE  WHEN project_url_id = ? THEN TRUE  WHEN default_url = TRUE THEN FALSE   ELSE default_url END";

}
