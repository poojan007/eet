<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%
	String masterType = (String) request.getAttribute("masterType");
	masterType = masterType.replaceAll("_", " ");
%>
<style>
	#addForm .error { color: red; }
	#editForm .error { color: red; }
</style>
<section class="content-header">
	<h1>
		<%=masterType %>
	</h1>
	<ol class="breadcrumb">
		<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">Master Management</li>
	</ol>
</section>

<section class="content">

	<div class="row">
		<div class="col-xs-12">
			<div id="messageDiv" style="display: none;"></div>
			
			<logic:equal value="MASTER_MANAGEMENT_GATES" name="masterType">
				<input type="hidden" value="Total Registered Gates" id="exportFileName"/>
				<div class="box box-primary">
	            	<div class="box-header">
	              		<h3 class="box-title">&nbsp;</h3>
			  			<div class="pull-right box-tools">
			                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#add-gate-modal" title="Add New Gate">
			                  <i class="fa fa-plus"></i>
							  &nbsp; Add New Gate
							</button>
	              		</div>
	            	</div>
	            	<div class="box-body">
	            		<div class="table-responsive">
	            			<table id="master-table" class="table table-bordered table-striped">
	                			<thead>
	                				<tr>
					                	<th>Sl.No.</th>
					                	<th>Gate Name</th>
										<th></th>
	                				</tr>
	                			</thead>
	                			<tbody>
				            		<logic:iterate id="master" name="masterList" type="bt.gov.moh.eet.dto.MasterDTO" indexId="index">
				                		<% int a = index.intValue(); %>
				                		<tr>
				                			<td><%=++a %></td>
				                			<td><bean:write name="master" property="gateName"/></td>
				                			<td align="center">
												<div class="box-tools pull-right">
													<button type="button" class="btn btn-box-tool" data-toggle="tooltip" data-placement="top" title="Edit" onclick="populateEditForm('<bean:write name="master" property="gateId"/>','<bean:write name="master" property="gateName"/>')"><i class="fa fa-pencil-square-o"></i></button>
													<button type="button" class="btn btn-box-tool" data-toggle="tooltip" data-placement="top" title="Edit" onclick="showConfirmDialog('<bean:write name="master" property="gateId"/>','MASTER_MANAGEMENT_GATES')"><i class="fa fa-trash"></i></button>
												</div>
											</td>
				                		</tr>
			                		</logic:iterate>
			                	</tbody>
			                </table>
	            		</div>
	            	</div>
	            </div>
	            
	            <div id="add-gate-modal" class="modal fade" tabindex="-1">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="blue bigger">Add New Gate</h4>
							</div>
		 					<div class="modal-body">
		 						<div class="row">
									<div class="col-xs-12"> 
		                   				<form class="form-horizontal" role="form" id="addForm">
		                   					<div class="form-group">
		                   						<label class="control-label col-sm-2">Gate Name<font color='red'>*</font></label>
		                   						<div class="col-sm-4">
		                   							<input type="text" class="form-control" id="gateName" name="gateName" placeholder="Gate Name"/>
		                   						</div>
		                   					</div>
		                   				</form>
			 						</div>
			 					</div>
			 				</div>
			 				<div class="modal-footer">
								<button type="button" class="btn btn-flat btn-primary" name="add" onclick="addGate()">
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
		 		
		 		<div id="edit-gate-modal" class="modal fade" tabindex="-1">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="blue bigger">Update Gate Details</h4>
							</div>
		 					<div class="modal-body">
		 						<div class="row">
									<div class="col-xs-12"> 
		                   				<form class="form-horizontal" role="form" id="editForm">
		                   					<div class="form-group">
		                   						<label class="control-label col-sm-2">Gate Name<font color='red'>*</font></label>
		                   						<div class="col-sm-4">
		                   							<input type="text" class="form-control" id="editGateName" name="editGateName" placeholder="Gate Name"/>
		                   						</div>
		                   					</div>
		                   				</form>
			 						</div>
			 					</div>
			 				</div>
			 				<div class="modal-footer">
			 					<input type="hidden" id="gateId"/>
								<button type="button" class="btn btn-flat btn-primary" name="edit" onclick="updateGate()">
									<i class="ace-icon fa fa-pencil-square-o"></i>
									Update
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
		 		
			 		$(document).ready(function() {
		 		 		$('#addForm').validate({
			 		 		  invalidHandler: function(form, validator) {
			 		            var errors = validator.numberOfInvalids();
			 		            if (errors) {                    
			 		                var firstInvalidElement = $(validator.errorList[0].element);
			 		                $('html,body').scrollTop(firstInvalidElement.offset().top);
			 		                firstInvalidElement.focus();
			 		            }
			 		        	  },
			 		 		  rules: {
			 		 			  'gateName':{
			 		 				  required:true
			 		 			  }
			 		 		  },
			 		 		  messages: {
			 		 			  'gateName':{
			 		 				  required:"Please provide gate name"
			 		 			  }
			 		 		  }
			 		 	  });
		 		 		
		 		 		  $('#editForm').validate({
			 		 		  invalidHandler: function(form, validator) {
			 		            var errors = validator.numberOfInvalids();
			 		            if (errors) {                    
			 		                var firstInvalidElement = $(validator.errorList[0].element);
			 		                $('html,body').scrollTop(firstInvalidElement.offset().top);
			 		                firstInvalidElement.focus();
			 		            }
			 		        	  },
			 		 		  rules: {
			 		 			  'editGateName':{
			 		 				  required:true
			 		 			  }
			 		 		  },
			 		 		  messages: {
			 		 			  'editGateName':{
			 		 				  required:"Please provide a gate name"
			 		 			  }
			 		 		  }
			 		 	  });
		 		 	});
		 		 	
		 		 	function addGate(){
		 		 		if($('#addForm').valid()) 
			 		 	{
		 		 			var gateName = $('#gateName').val();
		 		 			
		 		 		  	$.ajax
			 		 			({
			 		 				type : "POST",
			 		 				url : "<%=request.getContextPath()%>/master.html?method=addMaster&masterType=MANAGE_GATES&gateName="+gateName,
			 		 				data : $('form').serialize(),
			 		 				cache : false,
			 		 				dataType : "html",
			 		 				success : function(responseText) 
			 		 				{
			 		 					$("#messageDiv").html(responseText);
			 		 					$("#messageDiv").show();
			 		 					$('#add-gate-modal').modal('hide');
			 		 					setTimeout('reloadPage("MASTER_MANAGEMENT_GATES")', 3000);
			 		 				}
			 		 			});
			 		 	 }
		 		 	}
		 		 	
		 		 	function populateEditForm(gateId, gateName){
		 		 		$('#gateId').val(gateId);
		 		 		$('#editGateName').val(gateName);
		 		 		$('#edit-gate-modal').modal('show');
		 		 	}
		 		 	
		 		 	function updateGate(){
		 		 		if($('#editForm').valid()) 
			 		 	{
		 		 			var gateId = $('#gateId').val();
		 		 			var gateName = $('#editGateName').val();
		 		 			
		 		 		  	$.ajax
		 		 			({
		 		 				type : "POST",
		 		 				url : "<%=request.getContextPath()%>/master.html?method=updateMaster&masterType=MANAGE_GATES&gateName="+gateName+"&gateId="+gateId,
		 		 				data : $('form').serialize(),
		 		 				cache : false,
		 		 				dataType : "html",
		 		 				success : function(responseText) 
		 		 				{
		 		 					$("#messageDiv").html(responseText);
		 		 					$("#messageDiv").show();
		 		 					$('#edit-gate-modal').modal('hide');
		 		 					setTimeout('reloadPage("MASTER_MANAGEMENT_GATES")', 3000);
		 		 				}
		 		 			});
			 		 	 }
	 		 		}
		 		
		 		</script>
	            
			</logic:equal>
			
			<logic:equal value="MASTER_MANAGEMENT_IDENTIFICATION_TYPES" name="masterType">
				<input type="hidden" value="List of identification types" id="exportFileName"/>
				<div class="box box-primary">
	            	<div class="box-header">
	              		<h3 class="box-title">&nbsp;</h3>
			  			<div class="pull-right box-tools">
			                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#add-modal" title="Add New Identification Type">
			                  <i class="fa fa-plus"></i>
							  &nbsp; Add New Identification Type
							</button>
	              		</div>
	            	</div>
	            	<div class="box-body">
	            		<div class="table-responsive">
	            			<table id="master-table" class="table table-bordered table-striped">
	                			<thead>
	                				<tr>
					                	<th>Sl.No.</th>
					                	<th>Identification Type</th>
										<th></th>
	                				</tr>
	                			</thead>
	                			<tbody>
				            		<logic:iterate id="master" name="masterList" type="bt.gov.moh.eet.dto.MasterDTO" indexId="index">
				                		<% int a = index.intValue(); %>
				                		<tr>
				                			<td><%=++a %></td>
				                			<td><bean:write name="master" property="identificationType"/></td>
				                			<td align="center">
												<div class="box-tools pull-right">
													<button type="button" class="btn btn-box-tool" data-toggle="tooltip" data-placement="top" title="Edit" onclick="populateEditForm('<bean:write name="master" property="identificationTypeId"/>','<bean:write name="master" property="identificationType"/>')"><i class="fa fa-pencil-square-o"></i></button>
													<button type="button" class="btn btn-box-tool" data-toggle="tooltip" data-placement="top" title="Edit" onclick="showConfirmDialog('<bean:write name="master" property="identificationTypeId"/>','MASTER_MANAGEMENT_IDENTIFICATION_TYPES')"><i class="fa fa-trash"></i></button>
												</div>
											</td>
				                		</tr>
			                		</logic:iterate>
			                	</tbody>
			                </table>
	            		</div>
	            	</div>
	            </div>
	            
	            <div id="add-modal" class="modal fade" tabindex="-1">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="blue bigger">Add Identification Type</h4>
							</div>
		 					<div class="modal-body">
		 						<div class="row">
									<div class="col-xs-12"> 
		                   				<form class="form-horizontal" role="form" id="addForm">
		                   					<div class="form-group">
		                   						<label class="control-label col-sm-6">Identification Type<font color='red'>*</font></label>
		                   						<div class="col-sm-6">
		                   							<input type="text" class="form-control" id="identificationType" name="identificationType" placeholder="Identification Type"/>
		                   						</div>
		                   					</div>
		                   				</form>
			 						</div>
			 					</div>
			 				</div>
			 				<div class="modal-footer">
								<button type="button" class="btn btn-flat btn-primary" name="add" onclick="addMaster()">
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
		 		
		 		<div id="edit-modal" class="modal fade" tabindex="-1">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="blue bigger">Update Identification Type Details</h4>
							</div>
		 					<div class="modal-body">
		 						<div class="row">
									<div class="col-xs-12"> 
		                   				<form class="form-horizontal" role="form" id="editForm">
		                   					<div class="form-group">
		                   						<label class="control-label col-sm-6">Identification Type<font color='red'>*</font></label>
		                   						<div class="col-sm-6">
		                   							<input type="text" class="form-control" id="editIdentificationType" name="editIdentificationType" placeholder="Identification Type"/>
		                   						</div>
		                   					</div>
		                   				</form>
			 						</div>
			 					</div>
			 				</div>
			 				<div class="modal-footer">
			 					<input type="hidden" id="id"/>
								<button type="button" class="btn btn-flat btn-primary" name="edit" onclick="updateMaster()">
									<i class="ace-icon fa fa-pencil-square-o"></i>
									Update
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
		 		
			 		$(document).ready(function() {
		 		 		$('#addForm').validate({
			 		 		  invalidHandler: function(form, validator) {
			 		            var errors = validator.numberOfInvalids();
			 		            if (errors) {                    
			 		                var firstInvalidElement = $(validator.errorList[0].element);
			 		                $('html,body').scrollTop(firstInvalidElement.offset().top);
			 		                firstInvalidElement.focus();
			 		            }
			 		        	  },
			 		 		  rules: {
			 		 			  'identificationType':{
			 		 				  required:true
			 		 			  }
			 		 		  },
			 		 		  messages: {
			 		 			  'identificationType':{
			 		 				  required:"Please provide identification type"
			 		 			  }
			 		 		  }
			 		 	  });
		 		 		
		 		 		  $('#editForm').validate({
			 		 		  invalidHandler: function(form, validator) {
			 		            var errors = validator.numberOfInvalids();
			 		            if (errors) {                    
			 		                var firstInvalidElement = $(validator.errorList[0].element);
			 		                $('html,body').scrollTop(firstInvalidElement.offset().top);
			 		                firstInvalidElement.focus();
			 		            }
			 		        	  },
			 		 		  rules: {
			 		 			  'editIdentificationType':{
			 		 				  required:true
			 		 			  }
			 		 		  },
			 		 		  messages: {
			 		 			  'editIdentificationType':{
			 		 				  required:"Please provide a identification type"
			 		 			  }
			 		 		  }
			 		 	  });
		 		 	});
		 		 	
		 		 	function addMaster(){
		 		 		if($('#addForm').valid()) 
			 		 	{
		 		 			var name = $('#identificationType').val();
		 		 			
		 		 		  	$.ajax
			 		 			({
			 		 				type : "POST",
			 		 				url : "<%=request.getContextPath()%>/master.html?method=addMaster&masterType=MASTER_MANAGEMENT_IDENTIFICATION_TYPES&name="+name,
			 		 				data : $('form').serialize(),
			 		 				cache : false,
			 		 				dataType : "html",
			 		 				success : function(responseText) 
			 		 				{
			 		 					$("#messageDiv").html(responseText);
			 		 					$("#messageDiv").show();
			 		 					$('#add-modal').modal('hide');
			 		 					setTimeout('reloadPage("MASTER_MANAGEMENT_IDENTIFICATION_TYPES")', 3000);
			 		 				}
			 		 			});
			 		 	 }
		 		 	}
		 		 	
		 		 	function populateEditForm(id, name){
		 		 		$('#id').val(id);
		 		 		$('#editIdentificationType').val(name);
		 		 		$('#edit-modal').modal('show');
		 		 	}
		 		 	
		 		 	function updateMaster(){
		 		 		if($('#editForm').valid()) 
			 		 	{
		 		 			var id = $('#id').val();
		 		 			var name = $('#editIdentificationType').val();
		 		 			
		 		 		  	$.ajax
		 		 			({
		 		 				type : "POST",
		 		 				url : "<%=request.getContextPath()%>/master.html?method=updateMaster&masterType=MASTER_MANAGEMENT_IDENTIFICATION_TYPES&name="+name+"&id="+id,
		 		 				data : $('form').serialize(),
		 		 				cache : false,
		 		 				dataType : "html",
		 		 				success : function(responseText) 
		 		 				{
		 		 					$("#messageDiv").html(responseText);
		 		 					$("#messageDiv").show();
		 		 					$('#edit-modal').modal('hide');
		 		 					setTimeout('reloadPage("MASTER_MANAGEMENT_IDENTIFICATION_TYPES")', 3000);
		 		 				}
		 		 			});
			 		 	 }
	 		 		}
		 		
		 		</script>
			</logic:equal>
			
			<logic:equal value="MASTER_MANAGMENT_NATIONALITY" name="masterType">
				<input type="hidden" value="List of nationality" id="exportFileName"/>
				<div class="box box-primary">
	            	<div class="box-header">
	              		<h3 class="box-title">&nbsp;</h3>
			  			<div class="pull-right box-tools">
			                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#add-modal" title="Add New Nationality">
			                  <i class="fa fa-plus"></i>
							  &nbsp; Add New Nationality
							</button>
	              		</div>
	            	</div>
	            	<div class="box-body">
	            		<div class="table-responsive">
	            			<table id="master-table" class="table table-bordered table-striped">
	                			<thead>
	                				<tr>
					                	<th>Sl.No.</th>
					                	<th>Nationality</th>
										<th></th>
	                				</tr>
	                			</thead>
	                			<tbody>
				            		<logic:iterate id="master" name="masterList" type="bt.gov.moh.eet.dto.MasterDTO" indexId="index">
				                		<% int a = index.intValue(); %>
				                		<tr>
				                			<td><%=++a %></td>
				                			<td><bean:write name="master" property="nationality"/></td>
				                			<td align="center">
												<div class="box-tools pull-right">
													<button type="button" class="btn btn-box-tool" data-toggle="tooltip" data-placement="top" title="Edit" onclick="populateEditForm('<bean:write name="master" property="nationalityId"/>','<bean:write name="master" property="nationality"/>')"><i class="fa fa-pencil-square-o"></i></button>
													<button type="button" class="btn btn-box-tool" data-toggle="tooltip" data-placement="top" title="Edit" onclick="showConfirmDialog('<bean:write name="master" property="nationalityId"/>','MASTER_MANAGMENT_NATIONALITY')"><i class="fa fa-trash"></i></button>
												</div>
											</td>
				                		</tr>
			                		</logic:iterate>
			                	</tbody>
			                </table>
	            		</div>
	            	</div>
	            </div>
	            
	            <div id="add-modal" class="modal fade" tabindex="-1">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="blue bigger">Add Nationality</h4>
							</div>
		 					<div class="modal-body">
		 						<div class="row">
									<div class="col-xs-12"> 
		                   				<form class="form-horizontal" role="form" id="addForm">
		                   					<div class="form-group">
		                   						<label class="control-label col-sm-6">Nationality<font color='red'>*</font></label>
		                   						<div class="col-sm-6">
		                   							<input type="text" class="form-control" id="nationality" name="nationality" placeholder="Nationality"/>
		                   						</div>
		                   					</div>
		                   				</form>
			 						</div>
			 					</div>
			 				</div>
			 				<div class="modal-footer">
								<button type="button" class="btn btn-flat btn-primary" name="add" onclick="addMaster()">
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
		 		
		 		<div id="edit-modal" class="modal fade" tabindex="-1">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="blue bigger">Update Nationality Details</h4>
							</div>
		 					<div class="modal-body">
		 						<div class="row">
									<div class="col-xs-12"> 
		                   				<form class="form-horizontal" role="form" id="editForm">
		                   					<div class="form-group">
		                   						<label class="control-label col-sm-6">Nationality<font color='red'>*</font></label>
		                   						<div class="col-sm-6">
		                   							<input type="text" class="form-control" id="editNationality" name="editNationality" placeholder="Nationality"/>
		                   						</div>
		                   					</div>
		                   				</form>
			 						</div>
			 					</div>
			 				</div>
			 				<div class="modal-footer">
			 					<input type="hidden" id="id"/>
								<button type="button" class="btn btn-flat btn-primary" name="edit" onclick="updateMaster()">
									<i class="ace-icon fa fa-pencil-square-o"></i>
									Update
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
		 		
			 		$(document).ready(function() {
		 		 		$('#addForm').validate({
			 		 		  invalidHandler: function(form, validator) {
			 		            var errors = validator.numberOfInvalids();
			 		            if (errors) {                    
			 		                var firstInvalidElement = $(validator.errorList[0].element);
			 		                $('html,body').scrollTop(firstInvalidElement.offset().top);
			 		                firstInvalidElement.focus();
			 		            }
			 		        	  },
			 		 		  rules: {
			 		 			  'nationality':{
			 		 				  required:true
			 		 			  }
			 		 		  },
			 		 		  messages: {
			 		 			  'nationality':{
			 		 				  required:"Please provide nationality"
			 		 			  }
			 		 		  }
			 		 	  });
		 		 		
		 		 		  $('#editForm').validate({
			 		 		  invalidHandler: function(form, validator) {
			 		            var errors = validator.numberOfInvalids();
			 		            if (errors) {                    
			 		                var firstInvalidElement = $(validator.errorList[0].element);
			 		                $('html,body').scrollTop(firstInvalidElement.offset().top);
			 		                firstInvalidElement.focus();
			 		            }
			 		        	  },
			 		 		  rules: {
			 		 			  'editNationality':{
			 		 				  required:true
			 		 			  }
			 		 		  },
			 		 		  messages: {
			 		 			  'editNationality':{
			 		 				  required:"Please provide a nationality"
			 		 			  }
			 		 		  }
			 		 	  });
		 		 	});
		 		 	
		 		 	function addMaster(){
		 		 		if($('#addForm').valid()) 
			 		 	{
		 		 			var name = $('#nationality').val();
		 		 			
		 		 		  	$.ajax
			 		 			({
			 		 				type : "POST",
			 		 				url : "<%=request.getContextPath()%>/master.html?method=addMaster&masterType=MASTER_MANAGMENT_NATIONALITY&name="+name,
			 		 				data : $('form').serialize(),
			 		 				cache : false,
			 		 				dataType : "html",
			 		 				success : function(responseText) 
			 		 				{
			 		 					$("#messageDiv").html(responseText);
			 		 					$("#messageDiv").show();
			 		 					$('#add-modal').modal('hide');
			 		 					setTimeout('reloadPage("MASTER_MANAGMENT_NATIONALITY")', 3000);
			 		 				}
			 		 			});
			 		 	 }
		 		 	}
		 		 	
		 		 	function populateEditForm(id, name){
		 		 		$('#id').val(id);
		 		 		$('#editNationality').val(name);
		 		 		$('#edit-modal').modal('show');
		 		 	}
		 		 	
		 		 	function updateMaster(){
		 		 		if($('#editForm').valid()) 
			 		 	{
		 		 			var id = $('#id').val();
		 		 			var name = $('#editNationality').val();
		 		 			
		 		 		  	$.ajax
		 		 			({
		 		 				type : "POST",
		 		 				url : "<%=request.getContextPath()%>/master.html?method=updateMaster&masterType=MASTER_MANAGMENT_NATIONALITY&name="+name+"&id="+id,
		 		 				data : $('form').serialize(),
		 		 				cache : false,
		 		 				dataType : "html",
		 		 				success : function(responseText) 
		 		 				{
		 		 					$("#messageDiv").html(responseText);
		 		 					$("#messageDiv").show();
		 		 					$('#edit-modal').modal('hide');
		 		 					setTimeout('reloadPage("MASTER_MANAGMENT_NATIONALITY")', 3000);
		 		 				}
		 		 			});
			 		 	 }
	 		 		}
		 		
		 		</script>
			</logic:equal>
			
			<logic:equal value="MASTER_MANAGMENT_USERTYPES" name="masterType">
				<input type="hidden" value="List of nationality" id="exportFileName"/>
				<div class="box box-primary">
	            	<div class="box-header">
	              		<h3 class="box-title">&nbsp;</h3>
			  			<div class="pull-right box-tools">
			                <button type="button" class="btn btn-primary btn-sm" data-toggle="modal" data-target="#add-modal" title="Add New User Type">
			                  <i class="fa fa-plus"></i>
							  &nbsp; Add New User Type
							</button>
	              		</div>
	            	</div>
	            	<div class="box-body">
	            		<div class="table-responsive">
	            			<table id="master-table" class="table table-bordered table-striped">
	                			<thead>
	                				<tr>
					                	<th>Sl.No.</th>
					                	<th>User Type</th>
										<th></th>
	                				</tr>
	                			</thead>
	                			<tbody>
				            		<logic:iterate id="master" name="masterList" type="bt.gov.moh.eet.dto.MasterDTO" indexId="index">
				                		<% int a = index.intValue(); %>
				                		<tr>
				                			<td><%=++a %></td>
				                			<td><bean:write name="master" property="userType"/></td>
				                			<td align="center">
												<div class="box-tools pull-right">
													<button type="button" class="btn btn-box-tool" data-toggle="tooltip" data-placement="top" title="Edit" onclick="populateEditForm('<bean:write name="master" property="userTypeId"/>','<bean:write name="master" property="userType"/>')"><i class="fa fa-pencil-square-o"></i></button>
													<button type="button" class="btn btn-box-tool" data-toggle="tooltip" data-placement="top" title="Edit" onclick="showConfirmDialog('<bean:write name="master" property="userTypeId"/>','MASTER_MANAGMENT_USERTYPES')"><i class="fa fa-trash"></i></button>
												</div>
											</td>
				                		</tr>
			                		</logic:iterate>
			                	</tbody>
			                </table>
	            		</div>
	            	</div>
	            </div>
	            
	            <div id="add-modal" class="modal fade" tabindex="-1">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="blue bigger">Add New User Type</h4>
							</div>
		 					<div class="modal-body">
		 						<div class="row">
									<div class="col-xs-12"> 
		                   				<form class="form-horizontal" role="form" id="addForm">
		                   					<div class="form-group">
		                   						<label class="control-label col-sm-6">User Type<font color='red'>*</font></label>
		                   						<div class="col-sm-6">
		                   							<input type="text" class="form-control" id="userType" name="userType" placeholder="User Type"/>
		                   						</div>
		                   					</div>
		                   				</form>
			 						</div>
			 					</div>
			 				</div>
			 				<div class="modal-footer">
								<button type="button" class="btn btn-flat btn-primary" name="add" onclick="addMaster()">
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
		 		
		 		<div id="edit-modal" class="modal fade" tabindex="-1">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="blue bigger">Update User Type Details</h4>
							</div>
		 					<div class="modal-body">
		 						<div class="row">
									<div class="col-xs-12"> 
		                   				<form class="form-horizontal" role="form" id="editForm">
		                   					<div class="form-group">
		                   						<label class="control-label col-sm-6">User Type<font color='red'>*</font></label>
		                   						<div class="col-sm-6">
		                   							<input type="text" class="form-control" id="editUserType" name="editUserType" placeholder="User Type"/>
		                   						</div>
		                   					</div>
		                   				</form>
			 						</div>
			 					</div>
			 				</div>
			 				<div class="modal-footer">
			 					<input type="hidden" id="id"/>
								<button type="button" class="btn btn-flat btn-primary" name="edit" onclick="updateMaster()">
									<i class="ace-icon fa fa-pencil-square-o"></i>
									Update
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
		 		
			 		$(document).ready(function() {
		 		 		$('#addForm').validate({
			 		 		  invalidHandler: function(form, validator) {
			 		            var errors = validator.numberOfInvalids();
			 		            if (errors) {                    
			 		                var firstInvalidElement = $(validator.errorList[0].element);
			 		                $('html,body').scrollTop(firstInvalidElement.offset().top);
			 		                firstInvalidElement.focus();
			 		            }
			 		        	  },
			 		 		  rules: {
			 		 			  'userTYpe':{
			 		 				  required:true
			 		 			  }
			 		 		  },
			 		 		  messages: {
			 		 			  'userTYpe':{
			 		 				  required:"Please provide a user type"
			 		 			  }
			 		 		  }
			 		 	  });
		 		 		
		 		 		  $('#editForm').validate({
			 		 		  invalidHandler: function(form, validator) {
			 		            var errors = validator.numberOfInvalids();
			 		            if (errors) {                    
			 		                var firstInvalidElement = $(validator.errorList[0].element);
			 		                $('html,body').scrollTop(firstInvalidElement.offset().top);
			 		                firstInvalidElement.focus();
			 		            }
			 		        	  },
			 		 		  rules: {
			 		 			  'editUserType':{
			 		 				  required:true
			 		 			  }
			 		 		  },
			 		 		  messages: {
			 		 			  'editUserType':{
			 		 				  required:"Please provide a user type"
			 		 			  }
			 		 		  }
			 		 	  });
		 		 	});
		 		 	
		 		 	function addMaster(){
		 		 		if($('#addForm').valid()) 
			 		 	{
		 		 			var name = $('#userType').val();
		 		 			
		 		 		  	$.ajax
			 		 			({
			 		 				type : "POST",
			 		 				url : "<%=request.getContextPath()%>/master.html?method=addMaster&masterType=MASTER_MANAGMENT_USERTYPES&name="+name,
			 		 				data : $('form').serialize(),
			 		 				cache : false,
			 		 				dataType : "html",
			 		 				success : function(responseText) 
			 		 				{
			 		 					$("#messageDiv").html(responseText);
			 		 					$("#messageDiv").show();
			 		 					$('#add-modal').modal('hide');
			 		 					setTimeout('reloadPage("MASTER_MANAGMENT_USERTYPES")', 3000);
			 		 				}
			 		 			});
			 		 	 }
		 		 	}
		 		 	
		 		 	function populateEditForm(id, name){
		 		 		$('#id').val(id);
		 		 		$('#editNationality').val(name);
		 		 		$('#edit-modal').modal('show');
		 		 	}
		 		 	
		 		 	function updateMaster(){
		 		 		if($('#editForm').valid()) 
			 		 	{
		 		 			var id = $('#id').val();
		 		 			var name = $('#editUserType').val();
		 		 			
		 		 		  	$.ajax
		 		 			({
		 		 				type : "POST",
		 		 				url : "<%=request.getContextPath()%>/master.html?method=updateMaster&masterType=MASTER_MANAGMENT_USERTYPES&name="+name+"&id="+id,
		 		 				data : $('form').serialize(),
		 		 				cache : false,
		 		 				dataType : "html",
		 		 				success : function(responseText) 
		 		 				{
		 		 					$("#messageDiv").html(responseText);
		 		 					$("#messageDiv").show();
		 		 					$('#edit-modal').modal('hide');
		 		 					setTimeout('reloadPage("MASTER_MANAGMENT_USERTYPES")', 3000);
		 		 				}
		 		 			});
			 		 	 }
	 		 		}
		 		
		 		</script>
			</logic:equal>
			
		</div>
	</div>
	
	<div id="confirm-dialog" class="modal fade" tabindex="-1">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<h4 class="blue bigger">Confirmation!</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-12"> 
		       				<p>Do you want to delete the selected item?</p>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<input type="hidden" id="id"/>
					<input type="hidden" id="deleteMasterType"/>
					<button type="button" class="btn btn-flat btn-primary" name="edit" onclick="deleteMaster()">
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

</section>

<script>

	$(function () {
		var fileName = $('#exportFileName').val();
		$("#master-table").DataTable({
			dom: 'Bfrtip',
	        buttons: [
				{
				    extend: 'copyHtml5',
				    title: fileName,
				    text: '<i class="fa fa-files-o"></i>',
				    titleAttr: 'Copy'
				},
				{
				    extend: 'csvHtml5',
				    title: fileName,
				    text:      '<i class="fa fa-file-text-o"></i>',
	                titleAttr: 'CSV'
				},
				{
				    extend: 'excelHtml5',
				    title: fileName,
				    text:      '<i class="fa fa-file-excel-o"></i>',
	                titleAttr: 'Excel'
				},
				{
				    extend: 'pdfHtml5',
				    title: fileName,
				    text:      '<i class="fa fa-file-pdf-o"></i>',
	                titleAttr: 'PDF'
				}
	        ]
	    });
	});
	
	function showConfirmDialog(id, masterType){
		$('#id').val(id);
		$('#deleteMasterType').val(masterType);
		$('#confirm-dialog').modal('show');
	}
	
	function deleteMaster(){
		var id = $('#id').val();
		var masterType = $('#deleteMasterType').val();
		
		$.ajax
			({
				type : "POST",
				url : "<%=request.getContextPath()%>/master.html?method=deleteMaster&masterType="+masterType+"&id="+id,
				data : $('form').serialize(),
				cache : false,
				dataType : "html",
				success : function(responseText) 
				{
					$("#messageDiv").html(responseText);
					$("#messageDiv").show();
					$('#confirm-dialog').modal('hide');
					setTimeout('reloadPageAfterDelete()', 3000);
				}
			});
	}
	
	function reloadPageAfterDelete(){
		var masterType = $('#deleteMasterType').val();
		var url = "<%=request.getContextPath()%>/redirect.html?q="+masterType
		$("#contentDisplayDiv").load(url);
		$('#contentDisplayDiv').show();
	}

	function reloadPage(masterType){
		var url = "<%=request.getContextPath()%>/redirect.html?q="+masterType
		$("#contentDisplayDiv").load(url);
		$('#contentDisplayDiv').show();
	}


</script>