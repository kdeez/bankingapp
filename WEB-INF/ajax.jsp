<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Let's test technologies...</h1>
	<h1>
		<script>
			document.write("Client-side: Javascript works!");
		</script>
	</h1>
	<h1><% out.write("Server-side: Java works!"); %></h1>
	<h1 id=user></h1>
	<script>
		xmlhttp= new XMLHttpRequest();
		xmlhttp.open("GET","/rest/user?id=1", true);
		xmlhttp.onreadystatechange=function()
		  {
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    {
			  	var user = JSON.parse(xmlhttp.responseText)
			  	if(user){
			  		document.getElementById("user").innerHTML="AJAX Works!, retrieved user=" + user.username;
			  	}
		    }
		  }
		xmlhttp.send();
	</script>
</body>
</html>