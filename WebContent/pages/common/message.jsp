<%
	String message = (String) request.getAttribute("message");

	if(message.equalsIgnoreCase("SAVE_SUCCESS")){
%>
	<div class="alert alert-success" role="alert">
	  <h4 class="alert-heading">Success!</h4>
	  <p>Data has been successfully saved.</p>
	</div>
<%
	} else if(message.equalsIgnoreCase("SAVE_FAILURE")){
%>
	<div class="alert alert-danger" role="alert">
	  <h4 class="alert-heading">Failure!</h4>
	  <p>Data save failed, please try again later.</p>
	</div>
	<%
	}else if(message.equalsIgnoreCase("DUPLICATE_ENTRY")){
%>
	<div class="alert alert-danger" role="alert">
	  <h4 class="alert-heading">Failure!</h4>
	  <p>Data already exist.Please try with new identification number</p>
	</div>
<%
	} if(message.equalsIgnoreCase("UPDATE_SUCCESS")){
%>
	<div class="alert alert-success" role="alert">
	  <h4 class="alert-heading">Success!</h4>
	  <p>Data has been successfully updated.</p>
	</div>
<%
	} else if(message.equalsIgnoreCase("UPDATE_FAILURE")){
%>
	<div class="alert alert-danger" role="alert">
	  <h4 class="alert-heading">Failure!</h4>
	  <p>Data update failed, please try again later.</p>
	</div>
<%
	} if(message.equalsIgnoreCase("DELETE_SUCCESS")){
%>
	<div class="alert alert-success" role="alert">
	  <h4 class="alert-heading">Success!</h4>
	  <p>Data has been successfully deleted.</p>
	</div>
<%
	} else if(message.equalsIgnoreCase("DELETE_FAILURE")){
%>
	<div class="alert alert-danger" role="alert">
	  <h4 class="alert-heading">Failure!</h4>
	  <p>Data delete failed, please try again later.</p>
	</div>
<%
	} else if(message.equalsIgnoreCase("GUESTLOG_ADD_SUCCESS")){
		String status = (String) request.getAttribute("status");
%>
	<div class="alert alert-success" role="alert">
	  <h4 class="alert-heading">Success!</h4>
	  <p><b><%=status %></b> record has been recorded successfully</p>
	</div>
<%
	} else if(message.equalsIgnoreCase("GUESTLOG_ADD_FAILURE")){
%>
	<div class="alert alert-danger" role="alert">
	  <h4 class="alert-heading">Failure!</h4>
	  <p>Data save failed, please try again later.</p>
	</div>
<%
	} else if(message.equalsIgnoreCase("ALERT_FLAG_TRIGGERED")){
		String status = (String) request.getAttribute("status");
		String alertMsg = (String) request.getAttribute("FLAG_MESSAGES");
%>
	<div class="alert alert-warning" role="alert">
	  <h4 class="alert-heading">Alert!!</h4>
	  <p><b><%=status %></b> record has been recorded successfully. Following alerts were triggered:</p>
	  <p><%=alertMsg %></p>
	</div>
<%
	} else if(message.equalsIgnoreCase("GUESTLOG_MARK_SUCCESS")){
		String status = (String) request.getAttribute("status");
%>
	<div class="alert alert-warning" role="alert">
	  <h4 class="alert-heading">Success!</h4>
	  <p>Alert flag has been triggered against the above transaction</p>
	</div>
<%
	} else if(message.equalsIgnoreCase("GUESTLOG_MARK_FAILURE")){
%>
	<div class="alert alert-danger" role="alert">
	  <h4 class="alert-heading">Failure!</h4>
	  <p>Something went wrong, please try again later.</p>
	</div>
<%
	} else if(message.equalsIgnoreCase("USER_HAS_TRANSACTIONS")){
%>
	<div class="alert alert-warning" role="alert">
	  <h4 class="alert-heading">Warning!</h4>
	  <p>User cannot be deleted because there is a transaction mapped against it.</p>
	</div>
<%
	} else if(message.equalsIgnoreCase("DELETE_SUCCESS")){
%>
	<div class="alert alert-success" role="alert">
	  <h4 class="alert-heading">Success!</h4>
	  <p>User deleted successfully.</p>
	</div>
<%
	} else if(message.equalsIgnoreCase("DELETE_FAILURE")){
%>
	<div class="alert alert-danger" role="alert">
	  <h4 class="alert-heading">Failure!</h4>
	  <p>User could not be deleted, please try again later.</p>
	</div>
<%
	}
%>