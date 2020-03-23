<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<style>
	#searchForm .error { color: red; }
	p.groove {border-style: groove;}
</style>

<section class="content-header">
  <h1>
       Entry Exit Tracker
     </h1>
     <ol class="breadcrumb">
       <li><a href="<%=request.getContextPath()%>/login.html?q=dashboard"><i class="fa fa-dashboard"></i> Home</a></li>
       <li class="active">Enrollment</li>
     </ol>
</section>

<html:form styleClass="form-horizontal" styleId="searchForm" method="post" action="/enrollment.html">
	<section class="content">
		<div class="row">
			<div class="col-lg-12">
				<div id="messageDiv" style="display: none;"></div>
	       		<div class="box box-primary">
	       			<div class="box-body">
	       				<div class="form-group">
		                   		<label class="control-label col-sm-2">Identification No.<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<html:text property="identificationNo" styleClass="form-control" styleId="identificationNo"></html:text>
                   				</div>
	       					<label class="control-label col-sm-2">Name<font color='red'>*</font></label>
	       					<div class="col-sm-4">
	       						<html:text property="name" styleClass="form-control" styleId="name"></html:text>
	       					</div>
	       				</div>
	       				<div class="form-group">
		                   		<label class="control-label col-sm-2">Gender<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<select class="form-control" id="gender" name="gender">
	       							<option value ="">--select gender--</option>
	       							<option value ="M">Male</option>
	       							<option value ="F">Female</option>
	                   			</select>
                   				</div>
	       					<label class="control-label col-sm-2">Age<font color='red'>*</font></label>
	       					<div class="col-sm-4">
	       						<html:text property="age" styleClass="form-control" styleId="age"></html:text>
	       					</div>
	       				</div>
	       				<div class="form-group">
		                   		<label class="control-label col-sm-2">Nationality<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<html:select styleClass="form-control" styleId="nationality" property="nationality">
	                   					<html:option value ="">--select type--</html:option>
	                   					<html:options collection ="NATIONALITYLIST" property="headerId" labelProperty="headerName"/>		
	                   					
	                   					<%-- <html:option value="248">Bhutanese</html:option>
                   						<html:option value="101">Indian</html:option> --%>
	                   				</html:select>
                   				</div>
                   				<label class="control-label col-sm-2">Mobile Number<font color='red'>*</font></label>
		       					<div class="col-sm-4">
		       						<html:text property="mobileNo" styleClass="form-control" styleId="mobileNo"></html:text>
		       					</div>
	       				</div>
	       			
	       				
	       				
	       				<div class="row">
	       				
	       				<div class="col-sm-8">
	       					  <label class="control-label col-sm-3">Present Address<font color='red'>*</font></label>
	       						<div class="col-sm-9">
	       							<html:textarea styleClass="form-control" property="presentAddress" rows="5" styleId="presentAddress"></html:textarea>
	       						</div>
	       						
	       				<div class="form-group">
		                   	<label class="control-label col-sm-3">Residing across border?<font color='red'>*</font></label>
                   			<div class="col-sm-4">
                   				<div class="custom-control custom-radio">
							  		<input type="radio" class="custom-control-input" id="entry" name="residenceFlag" value="Y">
							  		<label class="custom-control-label" for="entry">Yes</label>
								</div>
								<div class="custom-control custom-radio">
							 		<input type="radio" class="custom-control-input" id="exit" name="residenceFlag" value="N">
							  		<label class="custom-control-label" for="exit">No</label>
								</div>
                  				</div>

	       				</div>
	       				
	       				</div>
	       				
	       				<div class="col-sm-4">
	       				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						 
						            <div class="booth">
						               <p class="groove"> <canvas id="canvas" width="200" height="200"></canvas>
						                <html:hidden property="imageData" styleId="imagePath" />
						            </div>
	       				</div>
	       				</div>
	       				
	       				
	       				
	       				
	       					<div class="row">
	       				
	       				<div class="col-sm-8">
	       					  
	       				
	       				</div>
	       				
	       				<div class="col-sm-4">
	       				 <div align="center">
						                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#imageCaptureModal">
						                    Capture Image 
						                </button>
						            </div>
	       				</div>
	       				</div>
	       				
							<div class="col-4">
								<%-- <jsp:include page="imagecapture.jsp"></jsp:include> --%>
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
	       			<div class="box-footer">
	       				<div id="msgDiv" style="display:none;"></div>
	       				<div class="text-right">
	       					<button class="btn btn-primary" type="button" onclick="fnValidateLogin()" ><i class="fa fa-search"></i>&nbsp;Save</button>
	       				</div>
	       			</div>
	       		</div>
	       	</div>
		</div>
		
	</section>
</html:form>

<script>

	$('#identificationNo').focus();
	
	function fnValidateLogin(){
		var context = "<%=request.getContextPath()%>";
		$.ajax({
			type : "POST",
			url : context+ '/enrollment.html',
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
        //imageData = context.getImageData(0, 0, imageWidth, imageHeight);
        
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
	

</script>