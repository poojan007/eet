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
                  <th>CID</th>
                  <th>Login ID</th>
                  <th>User Name</th>
                  <th>Mobile No</th>
                  <th>Designation</th>
                  <th>Working Address</th>
				  <th>Role</th>
				  <th></th>
                </tr>
                </thead>
                <tbody>
                	<logic:notEmpty name="userDetails">
						<logic:iterate id="user" name="userDetails" indexId="index">
							<%
							int i = index.intValue();
							%>
							<tr><%=++i %></tr>
							 
							<td><bean:write name="user" property="cid"/></td>
							<td><bean:write name="user" property="full_name"/></td>
							<td><bean:write name="user" property="mobile_number"/></td>
							<td><bean:write name="user" property="designation"/></td>
							<td><bean:write name="user" property="working_address"/></td>
							<td><bean:write name="user" property="user_type"/></td>
							<td><bean:write name="user" property="role_name"/></td>
							<td>
							<button type="button" class="btn btn-primary btn-sm" onclick="populateEditForm('<bean:write name="user" property="cid"/>',
							'<bean:write name="user" property="password"/>','<bean:write name="user" property="full_name"/>','<bean:write name="user" property="mobile_number"/>',
							'<bean:write name="user" property="designation"/>','<bean:write name="user" property="working_address"/>',
							'<bean:write name="user" property="user_type"/>','<bean:write name="user" property="role_name"/>')">
                  			<i class="fa fa-plus"></i>
				  		
							</button>
							</td>
				</logic:iterate>
				</logic:notEmpty>
                </tbody>
              </table>
			  </div>
            </div>
          </div>
        </div>
	</div>
</section>
<html:form action="/user" styleClass="card form-horizontal" method="post" styleId="masterFormBean">
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
						<div class="col-6">
                            <div class="form-group row">
                                <label class="col-sm-3">CID: </label>
                                <div class="col-sm-9">
									<html:text property="cid" styleId="add_cid" styleClass="form-control form-control-sm"></html:text>
                                </div>
                            </div>

                        </div>
                        <div class="col-6">
                            <div class="form-group row">
                                <label class="col-sm-3">Full Name: </label>
                                <div class="col-sm-9">
									<html:text property="full_name" styleId="add_full_name" styleClass="form-control form-control-sm"></html:text>
                                </div>
                            </div>

                        </div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12"> 
                        
                        <div class="col-6">
                            <div class="form-group row">
                                <label class="col-sm-3">Mobile No: </label>
                                <div class="col-sm-9">
									<html:text property="mobile_number" styleId="add_mobile_number" styleClass="form-control form-control-sm"></html:text>
                                </div>
                            </div>

                        </div>
                        <div class="col-6">
                            <div class="form-group row">
                                <label class="col-sm-3">Designation: </label>
                                <div class="col-sm-9">
									<html:text property="designation" styleId="add_designation" styleClass="form-control form-control-sm"></html:text>
                                </div>
                            </div>

                        </div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12"> 
						
                        
                        <div class="col-6">
                            <div class="form-group row">
                                <label class="col-sm-3">Working Address: </label>
                                <div class="col-sm-9">
									<html:text property="working_address" styleId="add_working_address" styleClass="form-control form-control-sm"></html:text>
                                </div>
                            </div>

                        </div>
                        <div class="col-6">
                            <div class="form-group row">
                                <label class="col-sm-3">User Type: </label>
                                <div class="col-sm-9">
									<html:select property="user_type" styleClass="form-control" styleId="add_role">
           								<html:option value="">--SELECT--</html:option>
            							<html:optionsCollection name="userTypeList" label="headerName" value="headerId"/>
            						</html:select>
                                </div>
                            </div>

                        </div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12"> 
						
                        
                        <div class="col-6">
                            <div class="form-group row">
                                <label class="col-sm-3">Role: </label>
                                <div class="col-sm-9">
									<html:select property="role_name" styleClass="form-control" styleId="add_role">
           								<html:option value="">--SELECT--</html:option>
            							<html:optionsCollection name="roleList" label="headerName" value="headerId"/>
            						</html:select>
                                </div>
                            </div>

                        </div>
					</div>
				</div>
				
			</div>
			<div class="modal-footer">
					<button type="button" class="btn btn-flat btn-primary" onclick="addUser()">
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

<div id="user-edit-modal" class="modal fade" tabindex="-1">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="blue bigger"><strong>Edit User</strong></h4>
			</div>
			 <div class="modal-body">
				<div class="row">
					<div class="col-xs-12"> 
						<div class="col-6">
                            <div class="form-group row">
                                <label class="col-sm-3">CID: </label>
                                <div class="col-sm-9">
									<html:text property="cid" styleId="edit_cid" styleClass="form-control form-control-sm"></html:text>
                                </div>
                            </div>

                        </div>
                        <div class="col-6">
                            <div class="form-group row">
                                <label class="col-sm-3">Password: </label>
                                <div class="col-sm-9">
									<html:text property="password" styleId="password" styleClass="form-control form-control-sm"></html:text>
                                </div>
                            </div>

                        </div>
                        
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12"> 
						<div class="col-6">
                            <div class="form-group row">
                                <label class="col-sm-3">Full Name: </label>
                                <div class="col-sm-9">
									<html:text property="full_name" styleId="edit_full_name" styleClass="form-control form-control-sm"></html:text>
                                </div>
                            </div>

                        </div>
                        
						<div class="col-6">
                            <div class="form-group row">
                                <label class="col-sm-3">Mobile No: </label>
                                <div class="col-sm-9">
									<html:text property="mobile_number" styleId="edit_mobile_number" styleClass="form-control form-control-sm"></html:text>
                                </div>
                            </div>

                        </div>
                        
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<div class="col-6">
                            <div class="form-group row">
                                <label class="col-sm-3">Designation: </label>
                                <div class="col-sm-9">
									<html:text property="designation" styleId="edit_designation" styleClass="form-control form-control-sm"></html:text>
                                </div>
                            </div>

                        </div> 
						<div class="col-6">
                            <div class="form-group row">
                                <label class="col-sm-3">Working Address: </label>
                                <div class="col-sm-9">
									<html:text property="working_address" styleId="edit_working_address" styleClass="form-control form-control-sm"></html:text>
                                </div>
                            </div>

                        </div>
                        
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12"> 
						<div class="col-6">
                            <div class="form-group row">
                                <label class="col-sm-3">User Type: </label>
                                <div class="col-sm-9">
									<html:text property="user_type" styleId="edit_user_type" styleClass="form-control form-control-sm"></html:text>
                                </div>
                            </div>

                        </div>
						<div class="col-6">
                            <div class="form-group row">
                                <label class="col-sm-3">Role: </label>
                                <div class="col-sm-9">
									<html:text property="role_name" styleId="add_role" styleClass="form-control form-control-sm"></html:text>
                                </div>
                            </div>

                        </div>
                        
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-flat btn-primary" name="search" onclick="editUser()">
						<i class="ace-icon fa fa-plus"></i>
						Edit
					</button>
					<button class="btn btn-flat btn-danger" data-dismiss="modal">
						<i class="ace-icon fa fa-times"></i>
						Cancel
					</button>
				 </div>
		  </div>
	</div>
</div>
</html:form>
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
	
	function addUser(){
		alert("ok");
		var add_cid = $("#add_cid").val();
		var add_full_name = $("#add_full_name").val();
		var add_mobile_number=$("#add_mobile_number").val();
		var add_designation = $("#add_designation").val();
		var add_working_address = $("#add_working_address").val();
		var add_user_type=$("#add_user_type").val();
		var add_role=$("#add_role").val();
		var password = $("#password").val();
		$.ajax({
					type : "POST",
					url : context+ '/user.html?method=saveUser&add_cid='+add_cid+'&add_full_name='+add_full_name+'&add_mobile_number='+add_mobile_number+'&add_designation='+add_designation+'&add_working_address='+add_working_address+'&add_user_type='+add_user_type+'&add_role='+add_role+'&password='+password,
					data : $('masterFormBean').serialize(),
					cache : false,
					dataType : "html",
					success : function(responseText) {
						$("#categoryDiv").html(responseText);
						$("#categoryDiv").show();
						$('#SubmitMsgDiv').show();
						setTimeout('hideStatus("SubmitMsgDiv")',2000);
					}
				})
	}
	function populateEditForm(cid,password, full_name, mobile_number, designation,working_address)
			{
				
				$("#edit_cid").val(cid);
				$("#password").val(password);
				$("#edit_full_name").val(full_name);
				$("#edit_mobile_number").val(mobile_number);
				$("#edit_designation").val(designation);
				$("#edit_working_address").val(working_address);
				$("#edit_user_type").val(user_type);
				$("#edit_role").val(role_name);
				
				$('#user-edit-modal').modal('show');
				
			}
			
	editAvgTime(){
		var edit_cid = $("#edit_cid").val(cid);
		var edit_full_name = $("#edit_full_name").val(full_name);
		var edit_mobile_number = $("#edit_mobile_number").val(mobile_number);
		var edit_designation = $("#edit_designation").val(designation);
		var edit_working_address = $("#edit_working_address").val(working_address);
		var edit_user_type = $("#edit_user_type").val(user_type);
		var edit_role = $("#edit_role").val(role_name);
		var password = 	$("#password").val(role_name);	
				$.ajax({
					type : "POST",
					url : context+ '/user.html?method=editUser&edit_cid='+edit_cid+'&edit_full_name='+edit_full_name+'&edit_mobile_number='+edit_mobile_number+'&edit_designation='+edit_designation+'&edit_working_address='+edit_working_address+'&edit_user_type='+edit_user_type+'&edit_role='+edit_role+'&password='+password,
					data : $('masterFormBean').serialize(),
					cache : false,
					dataType : "html",
					success : function(responseText) {
						$("#categoryDiv").html(responseText);
						$("#categoryDiv").show();
						$('#editSubmitMsgDiv').show();
						setTimeout('hideStatus("editSubmitMsgDiv")',2000);
					}
				});
			}
	
</script>