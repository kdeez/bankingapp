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
<%String accountId = request.getParameter("id"); %>
<!--include directive to import the navigation bar so it is not copy and pasted into every page -->
<%@include file="/components/navbar.jsp" %>
<div class="container">
	
		<!-- Main component for a primary marketing message or call to action -->
      <div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<h4>Transfer Funds</h4>
			</div>
			<div class="panel-body">
				<form id="transfer-funds-form" method="POST" action="/rest/account/transfer?id=<%=accountId%>">
				<div class="form-group">
					<input type="hidden" class="form-control" name="transactionType" value="1">
					<div class="input-group">
  						<span class="input-group-addon"></span>
  						<input type="text" class="form-control" placeholder= "Account Number" name="accountId"><br>
					</div>
					<br>
					<div class="input-group">
  						<span class="input-group-addon"></span>
  						<input type="text" class="form-control" placeholder= "Enter Description" name="description"><br>
					</div>
					<br>
					<div class="input-group">
  						<span class="input-group-addon">$</span>
  						<input type="text" class="form-control" placeholder= "Enter Amount" name="amount"><br>
					</div>
				</div>
				<div class="modal-footer">
					<a href="/account/details.jsp?id=<%=accountId%>" class="btn btn-default" role="button">Cancel</a>
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
			</form>
			</div>
		</div>
    </div>
<!-- form validation for new account http://bootstrapvalidator.com/getting-started/-->
	<script>
	$(document).ready(function() {
    	$('#transfer-funds-form').bootstrapValidator({
		        fields: {
		        	accountId: {
			                validators: {
			                    remote: {
			                        url: '/rest/account/validate',
			                        message: 'Invalid account number'
			                    }
			                }
			            },
		        	amount: {
		                validators: {
		                    notEmpty: {
		                        message: 'The amount field is required'
		                    },
		                    regexp: {
		                        regexp: /^[0-9]+\.[0-9]{2}$/,
		                        message: 'Invalid amount'
		                    }
		                }
		            },
		            description: {
		                validators: {
		                    notEmpty: {
		                        message: 'The description is required'
		                    },
		                    stringLength: {
		                        min: 5,
		                        max: 30,
		                        message: 'Description must be between 5 and 30 characters long'
		                    }
		                }
		            },
		        }
		})
	    .on('success.form.bv', function(e) {
	        // Prevent form submission
	        e.preventDefault();
	
	        // Get the form instance
	        var $form = $(e.target);
	
	        // Get the BootstrapValidator instance
	        var bv = $form.data('bootstrapValidator');
	
	        // Use JQuery Ajax to submit form data
	        $.ajax({
	  			url:$form.attr('action'),
	  			type:"POST",
	  			data:$form.toJSONString(),
	  			contentType:"application/json; charset=utf-8",
	  			dataType:"json",
	  			success: function(){
	  				window.location.href = "/account/details.jsp?id=" + <%=accountId%>;
	  			},
	  			error: function(xhr, status, error){
	  				var text = " Unable to complete transaction";
	  				if(xhr.responseText){
	  					text += ", " + xhr.responseText;
	  				}
	  				
	  				showErrorMessage(text);	
	  			}
			});
	    });
	});
	</script>
</body>
</html>