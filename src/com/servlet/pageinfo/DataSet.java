package com.servlet.pageinfo;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.FileWrite.GenerateSeleniumScript;
import com.dao.pageinfo.DataSetDAO;
import com.dto.DataSetValuesVo;
import com.dto.DatasetVo;
import com.dto.PageDetailsVo;
import com.dto.TestCaseElementsVo;
import com.google.gson.Gson;

/**
 * Servlet implementation class DataSet
 */
@WebServlet("/DataSet")
public class DataSet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String httpCall = request.getParameter("httpCall");
		String status = "";
		if (httpCall.equalsIgnoreCase("create")) {
			String datasetName = request.getParameter("datasetName");
			String testCaseId = request.getParameter("testcaseId");
			String elementInputs = request.getParameter("elementInputs");
			DataSetValuesVo[] datasetelements = jsonParse(elementInputs);

			DatasetVo dataset = new DatasetVo();
			dataset.setTestCaseId(Integer.parseInt(testCaseId));
			dataset.setDatasetName(datasetName);

			int datasetID = DataSetDAO.CreateDataSet(dataset);
			if (datasetID == -1) {
				status = "Dataset already exists.";
			} else if (datasetID == 0) {
				status = "Screen not created Please contact admin.";
			} else {
				status = DataSetDAO.insertDataSetElementValues(datasetelements, datasetID);
			}

			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(status);
		} else if (httpCall.equalsIgnoreCase("getdatasets")) {
			String testCaseId = request.getParameter("testcaseId");
			ArrayList<DatasetVo> lastdataset = new ArrayList<>();
			int testcaseId = Integer.parseInt(testCaseId);
			if (testcaseId != 0) {
				lastdataset = DataSetDAO.getDataSetByTestCase(testcaseId);
			}
			new Gson().toJson(lastdataset, response.getWriter());
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

		} else if (httpCall.equalsIgnoreCase("getLastRecord")) {
			String testCaseId = request.getParameter("testcaseId");
			ArrayList<PageDetailsVo> lastdataset = new ArrayList<>();
			int testcaseId = Integer.parseInt(testCaseId);
			if (testcaseId != 0) {
				lastdataset = DataSetDAO.getLastRecordDataSet(testcaseId);
			}
			new Gson().toJson(lastdataset, response.getWriter());
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

		} else if (httpCall.equalsIgnoreCase("executedataset")) {
			String testCaseId = request.getParameter("testcaseId");
			String url = request.getParameter("url");
			TestCaseElementsVo datasetResult = new TestCaseElementsVo();

			int testcaseId = Integer.parseInt(testCaseId);
			if (testcaseId != 0) {
				datasetResult = DataSetDAO.getDatasetScreensNames(testcaseId);
				datasetResult.setUrl(url);
			}
			new Gson().toJson(datasetResult, response.getWriter());

			Gson gson = new Gson();
			String json = gson.toJson(datasetResult);
			// System.out.println(json);
			try {
				responseParse(datasetResult);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

		} else if (httpCall.equalsIgnoreCase("executedatasetBYDataSet")) {
			String testCaseId = request.getParameter("testcaseId");
			String dataSetId = request.getParameter("datasetId");
			String url = request.getParameter("url");
			TestCaseElementsVo datasetResult = new TestCaseElementsVo();
			int testcaseId = Integer.parseInt(testCaseId);
			int datasetId = Integer.parseInt(dataSetId);
			if (testcaseId != 0 && datasetId != 0) {
				datasetResult = DataSetDAO.getDatasetByDatasetId(testcaseId, datasetId);
				datasetResult.setUrl(url);
			}
			new Gson().toJson(datasetResult, response.getWriter());

			Gson gson = new Gson();
			String json = gson.toJson(datasetResult);
			// System.out.println(json);
			try {
				responseParse(datasetResult);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

		}

	}

	private void responseParse(TestCaseElementsVo output) throws Exception {

		GenerateSeleniumScript filewriter = new GenerateSeleniumScript();
		try {
			filewriter.generateCode(output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private DataSetValuesVo[] jsonParse(String output) {
		// [{"element_id":"gjfhj","value":"4500","screenId":"32"},{"element_id":"fdfd","value":"4499","screenId":"32"},{"element_id":"fdsd","value":"4498","screenId":"32"},{"element_id":"sdfd","value":"4497","screenId":"32"},{"element_id":"fdsfg","value":"4496","screenId":"32"},{"element_id":"fssdf","value":"4495","screenId":"32"},{"element_id":"fsss","value":"4494","screenId":"32"},{"element_id":"fsdfdff","value":"4493","screenId":"32"},{"element_id":"fsds","value":"4500","screenId":"33"},{"element_id":"sfs","value":"4499","screenId":"33"},{"element_id":"ssf","value":"4498","screenId":"33"},{"element_id":"ghjfjf","value":"4497","screenId":"33"},{"element_id":"","value":"4496","screenId":"33"},{"element_id":"gjg","value":"4569","screenId":"35"},{"element_id":"fdj","value":"4568","screenId":"35"}]

		Gson gson = new Gson();
		DataSetValuesVo[] datasetValues = gson.fromJson(output, DataSetValuesVo[].class);
		// ArrayList<DataSetValuesVo> lsit = (ArrayList<DataSetValuesVo>)
		// Arrays.asList(datasetValues);
		return datasetValues;

	}

}
