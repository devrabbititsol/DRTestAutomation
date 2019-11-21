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

	
<link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
         rel = "stylesheet">
      <script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
      <script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
      
     
      
       <script>
         $(function() {
            $( "#pageslist" ).sortable({
                update: function(event, ui) {
                    var productOrder = $(this).sortable('toArray').toString();
                    $("#sortable-9").text (productOrder);
                 },
                 start: function(event, ui) { 
                	   // $("#sortable-9").text (ui);
                 }
              });
         });
         
      </script>
<script type="text/javascript">
	var testcaseid = 0;
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
									+ " onclick='getScreenNames(this.id)' class='list-group-item inlinediv'>"
									+ trimmString(msg[i].testCaseName)
									+ "</li>";
						}

						$('#testcaselist').empty().append(listprojects);
					}
				});
	}

	function getScreenNames(id) {
		$('#pageslist').empty();
		$('#screenDetails').empty();

		screens = {
			httpCall : "getscreens",
			testcaseId : id
		};
		$
				.ajax({
					type : 'POST',
					url : "./TestCaseScreens", //this is my servlet //./Home
					data : screens,
					success : function(msg) {
						//alert(JSON.stringify(msg));
						var data = msg;
						var screens = data.screens;
						testcaseid = data.testcaseId;
						//alert(JSON.stringify(data));
						var screensList = '';
						for (var i = 0; i < screens.length; i++) {
							screensList += "<li   id=screen_"+ screens[i].screenId+" name=screen_"+ screens[i].screenId+" >"
									+ screens[i].screenName + "</li>";
							table(screens[i], screens[i].screenId);
						}
						$('#pageslist').empty().append(screensList);
						populateData();
					}
				});

	}

	function table(screen, screenId) {

		var table_header = "<div class='pagedetails_main table-responsive testcases_screens'  id=dyn_"
		+screenId
		+"><h1>"
				+ screen.screenName + "</h1>";
		var table_start = "<table class='table' border='1'>" + "<thead>"
				+ "<tr>" + "  <th>Field Name</th>" + "  <th>Field Type</th>"
				+ "  <th>Input Value</th>" + "  <th>Action</th>" + " </tr>"
				+ " </thead>" + " <tbody>";
		var table_close = "</tbody></table></div>"

		//alert(JSON.stringify(msg));
		var elements = screen.elements;
		// code populate table  

		var pageelements = '';
		for (var i = 0; i < elements.length; i++) {
			elemdetails = elements[i];
			var isInput = '';
			if (elemdetails.fieldType.toLowerCase().includes("input")) {
				isInput = "<input type='text' id="+elemdetails.elementId+" name= 'sub_values[]' /><input type='hidden' value="+screenId+" id="+elemdetails.elementId+" name= 'screen_values[]' /><input type='hidden' value='false'  name= 'validate_values[]' />";
			}else{
				validationId = "valid_"+elemdetails.elementId;
				validateLable = "validate_lable_"+elemdetails.elementId;
				var validateIdSt = validationId.concat(screenId);
				var validatelableSt = validateLable.concat(screenId);
				isInput = "<input type='hidden' id="+elemdetails.elementId+" name= 'sub_values[]' /><input type='hidden' value="+screenId+" id="+elemdetails.elementId+" name= 'screen_values[]' /><input type='hidden' value='false' id="+validateIdSt+" name= 'validate_values[]' /><a><u onclick='validate_fields("+elemdetails.elementId+","+screenId+")' id="+validatelableSt+">validate</u></a>";
					
			}
			pageelements += "<tr>" + " </td>" + " <td>" + elemdetails.prop_name
					+ "</td>" + "<td>" + elemdetails.fieldType + "</td>"
					+ "<td>" + isInput + "</td>" + "</tr>";

		}
		var table = table_header + table_start + pageelements + table_close;

		$('#screenDetails').append(table);
	}

	function createDataSet() {
		var elementInfo = new Array();
		var elementvalues = document.getElementsByName("sub_values[]");
		var screenidsforelements = document.getElementsByName("screen_values[]");
		var validIds = document.getElementsByName("validate_values[]");
		
		var datasetname = $('input[name=datasetname]').val();

		for (var i = 0; i < elementvalues.length; i++) {
			var validate = false;
			if(validIds[i].value == 'true'){
				validate = true;
			}
			var elementData = {
				elementId : elementvalues[i].id,
				elementValue : elementvalues[i].value,
				ScreenId : screenidsforelements[i].value,
				isvaldate: validate
			};
			elementInfo.push(elementData);

		}
		//alert(datasetname);
		//alert(JSON.stringify(elementInfo));

		dataset = {
			httpCall : "create",
			datasetName : datasetname,
			testcaseId : testcaseid,
			elementInputs : JSON.stringify(elementInfo)
		};

		$.ajax({
			type : 'POST',
			data : dataset,
			url : "./DataSet", //this is my servlet //./Home
			success : function(msg) {
				alert(msg);
				$('input[name=datasetname]').val('');
				for (var i = 0; i < elementvalues.length; i++) {
					elementValue: elementvalues[i].value = '';

				}
			}
		});
	}

	function populateData() {
		var elementvalues = document.getElementsByName("sub_values[]");
		var validateValues = document.getElementsByName("validate_values[]");
		lastrecordInput = {
			httpCall : "getLastRecord",
			testcaseId : testcaseid
		};

		$.ajax({
			type : 'POST',
			data : lastrecordInput,
			url : "./DataSet", //this is my servlet //./Home
			success : function(msg) {
				//alert(JSON.stringify(msg));
				$('input[name=datasetname]').val('');
				for (var i = 0; i < elementvalues.length; i++) {
					elementvalues[i].value = msg[i].text_for_idtext;
					setInitialValidation(msg[i].elementId, msg[i].screenId, msg[i].isSelected);
				}
			}
		});
	}

	function executeDataset() {
		var tested_url = $('input[name=url]').val();
		if (!validateURL(tested_url)) {
			alert("Please enter valid URL");
			return false;
		}
		lastrecordInput = {
			httpCall : "executedataset",
			testcaseId : testcaseid,
			url: tested_url
		};

		$.ajax({
			type : 'POST',
			data : lastrecordInput,
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
							<div class="panel-heading">
								<ul class="nav nav-tabs">
									<li class="active"><a href="datasets.html">Create
											DataSets</a></li>
									<li><a href="datasets_update.html">Update DataSets</a></li>
									<li><a href="datasets_delete.html">Delete DataSets</a></li>
								</ul>

							</div>
							<div class="panel-body create_testcase">
								<div class="card">

									<div class="content">
										<ul class="main_title">
											<li>DataSet Name :</li>
											<li><div class="form-group">
													<input type="text" class="form-control border-input"
														id="datasetname" name="datasetname"
														placeholder="Enter DataSet Name">
												</div></li>
											<li><button type="button" class="btn btn-success"
													onclick='createDataSet()'>Create</button></li>
											<li><a data-toggle='modal' href='#myModal'><button
														type="button" style=" display: none; visibility: hidden; class="btn btn-danger">Execute</button></a></li>
										</ul>

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
																	<h2 class="text_heading" id="sortable-9">Screen Names</h2>

																	<div id="testDiv6">

																		<ul id="pageslist">


																		</ul>
<!-- <ul id = "sortable-1">
         <li id = "1" class = "default">Product 1</li>
         <li id = "2" class = "default">Product 2</li>
         <li id = "3" class = "default">Product 3</li>
         <li id = "4" class = "default">Product 4</li>
      </ul> -->

																	</div>

																</div>
															</div>
															<div class="col-md-6">
																<h2 class="text_heading">Screen Details</h2>
																<div class="pagedetails_maintable" id="screenDetails">

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
