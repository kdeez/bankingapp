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
<body>

<!--include directive to import the navigation bar so it is not copy and pasted into every page -->
	<%@include file="navbar.jsp" %>
    <div class="container">
      <!-- Main component for a primary marketing message or call to action -->
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">New Account</h3>
			</div>
			<div class="panel-body">
				<form id="new-account-form" method="POST" action="rest/account">
					<div class="form-group">
						<div class="input-group">
						<label>Account Type</label> 
							<select name="accountType" class="form-control" >
								<option value="0">Checking</option>
								<option value="1">Savings</option>
							</select>
						</div>
					</div>
					<div class="modal-footer">
						<a href="dashboard.jsp" class="btn btn-default" role="button">Cancel</a>
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</form>
			</div>
		</div>
	</div>

<script src="../js/jquery.js"></script>
<script src="../js/form2json.js"></script>
<script>
$(function() {
    $('#new-account-form').submit(function(evt) {
    	var form = $(this);
        var json = form.toJSONString();
        var action = this.getAttribute("action");
        xmlhttp= new XMLHttpRequest();
		xmlhttp.open("POST", action, true);
		xmlhttp.setRequestHeader("Content-Type","application/json");
		xmlhttp.onreadystatechange=function()
		  {
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    {
			  var entity = JSON.parse(xmlhttp.responseText)
			  if(entity)
			  {
// 				showSuccessMessage("Successfully opened new account.");
				//simply show the new account in the table listing
				window.location.href = "dashboard.jsp";
			  }
		    }else{
		    	showErrorMessage(" Unable to create account.");
		    }
		  }
		xmlhttp.send(json);
        return false;
    });
});
</script>
</body>
</html>
