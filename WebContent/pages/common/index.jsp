 <%
	 response.setHeader("Cache-Control","no-cache");
	 response.setHeader("Cache-Control","no-store");
	 response.setHeader("Pragma","no-cache");
	 response.setDateHeader ("Expires", 0);
	
	 if(session.getAttribute("SESSIONID") == null)
	     response.sendRedirect("/login.jsp");
 %> 
<%@page import="bt.org.crs.rma.dto.common.NotificationDTO"%>
<%@page import="bt.org.crs.rma.constant.CRSTConstants"%>
<%@page import="bt.org.crs.rma.vo.UserDetailsVO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta charset="utf-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <title>Central Registry</title>
 <link rel="icon" href="<%=request.getContextPath() %>/img/favicon.ico">
 <!-- Tell the browser to be responsive to screen width -->
 <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
</head>
<%
	UserDetailsVO userDetails = null;
	String role = null;
	if(session.getAttribute(CRSTConstants.USERDETAILS.getQuery())!=null)
	{
		userDetails = (UserDetailsVO) session.getAttribute(CRSTConstants.USERDETAILS.getQuery());
		role = userDetails.getRole();
	}
%>
<body class="hold-transition skin-blue sidebar-mini">

	<div class="wrapper">
	
		<jsp:include page="/pages/common/header.jsp"></jsp:include>
		<jsp:include page="/pages/common/navigation.jsp"></jsp:include>
		
		<div class="content-wrapper">
		
			<div id="contentDisplayDiv">
		
				<section class="content-header">
				  <h1>
			        Dashboard
			        <small>summary stats for today</small>
			      </h1>
			      <ol class="breadcrumb">
			        <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
			        <li class="active">Dashboard</li>
			      </ol>
				</section>
				
				<section class="content">
					<div class="row">
					
						<div class="col-lg-3 col-xs-6">
							<div class="small-box bg-aqua">
								<div class="inner">
									<h3><%=request.getAttribute("TOTAL_REGISTRATION") %></h3>
									<p>New Registrations</p>
								</div>
								<div class="icon">
									<i class="ion ion-archive"></i>
								</div>
							</div>
						</div>
						
						<div class="col-lg-3 col-xs-6">
				          <div class="small-box bg-green">
				            <div class="inner">
				              <h3><%=request.getAttribute("TOTAL_HIT_SEARCH") %></h3>
				              <p>Total Hit Searches</p>
				            </div>
				            <div class="icon">
				              <i class="ion ion-ios-search"></i>
				            </div>
				          </div>
				        </div>
				        
				        <div class="col-lg-3 col-xs-6">
				          <div class="small-box bg-yellow">
				            <div class="inner">
				              <h3><%=request.getAttribute("TOTAL_NO_HIT_SEARCH") %></h3>
				              <p>Total No-Hit Searches</p>
				            </div>
				            <div class="icon">
				              <i class="ion ion-ios-search"></i>
				            </div>
				          </div>
				        </div>
				        <%
				        	if(role.equals(CRSTConstants.SUPERADMIN.getQuery())){
				        %>
					        <div class="col-lg-3 col-xs-6">
					          <div class="small-box bg-red">
					            <div class="inner">
					              <h3>Nu.&nbsp;<%=request.getAttribute("TOTAL_REVENUE") %></h3>
					              <p>Revenue Generated</p>
					            </div>
					            <div class="icon">
					              <i class="ion ion-pie-graph"></i>
					            </div>
					          </div>
					        </div>
						<%
				        	}
						%>
					</div>
					
					<div class="row">
						
						<div class="col-lg-3 col-xs-6">
				          <div class="small-box bg-yellow">
				            <div class="inner">
				              <h3><%=request.getAttribute("TOTAL_AMENDMENT") %></h3>
				              <p>Total Amendments</p>
				            </div>
				            <div class="icon">
				              <i class="ion ion-ios-search"></i>
				            </div>
				          </div>
				        </div>
				        
				        <div class="col-lg-3 col-xs-6">
				          <div class="small-box bg-red">
				            <div class="inner">
				              <h3><%=request.getAttribute("TOTAL_CONTINUATION") %></h3>
				              <p>Total Continuations</p>
				            </div>
				            <div class="icon">
				              <i class="ion ion-ios-search"></i>
				            </div>
				          </div>
				        </div>
				        
				        <div class="col-lg-3 col-xs-6">
				          <div class="small-box bg-aqua">
				            <div class="inner">
				              <h3><%=request.getAttribute("TOTAL_DISCHARGE") %></h3>
				              <p>Total Discharges</p>
				            </div>
				            <div class="icon">
				              <i class="ion ion-logo-angular"></i>
				            </div>
				          </div>
				        </div>
				        
					</div>
					
					<div class="row">
						<% if(role.equals(CRSTConstants.SUPERADMIN.getQuery())){ %>
							<section class="col-lg-7 connectedSortable">
						        <!-- quick email widget -->
						        <div class="box box-info">
						          <div class="box-header">
						            <i class="fa fa-envelope"></i>
						
						            <h3 class="box-title">Quick Notice</h3>
						            <!-- /. tools -->
						          </div>
						          <div class="box-body">
						            <form action="/admin.html" method="post" id="noticeForm">
						              <div class="col-sm-12 form-group" id="noticeMsgDiv" style="display:none"></div>
						              <div class="form-group">
						                <input type="text" class="form-control" name="messageTitle" placeholder="Title" id="messageTitle">
						              </div>
						              <div>
						                <textarea class="textarea" placeholder="Message" id="message" name="message"
						                          style="width: 100%; height: 125px; font-size: 14px; line-height: 18px; border: 1px solid #dddddd; padding: 10px;"></textarea>
						              </div>
						            </form>
						          </div>
						          <div class="box-footer clearfix">
						            <button type="button" class="pull-right btn btn-primary" id="sendEmail" onclick="sendNotification()">Send
						              <i class="fa fa-arrow-circle-right"></i></button>
						          </div>
						        </div>
						     </section>
						     <section class="col-lg-5 connectedSortable">
					     <%
					     	} else if(!role.equals(CRSTConstants.SUPERADMIN.getQuery())){
					     %>
					     	<section class="col-lg-12 connectedSortable">
					     <%
					     	}
					     %>
					         <div class="box box-solid bg-light-blue-gradient">
					            <div class="box-header with-border">
					              <i class="fa fa-thumb-tack"></i>
					              <h3 class="box-title">
					                Notice Board
					              </h3>
					            </div>
					            <div class="box-body">
					              <%
					              	NotificationDTO dto = (NotificationDTO) request.getAttribute("NOTICE");
					              %>
					              <div id="world-map" style="height: 250px; width: 100%;" class="pre-scrollable">
					              	<strong><u><%=dto.getNoticeTitle() %></u></strong>
					              	<br>
					              	<p style="text-align:justify"><%=dto.getNoticeContent() %></p>
					              </div>
					            </div>
					          </div>
					        </section>
					     
					</div>
					
				</section>
			
			</div>
			
		</div>
		
		<jsp:include page="/pages/common/footer.jsp"></jsp:include>
		
	</div>
	<script>
	
		$(document).ready(function() 
		{
			generateApplicationToken();
		});
		
		function generateApplicationToken(){
			$.ajax
			({
				type : "POST",
				url : "<%=request.getContextPath()%>/getApplicationToken",
				data : $('form').serialize(),
				cache : false,
				dataType : "html",
				success : function(responseText) {}
			});
		}
	
		$.idleTimeout('#idletimeout', '#idletimeout a', 
		{
			idleAfter: 300,
			pollingInterval: 2,
			serverResponseEquals: 'OK',
			onTimeout: function()
			{
				$(this).slideUp(); 
				window.location = "<%=request.getContextPath()%>/logout.html";
			},
			onIdle: function()
			{
				$(this).slideDown();  //show the warning bar
				$(this).get(0).scrollIntoView();
			},
			onCountdown: function( counter )
			{
				$(this).find("span").html( counter );  //update the counter
			},
			onResume: function()
			{
				$(this).slideUp(); // hide the warning bar
			}
	   });
			
		function logout()
		{
			window.location = "<%=request.getContextPath()%>/logout.html";
		}
	
	 	$(function () {
	    	$('.textarea').wysihtml5()
	  	});
	 	
	 	function sendNotification(){
	 		var url= '<%=request.getContextPath()%>/admin.html?method=postNotice';
			var options = {target:'#noticeMsgDiv',url:url,type:'POST',enctype: 'multipart/form-data',data: $("#noticeForm").serialize()};
			$("#noticeForm").ajaxSubmit(options);
			$('#noticeMsgDiv').show();
			$('#message').val("");
			$('#messageTitle').val("");
	 	}
	 	
	</script>
</body>
</html>