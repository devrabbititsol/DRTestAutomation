<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.dto.PageDetailsVo"%>
<%@page import="com.dao.*"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="com.ui.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
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
<script
	src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>

<link
	href="https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script src="https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<script
	src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						getDefaultUrl();
						$("input[type='submit']")
								.click(
										function(e) {

											$("input[type='submit']")
													.click(
															function(e) {
<%if (request.getParameter("submit") != null) {
				String url = request.getParameter("url");
				String urlid = request.getParameter("hiddenField");
				int urlId = Integer.parseInt(urlid);
				System.out.println("debugs" + urlId + url);
				AttributeValues attribute = new AttributeValues();
				ArrayList<PageDetailsVo> pagedetailsList = new ArrayList<PageDetailsVo>();

				if (urlId == 0) {
					int projectId = attribute.inIt(url, false);
					session.setAttribute("projectId", projectId);
				} else {
					int projectId = attribute.openDriver(url, false);
					session.setAttribute("projectId", urlid);
				}
			}%>
	});
											});

					});

	function getDefaultUrl() {
<%if (request.getParameter("submit") == null) {%>
	var defaultUrl = 0;
		request = {
			httpCall : "defaultURL"
		};
		$.ajax({
			type : "post",
			url : "./ProjectUrl", //this is my servlet //./Home
			data : request,
			success : function(res) {
				//alert(JSON.stringify(res));
				setRespose(res);

			}
		});
<%}%>
	}
	
	function setRespose(res){
	if(res.projectId != 0){
		$('#url').val(res.Url);
		$("#url").attr("readonly", "readonly");
		$('#hiddenField').val(res.projectId);	
	}		
		}


	function resetValue() {

		$('#hiddenField').val(0);
		$("#url").removeAttr("readonly");

	}

	function callFunction() {
<%if (request.getParameter("submit") != null) {%>
	window.location = "pages_get.jsp";
<%}%>
	}

	function showExistingProjects() {
		request = {
			httpCall : "get"
		};
		$.ajax({
			type : 'POST',
			url : "./ProjectUrl", //this is my servlet //./Home
			data : request,
			success : function(msg) {
				//alert(JSON.stringify(msg));
				loadUrlDialog(msg);

			}
		});

	}

	function loadUrlDialog(data) {
		var urlsList = "";
		for (var i = 0; i < data.length; i++) {
			urlsList += "<div><input type='radio' name='urls' value="+data[i].projectId+" />"
					+ data[i].Url + "</div>";

		}
		if (data.length > 0) {
			$('#confirm').modal('show');
			$('#allURls').empty().append(urlsList);
		} else {
			alert("Sorry, No project is created.")
		}

	}

	function UrlSubmit() {
		var selectedurl = $("#allURls input[type='radio']:checked").val();
		request = {
			httpCall : "updateUrl",
			selectedUrl : selectedurl
		};
		$.ajax({
			type : 'POST',
			url : "./ProjectUrl", //this is my servlet //./Home
			data : request,
			success : function(msg) {
				alert(JSON.stringify(msg));
				setRespose(msg);

			}
		});
	}
</script>

</head>

<body onload="callFunction();">

	<div class="wrapper">
		<div class="sidebar" data-background-color="white"
			data-active-color="danger">

			<div class="sidebar-wrapper">
				<div class="logo">
					<a href="#" class="simple-text"> TAF LOGO </a>
				</div>

				<jsp:include page="sidebar.jsp" />
			</div>
		</div>
		<div class="main-panel">
			<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar bar1"></span> <span class="icon-bar bar2"></span>
						<span class="icon-bar bar3"></span>
					</button>
					<h1>TEST AUTOMATION FRAMEWORK</h1>
				</div>
				<div class="collapse navbar-collapse">
					<ul class="nav navbar-nav navbar-right">
						<li><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"> <i class="ti-user"></i>
								<p>Name</p>
						</a></li>

						<li><a href="index.html"> <i class="fa fa-sign-out"
								aria-hidden="true"></i>
								<p>LogOut</p>
						</a></li>
					</ul>

				</div>
			</div>
			</nav>

			<div class="content">
				<div class="container-fluid">



					<div class="bhoechie-tab-content active">
						<div class="panel with-nav-tabs panel-primary">
							<div class="panel-heading">
								<ul class="nav nav-tabs">
									<li class="active"><a href="pages.html">Create Pages</a></li>
									<li><a href="pages_update.html">Update Pages</a></li>
									<li><a href="pages_delete.html">Delete Pages</a></li>
								</ul>
							</div>
							<div class="panel-body">
								<div class="card">
									<div>
										<span>Pick from existing urls
											<button onclick="showExistingProjects()">Click</button>
										</span>
									</div>
									<div class="loadingpage_url">
										<center>
											<div class="loadingpage">
												<form id="form1">
													<input type="hidden" id="hiddenField" name="hiddenField"
														value=0 /> <input type="text" id="url" name="url"
														placeholder="Please Enter URL" /> <span
														class="form-control-clear glyphicon glyphicon-remove form-control-feedback hidden"></span>
													<input type="reset" onclick="resetValue()"
														class="btn btn-success" value="Clear" /> <input
														type="submit" class="btn btn-success" value="Start"
														name="submit" />
												</form>
											</div>
									</div>
									</center>
								</div>

							</div>
						</div>
					</div>


				</div>
			</div>


		</div>

	</div>


	<div class="modal fade" id="confirm" role="dialog">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="sortable">Select</h4>
				</div>
				<div class="modal-body">
					<div class="pagedetails_main table-responsive table--class"
						id="allURls"></div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-default" data-dismiss="modal"
						onclick="UrlSubmit()">Proceed</button>
				</div>
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
	<script
		src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>

	<script src="js/main_pages.js"></script>

	<!--- check box----------->
	<script>
		$(document)
				.ready(
						function() {
							$('.checkAll').on(
									'click',
									function() {
										$(this).closest('table').find(
												'tbody :checkbox').prop(
												'checked', this.checked)
												.closest('tr').toggleClass(
														'selected',
														this.checked);
									});

							$('tbody :checkbox')
									.on(
											'click',
											function() {
												$(this).closest('tr')
														.toggleClass(
																'selected',
																this.checked); //Classe de seleção na row

												$(this)
														.closest('table')
														.find('.checkAll')
														.prop(
																'checked',
																($(this)
																		.closest(
																				'table')
																		.find(
																				'tbody :checkbox:checked').length == $(
																		this)
																		.closest(
																				'table')
																		.find(
																				'tbody :checkbox').length)); //Tira / coloca a seleção no .checkAll
											});
						});
	</script>



	<script>
		$(document)
				.ready(
						function() {
							$('.checkAll').on(
									'click',
									function() {
										$(this).closest('.testcasescheckbox')
												.find('ul li :checkbox')
												.prop('checked', this.checked)
												.closest('tr').toggleClass(
														'selected',
														this.checked);
									});

							$('ul li :checkbox')
									.on(
											'click',
											function() {
												$(this).closest('tr')
														.toggleClass(
																'selected',
																this.checked); //Classe de seleção na row

												$(this)
														.closest(
																'.testcasescheckbox')
														.find('.checkAll')
														.prop(
																'checked',
																($(this)
																		.closest(
																				'.testcasescheckbox')
																		.find(
																				'ul li :checkbox:checked').length == $(
																		this)
																		.closest(
																				'.testcasescheckbox')
																		.find(
																				'ul li :checkbox').length)); //Tira / coloca a seleção no .checkAll
											});
						});
	</script>



</body>
</html>
