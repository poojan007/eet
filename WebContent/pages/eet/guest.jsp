<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<style>
	#searchForm .error { color: red; }
</style>

<section class="content-header">
  <h1>
       Page TItle
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
		                   		<label class="control-label col-sm-2">Test&nbsp;<font color='red'>*</font></label>
	                   			<div class="col-sm-4">
	                   				<select class="form-control" id="agentId">
	                   				</select>
                   				</div>
	       					<label class="control-label col-sm-2">Date of Journey<font color='red'>*</font></label>
	       					<div class="col-sm-4">
	       						<html:text property="test" styleClass="form-control datepicker" styleId="test" readonly="true"></html:text>
	       					</div>
	       				</div>
	       			</div>
	       			<div class="box-footer">
	       				<div class="text-right">
	       					<button class="btn btn-primary" type="button"><i class="fa fa-search"></i>&nbsp;Search</button>
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
