<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<style>
	#enrollmentForm .error { color: red; }
	.booth {
	    width: 220px;;
	    background-color: #ccc;
	    border: 10px solid #ddd;
	    margin: 0 auto;
	}
</style>
<link href="<%=request.getContextPath()%>/css/chosen.min.css"
	rel="stylesheet" />
<script src="<%=request.getContextPath()%>/js/chosen.jquery.min.js"></script>
<script language="javascript" type="text/javascript">
$(function() {
	var context="<%=request.getContextPath()%>";
	 $('.chzn-select').chosen();
});

</script>
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
							<html:hidden property="imageData" styleId="imagePath" />
						</div>
						<hr/>
						<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#imageCaptureModal">
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
			</div>
			
			<div class="col-md-8">
				<div class="box box-primary">
	       			<div class="box-body">
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
	       						<label class="control-label">Date of Birth<font color='red'>*</font></label>
	       						<div class="input-group">
					                <html:text property="dob" styleClass="form-control datepicker" styleId="dob" readonly="true"></html:text>
					                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
				              	</div>
	       					</div>
	       				</div>
	       				<div class="form-group">
	       					<div class="col-sm-6">
	       						<label class="control-label">Nationality<font color='red'>*</font></label>
                  				<html:select styleClass="form-control" styleId="nationality" property="nationality">
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
	       					<button class="btn btn-primary" type="button" onclick="fnValidateLogin()">
	       						<i class="fa fa-plus"></i>&nbsp;Save
	       					</button>
	       				</div>
	       			</div>
       			</div>
			</div>
       	</div>
	</section>
</html:form>

<script>

	$('#identificationNo').focus();
	
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
			  'dob':{
				  required:true
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
			  'dob':{
				  required: "Please enter a date of birth"
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

	function fnValidateLogin(){
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
					$('#enrollmentForm').trigger("reset");
					setTimeout('reloadPage()', 3000);
				}
			});
		}
	}

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
        let stream = video.nativeElement.srcObject;
        let tracks = stream.getTracks();

        tracks.forEach(function (track) {
            track.stop();
        });

        video.nativeElement.srcObject = null;
        video.nativeElement.stop();
    }
    
    function reloadPage(){
		var url = "<%=request.getContextPath()%>/redirect.html?q=MANAGE_ENROLLMENT"
		$("#contentDisplayDiv").load(url);
		$('#contentDisplayDiv').show();
	}

</script>