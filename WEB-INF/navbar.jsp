<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bytekonzz Banking</title>
<!-- Bootstrap core CSS -->
<link href="../css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%
	//check for user session
	Object user = session.getAttribute("user-name");
	if (user == null) {
		response.sendRedirect("login.jsp");
	}
%>
<!-- Fixed navbar -->
    <div class="navbar navbar-default navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Bytekonzz Banking</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#contact">Contact</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">I Want To... <span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <li><a href="#">Open Account</a></li>
                <li><a href="#">Deposit Funds</a></li>
                <li><a href="#">Withdraw Funds</a></li>
                <li><a href="#">Transfer Funds</a></li>
                <li><a href="#">Close Account</a></li>
<!--                 <li class="divider"></li> -->
<!--                 <li class="dropdown-header">Existing Accounts</li> -->
<!--                 <li><a href="#">Deposit Funds</a></li> -->
<!--                 <li><a href="#">Withdraw Funds</a></li> -->
<!--                 <li><a href="#">Transfer Funds</a></li> -->
<!--                 <li><a href="#">Close Account</a></li> -->
              </ul>
            </li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="logout.jsp">Sign out</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>
	<!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
</body>
</html>