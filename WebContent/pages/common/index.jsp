 <%@page import="bt.gov.moh.eet.vo.UserDetailsVO"%>
<%
	 response.setHeader("Cache-Control","no-cache");
	 response.setHeader("Cache-Control","no-store");
	 response.setHeader("Pragma","no-cache");
	 response.setDateHeader ("Expires", 0);
	
	 if(session.getAttribute("SESSIONID") == null)
	     response.sendRedirect("/login.jsp");
 %> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <meta charset="utf-8">
 <meta http-equiv="X-UA-Compatible" content="IE=edge">
 <title>EET</title>
 <!-- Tell the browser to be responsive to screen width -->
 <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
</head>
<%
	UserDetailsVO userDetails = null;
	String role = null;
	if(session.getAttribute("userdetails")!=null)
	{
		userDetails = (UserDetailsVO) session.getAttribute("userdetails");
		role = userDetails.getRole_name();
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
									<h3>100</h3>
									<p>Total Entry</p>
								</div>
								<div class="icon">
									<i class="ion ion-archive"></i>
								</div>
							</div>
						</div>
						
						<div class="col-lg-3 col-xs-6">
				          <div class="small-box bg-green">
				            <div class="inner">
				              <h3>200</h3>
				              <p>Total Exits</p>
				            </div>
				            <div class="icon">
				              <i class="ion ion-exit"></i>
				            </div>
				          </div>
				        </div>
					</div>
				</section>
			</div>
		</div>
		
		<jsp:include page="/pages/common/footer.jsp"></jsp:include>
		
	</div>
	<script>
	
		/* $(document).ready(function() 
		{
			generateApplicationToken();
		}); */
		
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
	 	
	</script>
</body>
</html>