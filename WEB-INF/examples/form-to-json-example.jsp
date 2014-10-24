<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form method="POST" action="rest/user">
		<label>Username</label><input type="text" name="username">
		<label>Password</label><input type="password" name="password">
		<label>Email</label><input type="text" name="email">
		<label>First Name</label><input type="text" name="firstName">
		<label>Last Name</label><input type="text" name="firstName">
		<input type="hidden" name="roleId" value="3">
		<input type="submit">
	</form>
	
	<div id="response-element"></div>
	
<script src="../js/jquery.js"></script>
<script src="../js/form2json.js"></script>
</body>
</html>