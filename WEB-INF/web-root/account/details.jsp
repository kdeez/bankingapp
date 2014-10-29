<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html">
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
<%String accountId = request.getParameter("id"); %>
<!--include directive to import the navigation bar so it is not copy and pasted into every page -->
	<%@include file="/frame/navbar.jsp" %>
    <div class="container">
      <!-- Main component for a primary marketing message or call to action -->
      <div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">Account Transactions</div>
			<div class="panel-body">
				<!-- table where transactions are displayed-->
				<table id="transaction-table" class="table"></table>
			</div>
		</div>
    </div>
 <!-- AJAX for loading the transactions -->
	<script>
		function getTransactions() {
			$.ajax({
				url : "/rest/account/transactions?id=<%=accountId%>",
				type : "GET",
				dataType : "json",
				success : function(data, status, xhr) {
					var content = "<tr><th>Date</th><th>Description</th><th>Amount</th><th>Balance</th></tr>";
					var transactions = JSON.parse(xhr.responseText)
					if (transactions) {
						for (i = 0; i < transactions.length; i++) {
							var date = new Date(transactions[i].dateTime);
							content += "<tr>";
							content += "<td>" + date.toLocaleFormat('%d-%b-%Y')+ "</td>";
							content += "<td>" + transactions[i].description + "</td>";
							content += "<td>" + transactions[i].amount + "</td>";
							content += "<td>$" + transactions[i].balance.toFixed(2) + "</td>";
							content += "</tr>";
						}
					}
					document.getElementById("transaction-table").innerHTML = content;
				}
			});
		}

		window.onload = function() {
			getTransactions();
		}
	</script>
</body>
</html>