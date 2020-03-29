<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<style>
	#reportForm .error { color: red; }
</style>
<section class="content-header">
  	 <h1> 
  	 	Reports
  	 	<small>generate historical reports</small>
  	 </h1>
     <ol class="breadcrumb">
       <li><a href="<%=request.getContextPath()%>/login.html?q=dashboard"><i class="fa fa-dashboard"></i> Home</a></li>
       <li class="active">Reports</li>
     </ol>
</section>
<section class="content">
	<div class="row">
		<div class="col-xs-12">
			<div class="box box-primary">
				<div class="box-body">
					<form class="form-horizontal" id="reportForm" action="/report.html">
						<div class="form-group">
							<label class="control-label col-sm-3">Start Date<font color='red'>*</font></label>
							<div class="col-sm-3">
								<div class="input-group">
					                <input type="text" class="form-control datepicker" readonly="readonly" id="startDate" name="startDate"/>
					                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
				              	</div>
		   					</div>
		   					
		   					<label class="control-label col-sm-3">End Date<font color='red'>*</font></label>
		   					<div class="col-sm-3">
		               			<div class="input-group">
					                <input type="text" class="form-control datepicker" readonly="readonly" id="endDate" name="endDate"/>
					                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
				              	</div>
		   					</div>
						</div>
						<div class="form-group">
							<label class="control-label col-sm-3">Report Type<font color='red'>*</font></label>
							<div class="col-sm-3">
		               			<select class="form-control" id="type" name="type">
		               				<option value=""></option>
		               				<option value="ENTRY">Entry</option>
		               				<option value="EXIT">Exit</option>
		               				<option value="ALERT">Alert</option>
		               			</select>
		   					</div>
						</div>
					</form>
				</div>
				<div class="box-footer">
					<div class="pull-right">
						<button class="btn btn-primary" onclick="generateReport()">
							<i class="fa fa-search"></i> Generate Report
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row" id="resultDiv" style="display:none"></div>
</section>
	

<script>

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
		
		$('#reportForm').validate({
		  invalidHandler: function(form, validator) {
	       var errors = validator.numberOfInvalids();
	       if (errors) {                    
	           var firstInvalidElement = $(validator.errorList[0].element);
	           $('html,body').scrollTop(firstInvalidElement.offset().top);
	           firstInvalidElement.focus();
	       }
	   	  },
		  rules: {
			  'startDate':{
				  required: true
			  },
			  'endDate':{
				  required: true
			  },
			  'type':{
				  required: true
			  }
		  },
		  messages: {
			  'startDate':{
				  required: "Start date is required"
			  },
			  'endDate':{
				  required: "End date is required"
			  },
			  'type':{
				  required: "type is required"
			  }
		  	}
	  	});
	});

	function generateReport(){
		if($('#reportForm').valid()) 
		{
			var startDate = $('#startDate').val();
			var endDate = $('#endDate').val();
			var type = $('#type').val();
			
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
				type : "POST",
				url : '<%=request.getContextPath()%>/report.html?startDate='+startDate+'&endDate='+endDate+'&type='+type,
				data : $('form').serialize(),
				cache : false,
				dataType : "html",
				success : function(responseText) {
					$("#resultDiv").html(responseText);
					$("#resultDiv").show();
					setTimeout($.unblockUI, 1000); 
				}
			});
		}
	}

</script>