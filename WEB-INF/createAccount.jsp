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
<%@ page import ="java.sql.*" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.util.Properties" %>
<% 
String username = request.getParameter("username");
String password = request.getParameter("password");
String firstname = request.getParameter("firstname");
String lastname = request.getParameter("lastname");
String email = request.getParameter("email");
if(firstname != null && lastname != null)
{
	//grab the database connection properties from our Java Properties file
	InputStream stream = new FileInputStream("config.properties");
	Properties props = new Properties();
	props.load(stream);

	Class.forName(props.getProperty("db.jdbcdriver", "com.mysql.jdbc.Driver"));
	Connection connection = DriverManager.getConnection(props.getProperty("db.jdbcurl"), props.getProperty("db.userid"), props.getProperty("db.userpwd"));
	
	
	
	//always use PreparedStatements to protect against SQL injection
	PreparedStatement statement = connection.prepareStatement("select * from users where firstName = ? and lastname = ?");
	statement.setString(1, firstname);
	statement.setString(2, lastname);
	
	ResultSet result = statement.executeQuery();
	
	if(result == null)
	{
		//create a user to get user id
	}
	else
	{
		//get the id and input into new account for the user id field
	}
	/*if (result.next()) 
	{
		//logged in... use the session attribute from now on
	    session.setAttribute("user-name", username);
	    response.sendRedirect("dashboard.jsp");
	}*/
	
}
%>
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
