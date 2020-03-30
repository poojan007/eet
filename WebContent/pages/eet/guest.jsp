<%@page import="java.util.Calendar"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<link href="<%=request.getContextPath()%>/css/chosen.min.css" rel="stylesheet" />
<script src="<%=request.getContextPath()%>/js/chosen.jquery.min.js"></script>
<style>
	#enrollmentForm .error { color: red; }
	.booth {
	    background-color: #ccc;
	    border: 10px solid #ddd;
	    margin: 0 auto;
	}
	
	.scandiv
	{
	    width: 100%;
	    height: auto;
	    max-height: 100%;
	}
</style>
<section class="content-header">
  	 <h1> 
  	 	Guest Enrollment
  	 	<small>register guest with no identification document</small>
  	 </h1>
     <ol class="breadcrumb">
       <li><a href="<%=request.getContextPath()%>/login.html?q=dashboard"><i class="fa fa-dashboard"></i> Home</a></li>
       <li class="active">Guest Enrollment</li>
     </ol>
</section>

<html:form styleClass="form-horizontal" styleId="enrollmentForm" method="post" action="/enrollment.html">
	<section class="content">
		<div class="row">
		
			<div class="col-md-4">
				<div class="box box-primary">
					<div class="box-body" align="center">
						<div class="booth">
							<canvas id="canvas" width="200" height="200"></canvas>
							<img id="personImage" src="<%=request.getContextPath() %>/img/user2-160x160.jpg" class="img-responsive" style="display:none">
							<html:hidden property="imageData" styleId="imagePath" />
						</div>
						<hr/>
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#imageCaptureModal" onclick="openCamera()">
		                    <i class="fa fa-camera"></i> Capture Image 
		                </button>
		                <div class="modal" tabindex="-1" role="dialog" id="imageCaptureModal">
						    <div class="modal-dialog" role="document">
						        <div class="modal-content">
						            <div class="modal-header">
						                <h5 class="modal-title">Capture Image</h5>
						                <button type="button" class="close" data-dismiss="modal" aria-label="Close"></button>
						            </div>
						            <div class="modal-body">
						                <div class="booth">
						                    <video id="video" playsinline autoplay></video>
						                </div>
						            </div>
						            <div class="modal-footer">
						                <button type="button" class="btn btn-primary" id="snap">Take Photo</button>
						            </div>
						        </div>
						    </div>
						</div>
					</div>
				</div>
				
				<div class="box box-primary" id="qrDisplayDiv" style="display:none">
					<div class="box-header">
						<h4 class="box-title">Guest QR Code</h4>
					</div>
					<div class="box-body">
						<div class="booth" align="center">
							<img id="qrImg" class="img-responsive"/>
						</div>
					</div>
				</div>
				
			</div>
			
			<div class="col-md-8">
				<div class="box box-primary">
	       			<div class="box-body">
	       				<div class="form-group">
	       					<div class="col-sm-12">
				              	<div class="input-group margin">
					                <input type="number" class="form-control" id="searchBar" placeholder="Search By Mobile Number" maxlength="10"/>
				                    <span class="input-group-btn">
				                      <button type="button" class="btn btn-info" onclick="searchGuest()"><i class="fa fa-search"></i> Search</button>
				                    </span>
				              	</div>
	       					</div>
	       				</div>
	       				<div class="form-group">
	       					<div class="col-sm-12">
	       						<div class="pull-right">
	       							<button type="button" class="btn btn-info btn-flat" data-toggle="modal" data-target="#scan-modal" onclick="triggerScanCamera()">
									  	<i class="fa fa-camera"></i> SCAN PRE-PRINTED QR CODE
								  	</button>
	       						</div>
	       					</div>
	       				</div>
	       				<div class="form-group">
	       					<div class="col-sm-6">
	       						<label class="control-label">Identification No.<font color='red'>*</font></label>
				                <html:text property="identificationNo" styleClass="form-control" styleId="identificationNo"/>
	       					</div>
	       					<div class="col-sm-6">
	       						<label class="control-label">Name<font color='red'>*</font></label>
	       						<html:text property="name" styleClass="form-control" styleId="name"></html:text>
	       					</div>
	       				</div>
	       				<div class="form-group">
	       					<div class="col-sm-6">
	       						<label class="control-label">Gender<font color='red'>*</font></label>
	       						<html:select property="gender" styleId="gender" styleClass="form-control">
	       							<option value =""></option>
	       							<option value ="M">Male</option>
	       							<option value ="F">Female</option>
	       						</html:select>
	       					</div>
	       					<div class="col-sm-6">
	       						<label class="control-label">Age<font color='red'>*</font></label>
	       						<input type="number" class="form-control" id="age" name="age" onchange="calculateDob(this.value)" min="0"/>
				                <html:hidden property="dob" styleClass="form-control datepicker" styleId="dob"></html:hidden>
	       					</div>
	       				</div>
	       				<div class="form-group">
	       					<div class="col-sm-6">
	       						<label class="control-label">Nationality<font color='red'>*</font></label>
                  				<html:select styleClass="form-control chzn-select" styleId="nationality" property="nationality">
                  					<html:option value =""></html:option>
                  					<html:options collection ="NATIONALITYLIST" property="headerId" labelProperty="headerName"/>		
                  				</html:select>
	       					</div>
	       					<div class="col-sm-6">
	       						<label class="control-label">Mobile Number<font color='red'>*</font></label>
	       						<html:text property="mobileNo" styleClass="form-control" styleId="mobileNo"></html:text>
	       					</div>
	       				</div>
       					<div class="form-group">
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
       						<div class="col-sm-6">
       							<label class="control-label">Present Address<font color='red'>*</font></label>
	   							<html:textarea styleClass="form-control" property="presentAddress" rows="5" styleId="presentAddress"></html:textarea>
       						</div>
	       				</div>
       				</div>
       				<div class="box-footer">
	       				<div id="msgDiv" style="display:none;"></div>
	       				<div class="text-right">
	       					<input type="hidden" id="guestId" name="guestId"/>
	       					<button class="btn btn-success" type="button" onclick="reloadPage()" id="refreshBtn">
	       						<i class="fa fa-refresh"></i>&nbsp;Refresh Form
	       					</button>
	       					&nbsp;
	       					<button class="btn btn-primary" type="button" onclick="fnValidateLogin()" id="saveBtn">
	       						<i class="fa fa-plus"></i>&nbsp;Save
	       					</button>
	       					<button class="btn btn-primary" type="button" onclick="updateQRCodeNumber()" id="updateBtn" style="display:none;">
	       						<i class="fa fa-check"></i>&nbsp;Update
	       					</button>
	       				</div>
	       			</div>
       			</div>
			</div>
       	</div>
	</section>
</html:form>

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
<script src="<%=request.getContextPath() %>/js/instascan.min.js"></script>
<script>

	var scanner;
	
	$('#identificationNo').focus();
	
	$("#scan-modal").on('hide.bs.modal', function(){
		 scanner.stop();
	});
	
	function triggerScanCamera(){
		scanner = new Instascan.Scanner({ video: document.getElementById('preview'), scanPeriod: 5, mirror: false });
		scanner.addListener('scan',function(content){
			$('#identificationNo').val(content);
			$('#identificationNo').attr('readonly', true);
			var x = document.getElementById("myAudio"); 
			x.play();
			$('#scan-modal').modal('hide');
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
	
	$(function() {
		var context="<%=request.getContextPath()%>";
	 	$('.chzn-select').chosen();
	});

	$(function(){
		$('.datepicker').datepicker({
			format: 'dd/mm/yyyy',
			autoclose: true
		}).next().on('click', function() {
			$(this).prev().focus();
		})
	});

	$(document).ready(function() {
	    $('[data-toggle="tooltip"]').tooltip();   
		
		$('#enrollmentForm').validate({
		  invalidHandler: function(form, validator) {
	       var errors = validator.numberOfInvalids();
	       if (errors) {                    
	           var firstInvalidElement = $(validator.errorList[0].element);
	           $('html,body').scrollTop(firstInvalidElement.offset().top);
	           firstInvalidElement.focus();
	       }
	   	  },
		  rules: {
			  'identificationNo':{
				  required: true
			  },
			  'name':{
				  required: true,
				  number: false
			  },
			  'gender':{
				  required: true
			  },
			  'age':{
				  required: true,
				  number: true,
				  maxlength: 3
			  },
			  'nationality':{
				  required:true
			  },
			  'mobileNo':{
				  required:true,
				  number: true,
				  maxlength: 10,
				  minlength: 8
			  },
			  'residenceFlag':{
				  required:true
			  },
			  'presentAddress':{
				  required:true
			  }
		  },
		  messages: {
			  'identificationNo':{
				  required: "Please enter a identification no"
			  },
			  'name':{
				  required: "Please enter the guest name",
				  number: "Name cannot have a number"
			  },
			  'gender':{
				  required: "Please select a gender"
			  },
			  'age':{
				  required: "Please provide an age",
				  number: "Age should be a number",
				  maxlength: "Age cannot be greater than 3 digits"
			  },
			  'nationality':{
				  required: "Please select a nationality"
			  },
			  'mobileNo':{
				  required: "Please enter a mobile number",
				  number: "Only numbers allowed",
				  maxlength: "Mobile no cannot be more then 10 digits",
				  minlength: "Mobile no cannot be less then 8 digits"
			  },
			  'residenceFlag':{
				  required: "Please select a residence flag"
			  },
			  'presentAddress':{
				  required: "Please enter a present address"
			  }
		  	}
	  	});
	});
	
	function calculateDob(age){
		var currentYear = "<%=Calendar.getInstance().get(Calendar.YEAR)%>";
		var birthYear = parseInt(currentYear) - parseInt(age);
		$('#dob').val("01/01/"+birthYear);
	}
	
	function searchGuest(){
		var mobileNo = $('#searchBar').val();
		
		if(mobileNo != ""){
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
			$.ajax({
		        async: false,
		        cache: false,
		        type: 'GET',
		        dataType : "xml",
		        url: '<%=request.getContextPath()%>/searchGuestDetails?mobileNo='+mobileNo+'&type=SEARCH',
		        success: function(xml)
		        {
		            $(xml).find('xml-response').each(function()
		            { 
						var guestId = $(this).find('guestId').text();
						var cidNo = $(this).find('cidNo').text();
						var name = $(this).find('name').text();
						var gender = $(this).find('gender').text();
						var dob = $(this).find('dob').text();
						var age = $(this).find('age').text();
						var contactno = $(this).find('contactno').text();
						var nationality = $(this).find('nationality').text();
						var presentAddress = $(this).find('presentAddress').text();
						var residentflag = $(this).find('residentflag').text();
						var imagepath = $(this).find('imagepath').text();
						var dataType = $(this).find('data-type').text();
						
						$('#guestId').val(guestId);
						$('#identificationNo').val(cidNo);
						$('#name').val(name);
						$('#gender').val(gender);
						$('#dob').val(dob);
						$('#age').val(age);
						$('#mobileNo').val(contactno);
						$('#nationality').val(nationality);
						$('#presentAddress').val(presentAddress);
						$('#residenceFlag').val(residentflag);
						$('#personImage').attr('src', '<%=request.getContextPath()%>/getImage?url='+imagepath);
						
						$('#guestId').attr("readonly", true);
						$('#identificationNo').val("");
						$('#name').attr("readonly", true);
						$('#gender').attr("style", "pointer-events: none;");
						$('#dob').attr("readonly", true);
						$('#age').attr("readonly", true);
						$('#dob').removeClass('datepicker');
						$('#mobileNo').attr("readonly", true);
						$('#nationality').attr("style", "pointer-events: none;");
						$('#presentAddress').attr("readonly", true);
						$('#residenceFlag').attr("style", "pointer-events: none;");
						
						$('#updateBtn').show();
						$('#personImage').show();
						$('#saveBtn').hide();
						$('#canvas').hide();
						setTimeout($.unblockUI, 1000); 
		            });
		        }, error: function(data, textStatus, errorThrown) {
		        	$('#guestId').val("");
					$('#identificationNo').val("");
					$('#name').val("");
					$('#gender').val("");
					$('#dob').val("");
					$('#dob').addClass('datepicker');
					$('#mobileNo').val("");
					$('#nationality').val("");
					$('#presentAddress').val("");
					$('#residenceFlag').val("");
					$('#personImage').attr('src', '<%=request.getContextPath() %>/img/user2-160x160.jpg');
					
					$('#guestId').attr("readonly", false);
					$('#identificationNo').attr("readonly", false);
					$('#name').attr("readonly", false);
					$('#gender').attr("style", "");
					$('#dob').attr("readonly", false);
					$('#mobileNo').attr("readonly", false);
					$('#nationality').attr("style", "");
					$('#presentAddress').attr("readonly", false);
					$('#residenceFlag').attr("style", "");
					
					$('#updateBtn').hide();
					$('#personImage').hide();
					$('#saveBtn').show();
					$('#canvas').show();
					setTimeout($.unblockUI, 1000); 
		        }
		    });
		}
	}
	
	function updateQRCodeNumber(){
		$('#updateBtn').attr('disabled', 'disabled');
		var identificationNo = $('#identificationNo').val();
		var guestId = $('#guestId').val();
		
		if(identificationNo != ""){
			$.ajax({
				type : "POST",
				url : '<%=request.getContextPath()%>/user.html?method=updateQRCodeNumber&identificationNo='+identificationNo+'&guestId='+guestId,
				data : $('form').serialize(),
				cache : false,
				dataType : "html",
				success : function(responseText) {
					$("#msgDiv").html(responseText);
					$("#msgDiv").show();
					setTimeout('reloadPage()', 5000);
				}
			});
		}
	}

	function fnValidateLogin(){
		$('#saveBtn').attr('disabled', 'disabled');
		var identificationNo = $('#identificationNo').val();
		if($('#enrollmentForm').valid()) 
		{
			$.ajax({
				type : "POST",
				url : '<%=request.getContextPath()%>/enrollment.html',
				data : $('form').serialize(),
				cache : false,
				dataType : "html",
				success : function(responseText) {
					$("#msgDiv").html(responseText);
					$("#msgDiv").show();
					
					//generateQRCode(identificationNo);
					
					//setTimeout('reloadPage()', 5000);
				}
			});
		}
	}
	
	function generateQRCode(identificationNo){
		$('#qrImg').attr('src', '<%=request.getContextPath()%>/guestqrcode?qrCodeText='+identificationNo+'&type=BARCODE_NUMBER&width=250&height=250');
		$('#qrDisplayDiv').show();
	}
	
	function openCamera(){
		'use strict';
	    const video = document.getElementById('video');
	    const canvas = document.getElementById('canvas');
	    const snap = document.getElementById('snap');
	    const errorMsgElement = document.getElementById('spanErrorMsg');

	    const constraints = {
	        audio: false,
	        video: {
	            width: 200,
	            height: 200
	        }
	    };

	    async function init(){
	        try{
	            const stream = await navigator.mediaDevices.getUserMedia(constraints);
	            handleSuccess(stream);
	        } catch (e){
	            errorMsgElement.innerHTML = 'navigator.getUserMedia.error:${e.toString()}';
	        }
	    }

	    function handleSuccess(stream){
	        window.stream = stream;
	        video.srcObject = stream;
	    }

	    init();
	    var context = canvas.getContext('2d');
	    var imageData;
	    
	    snap.addEventListener('click', function(){
	    	 
	        context.drawImage(video, 0, 0, 200, 200);
	        var imageHeight = video.height;
	        var imageWidth = video.width;
	        const imageURL = canvas.toDataURL('image/jpeg');
	        $('#imagePath').val(imageURL);
	        $('#imageCaptureModal').modal('hide');
	        stopRecording();
	    }, false);

	    function stopRecording(){
	        let stream = video.srcObject;
	        let tracks = stream.getTracks();

	        tracks.forEach(function (track) {
	            track.stop();
	        });

	        video.srcObject = null;
	        video.stop();
	    }
	}

    function reloadPage(){
		var url = "<%=request.getContextPath()%>/redirect.html?q=MANAGE_ENROLLMENT"
		$("#contentDisplayDiv").load(url);
		$('#contentDisplayDiv').show();
		window.stream = null;
        video.srcObject = null;
	}

</script>