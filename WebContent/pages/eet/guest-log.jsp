<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<style>
	#guestLogForm .error { color: red; }
	
	.booth {
	    width: 180px;;
	    background-color: #ccc;
	    border: 10px solid #ddd;
	    margin: 0 auto;
	}
</style>
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

<section class="content">
	<div class="row">
		<div class="col-md-3">
			<div class="box box-primary">
				<div class="box-body" align="center">
					<div class="booth">
						<img id="personImage" src="<%=request.getContextPath() %>/img/user2-160x160.jpg" class="img-responsive">
					</div>
					<hr/>
					<button class="btn btn-primary" onclick="refreshImage()" type="button">
						<i class="fa fa-refresh"></i> Refresh Image
					</button>
				</div>
			</div>
		</div>
		
		<div class="col-md-9">
			<div class="box box-primary">
				<div class="box-body">
					
					<html:form styleClass="form-horizontal" styleId="guestLogForm" method="post" action="/guestlog.html">
					
						<div class="form-group">
							<label class="control-label col-sm-4">Is he/she Entrying or Exiting?</label>
							<div class="col-sm-4">
								<html:select property="entryOrExit" styleId="entryOrExit" styleClass="form-control" onchange="showForm(this.value)">
									<html:option value=""></html:option>
									<html:option value="ENTRY">Entry</html:option>
									<html:option value="EXIT">Exit</html:option>
								</html:select>
							</div>
						</div>
						<hr/>
						<div id="formDiv" style="display:none;">
							<div class="form-group">
								<div class="col-sm-6">
									<label class="control-label">Identification Type<font color='red'>*</font></label>
									<html:select property="identificationType" styleId="identificationType" styleClass="form-control" onchange="changeIdentificationLabel(this)">
										<html:option value=""></html:option>
										<html:options collection ="IDENTIFICATIONTYPELIST" property="headerId" labelProperty="headerName"/>
									</html:select>
								</div>
								<div class="col-sm-6">
									<label class="control-label" id="identificationLbl">Identification No<font color='red'>*</font></label>
									<html:text property="identificationNo" styleId="identificationNo" styleClass="form-control" onchange="getGuestDetails(this.value)"></html:text>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-6">
									<label class="control-label">Name<font color='red'>*</font></label>
									<html:text property="guestName" styleId="guestName" styleClass="form-control"></html:text>
								</div>
								<div class="col-sm-6">
									<label class="control-label" id="identificationLbl">Gender<font color='red'>*</font></label>
									<html:select property="gender" styleId="gender" styleClass="form-control">
										<html:option value=""></html:option>
										<html:option value="M">Male</html:option>
										<html:option value="F">Female</html:option>
									</html:select>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-6">
									<label class="control-label">Age<font color='red'>*</font></label>
									<html:text property="age" styleId="age" styleClass="form-control"></html:text>
								</div>
								<div class="col-sm-6">
									<label class="control-label">Contact No<font color='red'>*</font></label>
									<html:text property="contactNo" styleId="contactNo" styleClass="form-control"></html:text>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-6">
									<label class="control-label" id="identificationLbl">Nationality<font color='red'>*</font></label>
									<html:select property="nationality" styleId="nationality" styleClass="form-control">
										<html:option value=""></html:option>
										<html:options collection ="NATIONALITYLIST" property="headerId" labelProperty="headerName"/>
									</html:select>
								</div>
								<div class="col-sm-6">
									<label class="control-label">Reason<font color='red'>*</font></label>
									<html:select property="reason" styleId="reason" styleClass="form-control" onchange="checkReasonType()">
										<html:option value=""></html:option>
										<html:options collection ="REASONLIST" property="headerId" labelProperty="headerName"/>
									</html:select>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-6" id="nextEntryGateDiv" style="display:none">
									<label class="control-label" id="identificationLbl">Next Entry Gate<font color='red'>*</font></label>
									<html:select property="nextEntryGate" styleId="nextEntryGate" styleClass="form-control">
										<html:option value="0"></html:option>
										<html:options collection ="GATELIST" property="headerId" labelProperty="headerName"/>
									</html:select>
								</div>
								<div class="col-sm-6" id="thermometerReadingDiv" style="display:none">
									<label class="control-label" id="identificationLbl">Thermometer Reading(Celsius)<font color='red'>*</font></label>
									<html:text property="thermometerReading" styleId="thermometerReading" styleClass="form-control" value="0"></html:text>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-6">
									<label class="control-label" id="identificationLbl">Present Address<font color='red'>*</font></label>
									<html:textarea property="presentAddress" styleId="presentAddress" styleClass="form-control"></html:textarea>
								</div>
								<div class="col-sm-6">
									<label class="control-label" id="identificationLbl">Remarks</label>
									<html:textarea property="remarks" styleId="remarks" styleClass="form-control"></html:textarea>
								</div>
							</div>
						</div>
						<html:hidden property="guestId" styleId="guestId"/>
					</html:form>
					
				</div>
				<div class="box-footer" id="formFooter" style="display:none">
					<div id="msgDiv" style="display:none;"></div>
       				<div class="text-right">
       					<button class="btn btn-primary" type="button" onclick="submitForm()" ><i class="fa fa-plus"></i>&nbsp;<span id="btnLbl"></span></button>
       				</div>
				</div>
			</div>
		</div>
		
	</div>
</section>

<script>

	$(document).ready(function() {
		$('#guestLogForm').validate({
		  invalidHandler: function(form, validator) {
           var errors = validator.numberOfInvalids();
           if (errors) {                    
               var firstInvalidElement = $(validator.errorList[0].element);
               $('html,body').scrollTop(firstInvalidElement.offset().top);
               firstInvalidElement.focus();
           }
       	  },
		  rules: {
			  'entryOrExit':{
				  required:true
			  },
			  'identificationType':{
				  required:true
			  },
			  'identificationNo':{
				  required:true
			  },
			  'guestName':{
				  required:true
			  },
			  'gender':{
				  required:true
			  },
			  'age':{
				  required:true,
				  number: true,
				  maxlength: 2
			  },
			  'contactNo':{
				  required:true,
				  number: true,
				  maxlength: 10,
				  minlength: 8
			  },
			  'nationality':{
				  required:true
			  },
			  'reason':{
				  required:true
			  },
			  'nextEntryGate':{
				  required: function () {
		                return $("#reason option:selected").text() == "Travel";
	            	}
			  },
			  'thermometerReading':{
				  required: function () {
		                return $("#entryOrExit").val() == "ENTRY";
            		},
				  number: true,
				  maxlength: 3,
				  minlength: 2
			  },
			  'presentAddress':{
				  required:true
			  }
		  },
		  messages: {
			  'entryOrExit':{
				  required: "Please select a type"
			  },
			  'identificationType':{
				  required: "Please select identification type"
			  },
			  'identificationNo':{
				  required: "Please enter a identification no"
			  },
			  'guestName':{
				  required: "Please enter a name"
			  },
			  'gender':{
				  required: "Please select a gender"
			  },
			  'age':{
				  required: "Please enter an age",
				  number: "Only numbers allowed",
				  maxlength: "Age can be more then 2 digits"
			  },
			  'contactNo':{
				  required: "Please enter a contact no",
				  number: "Only numbers allowed",
				  maxlength: "Contact no cannot be more then 10 digits",
				  minlength: "Contact no cannot be less then 8 digits"
			  },
			  'nationality':{
				  required: "Please select a nationality"
			  },
			  'reason':{
				  required: "Please select a reason"
			  },
			  'nextEntryGate':{
				  required: "Please select next entry gate"
			  },
			  'thermometerReading':{
				  required: "Please enter a thermometer reading",
				  number: "Only numbers allowed",
				  maxlength: "Reading cannot be more then 3 digits",
				  minlength: "Reading cannot be less then 2 digits"
			  },
			  'presentAddress':{
				  required: "Please enter a present address"
			  }
		  	}
	  	});
	});

	function showForm(type){
		$('#formDiv').show();
		$('#formFooter').show();
		
		if(type == "EXIT"){
			$('#thermometerReading').val("0");
			$('#btnLbl').html("Save Exit Record");
		} else {
			$('#btnLbl').html("Save Entry Record")
		}
	}
	
	function changeIdentificationLabel(obj){
		var selectedText = $("#identificationType option:selected").text();
		
		if(selectedText == "CID"){
			$('#identificationLbl').html('CID Number<font color="red">*</font>');
		} else if(selectedText == "Work Permit"){
			$('#identificationLbl').html('Work Permit Number<font color="red">*</font>');
		} else if(selectedText == "Driving License"){
			$('#identificationLbl').html('Driving License Number<font color="red">*</font>');
		} else if(selectedText == "Passport"){
			$('#identificationLbl').html('Passport Number<font color="red">*</font>');
		} else if(selectedText == "Barcode"){
			$('#identificationLbl').html('Barcode Number<font color="red">*</font>');
		} else {
			$('#identificationLbl').html('Identification Number<font color="red">*</font>');
		}
	}
	
	function checkReasonType(){
		var selectedText = $("#reason option:selected").text();
		
		if(selectedText == "Travel") {
			$('#nextEntryGateDiv').show();
		}
		else {
			$('#nextEntryGateDiv').hide();
		}
	}
	
	function getGuestDetails(idNo){
		var entryOrExit = $('#entryOrExit').val();
		var identificationType = $('#identificationType').val();
		
		$.ajax({
	        async: false,
	        cache: false,
	        type: 'GET',
	        dataType : "xml",
	        url: '<%=request.getContextPath()%>/getGuestDetails?idNo='+idNo+'&identificationType='+identificationType+'&entryOrExit='+entryOrExit,
	        success: function(xml)
	        {
	            $(xml).find('xml-response').each(function()
	            { 
					var guestId = $(this).find('guestId').text();
					var name = $(this).find('name').text();
					var gender = $(this).find('gender').text();
					var age = $(this).find('age').text();
					var contactno = $(this).find('contactno').text();
					var nationality = $(this).find('nationality').text();
					var presentAddress = $(this).find('presentAddress').text();
					
					$('#guestId').val(guestId);
					$('#guestName').val(name);
					$('#gender').val(gender);
					$('#age').val(age);
					$('#contactNo').val(contactno);
					$('#nationality').val(nationality);
					$('#presentAddress').val(presentAddress);
					
					$('#guestId').attr('readonly', true);
					$('#guestName').attr('readonly', true);
					$('#gender').attr('readonly', true);
					$('#age').attr('readonly', true);
					$('#contactNo').attr('readonly', true);
					$('#nationality').attr('readonly', true);
					$('#presentAddress').attr('readonly', true);
	            });
	        }, error: function(data, textStatus, errorThrown) {
	        	$('#guestId').val("");
				$('#guestName').val("");
				$('#gender').val("");
				$('#age').val("");
				$('#contactNo').val("");
				$('#nationality').val("");
				$('#presentAddress').val("");
				
				$('#guestId').attr('readonly', false);
				$('#guestName').attr('readonly', false);
				$('#gender').attr('readonly', false);
				$('#age').attr('readonly', false);
				$('#contactNo').attr('readonly', false);
				$('#nationality').attr('readonly', false);
				$('#presentAddress').attr('readonly', false);
	        }
	    });
	}
	
	function submitForm(){
		if($('#guestLogForm').valid()) 
		{
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
			   
	  		var options = {target:'#msgDiv',url:'<%=request.getContextPath()%>/guestlog.html?method=guestLog',type:'POST',data: $("#guestLogForm").serialize()}; 
			$("#guestLogForm").ajaxSubmit(options);
			$('#msgDiv').show();
			setTimeout($.unblockUI, 100);
			setTimeout('reloadPage()', 3000);
		}
	}
	
	function reloadPage(){
	   	var url = "<%=request.getContextPath()%>/redirect.html?q=MANAGE_ENTRY_EXIT"
	   	$("#contentDisplayDiv").load(url);
	   	$('#contentDisplayDiv').show();
    }

</script>
</section>
          