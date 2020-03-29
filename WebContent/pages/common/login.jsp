<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>EET | Log In</title>
	<!-- Tell the browser to be responsive to screen width -->
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- Bootstrap 3.3.7 -->
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.min.css">
	<!-- Font Awesome -->
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/font-awesome.min.css">
	<!-- Ionicons -->
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/ionicons.min.css">
	<!-- Theme style -->
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/AdminLTE.min.css">
	<!-- Offline style css -->
  	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/offline-language-english.css">
	<!-- Offline style -->
	<link rel="stylesheet" href="<%=request.getContextPath() %>/css/offline-theme-slide.css">
	
	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
	
	<!-- Google Font -->
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>
<body class="hold-transition login-page">
<div class="login-box">
  <div class="login-logo">
  	<img src="<%=request.getContextPath() %>/img/app-logo.png" style="width:100px;height:99px"/><br>
    <a href="#" style="font-size:30px;"><b>Entry Exit Tracker</b></a>
  </div>
  <!-- /.login-logo -->
  <div class="login-box-body">
    <p class="login-box-msg">Sign in to start your session</p>
	<div id="msgDiv" style="display:none"></div>
    <form action="/Login" method="post">
      <div class="form-group has-feedback">
        <input type="text" class="form-control" placeholder="Username" id="username" name="username">
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input type="password" class="form-control" placeholder="Password" id="password" name="password">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="button" onclick="fnValidateLogin()" class="btn btn-primary btn-block btn-flat" id="login" >Sign In</button>
        </div>
        <!-- /.col -->
      </div>
    </form>
  </div>
  <!-- /.login-box-body -->
</div>
<!-- /.login-box -->

<!-- jQuery 3 -->
<script src="<%=request.getContextPath() %>/js/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
<!-- Offline js -->
<script src="<%=request.getContextPath()%>/js/offline.min.js"></script>

<%
	String msg = null;
	if(request.getAttribute("FAILURE") != null)
	{
		msg = (String)request.getAttribute("FAILURE");
	}
%>

<script>

	var failureMsg = "<%=msg%>";
	var displayMsg;
	
	$(document).ready(function() {
		$('#username').focus();
		
		if("failure" == failureMsg) {
			displayMsg = "<div class='alert alert-danger'>The credentials you provided cannot be determined to be authentic</div>";
		}
		else if("UNAUTHORIZED" == failureMsg){
			displayMsg = "<div class='alert alert-warning'>Unauthorized access, please login to access the specified task</div>";
		} 
		else if("logoutsuccess" == failureMsg){
			displayMsg = "<div class='alert alert-success'>You have logged out successfully</div>";
		}
		
		$('#msgDiv').html(displayMsg);
		$('#msgDiv').show();
		setTimeout('hideStatus()',5000);
	});
	
	function hideStatus() {
		$('#msgDiv').hide();
	}

	$(document).keypress(function(event) {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if(keycode == '13')
		{
			$('#login').click();	
		}
	});

	function fnValidateLogin() {
		var loginId = $('#username').val();
		var password = $('#password').val();
		
		if(loginId == "" && password == "") {
			$('#msgDiv').html("<div class='alert alert-warning'>Please enter username and password.</div>")
			$('#msgDiv').show();
			return false;
		}
		else if(loginId == "") {
			$('#msgDiv').html("<div class='alert alert-warning'>Please enter your username.</div>")
			$('#msgDiv').show();
			return false;
		}
		else if(password == "") {
			$('#msgDiv').html("<div class='alert alert-warning'>Please enter your password.</div>")
			$('#msgDiv').show();
			return false;
		} else {
			$('msgDiv').hide();
			var url = "<%=request.getContextPath()%>/Login";
			document.forms[0].action = url;
			document.forms[0].submit();
		}
	}

</script>

</body>
</html>