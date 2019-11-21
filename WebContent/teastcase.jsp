<!DOCTYPE html>
<html lang="en" >
<head>
<meta charset="UTF-8">
<title>. : Welcome to Test Automation Framework : .</title>
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/custom.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/responsive.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/animate.css">
<link rel="stylesheet" href="css/themify-icons.css">
<link rel="stylesheet" href="css/sortable.css">
<link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
         rel = "stylesheet">
      <script src = "https://code.jquery.com/jquery-1.10.2.js"></script>
      <script src = "https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
      <script src="js/lodash.js"></script>
	
<script type="text/javascript">
	var checkedScreens = new Array();
	 var loadedRecords = new Array();
	 var checkedRecords = new Array();
	  var sortedElements = "";
	$(function() {
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

							listprojects += "<li  class='list-group-item inlinediv' id="
									+ msg[i].pageid
									+ " onclick='getScreensByPageId(this.id)'"
									+ ">"
									+ trimmString(msg[i].pageName)
									+ "</li>";
						}

						$('#pageslist').empty().append(listprojects);
					}
				});
	}

	function trimmString(title) {
		var length = 14;
		var trimmedString = title.length > length ? title.substring(0,
				length - 3)
				+ "..." : title;
		return trimmedString;
	}

	function getScreensByPageId(pageid) {

		pagedata = {
			pageId : pageid,
			httpCall : "getScreens"
		};

		$
				.ajax({
					type : "post",
					url : "./Screens", //this is my servlet //./Home
					data : pagedata,
					success : function(msg) {
						//alert(JSON.stringify(msg));
						var data = msg;
						// code populate table  
						var screensList = '';

						for (var i = 0; i < data.length; i++) {
							
							loadedRecords.push(data[i]);
							
							var checkedst = " />";
							if (checkExistOrNot('' + msg[i].screenId)) {
								checkedst = " checked/>"
							}
							screensList += "<li id=screen_"+ msg[i].screenId+" name=screen_"+ msg[i].screenId+" ><input type='checkbox' onclick='getDetailsByScreenId(this)'"
									+ "id="
									+ msg[i].screenId
									+ " value="
									+ msg[i].screenId
									+ checkedst
									+ ""
									+ msg[i].screenName + "</li>";
						}

						$('#screenslist').empty().append(screensList);
					}
				});
	}

	function checkIsCheck(obj) {
		if (obj.checked) {
			//alert("checked");
			return true;
		} else {
			//alert("un checked");
			var div = document.getElementById(obj.id);
			div.remove();
			return false;
		}

	}

	function checkExistOrNot(id) {
		var isexist = false;
		for ( var item in checkedScreens) {
			if (checkedScreens[item] === id) {
				isexist = true;
				break;
			}
		}
		return isexist;
	}

	function removeId(id) {
		for ( var index in checkedScreens) {
			if (checkedScreens[index] === id) {
				checkedScreens.splice(index, 1); //remove element
				break;
			}
		}
		return isexist;
	}
	
	function createTestCase() {
		checkedRecords = [];
		var testcase_name = $('input[name=testcasename]').val();
	//alert(checkedScreens.join());
		testcasedata = {
				testcase_name : testcase_name,			
			updatedIds : checkedScreens.join(),
			httpCall : "create"
		};
		 if(testcase_name.length == 0 ) {
             alert('Please enter test case name');
             return false;
          } else if(checkedScreens.length <= 0){
        	  alert('Please Select Screens to create');
                     return false;
          }
		 
			for(var i = 0; i < checkedScreens.length; i++){
				var screenid = Number(checkedScreens[i]);
				var user = _.find(loadedRecords, { screenId: screenid });
				checkedRecords.push(user);
			}			
			 $("#sorted_data").text (testcase_name);
			viewSelectedModel(checkedRecords);
		/* $.ajax({
			type : 'POST',
			url : "./TestCase", //this is my servlet //./Home
			data : testcasedata,
			success : function(msg) {
				//alert(JSON.stringify(msg));
				
				var data = msg;
				$('#status').empty().append(data);
			}
		}); */
	}
	
	
	
	function getDetailsByScreenId(obj) {
		var screenid = obj.id;
		var isExist = checkExistOrNot(screenid);
		if (!isExist) {
			checkedScreens.push(obj.id);
		}

		if (!obj.checked) {
			//alert("un checked");
			var div = document.getElementById('dyn_' + screenid);
			div.remove();
			removeId(screenid);		
			return false;

		}
		var offsetval = $('input[name=loadmore_offset]').val();
		pagedata = {
			offSetVal : 0,
			screenId : screenid,
			httpCall : "getlist"
		};
		//alert('all selected screens are: ' + checkedScreens.join());
		$.ajax({
			type : "post",
			url : "./ScreenElements", //this is my servlet //./Home
			data : pagedata,
			success : function(msg) {
				table(msg, screenid);
			}
		});

	}

	function table(msg, screenId) {
		var screenval = document.getElementById('screen_' + screenId).innerText;
		var table_header = "<div class='pagedetails_main table-responsive testcases_screens'  id=dyn_"
		+screenId
		+"><h1>"
				+ screenval + "</h1>";
		var table_start = "<table class='table' border='1'>" + "<thead>"
				+ "<tr>" + "  <th>Property Name</th>"
				+ "  <th>Input Value</th>" + "  <th>Action</th>"
				+ "  <th>Action</th>" + " </tr>" + " </thead>" + " <tbody>";
		var table_close = "</tbody></table></div>"

		//alert(JSON.stringify(msg));
		var data = msg;
		// code populate table  

		var pageelements = '';
		for (var i = 0; i < data.length; i++) {
			var elemdetails = data[i].pageDetails;
			//alert(msg[i].isSelected);
			var checkedst = " >";
			if (elemdetails.isSelected === true) {
				checkedst = " checked>"
			}
			pageelements += "<tr>"

					+ " </td>"
					+ " <td>"
					+ elemdetails.propertyName
					+ "</td>"
					+ "<td>"
					+ elemdetails.inputValue
					+ "</td>"
					+ "<td><a data-toggle='modal' href='#myModal'> <i class='fa fa-edit'></i> </a></td>"
					+ "</tr>";

		}
		var table = table_header + table_start + pageelements + table_close;

		$('#screenDetails').prepend(table);
	}
	function viewSelectedModel(selectedList){
		sortedElements = "";
		sortedElements = checkedScreens.join();
		$('#confirm').modal('show');
		loadSelectedData(selectedList);	    
	}
	function createTestcaseByscreens(){
	
		var testcase_name = $('input[name=testcasename]').val();
	//alert(checkedScreens.join());
		testcasedata = {
				testcase_name : testcase_name,			
			updatedIds : checkedScreens.join(),
			order: sortedElements,
			httpCall : "create"
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
	function loadSelectedData(data){
		var screens = '';
		for (var i = 0; i < data.length; i++) {						
			screens += "<li class='ui-state-default' id="+data[i].screenId+">"+data[i].screenName+"</li>";
				}
		$('#sortable').empty().append(screens);
	}
</script>
	
	
</head>

<body>

	<div class="wrapper">
    <div class="sidebar" data-background-color="white" data-active-color="danger">

    	<div class="sidebar-wrapper">
            <div class="logo">
                <a href="#" class="simple-text">
                    TAF LOGO
                </a>
            </div>
      <jsp:include page="sidebar.jsp" />
       	</div>
    </div>
		 <div class="main-panel">
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar bar1"></span>
                        <span class="icon-bar bar2"></span>
                        <span class="icon-bar bar3"></span>
                    </button> <h1>TEST AUTOMATION FRAMEWORK</h1>
                </div>
                <div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li>
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="ti-user"></i>
								<p>Name</p>
                            </a>
                        </li>
                        	<li> <a href="index.html">
								<i class="fa fa-sign-out" aria-hidden="true"></i>
								<p>LogOut</p>
                            </a>
                        </li>
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
                <li   class="active"><a href="teastcase.jsp" >Create Test Cases</a></li>
                <li><a href="teastcase_update.jsp">Update Test Cases</a></li>
                <li><a href="teastcase_delete.html">Delete Test Cases</a></li>
              </ul>
            </div>
            <div class="panel-body">
                                      <div class="card">
      
                            <div class="content">
                             <ul class="main_title">
								<li>Test Case Name :</li>	
								 <li><div class="form-group">
                                                <input type="text" class="form-control border-input" placeholder="please enter test case"
															id="testcase_name" name="testcasename">
                                            </div></li>	
								 <li><button type="button" class="btn btn-success" onclick="createTestCase()" >Create</button></li>	
								 <input type="hidden" name="loadmore_offset"
															id="loadmore_offset" value="0"> <input
															type="hidden" name="loadmore_id" id="loadmore_id"
															value="0">
								  <li><button type="button" class="btn btn-danger" >Cancel</button></li>
							</ul>
								
								<hr>
								
								<div class="tab-content">
                <div class="tab-pane fade in active">
                  
					<div class="">
					
						<div class="pages_list" id="testDiv4">
									<ul id="pageslist"></ul>
						</div>
						
					
					</div>
					
                  <div class="tab-content">
           
                      
               
                            <div class="tab-pane active">
                     
                      <div class="row">
						  <div class="col-md-3">
							  	
					
                     <div class="testcasescheckbox" id="testDiv5">
					 <div class="checkbox_all"> 
							 <input type="checkbox" class="checkAll" name="checkAll" /> Select All</div>
							<ul id="screenslist"></ul>
						 
					</div>
						</div>
						  
						  <div class="col-md-9">
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
					<button type="button" class="btn btn-default"  data-dismiss="modal" onclick="createTestcaseByscreens()">Proceed</button>
				</div>
			</div>
		</div>
	</div>
	<!------ model box END ------------>
	<footer>
	
		<div class="container">
			<p>© Copyright 2017 DevRabbit IT Solutions, Inc. All Rights Reserved.</p>
		</div>
			 
	</footer>
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
	
<script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script> 

		<script src="js/main_pages.js"></script>
		
	<!--- check box-----------> 
<script >
	  
	  $(document).ready(function () {
  $('.checkAll').on('click', function () {
    $(this).closest('table').find('tbody :checkbox')
      .prop('checked', this.checked)
      .closest('tr').toggleClass('selected', this.checked);
  });

  $('tbody :checkbox').on('click', function () {
    $(this).closest('tr').toggleClass('selected', this.checked); //Classe de seleção na row
  
    $(this).closest('table').find('.checkAll').prop('checked', ($(this).closest('table').find('tbody :checkbox:checked').length == $(this).closest('table').find('tbody :checkbox').length)); //Tira / coloca a seleção no .checkAll
  });
});  
	  </script> 
	

	
	<script >
	  
	  $(document).ready(function () {
  $('.checkAll').on('click', function () {
    $(this).closest('.testcasescheckbox').find('ul li :checkbox')
      .prop('checked', this.checked)
      .closest('tr').toggleClass('selected', this.checked);
  });

  $('ul li :checkbox').on('click', function () {
    $(this).closest('tr').toggleClass('selected', this.checked); //Classe de seleção na row
  
    $(this).closest('.testcasescheckbox').find('.checkAll').prop('checked', ($(this).closest('.testcasescheckbox').find('ul li :checkbox:checked').length == $(this).closest('.testcasescheckbox').find('ul li :checkbox').length)); //Tira / coloca a seleção no .checkAll
  });
});  
	  </script> 

<!------ scroll ------------->
	<script type="text/javascript" src="js/jquery.slimscroll.js"></script>
<script type="text/javascript">
    $(function(){

 
      $('#testDiv4').slimscroll({
        railVisible: true,
        railBorderRadius: 0
      });
		
      $('#testDiv5').slimscroll({
       railVisible: true,
        railBorderRadius: 0
      });

    });
</script>

</body>
</html>
