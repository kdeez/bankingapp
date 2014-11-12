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

<%
	Object role = session.getAttribute("user-role");
	String accountId = request.getParameter("id");
%>
<body>
	<!--include directive to import the navigation bar so it is not copy and pasted into every page -->
	<%@include file="/components/navbar.jsp" %>
	<%@include file="/components/deleteAccountModal.jsp" %>
    <div class="container">
      <!-- Main component for a primary marketing message or call to action -->
      <div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading"><h4>Accounts Summary</h4></div>
			<div class="panel-body">
				<!-- table where user accounts are displayed-->
				<div class="table-responsive">
					<table id="accounts-table" class="table table-striped"></table>
				</div>
			</div>
		</div>
    </div>	
	
    <!-- AJAX for loading the user's accounts -->
    <script>
    function getAccounts(userRole){
    	xmlhttp= new XMLHttpRequest();
		xmlhttp.open("GET", "/rest/user/accounts/", true);
		xmlhttp.setRequestHeader("Content-Type","application/json");
		xmlhttp.onreadystatechange=function()
		  {
		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
		    {
			  var content = "<tr><th>Account</th><th></th><th>Actions</th><th>Balance</th></tr>";
			  var accounts = JSON.parse(xmlhttp.responseText)
			  if(accounts)
			  {
				  for(i = 0; i < accounts.length; i++)
				  {	
					  content += "<tr>";
					  content += "<td><a href='/account/details.jsp?id=" + accounts[i].accountNumber + "'>" + accounts[i].description + "</a>" + "</td>";
					  content += "<td><span class='label label-default'>"+ (accounts[i].accountType == 0 ? "Checking (" : "Savings (") + ('000000000'+ accounts[i].accountNumber).slice(-9) + ")</span></td>";
					  content += "<td><div class='input-group'>" +
					  		"<div class='btn-group'>" +
								"<input type='hidden' name='accountType' value='0'>" +
								"<button type='button' class='btn btn-default btn-xs dropdown-toggle' data-toggle='dropdown' title='Defaults to Checking'>" +
									"<span data-bind='label'>I Want To... </span><span class='caret'></span>" +
								"</button>" +
								"<ul class='dropdown-menu' role='menu' style='cursor:pointer'>" +
									"<li><a href='/account/details.jsp?id=" + accounts[i].accountNumber + "'>View Transactions</a></li>" +
									((userRole == "Admin" || userRole == "Employee") ? ("<li><a href='/account/deposit.jsp?id=" + accounts[i].accountNumber + "'>Deposit Funds</a></li>") : "") +
									((userRole == "Admin" || userRole == "Employee") ? ("<li><a href='/account/debit.jsp?id=" + accounts[i].accountNumber + "'>Withdraw Funds</a></li>") :"") +
									"<li><a href='/account/transfer.jsp?id=" + accounts[i].accountNumber + "'>Transfer Funds</a></li>" +
									"<li class=\"divider\"></li>" +
									"<li><a href='/account/close.jsp?id=" + accounts[i].accountNumber + "'>Close Account</a></li>" +
									//"<li><a data-toggle=\"modal\" data-target=\"#close-account-modal\" data-id=\"" + accounts[i].accountNumber +"\" style=\"cursor:pointer;\">Close Account</a></li>" +
								"</ul>" +
							"</div>" +
						"</div></td>";
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
    	getAccounts("<%=role%>");
    }
	</script>
	
	
  </body>
</html>
