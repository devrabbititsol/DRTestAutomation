package com.db;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.dto.DatasetVo;
import com.dto.PageDetailsVo;
import com.dto.ProjectVo;
import com.dto.ScreenDTO;
import com.dto.TestCaseDTO;
import com.util.IntArray;
import com.util.QueryConstants;

public class PostgresDBHelper {

	private Connection conn;
	private String host = DbContract.HOST;
	private String dbName = DbContract.DB_NAME;
	private String user = DbContract.USERNAME;
	private String pass = DbContract.PASSWORD;

	public PostgresDBHelper() {

	}

	public boolean connect() throws SQLException, ClassNotFoundException {
		if (host.isEmpty() || dbName.isEmpty() || user.isEmpty() || pass.isEmpty()) {
			throw new SQLException("Database credentials missing");
		}

		Class.forName("org.postgresql.Driver");
		this.conn = DriverManager.getConnection(this.host + this.dbName, this.user, this.pass);
		return true;
	}

	public void close() {
		try {
			this.conn.commit();
			this.conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// getting records
	public ResultSet execQuery(String query) throws SQLException {
		return this.conn.createStatement().executeQuery(query);
	}

	public ResultSet execQuerywithParams(String query, Object... params) throws SQLException {
		PreparedStatement statement = this.conn.prepareStatement(query);
		for (int i = 0; i < params.length; i++) {
			Object param = params[i];
			statement.setObject(i + 1, param);
		}
		return statement.executeQuery();
	}

	public int execQueryParams(String query, Object... params) throws SQLException {
		PreparedStatement statement = this.conn.prepareStatement(query);
		for (int i = 0; i < params.length; i++) {
			Object param = params[i];
			statement.setObject(i + 1, param);
		}

		return statement.executeUpdate();
	}

	public int updateQueryMultipleRows(String query, IntArray ids) throws SQLException {
		PreparedStatement statement = this.conn.prepareStatement(query);
		statement.setArray(1, ids);
		return statement.executeUpdate();
	}

	// inserting records
	public int insert(String table, Map<String, Object> values) throws SQLException {

		StringBuilder columns = new StringBuilder();
		StringBuilder vals = new StringBuilder();

		for (String col : values.keySet()) {
			columns.append(col).append(",");

			if (values.get(col) instanceof String) {
				vals.append("'").append(values.get(col)).append("', ");
			} else
				vals.append(values.get(col)).append(",");
		}

		columns.setLength(columns.length() - 1);
		vals.setLength(vals.length() - 1);

		String query = String.format("INSERT INTO %s (%s) VALUES (%s)", table, columns.toString(), vals.toString());

		return this.conn.createStatement().executeUpdate(query);
	}

	public int insertGetRelatedId(String query, Object... params) throws SQLException {

		PreparedStatement statement = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		for (int i = 0; i < params.length; i++) {
			Object param = params[i];
			statement.setObject(i + 1, param);
		}
		statement.executeUpdate();
		ResultSet rs = statement.getGeneratedKeys();
		int newId = 0;
		if (rs.next()) {
			newId = rs.getInt(1);
		}
		return newId;
	}

	public boolean checkForDuplicateProject(ProjectVo project) {
		try {
			PreparedStatement statement = this.conn.prepareStatement(
					QueryConstants.CHECK_PROJECT_EXIST + "'" + project.getUrl() + "'", ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = statement.executeQuery();
			rs.beforeFirst();
			rs.next();
			long numOfDuplicatedRows = rs.getLong(1);
			return numOfDuplicatedRows != 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkForDuplicatePage(PageDetailsVo pageDetail) {
		try {
			PreparedStatement statement = this.conn.prepareStatement(
					QueryConstants.CHECK_PAGE_EXIST + "'" + pageDetail.getPageName() + "'",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = statement.executeQuery();
			rs.beforeFirst();
			rs.next();
			long numOfDuplicatedRows = rs.getLong(1);
			return numOfDuplicatedRows != 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkForDuplicateTestcase(TestCaseDTO testcase) {
		try {
			PreparedStatement statement = this.conn.prepareStatement(
					QueryConstants.CHECK_TESTCASE_EXIST + "'" + testcase.getTestCaseName() + "'",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = statement.executeQuery();
			rs.beforeFirst();
			rs.next();
			long numOfDuplicatedRows = rs.getLong(1);
			return numOfDuplicatedRows != 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkForDuplicateDataset(DatasetVo dataset) {
		try {
			PreparedStatement statement = this.conn.prepareStatement(
					QueryConstants.CHECK_DATASET_EXIST + "'" + dataset.getDatasetName() + "'",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = statement.executeQuery();
			rs.beforeFirst();
			rs.next();
			long numOfDuplicatedRows = rs.getLong(1);
			return numOfDuplicatedRows != 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkForDuplicateScreen(ScreenDTO screenvo) {
		try {
			PreparedStatement statement = this.conn.prepareStatement(
					QueryConstants.CHECK_SCREEN_EXIST + "'" + screenvo.getScreenName() + "'",
					ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = statement.executeQuery();
			rs.beforeFirst();
			rs.next();
			long numOfDuplicatedRows = rs.getLong(1);
			return numOfDuplicatedRows != 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public int updateProjectAsDefaultProject(String projectCreate, int projectId) {
		// TODO Auto-generated method stub
		return 0;
	}
}