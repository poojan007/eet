 <%@page import="bt.gov.moh.eet.vo.UserDetailsVO"%>
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/ionicons.min.css">
  <!-- DataTables -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/dataTables.bootstrap.min.css">
  <link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.2.4/css/buttons.dataTables.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/app.css">
  <!-- AdminLTE Skins -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/_all-skins.min.css">
  <!-- Date Picker -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap-datepicker.min.css">
  <!-- Select 2 -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/select2.min.css">
  <!-- Daterange picker -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/daterangepicker.css">
  <!-- bootstrap wysihtml5 - text editor -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap3-wysihtml5.min.css">
  <!-- Google Font -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/google-font.css">
  <!-- Sweet Alert -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/sweetalert.css">
  <!-- Summer Note -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/css/summernote.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
  
 <%
	UserDetailsVO userDetails = null;
	String userName = null;
	String role = null;
	String flag = null;
	String agencyName = null;
	int agencyId = 0;
	if(session.getAttribute("userdetails")!=null)
	{
		userDetails = (UserDetailsVO) session.getAttribute("userdetails");
		userName = userDetails.getFull_name().toUpperCase();
		role = userDetails.getRole_name();
	}
	
%>

<style type="text/css">
	#idletimeout { background:#3C8DBC; border:0px solid #969696; color:#fff; font-family:arial, sans-serif; text-align:center; font-size:12px; padding:10px; position:relative; top:0px; left:0; right:0; z-index:100000; display:none; }
	#idletimeout a { color:#fff; font-weight:bold }
	#idletimeout span { font-weight:bold }
</style>
  
  <header class="main-header">
  	<div id="idletimeout">
		<strong>You will be logged off in <span><!-- countdown place holder --></span>&nbsp;seconds due to inactivity.</strong>
		<a id="idletimeout-resume" href="#" class="btn btn-success">Stay</a>
		&nbsp;
		<button class="btn btn-warning" onclick="logout()">Leave</button>
	</div>
    <!-- Logo -->
    <a href="<%=request.getContextPath()%>/login.html?q=dashboard" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>E</b>ET</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>Entry Exit Tracker</b></span>
    </a>
    <!-- Header Navbar: style can be found in header.less -->
    <nav class="navbar navbar-static-top">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="push-menu" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>

      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <img src="img/user2-160x160.jpg" class="user-image" alt="User Image">
              <span class="hidden-xs"><%=userName %></span>
            </a>
            <ul class="dropdown-menu">
              <!-- User image -->
              <li class="user-header">
                <img src="<%=request.getContextPath() %>/img/user2-160x160.jpg" class="img-circle" alt="User Image">
	                <p>
	                  <%=userName %> - <%=role %>
	                </p>
              </li>
              <!-- Menu Footer-->
              <li class="user-footer">
                <div class="pull-right">
                  <a href="<%=request.getContextPath() %>/logout.html" class="btn btn-default btn-flat logout"><i class="fa fa-sign-out"></i>&nbsp;Sign out</a>
                </div>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </nav>
  </header>
  
  <script>
  
  </script>