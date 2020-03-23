<%
	String message = (String) request.getAttribute("message");

	if(message.equalsIgnoreCase("SAVE_SUCCESS")){
%>
		<div class="alert alert-success">
			<h4>Success!</h4>
			<p>
				Entry successfully saved.
			</p>
		</div>
<%
	}
%>



