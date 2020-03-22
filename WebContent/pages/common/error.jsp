  <link rel="icon" href="<%=request.getContextPath() %>/images/favicon.ico">
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/admin/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/admin/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/admin/css/ionicons.min.css">
  <!-- DataTables -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/admin/css/dataTables.bootstrap.min.css">
  <link rel="stylesheet" href="https://cdn.datatables.net/buttons/1.2.4/css/buttons.dataTables.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/admin/css/AdminLTE.min.css">
  <!-- AdminLTE Skins -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/admin/css/_all-skins.min.css">
  <!-- Date Picker -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/admin/css/bootstrap-datepicker.min.css">
  <!-- Daterange picker -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/admin/css/daterangepicker.css">
  <!-- bootstrap wysihtml5 - text editor -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/admin/css/bootstrap3-wysihtml5.min.css">
  <!-- Google Font -->
  <link rel="stylesheet" href="<%=request.getContextPath() %>/admin/css/google-font.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
<div class="container">
<section class="content col-xs-12">
  <div class="error-page">
    <!-- <h2 class="headline text-red">500</h2> -->
    <% if(request.getAttribute("errorMsg") != null) { %>
    	<div class="error-content">
	      <h3><i class="fa fa-warning text-red"></i> Error.</h3>
	      <p>
	        <%=request.getAttribute("errorMsg") %>
	      </p>
	    </div>
    <% } else{ %>
    <div class="error-content">
      <h3><i class="fa fa-warning text-red"></i> Oops! Something went wrong.</h3>
      <p>
        We will work on fixing that right away.
        Meanwhile, you may contact the system administrator to clarify about the issue.
      </p>
    </div>
    <% } %>
  </div>
  <!-- /.error-page -->
</section>
</div>
<!-- jQuery 3 -->
<script src="<%=request.getContextPath() %>/admin/js/jquery.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="<%=request.getContextPath() %>/admin/js/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
  $.widget.bridge('uibutton', $.ui.button);
</script>
<!-- Bootstrap 3.3.7 -->
<script src="<%=request.getContextPath() %>/admin/js/bootstrap.min.js"></script>
<!-- DataTables -->
<script src="<%=request.getContextPath() %>/admin/js/jquery.dataTables.min.js"></script>
<script src="<%=request.getContextPath() %>/admin/js/dataTables.bootstrap.min.js"></script>
<script src="<%=request.getContextPath() %>/admin/js/dataTables.buttons.min.js"></script>
<script src="<%=request.getContextPath() %>/admin/js/buttons.flash.min.js"></script>
<script src="<%=request.getContextPath() %>/admin/js/jszip.min.js"></script>
<script src="<%=request.getContextPath() %>/admin/js/pdfmake.min.js"></script>
<script src="<%=request.getContextPath() %>/admin/js/vfs_fonts.js"></script>
<script src="<%=request.getContextPath() %>/admin/js/buttons.html5.min.js"></script>
<script src="<%=request.getContextPath() %>/admin/js/buttons.print.min.js"></script>
<!-- daterangepicker -->
<script src="<%=request.getContextPath() %>/admin/js/moment.min.js"></script>
<script src="<%=request.getContextPath() %>/admin/js/daterangepicker.js"></script>
<!-- datepicker -->
<script src="<%=request.getContextPath() %>/admin/js/bootstrap-datepicker.min.js"></script>
<!-- Bootstrap WYSIHTML5 -->
<script src="<%=request.getContextPath() %>/admin/js/bootstrap3-wysihtml5.all.min.js"></script>
<!-- AdminLTE App -->
<script src="<%=request.getContextPath() %>/admin/js/adminlte.min.js"></script>
<!-- jQuery Block UI -->
<script src="<%=request.getContextPath() %>/admin/js/jquery-blockUI.js"></script>
<!--Ajax form submit -->
<script src="<%=request.getContextPath() %>/admin/js/jquery.form.js"></script>
<script src="<%=request.getContextPath() %>/admin/js/JqueryAjaxFormSubmit.js"></script>

<script src="<%=request.getContextPath() %>/admin/js/fileinput.js"></script>
<script src="<%=request.getContextPath() %>/admin/js/jquery.fileupload.js"></script>
<!-- Application Common Util JS -->
<script src="<%=request.getContextPath() %>/admin/js/app/common-util.js"></script>