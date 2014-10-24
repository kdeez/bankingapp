<%@ page language="java" contentType="text/html; UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Account</title>

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
<div class = "container">
	<form method="POST" action="rest/user">
		<!--<label>Username</label><input type="text" name="username">
		<label>Password</label><input type="password" name="password"> -->
		<label>Email</label><input type="text" name="email">
		<label>First Name</label><input type="text" name="firstName">
		<label>Last Name</label><input type="text" name="lastname">
		<input type="hidden" name="roleId" value="3">
		<input type="submit">
	</form>
	
	<div id="response-element"></div>
</div>	
<script src="../js/jquery.js"></script>
<script src="../js/form2json.js"></script>
</body>
</html>
