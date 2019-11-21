<!DOCTYPE html>
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


<link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
         rel = "stylesheet">
      <script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
      <script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
      <script src="js/lodash.js"></script>
      
<script src="js/common.js"></script>

<script type="text/javascript">

   var loadedRecords = new Array();
   var checkedRecords = new Array();
   var checkedValues = new Array();
   var sortedElements = "";
	$(function() {
		$("#loadmore_bt").hide();
		getPages();
	});

	function getPages() {
		$
				.ajax({
					type : "post",
					url : "./PageInfo", //this is my servlet //./Home
					data : "testcaseId=1",
					success : function(msg) {
						//	alert(JSON.stringify(msg));
						var data = msg;
						// code populate table  
						var listprojects = '';

						for (var i = 0; i < data.length; i++) {
                      
							listprojects += "<li id="
									+ msg[i].pageid
									+ " onclick='getDetailsById(this.id)' class='list-group-item inlinediv'>"
									+ trimmString(msg[i].pageName) + "</li>";
						}

						$('#pageslist').empty().append(listprojects);
					}
				});
	}

	function getDetailsById(pageid) {
	

		if ($('input[name=loadmore_id]').val() === pageid) {

		} else {
			$('#pages_elements_list').empty();
			$('input[name=loadmore_offset]').attr('value', 0);
			loadedRecords = [];
			checkedRecords = [];
		}
		$('input[name=loadmore_id]').attr('value', pageid);
		var offsetval = $('input[name=loadmore_offset]').val();

		pagedata = {
			offSetVal : offsetval,
			pageId : pageid,
			httpCall : "getlist"
		};

		$
				.ajax({
					type : "post",
					url : "./SelectedElements", //this is my servlet //./Home
					data : pagedata,
					success : function(msg) {
						//alert(JSON.stringify(msg));
						var data = msg;
						// code populate table  
						var listprojects = '';
						var pageelements = '';
						if(data.length >= 30){							
							$("#loadmore_bt").show();
						}else{
							$("#loadmore_bt").hide();
						}
						for (var i = 0; i < data.length; i++) {
							
							loadedRecords.push(data[i]);
							
							var relXPathById = msg[i].relativeXpathbyId;
							var relXPathByName = msg[i].relativeXpathbyName;
							var xPath = msg[i].xPath;
							//alert(msg[i].isSelected);
							var checkedst = " >";
							if (msg[i].isSelected === true) {
								checkedst = " checked>"
							}
							pageelements += "<tr>"
									+ "<td>"
									+ "<label class='custom-control custom-checkbox'>"
									+ " <input type='checkbox' class='custom-control-input' name='feature[]' id="
								+ msg[i].pageid
								+ " value="
								+ msg[i].pageid
								+ ""
								+ " >"
									+ " <span class='custom-control-indicator'></span>"
									+ " </label>"
									+ " </td>"
									+ " <td>"
									+ msg[i].propertyName
									+ "</td>"
									+ "<td>"
									+ msg[i].inputValue
									+ "</td>"
									+ "<td class='showmodel'><a> <i class='fa fa-edit' onclick='viewModelData(\"" + relXPathById + "\",\"" + relXPathByName + "\",\"" + xPath + "\")'></i> </a></td>"
									+ "</tr>";

						}
						var offset = parseInt(offsetval) + 30;
						$('input[name=loadmore_offset]').attr('value', offset);
						$('#pages_elements_list').append(pageelements);
					}
				});

	}
  
	function loadMoreElements() {
		var pageid = $('input[name=loadmore_id]').val();
		getDetailsById(pageid);
	}
	function trimmString(title) {
		var length = 14;
		var trimmedString = title.length > length ? title.substring(0,
				length - 3)
				+ "..." : title;
		return trimmedString;
	}
	function creteScreen() {
		checkedRecords = [];
		var screen_name = $('input[name=screen_name]').val();
		var pageid = $('input[name=loadmore_id]').val();
		$("#sortable").text (screen_name);
		checkedValues = [];
		var uncheckedValues = new Array();
		var allCheckboxes = document.getElementsByName("feature[]");
		for (var i = 0; i < allCheckboxes.length; i++) {
			if (allCheckboxes[i].checked)
				checkedValues.push(allCheckboxes[i].value);
			else
				uncheckedValues.push(allCheckboxes[i].value);
		}
		if (screen_name.length == 0) {
			alert('Please enter Screen Name');
			return false;
		} else if (pageid == 0) {
			alert('Please Select Page to create');
			return false;
		} else if (checkedValues.length <= 0) {
			alert('Please Select Elements');
			return false;
		}
		for(var i = 0; i < checkedValues.length; i++){
			var pageId = Number(checkedValues[i]);
			var user = _.find(loadedRecords, { pageid: pageId });
			checkedRecords.push(user);
		}
	
		viewSelectedModel(checkedRecords);
		
	}
	
	function createScreenwithSelectedrows(){
		var screen_name = $('input[name=screen_name]').val();
		var pageid = $('input[name=loadmore_id]').val();
		var checkedValues = new Array();
		var uncheckedValues = new Array();
		var allCheckboxes = document.getElementsByName("feature[]");
		for (var i = 0; i < allCheckboxes.length; i++) {
			if (allCheckboxes[i].checked)
				checkedValues.push(allCheckboxes[i].value);
			else
				uncheckedValues.push(allCheckboxes[i].value);
		}
	
		pagedata = {
				screen_name : screen_name,
				pageId : pageid,
				updatedIds : checkedValues.join(),
				uncheckedIds : uncheckedValues.join(),
				elementsOrder: sortedElements,
				httpCall : "create"
			};
		//alert(sortedElements);
		 $.ajax({
		type : 'POST',
		url : "./SelectedElements", //this is my servlet //./Home
		data : pagedata,
		success : function(msg) {
			alert(JSON.stringify(msg));
			var data = msg;
			$('#status').empty().append(data);
			$('#screen_name').val('');
		}
	}); 
	}
	
	function viewModelData(xpathById, xpathbyName,xpath ){
		$('#myModal').modal('show');
	     $('#rel_xpath').text(xpath);
	     $('#xpath_by_id').text(xpathbyName);
	     $('#xpath_by_name').text(xpathById);
	}
	
	function viewSelectedModel(selectedList){
		sortedElements = "";
		sortedElements = checkedValues.join();
		$('#confirm').modal('show');
		loadSelectedData(selectedList);
	}
	
	function loadSelectedData(data){
		var checkedele = "";
		for (var i = 0; i < data.length; i++) {
		
			checkedele += "<tr id="+ data[i].pageid+">"
					+ " <td>"
					+ data[i].propertyName
					+ "</td>"
					+ "<td>"
					+ data[i].inputValue
					+ "</td>"					
					+ "</tr>";

		}
		$('#checked_elements_list').empty().append(checkedele);
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
									<li class="active"><a href="screens.html">Create
											Screens</a></li>
									<li><a href="screens_update.html">Update Screens</a></li>
									<li><a href="screens_delete.html">Delete Screens</a></li>
								</ul>
							</div>
							<div class="panel-body">
								<div class="card">

									<div class="content">
										<ul class="main_title">
											<li>Screen Name :</li>
											<li><div class="form-group">
													<input type="text" class="form-control border-input"
														placeholder="Please Enter Screen Name" id="screen_name"
														name="screen_name">
												</div></li>
											<li><button type="button" class="btn btn-success"
													onclick="creteScreen()">OK</button></li>
											<input type="hidden" name="loadmore_offset"
												id="loadmore_offset" value="0">
											<input type="hidden" name="loadmore_id" id="loadmore_id"
												value="0">
										
										</ul>

										<hr>

										<div class="tab-content">
											<div class="tab-pane fade in active">



												<div class="pages_list" id="testDiv4" class="scrollpane">
													<ul id="pageslist">

													</ul>



												</div>

												<div class="tab-content">
													<div class="tab-pane active" id="tab_a">





														<div class="pagedetails_main table-responsive table--class">
															<table class="table table-fixed loadmore_elements" id=""  border="1">
																<thead>
																	<tr>
																		<th><input type="checkbox" class="checkAll"
																			name="checkAll" /> All</th>
																		<th>Field Name</th>
																		<th>Field Type</th>
																		<th>Field Locator</th>
																		<th>Action</th>
																	</tr>
																</thead>
																<tbody id="pages_elements_list"></tbody>
																
															</table>
														</div>
													</div>
<button type="button" style="float:right" id="loadmore_bt" class="btn btn-danger" onclick="loadMoreElements()">Load More</button>
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
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title">Field Locator</h4>
				</div>
				<div class="modal-body">
				<div class="fileds">
				
					<p><strong>X-Path</strong>: <strong id="rel_xpath"></strong></p>
					<p><strong>X-Path by Name</strong>: <strong id="xpath_by_id"></strong></p>
					<p><strong>X-Path by Id</strong>: <strong id="xpath_by_name"></strong></p>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="confirm" role="dialog">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="modal-title" id="sortable">Field Locator</h4>
				</div>
				<div class="modal-body">
			<div class="pagedetails_main table-responsive table--class">
															<table class="table table-fixed loadmore_elements" id=""  border="1">
																<thead>
																	<tr>
																		
																		<th>Field Name</th>
																		<th>Field Type</th>																		
																		<th>Action</th>
																	</tr>
																</thead>
																<tbody id="checked_elements_list"></tbody>
																
															</table>
														</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-default"  data-dismiss="modal" onclick="createScreenwithSelectedrows()">Proceed</button>
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

    <script type="text/javascript">
  $('#checked_elements_list').sortable({
      update: function(event, ui) {
          var productOrder = $(this).sortable('toArray').toString();
        //  $("#sortable").text (productOrder);
          sortedElements = productOrder;
       },
       start: function(event, ui) { 
      	   // $("#sortable-9").text (ui);
       }
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

		});
	</script>

	<div id="loading">loading..</div>
</body>
</html>

