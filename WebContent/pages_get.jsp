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
<head>
<meta charset="UTF-8">
<title>. : Welcome to Test Automation Framework : .</title>
<link
	href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="css/custom.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/responsive.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/animate.css">
<link rel="stylesheet" href="css/themify-icons.css">
<%@ page import="java.io.PrintWriter"%>
<%@ page import="com.dto.PageDetailsVo"%>
<%@ page import="com.dao.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.ui.*"%>
<%@ page import="com.dao.pageinfo.PageInfoElementsDTO"%>
<%@ page import="com.DemoLocalTest.CheckInsert"%>
<%@page import="com.util.ChromeDriverProvider"%>
<%@page import="org.openqa.selenium.chrome.ChromeDriver"%>

<script type="text/javascript">

function exitDriver()
{
	<%-- <%
	if (request.getParameter("exitDriver") != null) {
	ChromeDriver driver = ChromeDriverProvider.getInstance("");
    driver.quit();
    ChromeDriverProvider.doNull();
    driver = null;
	}
	%> --%>
	
	history.back();
}

function saveElements(){
	
		var checkedValues = new Array();
		var uncheckedValues = new Array();
		var allCheckboxes = document.getElementsByName("feature[]");
		   var  projectId = <%=session.getAttribute("projectId")%>; 
		
		var pagenamest =  $('#pagename').val();

		for (var i = 0; i < allCheckboxes.length; i++) {
			if (allCheckboxes[i].checked)
				checkedValues.push(allCheckboxes[i].value);
			else
				uncheckedValues.push(allCheckboxes[i].value);
		}
		var checkedValuesSt = checkedValues.join();
		
	   // alert(checkedValues.join()+pagenamest);
	    if(pagenamest.length == 0 ) {
		       alert('Please enter Page Name');
		       return false;
		    } else if(checkedValuesSt.length <= 0){
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
				alert(msg);
				window.history.back();
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

$(document).ready(function() {
	  var  projectId = <%=session.getAttribute("projectId")%>; 
	  if(projectId == -1 || projectId == 0){
		  alert("Url already exists.")
			history.back();  
	  }
	 $("input[type='submit']").click(function(e){
		
		 <%PrintWriter priout = response.getWriter();
			priout.println("<FORM>");
			priout.println("<div class='wrapper'>");
			priout.println("<div class='sidebar' data-background-color='white' data-active-color='danger'>");
			priout.println("<div class='sidebar-wrapper'>");
			priout.println("<div class='logo'>");
	
			priout.println(" <a  class='simple-text'>TAF LOGO</a>");
			priout.println(" </div>");
			priout.println("<ul class='nav'>");
			priout.println("<li  class='active'>");
			priout.println("<a href='pages_url.jsp'>");
			priout.println("<i class='ti-home'></i>");
			priout.println("<p>Home</p>");
			priout.println("</a>");
			priout.println("</li>");
			/* priout.println("<li>");
			priout.println("<a href='pages.jsp'>");
			priout.println("<i class='ti-layout-tab'></i>");
			priout.println("<p>Pages</p>");
			priout.println("</a>");
			priout.println("</li>"); */
			priout.println("<li>");
			priout.println("<a href='CreateScreens.jsp'>");
			priout.println("<i class='ti-layers-alt'></i>");
			priout.println("<p>Screens</p>");
			priout.println("</a>");
			priout.println("</li>");
			priout.println("<li>");
			priout.println("<a href='teastcase.jsp'>");
			priout.println("<i class='ti-ruler-pencil'></i>");
			priout.println("<p>Test Cases</p>");
			priout.println("</a>");
			priout.println("</li>");
			priout.println("<li>");
			priout.println("<a href='#'>");
			priout.println("<i class='ti-layers'></i>");
			priout.println("<p>Test Sets</p>");
			priout.println("</a>");
			priout.println("</li>");
			priout.println("<li>");
			priout.println("<a href='#'>");
			priout.println("<i class='ti-upload'></i>");
			priout.println("<p>Execute</p>");
			priout.println("</a>");
			priout.println("</li>");
			priout.println("</ul>");
			priout.println("</div>");
			priout.println("</div>");
			priout.println("<div class='main-panel'>");
			priout.println("<nav class='navbar navbar-default'>");
			priout.println("<div class='container-fluid'>");
			priout.println("<div class='navbar-header'>");
			priout.println("<button type='button' class='navbar-toggle'>");
			priout.println("<span class='sr-only'>Toggle navigation</span>");
			priout.println("<span class='icon-bar bar1'></span>");
			priout.println(" <span class='icon-bar bar2'></span>");
			priout.println(" <span class='icon-bar bar3'></span>");
			priout.println("</button>");
			priout.println("<h1>TEST AUTOMATION FRAMEWORK</h1>");
			priout.println("</div><div class='collapse navbar-collapse'>");
			priout.println("<ul class='nav navbar-nav navbar-right'>");
			priout.println("<li>");
			priout.println("<a href='#' class='dropdown-toggle' data-toggle='dropdown'>");
			priout.println("<i class='ti-user'></i>");
			priout.println("<p>Name</p>");
			priout.println(" </a>");
			priout.println(" </li>");
			priout.println("<li> <a href='index.html'>");
			priout.println("<i class='fa fa-sign-out' aria-hidden='true'></i>");
			priout.println("<p>LogOut</p>");
			priout.println("</a>");
			priout.println("</li>");
			priout.println("</ul>");
			priout.println("</div>");
			priout.println("</div>");
			priout.println("</nav>");
			priout.println("<div class='content'>");
			priout.println("<div class='container-fluid'>");
			priout.println("<div class='bhoechie-tab-content active'>");
			priout.println("<div class='panel with-nav-tabs panel-primary'>");
			priout.println("<div class='panel-heading'>");
			priout.println("<ul class='nav nav-tabs'>");
			priout.println("<li class='active'><a href='pages.html' >Create Pages</a></li>");
			priout.println("<li><a href='pages_update.html'>Update Pages</a></li>");
			priout.println("<li><a href='pages_delete.html'>Delete Pages</a></li>");
			priout.println("</ul>");
			priout.println("</div>");
			priout.println("<div class='panel-body'>");
			priout.println("<div class='card'>");
			priout.println("<div class='loadingpage_url'>");
			if (request.getParameter("getElements") != null) {
				priout.println("<center>");
			}
			priout.println("<div class='loadingpage'>");
			priout.println(
					"<input id='loadingdemo' class='btn btn-success'  value = 'Get Elements' name='getElements' type='submit'/>");
			priout.println(
					"<input type='submit' id='done' value='Done' class='btn btn-danger'  name='exitDriver' onclick= 'exitDriver();'/>");

			if (request.getParameter("getElements") != null) {

				AttributeValues attribute = new AttributeValues();
				ArrayList<PageDetailsVo> pagedetailsList = new ArrayList<PageDetailsVo>();
				pagedetailsList = attribute.getElements();

				String table = "<table class='table' border='1'>" + "<thead>" + "<tr>" 
				+ "<th><input type='checkbox' class='custom-control-input' class='checkAll' onclick='toggle(this)' name='checkAll'/> All</th>"
						+ "<th>Property Name</th>" + "<th>Input value</th>" + "<th>Action Name</th><th>Action Name</th>"
						+ "</tr><tbody" + "</thead><tbody>";
				ChromeDriverProvider.doSaveArrayLst(pagedetailsList);
				for (int i = 0; i < pagedetailsList.size(); i++) {
					table += "<tr>" + " 	<td><input type='checkbox' class='custom-control-input' id=" + i + " value="
							+ i + " name='feature[]'></td>" + "  <td>" + pagedetailsList.get(i).getPropertyName()
							+ "</td>" + "  <td>" + pagedetailsList.get(i).getInputValue() + "</td>" + "  <td>"
							+ pagedetailsList.get(i).getRelativeXpathbyId() + "</td>" + " </tr>";
				}
				table += "</tbody></table>";

				priout.println("<div id='loadingpagename'>");
				priout.println("<div class='row'>");
				priout.println("<label>Page Name :</label> <input type='text' id='pagename' placeholder='Title Name'/>");
				priout.println("</div>");
				priout.println("</div></br>");
				priout.println("<button type='button'class='btn btn-success' onclick= 'saveElements()'>Save Elements</button>");
				priout.println("</center>");
				priout.println("</div>");
				priout.println("<div class='tab-pane fade' id='tab2primary'> Primary 2</div>");
				priout.println("<div class='tab-pane fade' id='tab3primary'>Primary 3</div>");

				priout.println("<div class='pagedetails_main table-responsive'>" + table + "</div>");
			}
			priout.println("</div>");
			priout.println("</div>");
			priout.println("</div>");
			priout.println("</div>");
			priout.println(" </div>");
			priout.println("</div>");
			priout.println(" </div>");
			priout.println("</div>");
			priout.println(" </div>");
			priout.println("</div>");
			priout.println(" </div>");
			priout.println("</div>");
			priout.println("</div>");
			priout.println("</div>");
			priout.println("</FORM>");%>
	}); 
});

</script>
</head>
<body>
	<%
		priout.println("<footer>");
		priout.println("<div class='container'>");
		priout.println("<p>© Copyright 2017 DevRabbit IT Solutions, Inc. All Rights Reserved.</p>");
		priout.println("</div>");
		priout.println("</footer>");
	%>
	<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
	<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
	<script src="js/main_pages.js"></script>

	<!--- check box----------->
	<script>
	  
$(document).ready(function () {
  $('.checkAll').on('click', function () {
    $(this).closest('table').find('tbody :checkbox').prop('checked', this.checked).closest('tr').toggleClass('selected', this.checked);
  });

  $('tbody :checkbox').on('click', function () {
    $(this).closest('tr').toggleClass('selected', this.checked); //Classe de seleção na row  
    $(this).closest('table').find('.checkAll').prop('checked', ($(this).closest('table').find('tbody :checkbox:checked').length == $(this).closest('table').find('tbody :checkbox').length)); //Tira / coloca a seleção no .checkAll
  });
});  
	  </script>
	<script>

$(document).ready(function () {
  $('.checkAll').on('click', function () {
    $(this).closest('.testcasescheckbox').find('ul li :checkbox').prop('checked', this.checked).closest('tr').toggleClass('selected', this.checked);
  });

  $('ul li :checkbox').on('click', function () {
    $(this).closest('tr').toggleClass('selected', this.checked); //Classe de seleção na row
    $(this).closest('.testcasescheckbox').find('.checkAll').prop('checked', ($(this).closest('.testcasescheckbox').find('ul li :checkbox:checked').length == $(this).closest('.testcasescheckbox').find('ul li :checkbox').length)); //Tira / coloca a seleção no .checkAll
  });
});  
	  </script>
</body>
</html>
