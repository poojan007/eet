<div class="alert alert-danger">
    <% if(request.getAttribute("errorMsg") != null) { %>
    	<div class="error-content">
	      <h3><i class="fa fa-warning text-yellow"></i> Error.</h3>
	      <p>
	        <%=request.getAttribute("errorMsg") %>
	      </p>
	    </div>
    <% } else{ %>
    <div class="error-content">
      <h3><i class="fa fa-warning text-red"></i> Oops! Something went wrong.</h3>
      <p>
        We will work on fixing that right away.
        Meanwhile, you may contact the system administrator to clarify about the issue.
      </p>
    </div>
    <% } %>
</div>