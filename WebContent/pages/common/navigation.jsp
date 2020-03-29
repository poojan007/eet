<%@page import="bt.gov.moh.eet.vo.UserDetailsVO"%>
<%
	UserDetailsVO userDetails = null;
	String userName = null;
	String role = null;
	if(session.getAttribute("userdetails")!=null)
	{
		userDetails = (UserDetailsVO) session.getAttribute("userdetails");
		userName = userDetails.getFull_name().toUpperCase();
		role = userDetails.getRoleCode();
	}
%>

<aside class="main-sidebar">

	<section class="sidebar">
		
		<div class="user-panel">
			<div class="pull-left image">
				<img src="<%=request.getContextPath() %>/img/user2-160x160.jpg" class="img-circle" alt="User Image">
			</div>
			<div class="pull-left info">
				<p><%=userName %></p>
         		<a href="#"><i class="fa fa-circle text-success"></i> Online</a>
			</div>
		</div>
		
		<ul class="sidebar-menu" data-widget="tree">
			<li class="header">MAIN NAVIGATION</li>
			<li class="active">
				<a href="<%=request.getContextPath()%>/login.html?q=dashboard">
	        		<i class="fa fa-dashboard"></i> <span>Dashboard</span>
	        	</a>
			</li>
			<%
				if(role.equalsIgnoreCase("SUPER_ADMIN") || role.equalsIgnoreCase("ADMINISTRATOR")){
			%>
			<li>
				<a href="#" onclick="loadPage('MANAGE_USERS')">
					<i class="fa fa-users"></i>
					<span>User Management</span>
				</a>
			</li>
				<%
					if(role.equalsIgnoreCase("SUPER_ADMIN")){
				%>
			<li class="treeview">
		    	<a href="#">
		    	   <i class="fa fa-table"></i>
		           <span>Master Management</span>
		           <span class="pull-right-container">
		             <i class="fa fa-angle-left pull-right"></i>
		           </span>	
		    	</a>
		    	<ul class="treeview-menu">
		           <!-- <li><a href="#" onclick="loadPage('MASTER_MANAGEMENT_GATES')"><i class="fa fa-circle-o"></i><span> Gates</span></a></li> -->
		           <li><a href="#" onclick="loadPage('MASTER_MANAGEMENT_IDENTIFICATION_TYPES')"><i class="fa fa-circle-o"></i> Identification Types</a></li>
		           <li><a href="#" onclick="loadPage('MASTER_MANAGMENT_NATIONALITY')"><i class="fa fa-circle-o"></i> Nationality</a></li>
		           <li><a href="#" onclick="loadPage('MASTER_MANAGMENT_USERTYPES')"><i class="fa fa-circle-o"></i> User Types</a></li>
		           <!-- <li><a href="#" onclick="loadPage('MASTER_MANAGMENT_EXITREASONS')"><i class="fa fa-circle-o"></i> Exit Reasons</a></li> -->
		           <li><a href="#" onclick="loadPage('MASTER_MANAGMENT_AVERAGE_TIME')"><i class="fa fa-circle-o"></i> Average Travel Time</a></li>
		    	</ul>
		   	</li>
		   	<%
					}
				} else if(role.equalsIgnoreCase("DATA_MANAGER")){
		   	%>
		    <li>
				<a href="#" onclick="loadPage('MANAGE_ENROLLMENT')">
					<i class="fa fa-address-book"></i>
					<span>Enrollment</span>
				</a>
			</li>
			<li>
				<a href="#" onclick="loadPage('MANAGE_ENTRY_EXIT')">
					<i class="fa fa-check-square-o"></i>
					<span>Record Entry and Exit</span>
				</a>
			</li>
			<%
				}
			%>
			<li>
				<a href="#" onclick="loadPage('historical_report')">
	        		<i class="fa fa-database"></i> <span>Reports</span>
	        	</a>
			</li>
		</ul>
	
	</section>
	
</aside>
 
 <script>
 	function loadPage(identifier){
 		$.blockUI
        ({ 
        	css: 
        	{ 
	            border: 'none', 
	            padding: '15px', 
	            backgroundColor: '#000', 
	            '-webkit-border-radius': '10px', 
	            '-moz-border-radius': '10px', 
	            opacity: .5, 
	            color: '#fff' 
        	} 
        });
 		var url = "<%=request.getContextPath()%>/redirect.html?q="+identifier
		$("#contentDisplayDiv").load(url);
		$('#contentDisplayDiv').show();
		setTimeout($.unblockUI, 1000); 
 	}
 	
 </script>