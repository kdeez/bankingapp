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
					<input type="text" class="form-control" placeholder= "First Name" name="firstName" value="<%=userProfile.getFirstName() != null ? userProfile.getFirstName(): "" %>"><br>
					<input type="text" class="form-control" placeholder= "Last Name" name="lastname" value="<%=userProfile.getLastname() != null ? userProfile.getLastname(): "" %>"><br>
					<input type="text" class="form-control" placeholder= "Phone" name="phone" value="<%=userProfile.getPhone() != null ? userProfile.getPhone(): "" %>"><br>
					<input type="email" class="form-control" placeholder= "E-mail" name="email" value="<%=userProfile.getEmail() != null ? userProfile.getEmail(): "" %>"><br>
					
					<input type="text" class="form-control" placeholder= "Street Address" name="street" value="<%=userProfile.getStreet() != null ? userProfile.getStreet(): "" %>"><br>
					<input type="text" class="form-control" placeholder= "City" name="city" value="<%=userProfile.getCity() != null ? userProfile.getCity(): "" %>"><br>
					<input type="text" class="form-control" placeholder= "State" name="state" value="<%=userProfile.getState() != null ? userProfile.getState(): "" %>"><br>
					<input type="text" class="form-control" placeholder= "Zipcode" name="zipCode" value="<%=userProfile.getZipCode() != null ? userProfile.getZipCode(): "" %>"><br>
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
		                    },
		                    remote: {
		                        url: '/rest/user/validate',
		                        message: 'The email address is already in use'
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
		            phone: {
		                validators: {
		                    regexp: {
		                        regexp: /^(?:(?:\+?1\s*(?:[.-]\s*)?)?(?:\(\s*([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9])\s*\)|([2-9]1[02-9]|[2-9][02-8]1|[2-9][02-8][02-9]))\s*(?:[.-]\s*)?)?([2-9]1[02-9]|[2-9][02-9]1|[2-9][02-9]{2})\s*(?:[.-]\s*)?([0-9]{4})(?:\s*(?:#|x\.?|ext\.?|extension)\s*(\d+))?$/,
		                        message: 'Invalid phone number'
		                    },
		                    remote: {
		                        url: '/rest/user/validate',
		                        message: 'The phone number is already in use'
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