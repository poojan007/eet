<%@page import="bt.gov.moh.eet.vo.UserDetailsVO"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%
	UserDetailsVO userDetails = null;
	String userName = null;
	String roleCode = null;
	if(session.getAttribute("userdetails")!=null)
	{
		userDetails = (UserDetailsVO) session.getAttribute("userdetails");
		userName = userDetails.getFull_name().toUpperCase();
		roleCode = userDetails.getRoleCode();
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
                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#add-modal" title="Add Agent">
                  <i class="fa fa-plus"></i>
				  &nbsp; Add New User
				</button>
              </div>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
			  <div class="table-responsive">
              <table id="userTable" class="table table-bordered table-striped">
                <thead>
                <tr>
                  <th>Sl.No.</th>
                  <th>CID/Login ID</th>
                  <th>User Name</th>
                  <th>Mobile No</th>
                  <th>Designation</th>
                  <th>Working Address</th>
                  <th>User Type</th>
				  <th>Role</th>
				  <th>Gate</th>
				  <th>Dzongkhag</th>
				  <th></th>
                </tr>
                </thead>
                <tbody>
                	<logic:notEmpty name="userDetails">
						<logic:iterate id="user" name="userDetails" indexId="index">
							<tr>
								<%
									int i = index.intValue();
								%>
								<td><%=++i %></td>
								 
								<td><bean:write name="user" property="cid"/></td>
								<td><bean:write name="user" property="full_name"/></td>
								<td><bean:write name="user" property="mobile_number"/></td>
								<td><bean:write name="user" property="designation"/></td>
								<td><bean:write name="user" property="working_address"/></td>
								<td><bean:write name="user" property="user_type"/></td>
								<td><bean:write name="user" property="role_name"/></td>
								<td><bean:write name="user" property="gateName"/></td>
								<td><bean:write name="user" property="dzongkhagName"/></td>
								<td>
									<button type="button" class="btn btn-box-tool" data-toggle="tooltip" data-placement="top" title="Edit" onclick="getEditUsers('<bean:write name="user" property="cid"/>')"><i class="fa fa-pencil-square-o"></i></button>
									<button type="button" class="btn btn-box-tool" data-toggle="tooltip" data-placement="top" title="Delete" onclick="showDialog('<bean:write name="user" property="cid"/>')"><i class="fa fa-trash"></i></button>
									<button type="button" class="btn btn-box-tool" data-toggle="tooltip" data-placement="top" title="Reset" onclick="resetPassword('<bean:write name="user" property="cid"/>')"><i class="fa fa-key"></i></button>
								</td>
							</tr>
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

<html:form action="/user.html" styleClass="form-horizontal" method="post" styleId="masterFormBean">
	<div id="add-modal" class="modal fade" tabindex="-1">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="blue bigger"><strong>Add New User</strong></h4>
				</div>
				 <div class="modal-body">
				 <div class="col-lg-12">
		       		<div class="box box-primary">
		       			<div class="box-body">
		       				<div class="form-group">
		                   		<label class="control-label col-sm-2">CID&nbsp;<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<html:text property="cid" styleId="add_cid" styleClass="form-control" onchange="getCitizenDetails(this.value)"></html:text>
	                  			</div>
	                  			<label class="control-label col-sm-2">Full Name&nbsp;<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<html:text property="full_name" styleId="add_full_name" styleClass="form-control"></html:text>
	                  			</div>
	                   		</div>
	                   		<div class="form-group">
		                   		<label class="control-label col-sm-2">Mobile No&nbsp;<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<html:text property="mobile_number" styleId="add_mobile_number" styleClass="form-control"></html:text>
	                  			</div>
	                  			<label class="control-label col-sm-2">Designation&nbsp;<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<html:text property="designation" styleId="add_designation" styleClass="form-control"></html:text>
	                  			</div>
	                   		</div>
	                   		<div class="form-group">
		                   		<label class="control-label col-sm-2">Working Address&nbsp;<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<html:text property="working_address" styleId="add_working_address" styleClass="form-control"></html:text>
	                  			</div>
	                  			<label class="control-label col-sm-2">User Type&nbsp;<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<html:select property="user_type_id" styleClass="form-control" styleId="add_user_type">
	           								<html:option value="">--SELECT--</html:option>
	            							<html:optionsCollection name="userTypeList" label="headerName" value="headerId"/>
	            					</html:select>
	                  			</div>
	                   		</div>
	                   		<div class="form-group">
	                   			<%
	                   				if(roleCode.equalsIgnoreCase("SUPER_ADMIN")){
	                   			%>
		                   		<label class="control-label col-sm-2">Dzongkhag&nbsp;<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<html:select property="dzongkhag_id" styleClass="form-control" styleId="dzongkhag_id">
	       								<html:option value="">--SELECT--</html:option>
	           							<html:optionsCollection name="dzongkhagList" label="headerName" value="headerId"/>
	           						</html:select>
	                  			</div>
	                  			<%
	                   				} else if(roleCode.equalsIgnoreCase("ADMINISTRATOR")){
	                  			%>
	                  			<label class="control-label col-sm-2">Gate&nbsp;<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<html:select property="gate_id" styleClass="form-control" styleId="gate_id">
	       								<html:option value="">--SELECT--</html:option>
	           							<html:optionsCollection name="gateList" label="headerName" value="headerId"/>
	           						</html:select>
	                  			</div>
	                  			<%
	                   				}
	                  			%>
	                   		</div>
	                   	</div>
	                 </div>
	               </div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-flat btn-primary" name="add" onclick="addUser()">
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
	</html:form>
	<html:form action="/user.html" styleClass="form-horizontal" method="post" styleId="masterBean">
	<div id="edit-modal" class="modal fade" tabindex="-1">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="blue bigger"><strong>Edit User</strong></h4>
				</div>
			 <div class="modal-body">
			 	<div class="col-lg-12">
	       			<div class="box box-primary">
	       				<div class="box-body">
	       					<div class="form-group">
		                   		<label class="control-label col-sm-2">CID&nbsp;<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<html:text property="cidedit" styleId="edit_cid" styleClass="form-control" readonly="true"></html:text>
	                  			</div>
	                  			<label class="control-label col-sm-2">Full Name&nbsp;<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<html:text property="editfull_name" styleId="edit_full_name" styleClass="form-control" readonly="true"></html:text>
	                  			</div>
	                   		</div>
	                   		<div class="form-group">
		                   		<label class="control-label col-sm-2">Mobile No&nbsp;<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<html:text property="editmobile_number" styleId="edit_mobile_number" styleClass="form-control"></html:text>
	                  			</div>
	                  			<label class="control-label col-sm-2">Designation&nbsp;<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<html:text property="editdesignation" styleId="edit_designation" styleClass="form-control"></html:text>
	                  			</div>
	                   		</div>
	                   		<div class="form-group">
		                   		<label class="control-label col-sm-2">Working Address&nbsp;<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<html:text property="editworking_address" styleId="edit_working_address" styleClass="form-control"></html:text>
	                  			</div>
	                  			<label class="control-label col-sm-2">User Type&nbsp;<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<html:select property="edituser_type_id" styleClass="form-control" styleId="edit_user_type">
	          								<html:option value="">--SELECT--</html:option>
	           							<html:optionsCollection name="userTypeList" label="headerName" value="headerId"/>
	           						</html:select>
	                  			</div>
	                   		</div>
	                   		<div class="form-group">
	                   			<%
	                   				if(roleCode.equalsIgnoreCase("SUPER_ADMIN")){
	                   			%>
		                  			<label class="control-label col-sm-2">Dzongkhag&nbsp;<font color='red'>*</font></label>
		                   			<div class="col-sm-4">
		                   				<html:select property="edit_dzongkhag_id" styleClass="form-control" styleId="edit_dzongkhag_id">
		       								<html:option value="">--SELECT--</html:option>
		           							<html:optionsCollection name="dzongkhagList" label="headerName" value="headerId"/>
		           						</html:select>
		                  			</div>
	                  			<%
	                   				} else if(roleCode.equalsIgnoreCase("ADMINISTRATOR")){
	                  			%>
	                  				<label class="control-label col-sm-2">Gate&nbsp;<font color='red'>*</font></label>
		                   			<div class="col-sm-4">
		                   				<html:select property="editgate_id" styleClass="form-control" styleId="editgate_id">
		       								<html:option value="">--SELECT--</html:option>
		           							<html:optionsCollection name="gateList" label="headerName" value="headerId"/>
		           						</html:select>
		                  			</div>
	                  			<%
	                   				}
	                  			%>
	                   		</div>
	                   	</div>
	                 </div>
	               </div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-flat btn-primary" name="edit" onclick="editUser()">
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


<div id="delete-modal" class="modal fade" tabindex="-1">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="blue bigger">Confirmation</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12"> 
	         			<p>Are you sure you want to delete the selected user?</p>
					</div>
				</div>
			</div>
			<div class="modal-footer">
			<input type="hidden" id="userId" name="userId"/>
			<button type="button" class="btn btn-flat btn-primary" name="Delete" onclick="deleteUser()">
				<i class="ace-icon fa fa-trash"></i>
				Delete
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
		$("#userTable").DataTable({
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
	
	var roleCode = "<%=roleCode%>";
	$(document).ready(function() {
 		$('#masterFormBean').validate({
	 		  invalidHandler: function(form, validator) {
	            var errors = validator.numberOfInvalids();
	            if (errors) {                    
	                var firstInvalidElement = $(validator.errorList[0].element);
	                $('html,body').scrollTop(firstInvalidElement.offset().top);
	                firstInvalidElement.focus();
	            }
	        	  },
	 		  rules: {
	 			'add_cid':{
	 				  required:true
	 			  },
	 			'add_full_name':{
	 				  required:true
	 			  },
	 			  'add_mobile_number':{
	 				  required:true
	 			  },
	 			  'add_designation':{
	 				  required:true
	 			  },
	 			  'add_user_type':{
	 				  required:true
	 			  },
	 			  'dzongkhag_id':{
 				 	required: function () {
	                	return roleCode == "SUPER_ADMIN";
	            	}
	 			  },
	 			  'gate_id':{
	 				 required: function () {
	                	return roleCode == "ADMINISTRATOR";
            		}
	 			  }
	 		  },
	 		  messages: {
	 			  'add_cid':{
	 				  required:"Please provide CID"
	 			  },
	 			 'add_full_name':{
	 				  required:"Please provide full name"
	 			  },
	 			 'add_mobile_number':{
	 				  required:"Please provide mobile number"
	 			  },
	 			 'add_designation':{
	 				  required:"Please provide designation"
	 			  },
	 			 'add_user_type':{
	 				  required:"Please provide user type"
	 			  },
	 			 'dzongkhag_id':{
	 				  required:"Please provide dzongkhag"
	 			  },
	 			 'gate_id':{
	 				  required:"Please provide gate"
	 			  }
	 		  }
	 	  });
	 		
 		  $('#masterBean').validate({
	 		  invalidHandler: function(form, validator) {
	            var errors = validator.numberOfInvalids();
	            if (errors) {                    
	                var firstInvalidElement = $(validator.errorList[0].element);
	                $('html,body').scrollTop(firstInvalidElement.offset().top);
	                firstInvalidElement.focus();
	        	  }
	 		  	},
	        	  rules: {
		 			'edit_cid':{
		 				  required:true
		 			  },
		 			'edit_full_name':{
		 				  required:true
		 			  },
		 			  'edit_mobile_number':{
		 				  required:true
		 			  },
		 			  'edit_designation':{
		 				  required:true
		 			  },
		 			  'edit_user_type':{
		 				  required:true
		 			  },
		 			  'edit_dzongkhag_id':{
	 				 	required: function () {
		                	return roleCode == "SUPER_ADMIN";
		            	}
		 			  },
		 			  'editgate_id':{
		 				 required: function () {
		                	return roleCode == "ADMINISTRATOR";
	            		}
		 			  }
		 		  },
		 		  messages: {
		 			  'edit_cid':{
		 				  required:"Please provide CID"
		 			  },
		 			 'edit_full_name':{
		 				  required:"Please provide full name"
		 			  },
		 			 'edit_mobile_number':{
		 				  required:"Please provide mobile number"
		 			  },
		 			 'edit_designation':{
		 				  required:"Please provide designation"
		 			  },
		 			 'edit_user_type':{
		 				  required:"Please provide user type"
		 			  },
		 			 'edit_dzongkhag_id':{
		 				  required:"Please provide dzongkhag"
		 			  },
		 			 'editgate_id':{
		 				  required:"Please provide gate"
		 			  }
		 		  }
  			});
  		});
	
	function resetPassword(userId){
		$.ajax({
			type : "POST",
			url : context+ '/user.html?method=resetPassword&userId='+userId,
			data : $('form').serialize(),
			cache : false,
			dataType : "html",
			success : function(responseText) {
				$('#messageDiv').html(responseText);
				$('#messageDiv').show();
				setTimeout('reloadPage("MANAGE_USERS")', 3000);
			}
		});
	}
	
	function showDialog(id){
		$('#userId').val(id);
		$('#delete-modal').modal('show');
	}
	
	function deleteUser(){
		var userId = $('#userId').val();
		
		$.ajax({
			type : "POST",
			url : context+ '/user.html?method=deleteUser&userId='+userId,
			data : $('form').serialize(),
			cache : false,
			dataType : "html",
			success : function(responseText) {
				$('#messageDiv').html(responseText);
				$('#messageDiv').show();
				$('#delete-modal').modal('hide');
				setTimeout('reloadPage("MANAGE_USERS")', 3000);
			}
		});
	}
	
	function getCitizenDetails(cidNo){
		$.ajax({
	        async: false,
	        cache: false,
	        type: 'GET',
	        dataType : "xml",
	        url: '<%=request.getContextPath()%>/getCitizenDetails?idNo='+cidNo,
	        success: function(xml)
	        {
	            $(xml).find('xml-response').each(function()
	            { 
					var name = $(this).find('name').text();
					
					$('#add_full_name').val(name);
					$('#add_full_name').attr('readonly', true);
	            });
	        }, error: function(data, textStatus, errorThrown) {
				$('#add_full_name').val("");
				$('#add_full_name').attr('readonly', false);
	        }
	    });
	}
 		  
	function addUser(){
		$.ajax({
			type : "POST",
			url : context+'/user.html?method=addUser',
			data : $('form').serialize(),
			cache : false,
			dataType : "html",
			success : function(responseText) {
				$('#messageDiv').html(responseText);
				$('#messageDiv').show();
				$('#add-modal').modal('hide');
				setTimeout('reloadPage("MANAGE_USERS")', 3000);
			}
		});
	}
	
	function getEditUsers(cid)
	{
		var url;
		 url = "";
		 $.ajax({
	         async: false,
	         type: 'GET',
	         url: "<%=request.getContextPath()%>/user.html?method=getEditUserDetails&cid="+cid,
	         success: function(xml)
	         {
	             $(xml).find('xml-response').each(function()//we can parse through the xml tree using the find method
	             { 
	                 $('#edit_cid').val($(this).find('cid').text());
	                 $('#edit_full_name').val($(this).find('name').text());
	                 $('#edit_mobile_number').val($(this).find('mobile').text());
	                 $('#edit_designation').val($(this).find('designation').text());
	                 $('#edit_working_address').val($(this).find('address').text());
	                 $('#edit_user_type').val($(this).find('usertype').text());
	                 $('#edit_role').val($(this).find('rolename').text());
	                 $('#editgate_id').val($(this).find('gate_id').text());
	                 $('#edit_dzongkhag_id').val($(this).find('dzongkhag_id').text());
	                 $('#edit-modal').modal('show');
	             });
	         },
	         error: function(data, textStatus, errorThrown) {
	           
	         }
	     });  
	}
	
	function editUser(){
		$.ajax({
			type : "POST",
			url : context+ '/user.html?method=editUser',
			data : $('form').serialize(),
			cache : false,
			dataType : "html",
			success : function(responseText) {
				$('#messageDiv').html(responseText);
				$('#messageDiv').show();
				$('#edit-modal').modal('hide');
				setTimeout('reloadPage("MANAGE_USERS")', 3000);
			}
		});
	} 
	
	function reloadPage(pageLink){
		var url = "<%=request.getContextPath()%>/redirect.html?q="+pageLink
		$("#contentDisplayDiv").load(url);
		$('#contentDisplayDiv').show();
	}
	
</script>