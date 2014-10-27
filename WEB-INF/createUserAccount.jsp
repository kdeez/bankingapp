<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Create User Account</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../favicon.ico">
 <!-- Bootstrap core CSS -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
	<style>
	body {
		background-color: #191007;
		padding-top: 200px;
		padding-bottom: 200px;
		
	}
	
	.form-signin {
		max-width: 330px;
		padding: 15px;
		margin: 0 auto;
		color:white;
	}
	
	.form-signin .form-signin-heading, .form-signin .checkbox {
		margin-bottom: 2px;
	    
	}
	
	.form-signin .checkbox {
		font-weight: normal;
		text-align:left;
		left:22px
	 
	}
	
	.form-signin .form-control {
		position: relative;
		height: auto;
		-webkit-box-sizing: border-box;
		-moz-box-sizing: border-box;
		box-sizing: border-box;
		padding: 10px;
		font-size: 16px;
	}
	
	.form-signin .form-control:focus {
		z-index: 2;
	}
	
	.form-signin input[type="email"] {
		margin-bottom: -1px;
		border-bottom-right-radius: 0;
		border-bottom-left-radius: 0;
	}
	
	.form-signin input[type="password"] {
		margin-bottom: 10px;
		border-top-left-radius: 0;
		border-top-right-radius: 0;
	}
	</style>
</head>
<body>
<!--include directive to import the navigation bar so it is not copy and pasted into every page -->
    <div class="container">
      <!-- Main component for a primary marketing message or call to action -->
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">New User Account</h3>
			</div>
			<div class="panel-body">
				<form id="new-user-form" method="POST" action="rest/user">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Username" name="username"><br>
						<input type="password"class="form-control" placeholder="Password" name="password"><br>
						<input type="text" class="form-control" placeholder= "E-mail" name="email"><br>
						<input type="text" class="form-control" placeholder= "First Name" name="firstName"><br>
						<input type="text" class="form-control" placeholder= "Last Name" name="lastname"><br>
						<input type="hidden" name="roleId" value="3">
					</div>
					<div class="modal-footer">
						<a href="login.jsp" class="btn btn-default" role="button">Cancel</a>
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</form>
			</div>
		</div>
	</div>
		
<script src="../../js/jquery.js"></script>
<script src="../../js/form2json.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script>
$(function() {
    $('#new-user-form').submit(function(evt) {
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
				//here is where you can update the UI
				document.getElementById("response-element").innerHTML= "Created=" + xmlhttp.responseText;
			  }
		    }
		  }
		xmlhttp.send(json);
        return false;
    });
});
</script>
</body>
</html>