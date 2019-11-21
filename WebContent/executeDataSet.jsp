<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>. : Welcome to Test Automation Framework : .</title>
<link
	href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="css/custom.css">

<link rel="stylesheet" href="css/animate.css">
<link rel="stylesheet" href="css/themify-icons.css">
<link
	href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/responsive.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>

<script type="text/javascript">
	var testcaseid = 0;
	var datasetid = 0;
	$(function() {
		gettestCases();
		//getScreenNames();
	});

	function gettestCases() {
		var testcase = {
			httpCall : "gettestcases"
		};
		$
				.ajax({
					type : "post",
					url : "./TestCase", //this is my servlet //./Home
					data : testcase,
					success : function(msg) {
						//alert(JSON.stringify(msg));
						var data = msg;
						// code populate table  
						var listprojects = '';

						for (var i = 0; i < data.length; i++) {

							listprojects += "<li id="
									+ msg[i].testCaseId
									+ " onclick='getDatasets(this.id)' class='list-group-item inlinediv'>"
									+ trimmString(msg[i].testCaseName)
									+ "</li>";
						}

						$('#testcaselist').empty().append(listprojects);
					}
				});
	}

	function getDatasets(id) {
		$('#pageslist').empty();
		$('#screenDetails').empty();

		screens = {
			httpCall : "getdatasets",
			testcaseId : id
		};
		$
				.ajax({
					type : 'POST',
					url : "./DataSet", //this is my servlet //./Home
					data : screens,
					success : function(msg) {
						//alert(JSON.stringify(msg));
						var data = msg;
						var datasets = data;
						testcaseid = id;
						//alert(JSON.stringify(data));
						var screensList = '';
						for (var i = 0; i < datasets.length; i++) {
							screensList += "<li   id="+ datasets[i].datasetId+" >"
									+ datasets[i].datasetName + "<a data-toggle='modal' href='#myModal'><i onclick='setdatasetId("+datasets[i].datasetId+")'  class='fa fa-caret-right'></i></a></li>";
							
						}
						$('#pageslist').empty().append(screensList);
						
					}
				});

	}
	
	function setdatasetId(executeDatasetId){
		datasetid = executeDatasetId;
	}

	function executeDataset() {
		var tested_url = $('input[name=url]').val();
		if (!validateURL(tested_url)) {
			alert("Please enter valid URL");
			return false;
		}
		executeData = {
			httpCall : "executedatasetBYDataSet",
			testcaseId : testcaseid,
			datasetId : datasetid,
			url: tested_url
		};
       
		$.ajax({
			type : 'POST',
			data : executeData,
			url : "./DataSet", //this is my servlet //./Home
			success : function(msg) {
				//alert(JSON.stringify(msg));
				$('#myModal').modal('hide');
			}
		});
	}
	
	function validateURL(textval) {
		var urlregex = new RegExp(
				"^(http:\/\/www.|https:\/\/www.|ftp:\/\/www.|www.){1}([0-9A-Za-z]+\.)");
		return urlregex.test(textval);
	}
	
	function trimmString(title) {
		var length = 14;
		var trimmedString = title.length > length ? title.substring(0,
				length - 3)
				+ "..." : title;
		return trimmedString;
	}
	
	function validate_fields(id,screenId){
		
		validationId = "valid_"+id;
		validateLable = "validate_lable_"+id;
		var validateIdSt = validationId.concat(screenId);
		var validatelableSt = validateLable.concat(screenId);
		if(document.getElementById(validateIdSt).value == 'true'){
			document.getElementById(validateIdSt).value = 'false'; 
			document.getElementById(validatelableSt).innerHTML  = 'validate'; 
		}else{
			document.getElementById(validateIdSt).value = 'true'; 
			document.getElementById(validatelableSt).innerHTML  = 'valid'; 
		}
	
	}
function setInitialValidation(id,screenId,isSelected){
		
		validationId = "valid_"+id;
		validateLable = "validate_lable_"+id;
		var validateIdSt = validationId.concat(screenId);
		var validatelableSt = validateLable.concat(screenId);
		if(isSelected == true){
			document.getElementById(validateIdSt).value = 'true'; 
			document.getElementById(validatelableSt).innerHTML  = 'valid'; 
		}else{
			document.getElementById(validateIdSt).value = 'false'; 
			document.getElementById(validatelableSt).innerHTML  = 'validate'; 
		}
	
	}
	
</script>

</head>

<body>

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
							
							<div class="panel-body create_testcase">
								<div class="card">

									<div class="content">
									

										<hr>

										<div class="tab-content">
											<div class="tab-pane fade in active">

												<div class="">

													<div class="pages_list">
														<h2 class="text_heading">Test Case Names</h2>
														<div id="testDiv4">
															<ul id="testcaselist">

															</ul>

														</div>
													</div>


												</div>

												<div class="tab-content">



													<div class="tab-pane active">

														<div class="row">


															<div class="col-md-3">


																<div class="testcasescheckbox">
																	<h2 class="text_heading">DataSet</h2>

																	<div id="testDiv6">

																		<ul id="pageslist">


																		</ul>


																	</div>

																</div>
															</div>
														
														</div>





													</div>

												</div>
											</div>

										</div>


									</div>
								</div>

							</div>
						</div>
					</div>


				</div>
			</div>


		</div>

	</div>


	<!------ model box -------->

	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">

				<div class="modal-body" contenteditable="true">


					<label for="url">Enter Url:</label> <input type="text"
						class="form-control" id="url" name="url">


				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-success"
						onclick='executeDataset()'>Proceed</button>
					<button type="button" class="btn" data-dismiss="modal"
						aria-label="">Close</button>
				</div>
			</div>
		</div>
	</div>

	<!------ model box END ------------>


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

	<!------ scroll ------------->
	<script type="text/javascript" src="js/jquery.slimscroll.js"></script>
	<script type="text/javascript">
		$(function() {

			$('#testDiv4').slimscroll({
				railVisible : true,
				railBorderRadius : 0
			});

			$('#testDiv5').slimscroll({
				railVisible : true,
				railBorderRadius : 0
			});

			$('#testDiv6').slimscroll({
				railVisible : true,
				railBorderRadius : 0
			});

		});
	</script>

</body>
</html>
