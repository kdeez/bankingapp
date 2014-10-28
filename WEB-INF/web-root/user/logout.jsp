<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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