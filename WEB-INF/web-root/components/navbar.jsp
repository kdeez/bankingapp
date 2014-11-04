
<!-- Bootstrap core CSS -->
<link href="../assets/css/bootstrap.min.css" rel="stylesheet">
<link href="../assets/css/bootstrapValidator.min.css" rel="stylesheet">
<%
	//check for user session
	Object user = session.getAttribute("user-name");
	Object role = session.getAttribute("user-role");
	if (user == null || role == null) 
	{
		response.sendRedirect("/user/login.jsp");
	}
%>
<!-- Fixed navbar -->
<div class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Bytekonzz Banking</a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a href="/index.jsp">Home</a></li>
				<li><a href="#about">About</a></li>
				<li><a href="#contact">Contact</a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">I Want To... <span class="caret"></span></a>
					<ul class="dropdown-menu" role="menu">
						<li><a href="/account/create.jsp">Create New Account</a></li>
					</ul></li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="/user/logout.jsp">Logged in as <%=user%> (Sign Out)
				</a></li>
			</ul>
		</div>
		<!--User feedback messages can be displayed here... -->
		<div id="user-feedback-message"></div>
	</div>
</div>
<!-- Bootstrap core JavaScript -->
<script src="../assets/js/jquery.js"></script>
<script src="../assets/js/bootstrap.min.js"></script>
<script src="../assets/js/form2json.js"></script>
<script src="../assets/js/bootstrapValidator.min.js"></script>