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

<html:form styleClass="form-horizontal" styleId="entryExitForm" method="post" action="/entryExit">
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
			       						<html:select property="identificationType" styleClass="form-control" styleId="identificationType">
								    		<html:option value="">--Select--</html:option>
								    		<html:options collection ="IDENTIFICATIONTYPELIST" property="headerId" labelProperty="headerName"/>
								    	</html:select>
								        <span class="help-block" id="identificationTypeErrorMsg"></span>
			       					</div>
			       					<label class="control-label col-sm-2">Identification No.<font color='red'>*</font></label>
	                   			<div class="col-sm-4" id="identificationNoDiv">
	                   				<html:text property="identificationNo" styleClass="form-control" styleId="identificationNo" onchange=getCitizenDetail();></html:text>
	                   				<span class="help-block" id="identificationNoErrorMsg"></span>
                   				</div>
	       				</div>
	       				<div class="form-group">
		                   		
	       					<label class="control-label col-sm-2">Name</label>
	       					<div class="col-sm-4">
	       						<html:text property="name" styleClass="form-control" styleId="name" readonly="true"></html:text>
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
	                   				<html:select property="nationality" styleClass="form-control" styleId="nationality">
						    			<html:option value="">--Select--</html:option>
						    			<html:options collection ="NATIONALITYLIST" property="headerId" labelProperty="headerName"/>
						    		</html:select>
						        <span class="help-block" id="nationalityErrorMsg"></span>
                   				</div>
	       					<label class="control-label col-sm-2" id="presentAddressDiv">Present Address<font color='red'>*</font></label>
	       					<div class="col-sm-4">
	       						<textarea class="form-control" rows="5" id="presentAddress"></textarea>
	       						<span class="help-block" id="presentAddressErrorMsg"></span>
	       					</div>
	       				</div>
	       				<div class="form-group">
		                   		<label class="control-label col-sm-2">Reason<font color='red'>*</font></label>
	                   			<div class="col-sm-4" id="reasonIdDiv">
	                   				<html:select property="reasonId" styleClass="form-control" styleId="reasonId">
						    			<html:option value="">--Select--</html:option>
						    			<html:options collection ="REASONLIST" property="headerId" labelProperty="headerName"/>
						    		</html:select>
						    		<span class="help-block" id="reasonIdErrorMsg"></span>
                   				</div>
	       					<label class="control-label col-sm-2">Reason<font color='red'>*</font></label>
	       					<div class="col-sm-4" id="reasonDiv">
	       						<textarea class="form-control" rows="5" id="reason"></textarea>
	       						<span class="help-block" id="reasonErrorMsg"></span>
	       					</div>
	       				</div>
	       				<%-- <div id="travelDIv" style="display: none" class="form-group">
                   				<label class="control-label col-sm-2">Next Entry Gate<font color='red'>*</font></label>
	                   			<div class="col-sm-4" id="gateDiv">
	                   				<html:select property="gate" styleClass="form-control" styleId="gate" onchange="changeNextEntry(this.value);">
						    			<html:option value="">--Select--</html:option>
						    			<html:options collection ="GATELIST" property="headerId" labelProperty="headerName"/>
						    		</html:select>
						    		<span class="help-block" id="gateErrorMsg"></span>
                   				</div>
                   				</div> --%>
	       				<div class="form-group">
	       				<label class="control-label col-sm-2">Contact No<font color='red'>*</font></label>
	                   			<div class="col-sm-4" id="contactNoDiv">
	                   					<html:text property="contactNo" styleClass="form-control" styleId="contactNo"></html:text>
	                   					<span class="help-block" id="contactNoErrorMsg"></span>
                   				</div>
		                   		<label class="control-label col-sm-2">Thermometer Reading<font color='red'>*</font></label>
	                   			<div class="col-sm-4" id="thermometerReadingDiv">
	                   					<html:text property="thermometerReading" styleClass="form-control" styleId="thermometerReading"></html:text>
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
		if ($('#identificationType').val() == "" ) 
		{
			$('#identificationTypeDiv').addClass('has-error');
			$('#identificationTypeErrorMsg').html('Please select Identification type');
			$('#identificationTypeErrorMsg').show();
			setTimeout('$("#identificationTypeDiv").removeClass("has-error")',8000);
			setTimeout('$("#identificationTypeErrorMsg").hide()',8000);
			return false;	
		}
		if ($('#identificationNo').val() == "" ) 
		{
			$('#identificationNoDiv').addClass('has-error');
			$('#identificationNoErrorMsg').html('Please enter Identification no.');
			$('#identificationNoErrorMsg').show();
			setTimeout('$("#identificationNoDiv").removeClass("has-error")',8000);
			setTimeout('$("#identificationNoErrorMsg").hide()',8000);
			return false;	
		}
		if ($('#identificationType').val() == "" ) 
		{
			$('#identificationTypeDiv').addClass('has-error');
			$('#identificationTypeErrorMsg').html('Please select identification type.');
			$('#religionErrorMsg').show();
			setTimeout('$("#identificationTypeDiv").removeClass("has-error")',8000);
			setTimeout('$("#identificationTypeErrorMsg").hide()',8000);
			return false;	
		}
		if ($('#nationality').val() == "" ) 
		{
			$('#nationalityDiv').addClass('has-error');
			$('#nationalityErrorMsg').html('Please select nationality.');
			$('#nationalityErrorMsg').show();
			setTimeout('$("#nationalityDiv").removeClass("has-error")',8000);
			setTimeout('$("#nationalityErrorMsg").hide()',8000);
			return false;	
		}
		if ($('#presentAddress').val() == "" ) 
		{
			$('#presentAddressDiv').addClass('has-error');
			$('#presentAddressErrorMsg').html('Persent address requried.');
			$('#presentAddressErrorMsg').show();
			setTimeout('$("#presentAddressDiv").removeClass("has-error")',8000);
			setTimeout('$("#presentAddressErrorMsg").hide()',8000);
			return false;	
		}
		if ($('#reasonId').val() == "" ) 
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
		if ($('#contactNo').val() == "" ) 
		{
			$('#contactNoDiv').addClass('has-error');
			$('#contactNoErrorMsg').html('Contact No. is requried');
			$('#contactNoErrorMsg').show();
			setTimeout('$("#contactNoDiv").removeClass("has-error")',8000);
			setTimeout('$("#contactNoErrorMsg").hide()',8000);
			return false;	
		}
		if ($('#thermometerReading').val() == "" ) 
		{
			$('#thermometerReadingDiv').addClass('has-error');
			$('#thermometerReadingErrorMsg').html('Thermometer reading is requried');
			$('#thermometerReadingErrorMsg').show();
			setTimeout('$("#thermometerReadingDiv").removeClass("has-error")',8000);
			setTimeout('$("#thermometerReadingErrorMsg").hide()',8000);
			return false;	
		}
		var options = {
				 target:'#showResult',url:context+'/entryExit.html?method=entryExit',type:'POST',data: $("#entryExitForm").serialize()}; 
				 	$("#entryExitForm").ajaxSubmit(options);
				 	$('#submitBtn').hide();
				 	 var progress = $(".submit-progress").progressTimer({
				 		  timeLimit: 10,
				 		  onFinish: function () {
				 			  $(".submit-progress").hide();
				 		}
				 		});
				}
</script>