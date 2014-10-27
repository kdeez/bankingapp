<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form id="new-user-form" method="POST" action="rest/user">
		<label>Username</label><input type="text" name="username">
		<label>Password</label><input type="password" name="password">
		<label>Email</label><input type="text" name="email">
		<label>First Name</label><input type="text" name="firstName">
		<label>Last Name</label><input type="text" name="firstName">
		<input type="hidden" name="roleId" value="3">
		<input type="submit">
	</form>
	
	<div id="response-element"></div>
	
<script src="/asset//js/jquery.js"></script>
<script src="/assets/js/form2json.js"></script>
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