<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<style>
#searchForm .error {
	color: red;
}

p.groove {
	border-style: groove;
}
p.groove {
    width: fit-content;
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
	<h1>Entry Exit Tracker</h1>
	<ol class="breadcrumb">
		<li><a
			href="<%=request.getContextPath()%>/login.html?q=dashboard"><i
				class="fa fa-dashboard"></i> Home</a></li>
		<li class="active">Enrollment</li>
	</ol>
</section>

<html:form styleClass="form-horizontal" styleId="searchForm"
	method="post" action="/enrollment.html">
	<section class="content">
		<div class="row">
			<div class="col-lg-9">
				<div id="messageDiv" style="display: none;"></div>
				<div class="box box-primary">
					<div class="box-body">
						<div class="form-group">
							<label class="control-label col-sm-4">Are you residing
								across border?<font color='red'>*</font>
							</label>
							<div class="col-sm-4" id="residenceFlagDiv">
								<div class="custom-control custom-radio">
									<input type="radio" class="custom-control-input" id="entry"
										name="residenceFlag" value="Y"> <label
										class="custom-control-label" for="entry">Yes</label>&nbsp;&nbsp;&nbsp;

									<input type="radio" class="custom-control-input" id="exit"
										name="residenceFlag" value="N"> <label
										class="custom-control-label" for="exit">No</label>
								</div>
								<span class="help-block" id="residenceFlagErrorMsg"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">Identification No.<font
								color='red'>*</font></label>
							<div class="col-sm-4" id="identificationNoDiv">
								<html:text property="identificationNo" styleClass="form-control"
									styleId="identificationNo" maxlength="20" />
									<span class="help-block" id="identificationNoErrorMsg"></span>
							</div>
							<label class="control-label col-sm-2">Name<font
								color='red'>*</font></label>
							<div class="col-sm-4" id="nameDiv">
								<html:text property="name" styleClass="form-control"
									styleId="name"></html:text>
									<span class="help-block" id="nameErrorMsg"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">Gender<font
								color='red'>*</font></label>
							<div class="col-sm-4" id="genderDiv">
								<select class="form-control" id="gender" name="gender">
									<option value="">--select gender--</option>
									<option value="M">Male</option>
									<option value="F">Female</option>
								</select>
								<span class="help-block" id="genderErrorMsg"></span>
							</div>
							<label class="control-label col-sm-2">Age<font
								color='red'>*</font></label>
							<div class="col-sm-4" id="ageDiv">
								<html:text property="age" styleClass="form-control"
									styleId="age" maxlength="3" onkeypress="return numbersonly(event,'age');"></html:text>
									<span class="help-block" id="ageErrorMsg"></span>
							</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-2">Nationality<font
								color='red'>*</font></label>
							<div class="col-sm-4" id="nationalityDiv">
								<html:select styleClass="chzn-select form-control"
									styleId="nationality" property="nationality">
									<html:option value="">--select nationality--</html:option>
									<html:options collection="NATIONALITYLIST" property="headerId"
										labelProperty="headerName" />
								</html:select>
								<span class="help-block" id="nationalityErrorMsg"></span>
							</div>
							<label class="control-label col-sm-2">Mobile Number<font
								color='red'>*</font></label>
							<div class="col-sm-4" id="mobileNoDiv">
								<html:text property="mobileNo" styleClass="form-control"
									styleId="mobileNo" onkeypress="return numbersonly(event,'mobileNo');"></html:text>
									<span class="help-block" id="mobileNoErrorMsg"></span>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-8">
								<label class="control-label col-sm-3">Present Address<font
									color='red'>*</font></label>
								<div class="col-sm-9" id="presentAddressDiv">
									<html:textarea styleClass="form-control"
										property="presentAddress" rows="5" styleId="presentAddress"></html:textarea>
										<span class="help-block" id="presentAddressErrorMsg"></span>
								</div>
							</div>
					</div>
				</div>
				<div class="box-footer">
					<div id="msgDiv" style="display: none;"></div>
					<div class="text-center">
						<button class="btn btn-primary" type="button"
							onclick="fnValidateLogin()">
							<i class="fa fa-paper-plane"></i>&nbsp;&nbsp;Register Visitor
						</button>
					</div>
				</div>
			</div>
			</div>
			<div class="col-lg-3">
				<div class="row">
							<div class="col-sm-12">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />

								<div class="booth" id="imageDataDiv">
									<p class="groove">
										<canvas id="canvas" width="200" height="200"></canvas>
										<html:hidden property="imageData" styleId="imagePath" />
								</div>
								<span class="help-block" id="imageDataErrorMsg"></span>	
							</div>
						</div>
						<div class="row">
							<div class="col-sm-4">
								<div align="center">
									<button type="button" class="btn btn-primary"
										data-toggle="modal" data-target="#imageCaptureModal">
										Capture Image</button>
								</div>
							</div>
						</div>
						<div class="col-4">
							<%-- <jsp:include page="imagecapture.jsp"></jsp:include> --%>
							<div class="modal" tabindex="-1" role="dialog"
								id="imageCaptureModal">
								<div class="modal-dialog" role="document">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title">Capture Image</h5>
											<button type="button" class="close" data-dismiss="modal"
												aria-label="Close"></button>
										</div>
										<div class="modal-body">
											<div class="booth">
												<video id="video" playsinline autoplay></video>
											</div>
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-primary" id="snap">Take
												Photo</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
		</div>
	</section>
</html:form>

<script>

	$('#identificationNo').focus();
	
	function numbersonly(evt,value)
	{
		var charCode = (evt.which) ? evt.which : event.keyCode
		if (charCode < 48 || charCode > 57)
		{
		 if(charCode==8 || charCode==13)//back space
			return true;
				
		    $("#"+value+"Div").addClass('has-error');
			$("#"+value+"ErrorMsg").html('Enter Numerical Values Only...');
			setTimeout('$("#'+value+'Div").removeClass("has-error")',3000);
			$("#"+value+"ErrorMsg").show();
			setTimeout('$("#'+value+'ErrorMsg").hide()',3000);
		   return false;
		} 
		    return true;
	}
	function fnValidateLogin(){
		/* if ($('#canvas').val() == "") 
		{
			$('#imageDataDiv').addClass('has-error');
			$('#imageDataErrorMsg').html('Please capture the image');
			$('#imageDataErrorMsg').show();
			setTimeout('$("#imageDataDiv").removeClass("has-error")',8000);
			setTimeout('$("#imageDataErrorMsg").hide()',8000);
			return false;	
		}  */
		if ($('#identificationNo').val() == "" ) 
		{
			$('#identificationNoDiv').addClass('has-error');
			$('#identificationNoErrorMsg').html('Please enter Identification no.');
			$('#identificationNoErrorMsg').show();
			setTimeout('$("#identificationNoDiv").removeClass("has-error")',8000);
			setTimeout('$("#identificationNoErrorMsg").hide()',8000);
			return false;	
		}
		if ($('#name').val() == "" ) 
		{
			$('#nameDiv').addClass('has-error');
			$('#nameErrorMsg').html('Name is requried.');
			$('#nameErrorMsg').show();
			setTimeout('$("#nameDiv").removeClass("has-error")',8000);
			setTimeout('$("#nameErrorMsg").hide()',8000);
			return false;	
		}
		if ($('#age').val() == "" ) 
		{
			$('#ageDiv').addClass('has-error');
			$('#ageErrorMsg').html('Age is requried.');
			$('#ageErrorMsg').show();
			setTimeout('$("#ageDiv").removeClass("has-error")',8000);
			setTimeout('$("#nameErrorMsg").hide()',8000);
			return false;	
		}
		if ($('#gender').val() == "" ) 
		{
			$('#genderDiv').addClass('has-error');
			$('#genderErrorMsg').html('Gender is requried.');
			$('#genderErrorMsg').show();
			setTimeout('$("#genderDiv").removeClass("has-error")',8000);
			setTimeout('$("#genderErrorMsg").hide()',8000);
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
		if ($('#mobileNo').val() == "" ) 
		{
			$('#mobileNoDiv').addClass('has-error');
			$('#mobileNoErrorMsg').html('Contact No. is requried.');
			$('#mobileNoErrorMsg').show();
			setTimeout('$("#mobileNoDiv").removeClass("has-error")',8000);
			setTimeout('$("#mobileNoErrorMsg").hide()',8000);
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
		var context = "<%=request.getContextPath()%>";
		$.ajax({
			type : "POST",
			url : context + '/enrollment.html',
			data : $('form').serialize(),
			cache : false,
			dataType : "html",
			success : function(responseText) {
				$("#msgDiv").html(responseText);
				$("#msgDiv").show();
				$('#searchForm').trigger("reset");

			}
		});
	}

	'use strict';

	const
	video = document.getElementById('video');
	const
	canvas = document.getElementById('canvas');
	const
	snap = document.getElementById('snap');
	const
	errorMsgElement = document.getElementById('spanErrorMsg');

	const
	constraints = {
		audio : false,
		video : {
			width : 200,
			height : 200
		}
	};

	async function init() {
		try {
			const
			stream = await
			navigator.mediaDevices.getUserMedia(constraints);
			handleSuccess(stream);
		} catch (e) {
			errorMsgElement.innerHTML = 'navigator.getUserMedia.error:${e.toString()}';
		}
	}

	function handleSuccess(stream) {
		window.stream = stream;
		video.srcObject = stream;
	}

	init();
	var context = canvas.getContext('2d');
	var imageData;

	snap.addEventListener('click', function() {

		context.drawImage(video, 0, 0, 200, 200);
		var imageHeight = video.height;
		var imageWidth = video.width;
		//imageData = context.getImageData(0, 0, imageWidth, imageHeight);

		const
		imageURL = canvas.toDataURL('image/jpeg');
		$('#imagePath').val(imageURL);

		$('#imageCaptureModal').modal('hide');

		stopRecording();
	}, false);

	function stopRecording() {
		let
		stream = video.nativeElement.srcObject;
		let
		tracks = stream.getTracks();

		tracks.forEach(function(track) {
			track.stop();
		});

		video.nativeElement.srcObject = null;
		video.nativeElement.stop();
	}
</script>