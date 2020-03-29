<%@page import="bt.gov.moh.eet.vo.UserDetailsVO"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<link href="<%=request.getContextPath()%>/css/chosen.min.css" rel="stylesheet" />
<script src="<%=request.getContextPath()%>/js/chosen.jquery.min.js"></script>
<style>
	#guestLogForm .error { color: red; }
	
	.booth {
	    background-color: #ccc;
	    border: 10px solid #ddd;
	    margin: 0 auto;
	}
</style>
 <%
	UserDetailsVO userDetails = null;
	String gateId = null;
	if(session.getAttribute("userdetails")!=null)
	{
		userDetails = (UserDetailsVO) session.getAttribute("userdetails");
		gateId = userDetails.getGateId();
	}
%>
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
			<div class="box box-primary" id="qrDisplayDiv" style="display:none">
				<div class="box-header">
					<h4 class="box-title">Guest QR Code</h4>
				</div>
				<div class="box-body">
					<div class="booth" align="center">
						<img id="qrImg"/>
					</div>
				</div>
			</div>
		</div>
		
		<div class="col-md-9">
			<div class="box box-primary">
				<div class="box-body">
					
					<html:form styleClass="form-horizontal" styleId="guestLogForm" method="post" action="/guestlog.html">
					
						<div class="form-group">
							<label class="control-label col-sm-4">Is He/She Entrying or Exiting?</label>
							<div class="col-sm-4">
								<html:select property="entryOrExit" styleId="entryOrExit" styleClass="form-control" onchange="showForm(this.value)">
									<html:option value=""></html:option>
									<html:option value="ENTRY">Entry</html:option>
									<html:option value="EXIT">Exit</html:option>
								</html:select>
							</div>
							<div class="col-sm-4" align="center">
								<div class="hidden-lg hidden-md"><br/></div>
								<button type="button" id="qrScannerBtn" class="btn btn-primary" onclick="triggerCamera()" data-toggle="modal" data-target="#scan-modal" style="display:none;">
									<i class="fa fa-search"></i> Scan QR Code
								</button>
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
									<label class="control-label">Date of Birth<font color='red'>*</font></label>
									<html:text property="dob" styleId="dob" styleClass="form-control"></html:text>
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
									<label class="control-label">
	       								Resident Flag<font color='red'>*</font>
	       								<a href="#" data-toggle="tooltip" data-html="true" data-placement="right" title="" data-original-title="Bhutanese living in India or Foreign Nationals living in Bhutan - Select YES. Else all should be selected as NO">
	       									<i class="fa fa-question-circle fa-1x" style="padding-top: 3px;cursor:pointer"></i>
	       								</a>
	       							</label>
	                   				<html:select property="residenceFlag" styleId="residenceFlag" styleClass="form-control">
	                   					<html:option value=""></html:option>
	                   					<html:option value="Y">Yes</html:option>
	                   					<html:option value="N">No</html:option>
	                   				</html:select>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-6">
									<label class="control-label">Reason<font color='red'>*</font></label>
									<html:select property="reason" styleId="reason" styleClass="form-control" onchange="checkReasonType()">
										<html:option value=""></html:option>
										<html:options collection ="REASONLIST" property="headerId" labelProperty="headerName"/>
									</html:select>
								</div>
								<div class="col-sm-6" id="nextEntryGateDiv" style="display:none">
									<label class="control-label" id="identificationLbl">Next Entry Gate<font color='red'>*</font></label>
									<html:select property="nextEntryGate" styleId="nextEntryGate" styleClass="form-control" onchange="checkGate(this.value)">
										<html:option value="0"></html:option>
										<html:options collection ="GATELIST" property="headerId" labelProperty="headerName"/>
									</html:select>
									<span id="nextGateError" style="display:none"></span>
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-6" id="thermometerDiv">
									<label class="control-label" id="identificationLbl">Thermometer Reading(Celsius)<font color='red'>*</font></label>
									<html:text property="thermometerReading" styleId="thermometerReading" styleClass="form-control"></html:text>
								</div>
								<div class="col-sm-6">
									<label class="control-label" id="identificationLbl">Present Address<font color='red'>*</font></label>
									<html:textarea property="presentAddress" styleId="presentAddress" styleClass="form-control"></html:textarea>
								</div>
							</div>
							<div class="form-group">
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
					<input type="hidden" id="imagePath"/>
					<input type="hidden" id="imageId" name="imageId"/>
       				<div class="col-sm-12">
       					<button class="btn btn-success col-sm-4" type="button" onclick="reloadPage()" ><i class="fa fa-refresh"></i>&nbsp;<span>Refresh Form</span></button>
       					<button class="btn btn-primary col-sm-4" type="button" onclick="submitForm()" id="submitBtn"><i class="fa fa-plus"></i>&nbsp;<span id="btnLbl"></span></button>
       					<button class="btn btn-danger col-sm-4" type="button" onclick="markAsFlagged()" id="markBtn"><i class="fa fa-check"></i>&nbsp;<span>Mark as Flagged</span></button>
       				</div>
				</div>
			</div>
		</div>
	</div>
</section>

<div id="scan-modal" class="modal fade" tabindex="-1">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="blue bigger">Scan QR Code</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-xs-12" align="center"> 
          				<video id="preview" style="width:100%"></video>
          				<audio id="myAudio">
						  <source src="<%=request.getContextPath() %>/sound/beep.mp3" type="audio/mpeg">
						</audio>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script src="https://rawgit.com/schmich/instascan-builds/master/instascan.min.js"></script>
<script>

	$(function() {
		var context="<%=request.getContextPath()%>";
	 	$('.chzn-select').chosen();
	});

	$(document).ready(function() {
		$('[data-toggle="tooltip"]').tooltip();   
		
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
			  'dob':{
				  required:true
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
				  minlength: 2
			  },
			  'presentAddress':{
				  required:true
			  },
			  'residenceFlag':{
				 required: true
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
			  'dob':{
				  required: "Please enter a date of birth"
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
				  minlength: "Reading cannot be less then 2 digits"
			  },
			  'presentAddress':{
				  required: "Please enter a present address"
			  },
			  'residenceFlag':{
				  required: "Please select a residence flag"
			  }
		  	}
	  	});
	});
	
	function checkGate(selectedGateId){
		var userGateId = "<%=gateId%>";
		if(selectedGateId == userGateId){
			$('#nextEntryGate').val('');
			$('#nextGateError').html('<font color="red">Requested gate cannot be same as current gate</font>');
			$('#nextGateError').show();
		} else {
			$('#nextGateError').hide();
		}
	}

	function showForm(type){
		$('#formDiv').show();
		$('#formFooter').show();
		$('#qrScannerBtn').show();
		
		if(type == "EXIT"){
			$('#thermometerReading').val("0");
			$('#thermometerDiv').hide();
			$('#btnLbl').html("Save Exit Record");
		} else {
			$('#thermometerDiv').show();
			$('#btnLbl').html("Save Entry Record");
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
		
		$('#identificationNo').val("");
		$('#identificationNo').attr('readonly', false);
		$('#nationality').attr("style", "");
		$('#gender').attr("style", "");
		$('#residenceFlag').attr("style", "");
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
	
	function refreshImage(){
		var imagePath = $('#imagePath').val();
		
		if(imagePath == ""){
			var cidNo = $('#identificationNo').val();
			$('#personImage').attr('src', 'https://www.citizenservices.gov.bt/BtImgWS/ImageServlet?type=PH&cidNo='+cidNo);
		} else {
			$('#personImage').attr('src', '<%=request.getContextPath()%>/getImage?url='+imagePath);
		}
	}
	
	function getGuestDetails(idNo){
		$('#guestId').val("");
		$('#guestName').val("");
		$('#gender').val("");
		$('#dob').val("");
		$('#contactNo').val("");
		$('#nationality').val("");
		$('#presentAddress').val("");
		$('#imagePath').val("");
		$('#personImage').attr('src', '<%=request.getContextPath() %>/img/user2-160x160.jpg');
		
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
					var cidNo = $(this).find('cidNo').text();
					var name = $(this).find('name').text();
					var gender = $(this).find('gender').text();
					var dob = $(this).find('dob').text();
					var contactno = $(this).find('contactno').text();
					var nationality = $(this).find('nationality').text();
					var presentAddress = $(this).find('presentAddress').text();
					var residentflag = $(this).find('residentflag').text();
					var imagepath = $(this).find('imagepath').text();
					var dataType = $(this).find('data-type').text();
					
					setFieldValues(guestId, cidNo, name, gender, dob, contactno, nationality, presentAddress, imagepath, dataType, residentflag);
	            });
	        }, error: function(data, textStatus, errorThrown) {
	        	$('#guestId').val("");
				$('#guestName').val("");
				$('#gender').val("");
				$('#dob').val("");
				$('#contactNo').val("");
				$('#nationality').val("");
				$('#presentAddress').val("");
				$('#imagePath').val("");
				$('#personImage').attr('src', '<%=request.getContextPath() %>/img/user2-160x160.jpg');
				
				$('#guestId').attr('readonly', false);
				$('#guestName').attr('readonly', false);
				$('#gender').attr('readonly', false);
				$('#dob').attr('readonly', false);
				$('#contactNo').attr('readonly', false);
				$('#nationality').attr('readonly', false);
				$('#presentAddress').attr('readonly', false);
	        }
	    });
	}
	
	function setFieldValues(guestId, cidNo, name, gender, dob, contactno, nationality, presentAddress, imagepath, dataType, residentFlag){
		$('#identificationNo').attr('readonly', true);
		$('#guestId').val(guestId);
		$('#guestId').attr('readonly', true);
		
		if(dataType == "SYSTEM"){
			$('#guestName').val(name);
			$('#gender').val(gender);
			$('#dob').val(dob);
			$('#contactNo').val(contactno);
			$('#nationality').val(nationality);
			$('#presentAddress').val(presentAddress);
			$('#residenceFlag').val(residentFlag);
			$('#imagePath').val(imagepath);
			$('#personImage').attr('src', '<%=request.getContextPath()%>/getImage?url='+imagepath);
			
			$('#guestName').attr('readonly', true);
			$('#dob').attr('readonly', true);
			$('#contactNo').attr('readonly', true);
			$('#presentAddress').attr('readonly', true);
			$('#nationality').attr("style", "pointer-events: none;");
			$('#gender').attr("style", "pointer-events: none;");
			$('#residenceFlag').attr("style", "pointer-events: none;");
			$('#nationality').attr('readonly', true);
			$('#gender').attr('readonly', true);
			$('#residenceFlag').attr('readonly', true);
		} else if(dataType == "CITIZEN_API"){
			$('#guestName').val(name);
			$('#gender').val(gender);
			$('#dob').val(dob);
			$("#identificationType option:contains(CID)").attr('selected', 'selected');
			$("#nationality option:contains(Bhutan)").attr('selected', 'selected');
			
			var imagePathArray = new Array();
			imagePathArray = imagepath.split("#");
			$('#imageId').val(imagePathArray[0]);
			$('#imagePath').val(imagePathArray[1]);
			$('#personImage').attr('src', '<%=request.getContextPath()%>/getImage?url='+imagePathArray[1]);
			//$('#personImage').attr('src', 'https://www.citizenservices.gov.bt/BtImgWS/ImageServlet?type=PH&cidNo='+cidNo);
			
			$('#guestName').attr('readonly', true);
			$('#dob').attr('readonly', true);
			$('#nationality').attr('readonly', true);
			$('#gender').attr('readonly', true);
			$('#nationality').attr("style", "pointer-events: none;");
			$('#gender').attr("style", "pointer-events: none;");
		} else if(dataType == "RSTA_API"){
			$('#identificationNo').val(cidNo);
			$('#guestName').val(name);
			$('#dob').val(dob);
			$("#identificationType option:contains(CID)").attr('selected', 'selected');
			$("#nationality option:contains(Bhutan)").attr('selected', 'selected');
			
			var imagePathArray = new Array();
			imagePathArray = imagepath.split("#");
			$('#imageId').val(imagePathArray[0]);
			$('#imagePath').val(imagePathArray[1]);
			$('#personImage').attr('src', '<%=request.getContextPath()%>/getImage?url='+imagePathArray[1]);
			//$('#personImage').attr('src', 'https://www.citizenservices.gov.bt/BtImgWS/ImageServlet?type=PH&cidNo='+cidNo);
			
			$('#guestName').attr('readonly', true);
			$('#dob').attr('readonly', true);
		} else if(dataType == "IMMIGRATION_API"){
			$('#guestName').val(name);
			$('#gender').val(gender);
			$('#nationality').val(nationality);
			$('#presentAddress').val(presentAddress);
			$('#dob').addClass('datepicker');
			
			$('#guestName').attr('readonly', true);
			$('#gender').attr('readonly', true);
			$('#presentAddress').attr('readonly', true);
			$('#dob').attr('readonly', true);
			$('#nationality').attr('readonly', true);
			$('#nationality').attr("style", "pointer-events: none;");
			$('#gender').attr("style", "pointer-events: none;");
		} else if(dataType == "PASSPORT_API"){
			$('#guestName').val(name);
			$('#gender').val(gender);
			$('#dob').val(dob);
			$("#identificationType option:contains(CID)").attr('selected', 'selected');
			$("#nationality option:contains(Bhutan)").attr('selected', 'selected');
			
			var imagePathArray = new Array();
			imagePathArray = imagepath.split("#");
			$('#imageId').val(imagePathArray[0]);
			$('#imagePath').val(imagePathArray[1]);
			$('#personImage').attr('src', '<%=request.getContextPath()%>/getImage?url='+imagePathArray[1]);
			//$('#personImage').attr('src', 'https://www.citizenservices.gov.bt/BtImgWS/ImageServlet?type=PH&cidNo='+cidNo);
			
			$('#gender').attr('readonly', true);
			$('#guestName').attr('readonly', true);
			$('#gender').attr("style", "pointer-events: none;");
			$('#dob').attr('readonly', true);
		}
	}
	
	function submitForm(){
		if($('#guestLogForm').valid()) 
		{
			$('#submitBtn').attr('disabled', 'disabled');
			$('#markBtn').attr('disabled', 'disabled');
			
			var identificationNo = $('#identificationNo').val();
			var identificationType = $('#identificationType').val();
			
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
			   
	  		var options = {target:'#msgDiv',url:'<%=request.getContextPath()%>/guestlog.html?method=guestLog&transactionType=save',type:'POST',data: $("#guestLogForm").serialize()}; 
			$("#guestLogForm").ajaxSubmit(options);
			$('#msgDiv').show();
			setTimeout($.unblockUI, 100);
			
			var identificationTypeTxt = $("#identificationType option:selected").text();
			
			if(identificationTypeTxt != "QR Code"){
				generateQRCode(identificationNo, identificationType);
			}
		}
	}
	
	function markAsFlagged(){
		$('#submitBtn').attr('disabled', 'disabled');
		$('#markBtn').attr('disabled', 'disabled');
		
		$('#thermometerReading').val("0");
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
		   
		var options = {target:'#msgDiv',url:'<%=request.getContextPath()%>/guestlog.html?method=guestLog&transactionType=mark',type:'POST',data: $("#guestLogForm").serialize()}; 
		$("#guestLogForm").ajaxSubmit(options);
		$('#msgDiv').show();
		setTimeout($.unblockUI, 100);
	}
	
	function generateQRCode(identificationNo, identificationType){
		$('#qrImg').attr('src', '<%=request.getContextPath()%>/guestqrcode?qrCodeText='+identificationNo+'&type='+identificationType+'&width=200&height=200');
		$('#qrDisplayDiv').show();
	}
	
	function triggerCamera(){
		var scanner = new Instascan.Scanner({ video: document.getElementById('preview'), scanPeriod: 5, mirror: false });
		scanner.addListener('scan',function(content){
			getIdentificationTypeByIdNo(content);
			var x = document.getElementById("myAudio"); 
			x.play();
			scanner.stop();
		});
		
		Instascan.Camera.getCameras().then(function (cameras){
			if(cameras.length>0){
				scanner.start(cameras[0]);
				$('[name="options"]').on('change',function(){
					if($(this).val()==1){
						if(cameras[0]!=""){
							scanner.start(cameras[0]);
						}else{
							alert('No Front camera found!');
						}
					}else if($(this).val()==2){
						if(cameras[1]!=""){
							scanner.start(cameras[1]);
						}else{
							alert('No Back camera found!');
						}
					}
				});
			}else{
				console.error('No cameras found.');
			}
		})
	}
	
	var identificationNo, identificationType;
	function getIdentificationTypeByIdNo(content){
		
		if(content.indexOf('#') > 0){
			var contentArray = new Array();
			contentArray = content.split("#");
			identificationNo = contentArray[0];
			identificationType = contentArray[1];
			$('#identificationNo').val(identificationNo);
			$('#identificationType').val(identificationType);
			
			getGuestDetails(identificationNo);
		} else {
			$('#identificationNo').val(content);
			$("#identificationType option:contains(QR Code)").attr('selected', 'selected');
			
			getGuestDetails(content);
		}
		
		$('#scan-modal').modal('hide');
	}
	
	function reloadPage(){
	   	var url = "<%=request.getContextPath()%>/redirect.html?q=MANAGE_ENTRY_EXIT"
	   	$("#contentDisplayDiv").load(url);
	   	$('#contentDisplayDiv').show();
    }

</script>          