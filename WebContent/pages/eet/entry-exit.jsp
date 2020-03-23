<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<style>
	#searchForm .error { color: red; }
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

<html:form styleClass="form-horizontal" styleId="searchForm" method="post" action="/entryExit">
	<section class="content">
		<div class="row">
			<div class="col-lg-12">
				<div id="messageDiv" style="display: none;"></div>
	       		<div class="box box-primary">
	       			<div class="box-body">
	       				<div class="form-group">
		                   		<label class="control-label col-sm-2">Are you Existing or Entering ?<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<div class="custom-control custom-radio">
								  <input type="radio" class="custom-control-input" id="entry" name="entryexit">
								  <label class="custom-control-label" for="entry">Entry</label>
								</div>
								<div class="custom-control custom-radio">
								  <input type="radio" class="custom-control-input" id="exit" name="entryexit">
								  <label class="custom-control-label" for="exit">Exit</label>
								</div>
                   				</div>
	       					<label class="control-label col-sm-2">Identification Type<font color='red'>*</font></label>
	       					<div class="col-sm-4">
	       						<select class="form-control" id="identificationType">
	       							<option value ="">--select type--</option>
	                   			</select>
	       					</div>
	       				</div>
	       				<div class="form-group">
		                   		<label class="control-label col-sm-2">Identification No.<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<html:text property="identificationNo" styleClass="form-control" styleId="identificationNo"></html:text>
                   				</div>
	       					<label class="control-label col-sm-2">Name<font color='red'>*</font></label>
	       					<div class="col-sm-4">
	       						<html:text property="name" styleClass="form-control" styleId="name" readonly="true"></html:text>
	       					</div>
	       				</div>
	       				<div class="form-group">
		                   		<label class="control-label col-sm-2">Gender<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<select class="form-control" id="gender" name="gender" readonly="true">
	       							<option value ="">--select gender--</option>
	       							<option value ="M">Male</option>
	       							<option value ="F">Female</option>
	                   			</select>
                   				</div>
	       					<label class="control-label col-sm-2">Age<font color='red'>*</font></label>
	       					<div class="col-sm-4">
	       						<html:text property="age" styleClass="form-control" styleId="age" readonly="true"></html:text>
	       					</div>
	       				</div>
	       				<div class="form-group">
		                   		<label class="control-label col-sm-2">Nationality<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<select class="form-control" id="nationality">
	                   					<option value ="">--select type--</option>
	                   				</select>
                   				</div>
	       					<label class="control-label col-sm-2">Present Address<font color='red'>*</font></label>
	       					<div class="col-sm-4">
	       						<textarea class="form-control" rows="5" id="presentAddress"></textarea>
	       					</div>
	       				</div>
	       				<div class="form-group">
		                   		<label class="control-label col-sm-2">Reason<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<select class="form-control" id="reasonId">
	                   					<option value ="">--select type--</option>
	                   				</select>
                   				</div>
	       					<label class="control-label col-sm-2">Reason<font color='red'>*</font></label>
	       					<div class="col-sm-4">
	       						<textarea class="form-control" rows="5" id="reason"></textarea>
	       					</div>
	       				</div>
	       				<div class="form-group">
		                   		<label class="control-label col-sm-2">Thermometer Reading<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   					<html:text property="thermometerReading" styleClass="form-control" styleId="thermometerReading"></html:text>
                   				</div>
	       				</div>
	       			</div>
	       			<div class="box-footer">
	       				<div class="text-right">
	       					<button class="btn btn-primary" type="button"><i class="fa fa-search"></i>&nbsp;Enter</button>
	       				</div>
	       			</div>
	       		</div>
	       	</div>
		</div>
		
	</section>
</html:form>

<script>

	var context = "<%=request.getContextPath()%>";
	

</script>