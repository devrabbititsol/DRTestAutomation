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
<link rel="stylesheet" href="css/sortable.css">
	
<link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
         rel = "stylesheet">
      <script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
      <script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script src="js/lodash.js"></script>
     
 <script >
	$(function() {
    $( "#sortable" ).sortable({
        update: function(event, ui) {
            var productOrder = $(this).sortable('toArray').toString();
           // $("#sorted_data").text (productOrder);
            sortedElements = productOrder;
         },
         start: function(event, ui) { 
        	   // $("#sortable-9").text (ui);
         }
      });
    $( "#sortable" ).disableSelection();
 
});
	  </script>
<script type="text/javascript">
	var testcaseid = 0;
	var selectedScreens = new Array();
	 var updateScreensList = new Array();
	 var testcaseList = new Array();
	  var sortedElements = "";
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
							testcaseList.push(msg[i]);
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
	function setTestCaseNameById(tcId){
		var tcid = Number(tcId);
		var tc = _.find(testcaseList, { testCaseId: tcid });
		 $("#testcase_name").val(tc.testCaseName);
	}

	function getScreenNames(id) {
		setTestCaseNameById(id);
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
							selectedScreens.push(screens[i].screenId);
							var screen = {screenId: screens[i].screenId, screenName: screens[i].screenName};
							updateScreensList.push(screen);
							screensList += "<li   id=screen_"+ screens[i].screenId+" name=screen_"+ screens[i].screenId+" >"
									+ screens[i].screenName + "</li>";
									
							table(screens[i], screens[i].screenId);
						}
						$('#pageslist').empty().append(screensList);
						
					}
				});

	}
	function viewSelectedModel(){
		sortedElements = "";
		sortedElements = selectedScreens.join();
		$('#confirm').modal('show');
		loadSelectedData(updateScreensList);	    
	}
	
	function loadSelectedData(data){
		var screens = '';
		for (var i = 0; i < data.length; i++) {						
			screens += "<li class='ui-state-default' id="+data[i].screenId+">"+data[i].screenName+"</li>";
				}
		$('#sortable').empty().append(screens);
	}
	function table(screen, screenId) {

		var table_header = "<div class='pagedetails_main table-responsive testcases_screens'  id=dyn_"
		+screenId
		+"><h1>"
				+ screen.screenName + "</h1>";
		var table_start = "<table class='table' border='1'>" + "<thead>"
				+ "<tr>" + "  <th>Field Name</th>" + "  <th>Field Type</th>"
	            + "  <th>Action</th>" + " </tr>"
				+ " </thead>" + " <tbody>";
		var table_close = "</tbody></table></div>"

		//alert(JSON.stringify(msg));
		var elements = screen.elements;
		// code populate table  

		var pageelements = '';
		for (var i = 0; i < elements.length; i++) {
			elemdetails = elements[i];
			var isInput = '';
			
			pageelements += "<tr>" + " </td>" + " <td>" + elemdetails.prop_name
					+ "</td>" + "<td>" + elemdetails.fieldType + "</td>"
					+  "</tr>";

		}
		var table = table_header + table_start + pageelements + table_close;

		$('#screenDetails').append(table);
	}

	function updateTestcase(){
			//alert(checkedScreens.join());
		testcasedata = {
				testcase_id : testcaseid,			
      			order: sortedElements,
		    	httpCall : "update"
		};
	
		 $.ajax({
			type : 'POST',
			url : "./TestCase", //this is my servlet //./Home
			data : testcasedata,
			success : function(msg) {
				var data = msg;
				alert(data);
				$('#status').empty().append(data);
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
				 <li><a href="teastcase.jsp" >Create Test Cases</a></li>
                <li    class="active"><a href="teastcase_update.jsp">Update Test Cases</a></li>
                <li><a href="teastcase_delete.html">Delete Test Cases</a></li>
								</ul>

							</div>
							<div class="panel-body create_testcase">
								<div class="card">

									<div class="content">
										    <ul class="main_title">
								<li>Test Case Name :</li>	
								 <li><div class="form-group">
                                                <input type="text" class="form-control border-input" placeholder="please enter test case"
															id="testcase_name" name="testcasename" readonly>
                                            </div></li>	
								 <li><button type="button" class="btn btn-success" onclick="viewSelectedModel()" >Update</button></li>	
								
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

	<div class="modal fade" id="confirm" role="dialog">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="sorted_data">Selected Fields</h4>
				</div>
				<div class="modal-body">
			<div class="pagedetails_main table-responsive table--class">
			
			
			<ul id="sortable"></ul>
				<table class="table table-fixed loadmore_elements" id=""  border="1">
														
															</table>
														</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-default"  data-dismiss="modal" onclick="updateTestcase()">Proceed</button>
				</div>
			</div>
		</div>
	</div>
	<!------ model box -------->



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
