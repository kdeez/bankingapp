<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Thank you for using Bytekonzz</title>
</head>
<body>
<%
//destroy the session for the user.
Object user = session.getAttribute("user-name");
if(user != null){
	session.removeAttribute("user-name");
	session.invalidate();
}
response.sendRedirect("/user/login.jsp");
%>

</body>
</html>