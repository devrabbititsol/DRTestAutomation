package com.servlet.home;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.db.DbContract;
import com.db.PostgresHelper;
import com.dto.ClientsVo;
import com.google.gson.Gson;
import com.util.QueryConstants;

/**
 * Servlet implementation class ClientsList
 */
@WebServlet("/Projects")
public class ProjectsList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectsList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
         ArrayList<ClientsVo> clientList =  new ArrayList<>();
		PostgresHelper client = new PostgresHelper(
				DbContract.HOST, 
				DbContract.DB_NAME,
				DbContract.USERNAME,
				DbContract.PASSWORD);
		PrintWriter out = response.getWriter();
		int projectId = Integer.parseInt(request.getParameter("projectId"));
		try {
			if (client.connect()) {
				System.out.println("DB connected  id"+projectId);
				
				ResultSet rs = client.execQuerywithParams(QueryConstants.GETPROJECTS, projectId);	
			
				  ResultSetMetaData rsmd = rs.getMetaData();				 
				   int columnsNumber = rsmd.getColumnCount();
				   while (rs.next()) {
					   ClientsVo client1 = new ClientsVo();				      
				          client1.setId(rs.getInt(1));
				         // client1.setClientName(rs.getString(2));
				          client1.setClientName(rs.getString(3));
				        clientList.add(client1);
				       System.out.println("");
				   }
				 /*  for(ClientsVo cle : clientList) {
					   System.out.println(cle.getId()+"----"+cle.getClientName()+"----"+cle.getAddress());
					   out.println(cle.getId());
				   }*/
				   
				   new Gson().toJson(clientList, response.getWriter());
				    response.setContentType("application/json");
				    response.setCharacterEncoding("UTF-8");
				
				
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
