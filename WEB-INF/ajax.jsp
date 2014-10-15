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
	<p>We have a few choices when it comes to building web content.  This file is created using static HTML markup which the browser understands.  However, I can also
		dynamically create content using client or server side scripting languages.  For database driven applications, data is used from the database to populate web
		content.  AJAX is a term used for async communication to the server so that the entire page isn't loaded at once and the entire page does not need to be refreshed.
		Our application will be able to leverage all the three strategies for building web content.
	</p>
	<h2>Client-side scripting</h2>
	<p>
		<script>
			document.write("Javascript works! I am code that runs in the client (browser)");
		</script>
	</p>
	<h2>Server-side scripting</h2>
	<p><% out.write("Java works! This content was actually created on the server before being sent to client (browser)"); %></p>
	
	<h2>Ajax</h2>
	<p id=ajax-example></p>
	<script>
		xmlhttp= new XMLHttpRequest();
		xmlhttp.open("GET","/rest/user?id=1", true);
		xmlhttp.onreadystatechange=function()
		  {
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    {
			  	var user = JSON.parse(xmlhttp.responseText)
			  	if(user){
			  		document.getElementById("ajax-example").innerHTML="AJAX Works! I sent a specific HTTP request to the server and the server sent back a HTTP response. user=" + user.username;
			  	}
		    }
		  }
		xmlhttp.send();
	</script>
</body>
</html>