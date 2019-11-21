<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>. : Welcome to Test Automation Framework : .</title>
<link
	href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
<link rel="stylesheet" href="css/responsive.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.css">
<%@ page import="com.dto.PageDetailsVo"%>
<%@page import="com.dao.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="com.ui.*"%>
<%@ page import="com.dao.pageinfo.PageInfoElementsDTO"%>
<%@ page import="com.DemoLocalTest.CheckInsert"%>
<%@page import="com.util.ChromeDriverProvider"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="org.openqa.selenium.chrome.ChromeDriver"%>
<script type="text/javascript">
	function exitDriver() {
<%if (request.getParameter("exitDriver") != null) {
				ChromeDriver driver = ChromeDriverProvider.getInstance("");
				driver.quit();
				ChromeDriverProvider.doNull();
				driver = null;
			}%>
	history.back();
	}

	function saveElements() {

		var checkedValues = new Array();
		var uncheckedValues = new Array();
		var allCheckboxes = document.getElementsByName("feature[]");
		   var  projectId = <%= session.getAttribute("projectId") %>; 
		
		var pagenamest = $('#pagename').val();

		for (var i = 0; i < allCheckboxes.length; i++) {
			if (allCheckboxes[i].checked)
				checkedValues.push(allCheckboxes[i].value);
			else
				uncheckedValues.push(allCheckboxes[i].value);
		}
		var checkedValuesSt = checkedValues.join();

		// alert(checkedValues.join()+pagenamest);
		if (pagenamest.length == 0) {
			alert('Please enter Page Name');
			return false;
		} else if (checkedValuesSt.length <= 0) {
			alert('Please Select Elements');
			return false;
		}
		var data = {
			httpCall : 'createpage',
			updatedIds : checkedValuesSt,
			uncheckedIds : uncheckedValues.join(),
			pagename : pagenamest,
			projectid: projectId
		};

		$.ajax({
			type : "post",
			url : "./CreatePages", //this is my servlet //./Home
			datatype : "json",
			data : data,
			success : function(msg) {
				//	alert(JSON.stringify(msg));
				var data = msg;
				alert('Records inserted sucessfully')
				window.history.back();
			}
		});
	}

	function xpathOptionsClicked(xPathById, xPathByName, absolutexPath){
		$.confirm({
		    title: false,
		    content: '<b>Relative Xpath by Id :</b> ' + xPathById 
		    + '<br><b>Relative Xpath by Name :</b> ' + xPathByName 
		    + '<br><b>Absolute Xpath :</b> ' + absolutexPath,
		    
		    buttons: {
		        ok: {
		            keys: ['y'],
		        },
		    }
		});
	}
	
	function toggle(source) {
		var checkboxes = document.querySelectorAll('input[type="checkbox"]');
		for (var i = 0; i < checkboxes.length; i++) {
			if (checkboxes[i] != source)
				checkboxes[i].checked = source.checked;
		}
	}
	$(document)
			.ready(
					function() {

						$("input[type='submit']")
								.click(
										function(e) {
<%PrintWriter priout = response.getWriter();

			//out.println("location='pages.jsp';");

			priout.println("<form>");
			priout.println("<header>");
			priout.println("<div class='container'>");
			priout.println("<div class='logo'>");

			priout.println("<h1>TEST AUTOMATION FRAMEWORK</h1>");
			priout.println("</div>");

			priout.println("</div>");
			priout.println("</header>");

			priout.println("<div class='container'>");

			priout.println("<div class='row testcases'>");
			priout.println("<div class='col-md-12 bhoechie-tab-container'>");
			priout.println("<div class='col-md-3 bhoechie-tab-menu'>");
			priout.println(
					" <div class='list-group'> <a href='LoadDriver.jsp' class='list-group-item active text-center'> Pages </a>");
			priout.println("<a href='CreateScreens.jsp' class='list-group-item text-center'> Screens </a>");
			priout.println("<a href='teastcase.jsp' class='list-group-item  text-center'> Test Cases </a> </div>");
			priout.println("</div>");
			priout.println("<div class='col-md-9  bhoechie-tab'>");

			priout.println("<div class='bhoechie-tab-content active'>");
			priout.println("<div class='panel with-nav-tabs panel-primary'>");
			priout.println("<div class='panel-heading'>");
			priout.println("<ul class='nav nav-tabs'>");
			priout.println("<li class='active'><a href='#tab1primary' >Create Pages</a></li>");
			priout.println("<li><a href='#'>Update Pages</a></li>");
			priout.println("<li><a href='#'>Delete Pages</a></li>");
			priout.println("</ul>");
			priout.println("</div>");
			priout.println("<div class='panel-body'>");
			priout.println("<div class='tab-content'>");
			priout.println("<div class='tab-pane fade in active' id='tab1primary'>");
			priout.println(" <center>");
			priout.println("<div class='loadingpage'>");
			priout.println(
					"<input id='loadingdemo' class='btn btn-primary'  value = 'Get Elements' name='getElements' type='submit'/>");

			priout.println(
					"<input type='submit' id='done' value='Done' class='btn btn-primary' name='exitDriver' onclick= 'exitDriver();'/>");
			priout.println("</form>");

			if (request.getParameter("getElements") != null) { %>
	$('#done').hide();
<%AttributeValues attribute = new AttributeValues();
				ArrayList<PageDetailsVo> pagedetailsList = new ArrayList<PageDetailsVo>();
				pagedetailsList = attribute.getElements();
				String table = "<table class='table' border='1'>" + "<thead>" + "<tr>" +

						"<th><input type='checkbox' class='custom-control-input' class='checkAll' onclick='toggle(this)' name='checkAll'/> All</th>"
						+ "<th>Property Name</th>" + "<th>Input value</th>" + "<th>Action Name</th>" +

						"</tr><tbody" + "</thead><tbody>";
				ChromeDriverProvider.doSaveArrayLst(pagedetailsList);
				for (int i = 0; i < pagedetailsList.size(); i++) {
					String relXPathById = pagedetailsList.get(i).getRelativeXpathbyId();
					String relXPathByName = pagedetailsList.get(i).getRelativeXpathbyName();
					String xPath = pagedetailsList.get(i).getxPath();
					table += "<tr>" + " 	<td><input type='checkbox' class='custom-control-input' id=" + i + " value="
							+ i + " name='feature[]'></td>" + "  <td>" + pagedetailsList.get(i).getPropertyName()
							+ "</td>" + "  <td>" + pagedetailsList.get(i).getInputValue() + "</td>" + 
							"  <td><a data-toggle='modal'> <i class='fa fa-edit' onClick=xpathOptionsClicked(\"" + relXPathById + "\",\"" + relXPathByName + "\",\"" + xPath + "\")></i> </a></td> </tr>";
				}
				table += "</tbody></table>";

				priout.println("<div id='loadingpagename'>");
				priout.println("<div class='row'></br>");
				priout.println("<label >Page Name :</label>");

				priout.println("<input type='text' id='pagename' placeholder='Title Name' />");
				priout.println("</div>");
				priout.println("</div></br>");
				priout.println(
						"<button type='button'class='btn btn-success' onclick= 'saveElements()'>Save Elements</button>");
				//priout.println("<button type='button' class='btn btn-danger' onclick='history.back()' >Cancel</button>");

				priout.println("</center>");
				priout.println("</div>");
				priout.println("<div class='tab-pane fade' id='tab2primary'> Primary 2</div>");
				priout.println("<div class='tab-pane fade' id='tab3primary'>Primary 3</div>");
				priout.println("</div>");
				priout.println("</div>");
				priout.println("</div>");
				priout.println("</div>");

				priout.println("<div class='pagedetails_main table-responsive'>" + table + "</div>");

				priout.println("</div>");
				priout.println("</div>");
				priout.println("</div>");
				priout.println("</div>");

			}%>
	});
					});
</script>
</head>
<body>
<!------ model box -------->
	    	<div class="modal fade" id="myModal" role="dialog">
	    		<div class="modal-dialog">
	    			<div class="modal-content">

	    				<div class="modal-body" contenteditable="true">
	    					<table class="table table-striped table-bordered" id="example">
	    						<thead>
	    							<tr>
	    								<th>S.No.</th>
	    								<th>Property Name</th>
	    								<th>Input Value</th>
	    								<th>Absolute Xpath</th>
	    								<th>Relative Xpath by Id</th>
	    								<th>Relative Xpath by Name</th>
	    							</tr>
	    						</thead>
	    						<tbody>
	    							<tr>
	    								<th>1</th>
	    								<td>Name</td>
	    								<td>Value</td>
	    								<td>path</td>
	    								<td>path Id</td>
	    								<td>path Name</td>
	    							</tr>
	    							<tr>
	    								<th>2</th>
	    								<td>Name</td>
	    								<td>value</td>
	    								<td>path</td>
	    								<td>path Id</td>
	    								<td>path Name</td>
	    							</tr>
	    							<tr>
	    								<th>3</th>
	    								<td>Name</td>
	    								<td>value</td>
	    								<td>path</td>
	    								<td>path Id</td>
	    								<td>path Name</td>
	    							</tr>
	    							<tr>
	    								<th>4</th>
	    								<td>Name</td>
	    								<td>value</td>
	    								<td>path</td>
	    								<td>path Id</td>
	    								<td>path Name</td>
	    							</tr>

	    							<tr>
	    								<th>5</th>
	    								<td>Name</td>
	    								<td>value</td>
	    								<td>path</td>
	    								<td>path Id</td>
	    								<td>path Name</td>
	    							</tr>

	    							<tr>
	    								<th>6</th>
	    								<td>Name</td>
	    								<td>value</td>
	    								<td>path</td>
	    								<td>path Id</td>
	    								<td>path Name</td>
	    							</tr>

	    							<tr>
	    								<th>7</th>
	    								<td>Name</td>
	    								<td>value</td>
	    								<td>path</td>
	    								<td>path Id</td>
	    								<td>path Name</td>
	    							</tr>

	    							<tr>
	    								<th>8</th>
	    								<td>Name</td>
	    								<td>value</td>
	    								<td>path</td>
	    								<td>path Id</td>
	    								<td>path Name</td>
	    							</tr>


	    						</tbody>
	    					</table>
	    				</div>
	    				<!-- <div class="modal-footer">
	                                  <button type="button" class="btn btn-success">Update</button>
	                                  <button type="button" class="btn btn2 btn-danger">Cancel</button>
	    								 <button type="button" class="btn" data-dismiss="modal" aria-label=""> Close</button>
	                                </div> -->
	    			</div>
	    		</div>
	    	</div>
	<footer>
		<div class="container">
			<p>© Copyright 2017 DevRabbit IT Solutions, Inc. All Rights
				Reserved.</p>
		</div>
	</footer>
	<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>

</body>
</html>