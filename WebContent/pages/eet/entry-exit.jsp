<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<style>
	#searchForm .error { color: red; }
</style>
<link href="<%=request.getContextPath()%>/css/chosen.min.css" rel="stylesheet" />
<script src="<%=request.getContextPath()%>/js/chosen.jquery.min.js"></script>

<section class="content-header">
  <h1>
       Entry Exit Application Form
     </h1>
     <ol class="breadcrumb">
       <li><a href="<%=request.getContextPath()%>/login.html?q=dashboard"><i class="fa fa-dashboard"></i> Home</a></li>
       <li class="active">Enrollment</li>
     </ol>
</section>

<html:form styleClass="form-horizontal" styleId="EntryExitForm" method="post" action="/entryExit">
	<section class="content">
		<div class="row">
			<div class="col-lg-12">
				<div id="messageDiv" style="display: none;"></div>
	       		<div class="box box-primary">
	       			<div class="box-body">
	       				<div class="form-group">
		                   		<label class="control-label col-sm-4">Are you Existing or Entering ?<font color='red'>*</font></label>
	                   			<div class="col-sm-8">
	                   				<div class="custom-control custom-radio">
								  <input type="radio" class="custom-control-input" id="entry" name="entryexit">
								  <label class="custom-control-label" for="entry">Entry</label>
									</div>
									<div class="custom-control custom-radio">
									  <input type="radio" class="custom-control-input" id="exit" name="entryexit">
									  <label class="custom-control-label" for="exit">Exit</label>
									</div>
                   				</div>
                   		</div>
	       				<div class="form-group">
	       					<label class="control-label col-sm-2">Identification Type<font color='red'>*</font></label>
	       					<div class="col-sm-4" id="identificationTypeDiv">
	       						<html:select property="identification_type_id" styleClass="form-control" styleId="identificationType">
						    		<html:option value="">--Select--</html:option>
						    		<html:options collection ="IDENTIFICATIONTYPELIST" property="headerId" labelProperty="headerName"/>
						    	</html:select>
						        <span class="help-block" id="identificationTypeErrorMsg"></span>
	       					</div>
	       				</div>
	       				<div class="form-group">
		                   		<label class="control-label col-sm-2">Identification No.<font color='red'>*</font></label>
	                   			<div class="col-sm-4" id="identificationNoDiv">
	                   				<html:text property="identification_no" styleClass="form-control" styleId="identification_no"></html:text>
	                   				<span class="help-block" id="identificationNoErrorMsg"></span>
                   				</div>
	       					<label class="control-label col-sm-2">Name<font color='red'>*</font></label>
	       					<div class="col-sm-4">
	       						<html:text property="guest_name" styleClass="form-control" styleId="name" readonly="true"></html:text>
	       					</div>
	       				</div>
	       				<div class="form-group">
		                   		<label class="control-label col-sm-2">Gender</label>
	                   			<div class="col-sm-4">
	                   				<select class="form-control" id="gender" name="gender" readonly="true">
	       							<option value ="">--select gender--</option>
	       							<option value ="M">Male</option>
	       							<option value ="F">Female</option>
	                   			</select>
                   				</div>
	       					<label class="control-label col-sm-2">Age</label>
	       					<div class="col-sm-4">
	       						<html:text property="age" styleClass="form-control" styleId="age" readonly="true"></html:text>
	       					</div>
	       				</div>
	       				<div class="form-group">
		                   		<label class="control-label col-sm-2">Nationality<font color='red'>*</font></label>
	                   			<div class="col-sm-4" id="nationalityDiv">
	                   				<html:select property="nationality_id" styleClass="form-control" styleId="nationality_id">
						    			<html:option value="">--Select--</html:option>
						    			<html:options collection ="NATIONALITYLIST" property="headerId" labelProperty="headerName"/>
						    		</html:select>
						        <span class="help-block" id="nationalityErrorMsg"></span>
                   				</div>
	       					<label class="control-label col-sm-2">Present Address<font color='red'>*</font></label>
	       					<div class="col-sm-4" id="presentAddressDiv">
	       						<textarea class="form-control" rows="5" id="present_address" name="present_address"></textarea>
	       						<span class="help-block" id="presentAddressErrorMsg"></span>
	       					</div>
	       				</div>
	       				<div class="form-group">
		                   		<label class="control-label col-sm-2">Reason<font color='red'>*</font></label>
	                   			<div class="col-sm-4" id="reasonIdDiv">
	                   				<html:select property="reason_id" styleClass="form-control" styleId="reason_id" onchange="changeNextEntry(this.value);">
						    			<html:option value="">--Select--</html:option>
						    			<html:options collection ="REASONLIST" property="headerId" labelProperty="headerName"/>
						    		</html:select>
						    		<span class="help-block" id="reasonIdErrorMsg"></span>
                   				</div>
	       					<label class="control-label col-sm-2">Reason<font color='red'>*</font></label>
	       					<div class="col-sm-4" id="reasonDiv">
	       						<textarea class="form-control" rows="5" id="reason" name="reason"></textarea>
	       						<span class="help-block" id="reasonErrorMsg"></span>
	       					</div>
	       				</div>
	       				<div id="travelDIv" style="display: none" class="form-group">
                   				<label class="control-label col-sm-2">Next Entry Gate<font color='red'>*</font></label>
	                   			<div class="col-sm-4" id="gateDiv">
	                   				<html:select property="gate_id" styleClass="form-control" styleId="gate_id">
						    			<html:option value="">--Select--</html:option>
						    			<html:options collection ="GATELIST" property="headerId" labelProperty="headerName"/>
						    		</html:select>
						    		<span class="help-block" id="gateErrorMsg"></span>
                   				</div>
                   		</div>
	       				<div class="form-group">
	       				<label class="control-label col-sm-2">Contact No<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   					<html:text property="contact_no" styleClass="form-control" styleId="contact_no"></html:text>
                   				</div>
		                   		<label class="control-label col-sm-2">Thermometer Reading<font color='red'>*</font></label>
	                   			<div class="col-sm-4" id="thermometerReadingDiv">
	                   					<html:text property="temperature" styleClass="form-control" styleId="temperature"></html:text>
	                   					<span class="help-block" id="thermometerReadingErrorMsg"></span>
                   				</div>
	       				</div>
	       			</div>
	       			<div class="box-footer">
	       				<div class="text-left">
	       					<button type = "button" class="btn btn-primary" id="submitBtn" onclick="return submitApplication();"><i class="fa fa-floppy-o"></i>&nbsp;Register Visitor</button>
	       				</div>
	       			</div>
	       		</div>
	       	</div>
		</div>
		<div id = "showResult"></div>
	</section>
</html:form>
<script type="text/javascript">
	var context = "<%=request.getContextPath()%>";
	function changeNextEntry(value)
	{
		alert(value);
		if(value=="2")
		{
			$('#travelDIv').show();
		}
		else
		{
			$('#travelDIv').hide();
		}
	}
	
	function submitApplication(){
		if ($('#identification_type_id').val() == "" ) 
		{
			$('#identificationTypeDiv').addClass('has-error');
			$('#identificationTypeErrorMsg').html('Please select Identification type');
			$('#identificationTypeErrorMsg').show();
			setTimeout('$("#identificationTypeDiv").removeClass("has-error")',8000);
			setTimeout('$("#identificationTypeErrorMsg").hide()',8000);
			return false;	
		}
		if ($('#identification_no').val() == "" ) 
		{
			$('#identificationNoDiv').addClass('has-error');
			$('#identificationNoErrorMsg').html('Please enter Identification no.');
			$('#identificationNoErrorMsg').show();
			setTimeout('$("#identificationNoDiv").removeClass("has-error")',8000);
			setTimeout('$("#identificationNoErrorMsg").hide()',8000);
			return false;	
		}
		if ($('#nationality_id').val() == "" ) 
		{
			$('#nationalityDiv').addClass('has-error');
			$('#nationalityErrorMsg').html('Please select nationality.');
			$('#nationalityErrorMsg').show();
			setTimeout('$("#nationalityDiv").removeClass("has-error")',8000);
			setTimeout('$("#nationalityErrorMsg").hide()',8000);
			return false;	
		}
		if ($('#present_address').val() == "" ) 
		{
			$('#presentAddressDiv').addClass('has-error');
			$('#presentAddressErrorMsg').html('Persent address requried.');
			$('#presentAddressErrorMsg').show();
			setTimeout('$("#presentAddressDiv").removeClass("has-error")',8000);
			setTimeout('$("#presentAddressErrorMsg").hide()',8000);
			return false;	
		}
		if ($('#reason_id').val() == "" ) 
		{
			$('#reasonIdDiv').addClass('has-error');
			$('#reasonIdErrorMsg').html('Please select the reason.');
			$('#reasonIdErrorMsg').show();
			setTimeout('$("#reasonIdDiv").removeClass("has-error")',8000);
			setTimeout('$("#reasonIdErrorMsg").hide()',8000);
			return false;	
		}
		if ($('#reason').val() == "" ) 
		{
			$('#reasonDiv').addClass('has-error');
			$('#reasonErrorMsg').html('Please enter the reasons.');
			$('#reasonErrorMsg').show();
			setTimeout('$("#reasonDiv").removeClass("has-error")',8000);
			setTimeout('$("#reasonErrorMsg").hide()',8000);
			return false;	
		}
		if ($('#gate_id').val() == "" ) 
		{
			$('#gateDiv').addClass('has-error');
			$('#gateErrorMsg').html('Please select the gate.');
			$('#gateErrorMsg').show();
			setTimeout('$("#gateDiv").removeClass("has-error")',8000);
			setTimeout('$("#gateErrorMsg").hide()',8000);
			return false;	
		}
		if ($('#contactNo').val() == "" ) 
		{
			$('#contactNoDiv').addClass('has-error');
			$('#contactNoErrorMsg').html('Contact No. is requried');
			$('#contactNoErrorMsg').show();
			setTimeout('$("#contactNoDiv").removeClass("has-error")',8000);
			setTimeout('$("#contactNoErrorMsg").hide()',8000);
			return false;	
		}
		if ($('#temperature').val() == "" ) 
		{
			$('#thermometerReadingDiv').addClass('has-error');
			$('#thermometerReadingErrorMsg').html('Thermometer reading is requried');
			$('#thermometerReadingErrorMsg').show();
			setTimeout('$("#thermometerReadingDiv").removeClass("has-error")',8000);
			setTimeout('$("#thermometerReadingErrorMsg").hide()',8000);
			return false;	
		}
		var options = {
				 target:'#showResult',
				 url:context+'/entryExit.html?method=entryExit',
				 type:'POST',
				 data: $("#entryExitForm").serialize();
				 	$("#entryExitForm").ajaxSubmit(options);
				 	$('#submitBtn').hide();
				 	 var progress = $(".submit-progress").progressTimer({
				 		  timeLimit: 10,
				 		  onFinish: function () {
				 			  $(".submit-progress").hide();
				 		}
				 		});
				}
	} 
</script>