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
    <link href="../assets/css/bootstrap.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
	<link href="../assets/css/login-form.css" rel="stylesheet">

  </head>
  
 <!-- JSP server-side code for logging in the user -->
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
	InputStream stream = new FileInputStream("WEB-INF/config.properties");
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
	    response.sendRedirect("/index.jsp");
	}
}
%>

  <body>
   <!-- login form -->
  	<div class="container">
		<form class="form-signin" role="form" method="post" action="/user/login.jsp">
			<h2 class="form-signin-heading">Bytekonzz Banking</h2>
			<input type="text" class="form-control" placeholder="Username" value="<%=username != null ? username : "" %>" name="username"> 
			<input type="password"class="form-control" placeholder="Password" name="password"> 
			<label class="checkbox"><input type="checkbox" value="remember-me">Remember me</label>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
			<a data-toggle="modal" data-target="#create-user-modal" style="cursor:pointer;">Not a member? (create new account)</a>
		</form>
	</div>

	<!-- new user account modal(popup) window -->
	<div class="modal fade" id="create-user-modal" tabindex="-1" role="dialog" aria-labelledby="create-user-modal-Label" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" id="create-user-modal-Label">Create Account</h4>
	      </div>
	      <div class="modal-body">
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
					<a href="/user/login.jsp" class="btn btn-default" role="button">Cancel</a>
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
			</form>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="../assets/js/jquery.js"></script>
    <script src="../assets/js/bootstrap.min.js"></script>
	<script src="../assets/js/form2json.js"></script>
	<!-- AJAX for creating a new user -->
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
				  window.location.href = "/user/login.jsp";
				return;
			    }
			  }
			xmlhttp.send(json);
	        return false;
	    });
	});
	</script>
  </body>
</html>
