<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<style>
	#searchForm .error { color: red; }
	
	.booth {
	    width: 180px;;
	    background-color: #ccc;
	    border: 10px solid #ddd;
	    margin: 0 auto;
	}

</style>
<link href="<%=request.getContextPath()%>/css/chosen.min.css" rel="stylesheet" />
<script src="<%=request.getContextPath()%>/js/chosen.jquery.min.js"></script>

<section class="content-header">
  <h1>
       Manage Entry and Exit
       <small>manage new and registered guest entry and exit log</small>
     </h1>
     <ol class="breadcrumb">
       <li><a href="<%=request.getContextPath()%>/login.html?q=dashboard"><i class="fa fa-dashboard"></i> Home</a></li>
       <li class="active">Manage entry and exit</li>
     </ol>
</section>

<html:form styleClass="form-horizontal" styleId="EntryExitForm" method="post" action="/entryExit.html">
	<section class="content">
		<div class="row">
			<div class="col-lg-12">
				<div id="messageDiv" style="display: none;"></div>
	       		<div class="box box-primary">
	       			<div class="box-body">
	       				<div class="form-group">
		            		<label class=" col-sm-4">Are you Existing or Entering ?<font color='red'>*</font></label>
		           			<div class="col-sm-4">
           						<div class="custom-control custom-radio">
								  <input type="radio" class="custom-control-input" id="entry" name="entryexit" onclick="showForm('ENTRY')">
								  <label class="custom-" for="entry">Entry</label>
								  <input type="radio" class="custom-control-input" id="exit" name="entryexit" onclick="showForm('EXIT')">
								  <label class="custom-" for="exit">Exit</label>
								</div>
          					</div>
          					<div class="col-sm-4 text-right">
	          					<div class="booth">
	          						<img id="personImage" src="<%=request.getContextPath() %>/img/user2-160x160.jpg" class="img-responsive">
								</div>
							</div>
                   		</div>
	       				<div class="form-group">
	       					<label class=" col-sm-3">Identification Type<font color='red'>*</font></label>
	       					<div class="col-sm-3 text-left" id="identificationTypeDiv">
	       						<html:select property="identification_type_id" styleClass="form-control" styleId="identificationType">
						    		<html:option value="">--SELECT IDENTIFICATION TYPE--</html:option>
						    		<html:options collection ="IDENTIFICATIONTYPELIST" property="headerId" labelProperty="headerName"/>
						    	</html:select>
						        <span class="help-block" id="identificationTypeErrorMsg"></span>
	       					</div>
	       				</div>
	       				<div class="form-group">
		                   		<label class=" col-sm-3">Identification No.<font color='red'>*</font></label>
	                   			<div class="col-sm-3 text-left" id="identificationNoDiv">
	                   				<html:text property="identification_no" styleClass="form-control" styleId="identification_no" onchange="getCitizenDetails(this.value);"/>
	                   				<span class="help-block" id="identificationNoErrorMsg"></span>
                   				</div>
	       					<label class=" col-sm-3">Name<font color='red'>*</font></label>
	       					<div class="col-sm-3 text-left">
	       						<html:text property="guest_name" styleClass="form-control" styleId="guest_name"></html:text>
	       					</div>
	       				</div>
	       				<div class="form-group">
		                   		<label class=" col-sm-3">Gender</label>
	                   			<div class="col-sm-3 text-left">
	                   				<select class="form-control" id="gender" name="gender">
	       							<option value ="">--SELECT GENDER--</option>
	       							<option value ="M">Male</option>
	       							<option value ="F">Female</option>
	                   			</select>
                   				</div>
	       					<label class=" col-sm-3">Age</label>
	       					<div class="col-sm-3 text-left">
	       						<html:text property="age" styleClass="form-control" styleId="age"></html:text>
	       					</div>
	       				</div>
	       				<div class="form-group">
		                   		<label class=" col-sm-3">Nationality<font color='red'>*</font></label>
	                   			<div class="col-sm-3 text-left" id="nationalityDiv">
	                   				<html:select property="nationality_id" styleClass="form-control" styleId="nationality_id">
						    			<html:option value="">--SELECT NATIONALITY--</html:option>
						    			<html:options collection ="NATIONALITYLIST" property="headerId" labelProperty="headerName"/>
						    		</html:select>
						        <span class="help-block" id="nationalityErrorMsg"></span>
                   				</div>
	       					<label class=" col-sm-3">Present Address<font color='red'>*</font></label>
	       					<div class="col-sm-3 text-left" id="presentAddressDiv">
	       						<html:text property="present_address" styleId="present_address" styleClass="form-control"></html:text>
	       						<span class="help-block" id="presentAddressErrorMsg"></span>
	       					</div>
	       				</div>
	       				<div class="form-group">
	       				<label class=" col-sm-3">Contact No<font color='red'>*</font></label>
	                   			<div class="col-sm-3 text-left">
	                   					<html:text property="contact_no" styleClass="form-control" styleId="contact_no"></html:text>
                   				</div>
		                   		<label class=" col-sm-3">Thermometer Reading<font color='red'>*</font></label>
	                   			<div class="col-sm-3 text-left" id="thermometerReadingDiv">
	                   					<html:text property="temperature" styleClass="form-control" styleId="temperature"></html:text>
	                   					<span class="help-block" id="thermometerReadingErrorMsg"></span>
                   				</div>
	       				</div>
	       				<div class="form-group" id="reasonDiv">
		                   		<label class=" col-sm-3">Reason<font color='red'>*</font></label>
	                   			<div class="col-sm-3 text-left" id="reasonIdDiv">
	                   				<html:select property="reason_id" styleClass="form-control" styleId="reason_id" onchange="changeNextEntry(this.value);">
						    			<html:option value="">--SELECT REASON FOR EXIT--</html:option>
						    			<html:options collection ="REASONLIST" property="headerId" labelProperty="headerName"/>
						    		</html:select>
						    		<span class="help-block" id="reasonIdErrorMsg"></span>
                   				</div>
	       					<label class=" col-sm-3">Reason<font color='red'>*</font></label>
	       					<div class="col-sm-3 text-left" id="reasonDiv">
	       						<textarea class="form-control" rows="5" id="reason" name="reason"></textarea>
	       						<span class="help-block" id="reasonErrorMsg"></span>
	       					</div>
	       				</div>
	       				<div id="travelDIv" style="display: none" class="form-group">
                   				<label class=" col-sm-3">Next Entry Gate<font color='red'>*</font></label>
	                   			<div class="col-sm-3 text-left" id="gateDiv">
	                   				<html:select property="gate_id" styleClass="form-control" styleId="gate_id">
						    			<html:option value="">--SELECT NEXT ENTRY GATE--</html:option>
						    			<html:options collection ="GATELIST" property="headerId" labelProperty="headerName"/>
						    		</html:select>
						    		<span class="help-block" id="gateErrorMsg"></span>
                   				</div>
                   		</div>
	       			</div>
	       			<div class="box-footer">
	       				<div id="msgDiv" style="display:none;"></div>
	       				<div class="text-right">
	       					<button class="btn btn-primary" type="button" onclick="fnValidateLogin()" ><i class="fa fa-search"></i>&nbsp;Save</button>
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
	
	function showForm(type){
		if(type == "ENTRY"){
			$('#travelDIv').hide();
			$('#reasonDiv').hide();
		} else {
			$('#reasonDiv').show();
		}
	}
	
	function changeNextEntry(value)
	{
		if(value=="2")
		{
			$('#travelDIv').show();
		}
		else
		{
			$('#travelDIv').hide();
		}
	}
	function getCitizenDetails(){
		$('#personImage').attr('src', '');
		var identificationType = $('#identificationType').val();
		var identification_no = $("#identification_no").val();
		
		if(identificationType == 1){
			$.ajax({
		        async: false,
		        cache: false,
		        type: 'GET',
		        dataType : "xml",
		        url: '<%=request.getContextPath()%>/getCitizenDetails?cidNo='+identification_no,
		        success: function(xml)
		        {
		            $(xml).find('xml-response').each(function()
		            { 
		            	 var cid = $(this).find('cid').text();
		                  var name = $(this).find('name').text();
				          var gender = $(this).find('gender').text();
				          var age = $(this).find('age').text();
				          
				          $('#identification_no').val(cid);
				          $('#guest_name').val(name);
				          $('#gender').val(gender);
				          $('#age').val(age);
				          
				          $('#personImage').attr('src', 'http://www.citizenservices.gov.bt/BtImgWS/ImageServlet?type=PH&cidNo='+cid);
		             });
		        }, error: function(data, textStatus, errorThrown) {
		          
	          	}
		    });
		} else if(identificationType == 2){
			$.ajax({
		        async: false,
		        cache: false,
		        type: 'GET',
		        dataType : "xml",
		        url: '<%=request.getContextPath()%>/getImmigrationDetails?workPermitNo='+identification_no,
		        success: function(xml)
		        {
		            $(xml).find('xml-response').each(function()
		            { 
		                 var name = $(this).find('name').text();
				          var gender = $(this).find('gender').text();
				          var mobileNo = $(this).find('contactno').text();
				          var worklocation = $(this).find('worklocation').text();
				          
				          $('#guest_name').val(name);
				          $('#gender').val(gender);
				          $('#contact_no').val(mobileNo);
				          $('#present_address').val(worklocation);
				          
				          $('#age').attr('readonly', false);
		             });
		        }, error: function(data, textStatus, errorThrown) {
		          
	          	}
		    });
		} else if(identificationType == 3){
			$.ajax({
		        async: false,
		        cache: false,
		        type: 'GET',
		        dataType : "xml",
		        url: '<%=request.getContextPath()%>/DrivingLicenseServlet?licenseNo='+identification_no,
		        success: function(xml)
		        {
		            $(xml).find('xml-response').each(function()
		            { 
		                 var name = $(this).find('name').text();
				          var gender = $(this).find('gender').text();
				          var age = $(this).find('age').text();
				          
				          $('#guest_name').val(name);
				          $('#gender').val(gender);
				          $('#age').val(age);
		             });
		        }, error: function(data, textStatus, errorThrown) {
		          
	          	}
		    });
		} else if(identificationType == 4){
			$.ajax({
		        async: false,
		        cache: false,
		        type: 'GET',
		        dataType : "xml",
		        url: '<%=request.getContextPath()%>/getPassportDetails?passportNo='+identification_no,
		        success: function(xml)
		        {
		            $(xml).find('xml-response').each(function()
		            { 
		                 var name = $(this).find('name').text();
				          var gender = $(this).find('gender').text();
				          var age = $(this).find('age').text();
				          
				          $('#guest_name').val(name);
				          $('#gender').val(gender);
				          $('#age').val(age);
		             });
		        }, error: function(data, textStatus, errorThrown) {
		          
	          	}
		    });
		} else if(identificationType == 5){
			$.ajax({
		        async: false,
		        cache: false,
		        type: 'GET',
		        dataType : "xml",
		        url: '<%=request.getContextPath()%>/getBarcodeEnrollmentDetails?barcodeNo='+identification_no,
		        success: function(xml)
		        {
		            $(xml).find('xml-response').each(function()
		            { 
		                 var identificationNo = $(this).find('identificationNo').text();
				          var identificationType = $(this).find('identificationType').text();
				          var nationality = $(this).find('nationality').text();
				          var name = $(this).find('name').text();
				          var gender = $(this).find('gender').text();
				          var age = $(this).find('age').text();
				          var presentAddress = $(this).find('presentAddress').text();
				          var contactNo = $(this).find('contactNo').text();
				          
				          $('#identification_no').val(identificationNo);
				          $('#identificationType').val(identificationType);
				          $('#nationality_id').val(nationality);
				          $('#guest_name').val(name);
				          $('#gender').val(gender);
				          $('#age').val(age);
				          $('#present_address').val(presentAddress);
				          $('#contact_no').val(contactNo);
		             });
		        }, error: function(data, textStatus, errorThrown) {
		          
	          	}
		    });
		}
	}
	/* function submitApplication(){
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
	}  */
	function fnValidateLogin(){
		var context = "<%=request.getContextPath()%>";
		$.ajax({
			type : "POST",
			url : context+ '/entryExit.html?method=addGuest',
			data : $('form').serialize(),
			cache : false,
			dataType : "html",
			success : function(responseText) {
				$("#msgDiv").html(responseText);
				$("#msgDiv").show();
			}
		});
	}
</script>