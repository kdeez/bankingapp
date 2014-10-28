<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Create Account</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../favicon.ico">
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
			<div class="panel-heading">
				<h3 class="panel-title">New Account</h3>
			</div>
			<div class="panel-body">
				<form id="new-account-form" method="POST" action="/rest/account">
					<div class="form-group">
						<div class="input-group">
							<input type="text" class="form-control" placeholder="Account Name" name="description">
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<div class="btn-group">
								<input type="hidden" name="accountType">
								<button type="button" class="btn btn-default dropdown-toggle"
									data-toggle="dropdown">
									<span data-bind="label">Account Type </span><span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu" style="cursor:pointer">
									<li value="0"><a>Checking </a></li>
									<li value="1"><a>Savings </a></li>
								</ul>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<a href="/index.jsp" class="btn btn-default" role="button">Cancel</a>
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	
<!-- AJAX for creating a new bank account -->
<script>
$(function() {
    $('#new-account-form').submit(function(evt) {
    	var form = $(this);
        var json = form.toJSONString();
        var action = this.getAttribute("action");
        xmlhttp= new XMLHttpRequest();
		xmlhttp.open("POST", action, true);
		xmlhttp.setRequestHeader("Content-Type","application/json");
		xmlhttp.onreadystatechange=function()
		  {
		  if (xmlhttp.readyState==4)
		    {
			  if(xmlhttp.status==200)
				{
				  var entity = JSON.parse(xmlhttp.responseText)
				  if(entity)
				  {
					//simply show the new account in the table listing
					window.location.href = "/index.jsp";
					return;
				  }
				}else{
					showErrorMessage(" Unable to create account.");
				}
		    }
		  }
		xmlhttp.send(json);
        return false;
    });
});
</script>

<!-- This is the jquery code for making a Bootstrap dropdown widget behave like a native "select" element -->
<script>
$(document.body).on('click', '.dropdown-menu li', function(event) {
	var $target = $(event.currentTarget);
	$target.closest('.btn-group').find('[data-bind="label"]').text($target.text()).end().children('.dropdown-toggle').dropdown('toggle');
	$target.closest('.btn-group').find('input[type=hidden]').attr('value', $target.val());
	return false;
});
</script>
</body>
</html>
