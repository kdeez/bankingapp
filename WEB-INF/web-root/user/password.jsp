<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
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
<%@include file="/components/navbar.jsp" %>
	<div class="container">
	<!-- Main component for a primary marketing message or call to action -->
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<h4>Change Password</h4>
			</div>
			<div class="panel-body">
				<form id="new-user-form" method="POST" action="/rest/user">
				<div class="form-group">
					<input type="password" class="form-control" placeholder="Existing Password" name="password"><br>
					<input type="password" class="form-control" placeholder="New Password" name="password"><br>
					<input type="password" class="form-control" placeholder="Re-enter Password" name="password"><br>
				</div>
				<div class="modal-footer">
					<a href="/index.jsp" class="btn btn-default" role="button">Cancel</a>
					<button type="submit" class="btn btn-primary">Save</button>
				</div>
			</form>
			</div>
		</div>
	</div>
</body>
</html>