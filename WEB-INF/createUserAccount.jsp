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
		<form id="new-user-form" method="POST" action="rest/user">
				<label>Username</label><input type="text" name="username"><br>
				<label>Password</label><input type="password" name="password"><br>
				<label>Email</label><input type="text" name="email"><br>
				<label>First Name</label><input type="text" name="firstName"><br>
				<label>Last Name</label><input type="text" name="lastname"><br>
				<input type="hidden" name="roleId" value="3">
				<input type="submit">
		</form>
	
<script src="../../js/jquery.js"></script>
<script src="../../js/form2json.js"></script>
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