<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Create Account</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../favicon.ico">
    <!-- Custom styles for this template -->
    <style>
		body {
			min-height: 2000px;
			padding-top: 70px;
		}
	</style>
</head>

<%
	String accountId = request.getParameter("id");
%>
<body>

<!--include directive to import the navigation bar so it is not copy and pasted into every page -->
	<%@include file="/components/navbar.jsp" %>
    <div class="container">
      <!-- Main component for a primary marketing message or call to action -->
		<div class="panel panel-danger">
			<div class="panel-heading">
				<h3 class="panel-title">Close Account</h3>
			</div>
			<div class="panel-body">
				<p>Are you sure you would like to close the account?</p>
				<p>Please remove all funds before proceeding</p>
				<p class="text-danger"><strong>WARNING: This cannot be undone</strong></p>
				<form id="close-account-form">
					<div class="modal-footer">
						<a href="/index.jsp" class="btn btn-default" role="button">Cancel</a>
						<button type="submit" class="btn btn-danger">Submit</button>
					</div>
				</form>
			</div>
		</div>
	</div>

<!-- form validation for new account http://bootstrapvalidator.com/getting-started/-->
<script>
	$(document).ready(function() {
    	$('#close-account-form')
	    .on('submit',  function(e) {
		    
	        // Prevent form submission
	        e.preventDefault();
	
	        // Get the form instance
	        var $form = $(e.target);
				
	        // Use JQuery Ajax to submit form data
	        $.ajax({
	  			url: "/rest/account?id=<%=accountId%>",
	  			type:"DELETE",
	  			success: function(){
	  				window.location.href = "/index.jsp";
	  			},
	  			error: function(xhr, status, error){
	  				showErrorMessage(" Unable to complete transaction"); 			
	  			}
			});
	    });
	});
	
</script>
</body>
</html>