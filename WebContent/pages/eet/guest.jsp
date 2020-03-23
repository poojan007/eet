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
	       				    <div class="row">
						        <div class="col-4">
						            <div class="row">
						                <div class="col-2">
						                    </div>
						                <div class="col-4">
						                    <div class="custom-control custom-radio custom-control-inline">
						                        <input type="radio" class="custom-control-input" id="entry" name="entry" value="Entry">
						                        <label class="custom-control-label" for="entry">Entry</label>
						                    </div>
						                </div>
						                <div class="col-4">
						                    <div class="custom-control custom-radio custom-control-inline">
						                        <input type="radio" class="custom-control-input" id="exit" name="entry" value="Exit" checked>
						                        <label class="custom-control-label" for="entry">Exit</label>
						                    </div>
						                </div>
						            </div>
						        </div>
						        <div class="col-4">
						            asd
						        </div>
						        <div class="col-4">
						            <%-- <jsp:include page="imagecapture.jsp"></jsp:include>
						            <input type="text" id="imageURL" name="imageURL"> --%>
						
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
