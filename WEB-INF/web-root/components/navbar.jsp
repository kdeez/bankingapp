
<!-- Bootstrap core CSS -->  
<link href="/assets/css/bootstrap.min.css" rel="stylesheet">
<link href="/assets/css/bootstrapValidator.min.css" rel="stylesheet">

<%@ page import="rest.server.model.User, rest.server.model.Role" %>
<%
	//check for user session
	Object user = session.getAttribute("user-name");
	if (user == null) 
	{
		response.sendRedirect("/user/login.jsp");
	}
	
	String username = ((User) user).getUsername();
	Role ifAdmin = ((User) session.getAttribute("user-name")).getRole(); //created 'ifAdmin' because using 'role' was not working
%>
<!-- Fixed navbar -->
<div class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/index.jsp">Bytekonzz Banking</a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a href="/index.jsp">Home</a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">I Want To... <span class="caret"></span></a>
					<ul class="dropdown-menu" role="menu">
					<%
						if(ifAdmin.getName().equals("Admin") || ifAdmin.getName().equals("Employee"))
						{
							out.write("<li><a href='/account/inactive/inactive.jsp'>View Inactive Accounts</a></li>");
						}
					%>
						<li><a href="/account/create.jsp">Create New Account</a></li>
					</ul></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"><%=username%> <span class="caret"></span></a>
				<ul class="dropdown-menu" role="menu">
				<li><a href="/user/profile.jsp">Edit Profile</a></li>
				<li><a href="/user/password.jsp">Change Password</a></li>
				<li><a href="/user/logout.jsp">Sign Out
				</a></li>
			</ul>
		</div>
		<!--User feedback messages can be displayed here... -->
		<div id="user-feedback-message"></div>
	</div>
</div>
<!-- Bootstrap core JavaScript -->
<script src="/assets/js/jquery.js"></script>
<script src="/assets/js/bootstrap.min.js"></script>
<script src="/assets/js/form2json.js"></script>
<script src="/assets/js/bootstrapValidator.min.js"></script>