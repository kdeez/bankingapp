<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../favicon.ico">

    <title>Bytekonzz Banking</title>

    <!-- Bootstrap core CSS -->
    <link href="../css/bootstrap.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
	<style>
	body {
		background-color: #A4A4A4;
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
<%@ page import ="java.sql.*" %>
<%@ page import="java.io.InputStream" %>
<%@ page import="java.io.FileInputStream" %>
<%@ page import="java.util.Properties" %>
<% 
String username = request.getParameter("username");
String password = request.getParameter("password");
if(username != null && password != null)
{
	//grab the database connection properties from our Java Properties file
	InputStream stream = new FileInputStream("config.properties");
	Properties props = new Properties();
	props.load(stream);

	Class.forName(props.getProperty("db.jdbcdriver", "com.mysql.jdbc.Driver"));
	Connection connection = DriverManager.getConnection(props.getProperty("db.jdbcurl"), props.getProperty("db.userid"), props.getProperty("db.userpwd"));
	
	//always use PreparedStatements to protect against SQL injection
	PreparedStatement statement = connection.prepareStatement("select * from users where username = ? and password = ?");
	statement.setString(1, username);
	statement.setString(2, password);
	
	ResultSet result = statement.executeQuery();
	if (result.next()) 
	{
		//logged in... use the session attribute from now on
	    session.setAttribute("user-name", username);
	    response.sendRedirect("dashboard.jsp");
	}
}
%>

  <body>
 
  <div class="container">
		<form class="form-signin" role="form" method="post" action="login.jsp">
			<h2 class="form-signin-heading">Bytekonzz Banking</h2>
			<input type="text" class="form-control" placeholder="Username" value="<%=username != null ? username : "" %>" name="username"> 
			<input type="password"class="form-control" placeholder="Password" name="password"> 
			<label class="checkbox"><input type="checkbox" value="remember-me">Remember me</label>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
			<a href = "createUserAccount.jsp">Not a member? (create new account)</a>
		</form>
	</div>
  </body>
</html>
