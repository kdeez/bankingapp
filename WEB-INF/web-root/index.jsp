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
				<!-- Table -->
				<table class="table">
					<tr>
						<th>Name</th>
						<th>Account Number</th>
						<th>Type</th>
						<th>Balance</th>
					</tr>
					<tr>
						<td>Everyday Checking</td>
						<td>000012356</td>
						<td>Checking</td>
						<td>$25,235</td>
					</tr>
					<tr>
						<td>Fluffer Savings</td>
						<td>000012500</td>
						<td>Savings</td>
						<td>$100,000</td>
					</tr>
				</table>
			</div>
			
		</div>

    </div> <!-- /container -->
  </body>
</html>
