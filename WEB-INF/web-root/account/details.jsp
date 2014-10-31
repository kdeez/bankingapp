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
	<%@include file="/components/navbar.jsp" %>
	<div class="container">
	
		<!-- Main component for a primary marketing message or call to action -->
      <div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<div class="collapse navbar-collapse"
					id="bs-example-navbar-collapse-1">
					<ul class="nav navbar-nav navbar-left">
						<li><h4 id="account-title">Account Summary</h4></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown"><a href="" class="dropdown-toggle"
							data-toggle="dropdown">I Want To... <span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="/account/deposit.jsp?id=<%=accountId%>">Deposit Funds</a></li>
								<li><a href="/account/debit.jsp?id=<%=accountId%>">Withdraw Funds</a></li>
								<li><a href="/account/transfer.jsp?id=<%=accountId%>">Transfer Funds</a></li>
								<li><a href="/account/close.jsp?id=<%=accountId%>">Close Account</a></li>
							</ul>
						</li>
					</ul>
				</div>
			</div>
			<div class="panel-body">
				<!-- table where transactions are displayed-->
				<div class="table-responsive">
					<table id="transaction-table" class="table table-striped"></table>
				</div>
			</div>
		</div>
    </div>
 <!-- AJAX for loading the transactions table-->
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
							var style = transactions[i].transactionType == 0 ? " style='color:red' " : " style='color:green' ";
							content += "<tr>";
							content += "<td>" + date.toLocaleFormat('%d-%b-%Y')+ "</td>";
							content += "<td>" + transactions[i].description + "</td>";
							content += "<td"+style+">$" + transactions[i].amount.toFixed(2) + "</td>";
							content += "<td>$" + transactions[i].balance.toFixed(2) + "</td>";
							content += "</tr>";
						}
					}
					document.getElementById("transaction-table").innerHTML = content;
				}
			});
		}

		window.onload = function() {
			getAccount();
			getTransactions();
		}
	</script>
	<script>
		function getAccount(){
			$.ajax({
				url : "/rest/account?id=<%=accountId%>",
				type : "GET",
				dataType : "json",
				success : function(data, status, xhr) {
					var account = JSON.parse(xhr.responseText)
					if (account) {
						document.getElementById("account-title").innerHTML = account.description + " Summary ";
					}
				}
			});
		}
	</script>
</body>
</html>