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
<%@ page import="rest.server.model.User"%>
<%@include file="/components/navbar.jsp" %>
<%
	User userProfile = ((User) session.getAttribute("user-name"));
%>
	<div class="container">
	<!-- Main component for a primary marketing message or call to action -->
		<div class="panel panel-default">
			<!-- Default panel contents -->
			<div class="panel-heading">
				<h4>My Profile</h4>
			</div>
			<div class="panel-body">
				<form id="profile-form" method="POST" action="/rest/user/profile">
				<div class="form-group">
					<input type="text" class="form-control" placeholder= "First Name" name="firstName" value="<%=userProfile.getFirstName()%>"><br>
					<input type="text" class="form-control" placeholder= "Last Name" name="lastname" value="<%=userProfile.getLastname()%>"><br>
					<input type="text" class="form-control" placeholder= "Phone" name="phone" value="<%=userProfile.getPhone()%>"><br>
					<input type="email" class="form-control" placeholder= "E-mail" name="email" value="<%=userProfile.getEmail()%>"><br>
					
					<input type="text" class="form-control" placeholder= "Street Address" name="street" value="<%=userProfile.getStreet()%>"><br>
					<input type="text" class="form-control" placeholder= "City" name="city" value="<%=userProfile.getCity()%>"><br>
					<input type="text" class="form-control" placeholder= "State" name="state" value="<%=userProfile.getState()%>"><br>
					<input type="text" class="form-control" placeholder= "Zipcode" name="zipCode" value="<%=userProfile.getZipCode()%>"><br>
				</div>
				<div class="modal-footer">
					<a href="/index.jsp" class="btn btn-default" role="button">Cancel</a>
					<button type="submit" class="btn btn-primary">Save</button>
				</div>
				</form>
			</div>
		</div>
	</div>
<script>
	$(document).ready(function() {
    	$('#profile-form').bootstrapValidator({
		        fields: {
		            email: {
		                validators: {
		                    notEmpty: {
		                        message: 'The email is required and cannot be empty'
		                    },
		                    emailAddress: {
		                        message: 'The input is not a valid email address'
		                    }
		                }
		            },
		            firstName: {
		                validators: {
		                    notEmpty: {
		                        message: 'The first name is required'
		                    },
		                }
		            },
		            lastname: {
		                validators: {
		                    notEmpty: {
		                        message: 'The last name is required'
		                    },
		                }
		            },
		            zipCode: {
		                validators: {
		                    regexp: {
		                        regexp: /^\d{5}$/,
		                        message: 'The US zipcode must contain 5 digits'
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
	  			type:$form.attr('method'),
	  			data:$form.toJSONString(),
	  			contentType:"application/json; charset=utf-8",
	  			dataType:"json",
	  			
	  			success: function(){
	  				window.location.href = "/index.jsp";
	  			},
	  			error: function(xhr, status, error){
	  				showErrorMessage(" Unable to update profile."); 	 			
	  			}
			});
	    });
	});
	</script>
</body>
</html>