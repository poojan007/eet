<%@page import="bt.gov.moh.eet.dto.UserDTO"%>
<%@page import="bt.gov.moh.eet.vo.UserDetailsVO"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%
	UserDetailsVO userDetails = null;
	String userName = null;
	String role = null;
	String gateName = null;
	String dzongkhagName = null;
	String roleCode = null;
	String uid = null;
	if(session.getAttribute("userdetails")!=null)
	{
		userDetails = (UserDetailsVO) session.getAttribute("userdetails");
		userName = userDetails.getFull_name().toUpperCase();
		role = userDetails.getRole_name();
		gateName = userDetails.getGateName();
		dzongkhagName = userDetails.getDzongkhagName();
		roleCode = userDetails.getRoleCode();
		uid = userDetails.getCid();
	}
	
	UserDTO dto = (UserDTO) request.getAttribute("loggedInUser");
%>	
<style>
	#profileForm .error { color: red; }
	#changePasswordForm .error { color: red; }
</style>

<section class="content-header">
  <h1>
       User Profile
       <small>manage your profile & login credentials</small>
     </h1>
     <ol class="breadcrumb">
       <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
       <li class="active">User Profile</li>
     </ol>
</section>

<section class="content">
	<div class="row">
		<div class="col-md-3">
		
			<div class="box box-primary">
				<div class="box-body box-profile">
					<img class="profile-user-img img-responsive img-circle" src="<%=request.getContextPath() %>/img/user2-160x160.jpg" alt="User Profile Picture"/>
					<h3 class="profile-username text-center"><%=userName %></h3>
					<p class="text-muted text-center"><%=role.replaceAll("_", " ")%></p>
					<%
	                	if(roleCode.equalsIgnoreCase("DATA_MANAGER")){
	                %>
	                	<p class="text-muted text-center"><%=gateName %></p>
	                <%
	                	} else {
	                %>
	                	<p class="text-muted text-center"><%=dzongkhagName %></p>
	                <%
	                	}
	                %>
				</div>
			</div>
		
		</div>
		
		<div class="col-md-9">
			<div class="nav-tabs-custom">
				<ul class="nav nav-tabs">
					<li class="active"><a href="#settings" data-toggle="tab">Update Profile</a></li>
					<li><a href="#login" data-toggle="tab">Change Password</a></li>
				</ul>
				<div class="tab-content">
					<div class="active tab-pane" id="settings">
		                <form class="form-horizontal" action="<%=request.getContextPath() %>/user.html" method="post" role="form" id="profileForm">
		                	<div class="form-group">
		                		<label for="name" class="col-sm-4">CID</label>
			                    <div class="col-sm-8">
			                      <input type="text" class="form-control" id="cid" name="cid" placeholder="CID Number" readonly="readonly" value="<%=userDetails.getCid()%>">
			                    </div>
		                	</div>
		                  	<div class="form-group">
			                    <label for="name" class="col-sm-4">Name</label>
			                    <div class="col-sm-8">
			                      <input type="text" class="form-control" id="name" name="name" placeholder="Full Name" readonly="readonly" value="<%=userName%>">
			                    </div>
		                  	</div>
		                  	<div class="form-group">
			                    <label for="mobileNo" class="col-sm-4">User Type</label>
			                    <div class="col-sm-8">
			                      <input type="text" class="form-control" readonly="readonly" placeholder="Roles" value="<%=userDetails.getUser_type()%>">
			                    </div>
		                  	</div>
		                  	<div class="form-group">
			                    <label for="mobileNo" class="col-sm-4">Role</label>
			                    <div class="col-sm-8">
			                      <input type="text" class="form-control" readonly="readonly" placeholder="Roles" value="<%=role%>">
			                    </div>
		                  	</div>
		                  	<div class="form-group">
			                    <label for="mobileNo" class="col-sm-4">Dzongkhag</label>
			                    <div class="col-sm-8">
			                      <input type="text" class="form-control" readonly="readonly" placeholder="Roles" value="<%=userDetails.getDzongkhagName()%>">
			                    </div>
		                  	</div>
		                  	<%
		                  		if(userDetails.getRoleCode().equalsIgnoreCase("DATA_MANAGER")){
		                  	%>
			                  	<div class="form-group">
				                    <label for="mobileNo" class="col-sm-4">Gate</label>
				                    <div class="col-sm-8">
				                      <input type="text" class="form-control" readonly="readonly" placeholder="Roles" value="<%=userDetails.getGateName()%>">
				                    </div>
			                  	</div>
			                <%
		                  		}
			                %>
		                  	<div class="form-group">
			                    <label for="mobileNo" class="col-sm-4 ">Mobile Number</label>
			                    <div class="col-sm-8">
			                      <input type="number" class="form-control" id="mobileNo" name="mobileNo" placeholder="Mobile Number" maxlength="8" value="<%=dto.getMobile_number()%>">
			                    </div>
		                  	</div>
		                  	<div class="form-group">
			                    <label for="mobileNo" class="col-sm-4 ">Designation</label>
			                    <div class="col-sm-8">
			                      <input type="text" class="form-control" id="designation" name="designation" placeholder="Mobile Number" value="<%=dto.getDesignation()%>">
			                    </div>
		                  	</div>
		                  	<div class="form-group">
			                    <label for="mobileNo" class="col-sm-4 ">Working Address</label>
			                    <div class="col-sm-8">
			                      <input type="text" class="form-control" id="workingAddress" name="workingAddress" placeholder="Mobile Number" value="<%=dto.getWorking_address()%>">
			                    </div>
		                  	</div>
		                  	<div class="form-group">
		                  	<span id="messageDiv" style="display:none;"></span>
		                    <div class="col-sm-offset-2 col-sm-10">
		                      <input type="hidden" value="<%=uid %>" id="uid" name="id"/>
		                      <input type="hidden" value="PROFILE_UPDATE" id="requestType" name="requestType"/>
		                      <button type="button" class="btn btn-primary pull-right" onclick="updateProfile()">
		                      	<i class="fa fa-edit"></i>&nbsp;Update
		                      </button>
	                    	 </div>
		                  </div>
		                </form>
		              </div>
		              <div class="tab-pane" id="login">
		                <ul class="list-group">
		                	<li class="list-group-item">
		                		UID
		                		<span class="pull-right"><strong><%=uid %></strong></span>
		                	</li>
		                	<li class="list-group-item">
		         				Password
		         				<span class="pull-right">
		         				<button data-toggle="modal" href="#" data-target="#change-password-modal" class="btn btn-primary btn-xs">
									<i class="fa fa-key"></i>
									Change Password
								</button>
								</span>
		         			</li>
		                </ul>
		              </div>
				</div>
			</div>
		</div>
	</div>
</section>

<div id="change-password-modal" class="modal" tabindex="-1">
	<form class="form-horizontal" action="<%=request.getContextPath() %>/user.html" method="post" role="form" id="changePasswordForm">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="blue bigger"><i class="fa fa-key 3x"></i>&nbsp;<strong>Change Password</strong></h4>
				</div>
	
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-12 col-sm-12">
							<div id="changePasswordMsgDiv" style="display: none"></div>
							<div class="form-group">
								<label class="control-label col-sm-3">Login Id:</label>
								<div class="control-label col-sm-9">
									<label><%=uid %></label>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-3">Current Password:</label>
								<div class="col-sm-9">
									<input type="password" class="form-control" id="cpassword" name="cpassword" placeholder="Current Password"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-3">New Password:</label>
								<div class="col-sm-9">
									<input type="password" class="form-control" id="npassword" name="npassword" placeholder="New Password"/>
								</div>
							</div>
							<div class="form-group">
								<label class="control-label col-sm-3">Confirm Password:</label>
								<div class="col-sm-9">
									<input type="password" class="form-control" id="npasswordconfirm" name="npasswordconfirm" placeholder="Confirm Password"/>
								</div>
							</div>
						</div>
					</div>
				</div>
	
				<div class="modal-footer">
					<button class="btn btn-danger" data-dismiss="modal">
						<i class="fa fa-times"></i>
						Close
					</button>
	
					<button type="button" class="btn btn-primary" onclick="changePassword()">
						<i class="fa fa-check"></i>
						Change
					</button>
				</div>
			</div>
		</div>
	</form>
</div>

<script>

	jQuery.validator.addMethod("notEqualTo",
		function(value, element, param) {
		    var notEqual = true;
		    value = $.trim(value);
		    for (i = 0; i < param.length; i++) {
		        if (value == $.trim($(param[i]).val())) { notEqual = false; }
		    }
		    return this.optional(element) || notEqual;
		},
		"Please enter a diferent value."
	);

	$(document).ready(function(){
		$('#profileForm').validate({
			invalidHandler: function(form, validator){
				var errors = validator.numberOfInvalids();
				if(errors){
					var firstInvalidElement = $(validator.errorList[0].element);
					$('html,body').scrollTop(firstInvalidElement.offset().top);
					firstInvalidElement.focus();
				}
			},
			rules: {
				'mobileNo': {
					required:true,
					number:true,
					maxlength:10,
					minlength:8
				},
				'designation':{
					required: true
				},
				'workingAddress':{
					required: true
				}
			},
			messages: {
				'mobileNo': {
					required: "Please provide your mobile number",
					number: "Only numbers allowed",
					maxlength: "Only 10 digits allowed",
					minlength: "Mobile number should be 8 digits long"
				},
				'designation':{
					required: "Please provide your designation"
				},
				'workingAddress':{
					required: "Please provide your working address"
				}
			}
		});
		
		$('#changePasswordForm').validate({
			invalidHandler: function(form, validator){
				var errors = validator.numberOfInvalids();
				if(errors){
					var firstInvalidElement = $(validator.errorList[0].element);
					$('html,body').scrollTop(firstInvalidElement.offset().top);
					firstInvalidElement.focus();
				}
			},
			rules: {
				'cpassword': {
					required: true,
					remote: {
                        type: "POST", 
                        url: "<%=request.getContextPath()%>/profileservlet?q=validateCurrentPassword&userId=<%=uid %>",
                        cache: false,
						onchange: false	
					}
				},
				'npassword': {
					required:true,
					notEqualTo: ['#cpassword']
				},
				'npasswordconfirm': {
					required:true,
					equalTo: "#npassword"
				}
			},
			messages: {
				'cpassword': {
					required: "Please provide your current password",
					remote: "Current password is incorrect"
				},
				'npassword': {
					required: "Please provide a new password",
					notEqualTo: "New Password cannot be same as current password"
				},
				'npasswordconfirm': {
					required: "Please enter your new password again",
					equalTo: "Confirmation password should be same as new password"
				}
			}
		});
	});

	function updateProfile(){
		if($('#profileForm').valid()) {
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
			
			var url= '<%=request.getContextPath()%>/user.html?method=updateProfile';
			var options = {target:'#messageDiv',url:url,type:'POST',data: $("#profileForm").serialize()};
			$("#profileForm").ajaxSubmit(options);
			$('#messageDiv').show();
			setTimeout($.unblockUI, 1000);
			setTimeout('reloadProfilePage()', 2000);
		}
	}
	
	function reloadProfilePage(){
		var url = "<%=request.getContextPath()%>/redirect.html?q=profile"
		$("#contentDisplayDiv").load(url);
		$('#contentDisplayDiv').show();
	}
	
	function changePassword(){
		if($('#changePasswordForm').valid()) {
			
			var cpassword = $('#cpassword').val();
			var npassword = $('#npassword').val();
			
			$.ajax
			({
				type : "POST",
				url : "<%=request.getContextPath()%>/profileservlet?q=updateUserPassword&userId=<%=uid %>&npassword="+npassword,
				data : $('form').serialize(),
				cache : false,
				dataType : "html",
				success : function(responseText) 
				{
					var message;
					
					if(responseText == "SUCCESS")
						message = '<div class="alert alert-success">Password updated successfully</div>';
					else
						message = '<div class="alert alert-danger">Password update failed, please try again later</div>';		
							
					$("#changePasswordMsgDiv").html(message);
					$("#changePasswordMsgDiv").show();
				}
			});
		}
	}
</script>