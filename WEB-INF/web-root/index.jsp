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
    <!-- Custom styles for this template -->
    <style>
		body {
			min-height: 2000px;
			padding-top: 70px;
		}
	</style>
  </head>

<body>
	<!--include directive to import the navigation bar so it is not copy and pasted into every page -->
	<%@include file="/frame/navbar.jsp" %>
    <div class="container">
      <!-- Main component for a primary marketing message or call to action -->
      <div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">Accounts Summary</div>
			<div class="panel-body">
				<!-- table where user accounts are displayed-->
				<table id="accounts-table" class="table"></table>
			</div>
		</div>
    </div>
    
    <!-- AJAX for loading the user's accounts -->
    <script>
    function getAccounts(){
    	xmlhttp= new XMLHttpRequest();
		xmlhttp.open("GET", "/rest/user/accounts/", true);
		xmlhttp.setRequestHeader("Content-Type","application/json");
		xmlhttp.onreadystatechange=function()
		  {
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    {
			  var content = "<tr><th>Name</th><th>Account</th><th>Type</th><th>Balance</th></tr>";
			  var accounts = JSON.parse(xmlhttp.responseText)
			  if(accounts)
			  {
				  for(i = 0; i < accounts.length; i++)
				  {	
					  var zerofilled = ('000000000'+ accounts[i].accountNumber).slice(-9);
					  content += "<tr>";
					  content += "<td><a href='/account/details.jsp?id="+accounts[i].accountNumber+"'>"+accounts[i].description+"</a></td>";
					  content += "<td>" + zerofilled + "</td>";
					  content += "<td>" + (accounts[i].accountType == 0 ? "Checking" : "Savings") + "</td>";
					  content += "<td>$" + accounts[i].balance.toFixed(2) + "</td>";
					  content += "</tr>";
				  }
			  }
			  document.getElementById("accounts-table").innerHTML = content;
		    }
		  }
		xmlhttp.send();
    }
    
    window.onload = function(){
    	getAccounts();
    }
	</script>
  </body>
</html>
