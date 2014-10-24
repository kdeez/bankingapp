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
			<form method="POST" action="rest/user">
				<!--<label>Username</label><input type="text" name="username">
				<label>Password</label><input type="password" name="password"> -->
				<label>Email</label><input type="text" name="email"><br>
				<label>First Name</label><input type="text" name="firstName"><br>
				<label>Last Name</label><input type="text" name="lastname"><br>
				<input type="radio" name="type" value="Saving">Saving
				<input type="radio" name="type" value="Checking">Checking<br>
				<input type="hidden" name="roleId" value="3">
				<input type="submit">
			</form>
	
			<div id="response-element"></div>
	</div>
</div>

<script src="../js/jquery.js"></script>
<script src="../js/form2json.js"></script>
</body>
</html>
