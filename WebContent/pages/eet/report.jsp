<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-nested.tld" prefix="nested"%>
<%
	String type = (String) request.getAttribute("type");
	String fileName = "";
	if(type.equalsIgnoreCase("ENTRY"))
		fileName = "List of total entry for today";
	else if(type.equalsIgnoreCase("EXIT"))
		fileName = "List of total exit for today";
	else
		fileName = "List of total alerts raised for today";	
%>
<div class="col-xs-12">
	<div class="box box-primary">
		<div class="box-body">
			<div class="table-responsive">
				<table class="table table-sorted table-bordered" id="report-table">
					<thead>
						<tr>
							<th>Sl.No</th>
							<th>Identification No</th>
							<th>Identification Type</th>
							<th>Name</th>
							<th>Gender</th>
							<th>DOB</th>
							<th>Contact No</th>
							<th>Entry(or)Exit</th>
							<th>Time</th>
							<th>Gate</th>
							<th>Dzongkhag</th>
							<th>Remarks</th>
						</tr>
					</thead>
					<tbody>
						<logic:iterate id="report" name="reportList" type="bt.gov.moh.eet.dto.GuestLogDTO" indexId="idx">
							<%
								int i = idx.intValue();
							%>
							<tr>
								<td><%=++i %></td>
								<td><bean:write name="report" property="identificationNo"/></td>
								<td><bean:write name="report" property="identificationType"/></td>
								<td><bean:write name="report" property="guestName"/></td>
								<td><bean:write name="report" property="gender"/></td>
								<td><bean:write name="report" property="dob"/></td>
								<td><bean:write name="report" property="contactNo"/></td>
								<td><bean:write name="report" property="entryOrExit"/></td>
								<td><bean:write name="report" property="transactionTime"/></td>
								<td><bean:write name="report" property="gateName"/></td>
								<td><bean:write name="report" property="dzongkhagName"/></td>
								<td><bean:write name="report" property="alertRemarks"/></td>
							</tr>
						</logic:iterate>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</div>

<script>

var fileName = "<%=fileName%>";
$(function () {
	$("#report-table").DataTable({
		dom: 'Bfrtip',
        buttons: [
			{
			    extend: 'copyHtml5',
			    title: fileName,
			    text: '<i class="fa fa-files-o"></i>',
			    titleAttr: 'Copy'
			},
			{
			    extend: 'csvHtml5',
			    title: fileName,
			    text:      '<i class="fa fa-file-text-o"></i>',
                titleAttr: 'CSV'
			},
			{
			    extend: 'excelHtml5',
			    title: fileName,
			    text:      '<i class="fa fa-file-excel-o"></i>',
                titleAttr: 'Excel'
			},
			{
			    extend: 'pdfHtml5',
			    title: fileName,
			    text:      '<i class="fa fa-file-pdf-o"></i>',
                titleAttr: 'PDF'
			}
        ]
    });
});

</script>