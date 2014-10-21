<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Sign in &middot; ByteKonzz Banking</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Le styles -->
<link href="../css/bootstrap.css" rel="stylesheet">
<style type="text/css">
body {
	padding-top: 40px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
}

.form-signin {
	max-width: 300px;
	padding: 19px 29px 29px;
	margin: 0 auto 20px;
	background-color: #fff;
	border: 1px solid #e5e5e5;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
}

.form-signin .form-signin-heading, .form-signin .checkbox {
	margin-bottom: 10px;
}

.form-signin input[type="text"], .form-signin input[type="password"] {
	font-size: 16px;
	height: auto;
	margin-bottom: 15px;
	padding: 7px 9px;
}
</style>
<link href="../css/bootstrap-responsive.css" rel="stylesheet">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="../js/html5shiv.js"></script>
    <![endif]-->

<!-- Fav and touch icons -->
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="../ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="../ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="../ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="../ico/apple-touch-icon-57-precomposed.png">
<link rel="shortcut icon" href="../ico/favicon.png">
</head>

<%@ page import ="java.sql.*" %>
<%@page import="java.io.InputStream" %>
<%@ page import="java.io.FileInputStream" %>
<%@page import="java.util.Properties" %>
<% 
String username = request.getParameter("user-name");
String password = request.getParameter("pass-word");
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
	    response.sendRedirect("ajax.jsp");
	}
}
%>

<body>
	<div class="container">
		<form class="form-signin" method="post" action="login.jsp">
			<h2 class="form-signin-heading">Please sign in</h2>
			<input type="text" class="input-block-level" placeholder="Username" name="user-name"> 
			<input type="password"class="input-block-level" placeholder="Password" name="pass-word"> 
			<label class="checkbox"><input type="checkbox" value="remember-me">Remember me</label>
			<button class="btn btn-large btn-primary" type="submit">Sign in</button>
		</form>
	</div>
	<!-- /container -->

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="../js/jquery.js"></script>
	<script src="../js/bootstrap.js"></script>
</body>
</html>
