<%@page import="bt.gov.moh.eet.vo.UserDetailsVO"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%
	UserDetailsVO userDetails = null;
	String userName = null;
	String roleCode = null;
	String agentId = null;
	if(session.getAttribute("")!=null)
	{
		userDetails = (UserDetailsVO) session.getAttribute("");
		userName = userDetails.getFull_name().toUpperCase();
		roleCode = userDetails.getRole_name();
	}
%>
<style>
	#addForm .error { color: red; }
	#editForm .error { color: red; }
</style>

<section class="content-header">
  <h1>
       User Management
       <small>manage new and registered counter users</small>
     </h1>
     <ol class="breadcrumb">
       <li><a href="<%=request.getContextPath()%>/login.html?q=dashboard"><i class="fa fa-dashboard"></i> Home</a></li>
       <li class="active">User Management</li>
     </ol>
</section>

<section class="content">
	<div class="row">
		<div class="col-xs-12">
		  <div id="messageDiv" style="display: none;"></div>
          <div class="box box-primary">
            <div class="box-header">
              <h3 class="box-title">Registered User List</h3>
			  <div class="pull-right box-tools">
                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#user-add-modal" title="Add Agent">
                  <i class="fa fa-plus"></i>
				  &nbsp; Add New User
				</button>
              </div>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
			  <div class="table-responsive">
              <table id="example1" class="table table-bordered table-striped">
                <thead>
                <tr>
                  <th>Sl.No.</th>
                  <th>Agent</th>
                  <th>Login ID</th>
                  <th>User Name</th>
                  <th>Mobile No</th>
                  <th>Last Login Date</th>
				  <th></th>
                </tr>
                </thead>
                <tbody>
                	
                </tbody>
              </table>
			  </div>
            </div>
          </div>
        </div>
	</div>
</section>

<div id="user-add-modal" class="modal fade" tabindex="-1">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="blue bigger"><strong>Add New User</strong></h4>
			</div>
			 <div class="modal-body">
				<div class="row">
					<div class="col-xs-12"> 
						
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-flat btn-primary" name="search" onclick="addUser()">
						<i class="ace-icon fa fa-plus"></i>
						Add
					</button>
					<button class="btn btn-flat btn-danger" data-dismiss="modal">
						<i class="ace-icon fa fa-times"></i>
						Cancel
					</button>
				 </div>
		  </div>
	</div>
</div>

<script>

	var context = "<%=request.getContextPath()%>";

	$(function () {
		$("#example1").DataTable({
			dom: 'Bfrtip',
	        buttons: [
				{
				    extend: 'copyHtml5',
				    title: 'Total Registered Users',
				    text: '<i class="fa fa-files-o"></i>',
				    titleAttr: 'Copy'
				},
				{
				    extend: 'csvHtml5',
				    title: 'Total Registered Users',
				    text:      '<i class="fa fa-file-text-o"></i>',
	                titleAttr: 'CSV'
				},
				{
				    extend: 'excelHtml5',
				    title: 'Total Registered Users',
				    text:      '<i class="fa fa-file-excel-o"></i>',
	                titleAttr: 'Excel'
				},
				{
				    extend: 'pdfHtml5',
				    title: 'Total Registered Users',
				    text:      '<i class="fa fa-file-pdf-o"></i>',
	                titleAttr: 'PDF'
				}
	        ]
	    });
	});
	
</script>