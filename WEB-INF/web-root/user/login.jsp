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
    <link rel="icon" href="../assets/favicon.ico">

    <title>Bytekonzz Banking</title>

    <!-- Bootstrap core CSS -->
    <link href="../assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="../assets/css/bootstrapValidator.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
	<link href="../assets/css/login-form.css" rel="stylesheet">

  </head>
  
 <!-- JSP server-side code -->
<% 
String success = request.getParameter("success");
if(success != null)
{
	out.print("<div class='alert alert-success'><a href='#' class='close' data-dismiss='alert'>&times;</a><strong>Success!</strong> " + success + "</div>");
}

String error = request.getParameter("error");
if(error != null)
{
	out.print("<div class='alert alert-danger'><a href='#' class='close' data-dismiss='alert'>&times;</a><strong>Error!</strong> " + error + "</div>");
}

%>

  <body>
   <!-- login form -->
  	<div class="container">
		<form id="signin-form" class="form-signin" role="form" method="post" action="/rest/user/login">
			<h2 class="form-signin-heading">Bytekonzz Banking</h2>
			<input type="text" class="form-control" placeholder="Username" name="username"> 
			<input type="password"class="form-control" placeholder="Password" name="password"> 
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
			<a data-toggle="modal" data-target="#create-user-modal" style="cursor:pointer;">Not a member? (create new account)</a>
		</form>
	</div>

	<!-- new user account modal(popup) window -->
	<div class="modal fade" id="create-user-modal" tabindex="-1" role="dialog" aria-labelledby="create-user-modal-Label" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" id="create-user-modal-Label">Create Account</h4>
	      </div>
	      <div class="modal-body">
	       	<form id="new-user-form" method="POST" action="/rest/user">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Username" name="username"><br>
					<input type="password"class="form-control" placeholder="Password" name="password"><br>
					<input type="password" class="form-control" placeholder="Confirm Password" name="confirmPassword"><br>
					<input type="email" class="form-control" placeholder= "E-mail" name="email"><br>
					<input type="text" class="form-control" placeholder= "First Name" name="firstName"><br>
					<input type="text" class="form-control" placeholder= "Last Name" name="lastname"><br>
					<input type="text" class="form-control" placeholder= "Phone" name="phone"><br>
					
					<input type="text" class="form-control" placeholder= "Street Address" name="street"><br>
					<input type="text" class="form-control" placeholder= "City" name="city"><br>
					<input type="text" class="form-control" placeholder= "State" name="state"><br>
					<input type="text" class="form-control" placeholder= "Zipcode" name="zipCode"><br>
				</div>
				<div class="modal-footer">
					<a href="/user/login.jsp" class="btn btn-default" role="button">Cancel</a>
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
			</form>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="../assets/js/jquery.js"></script>
    <script src="../assets/js/bootstrap.min.js"></script>
    <script src="../assets/js/bootstrapValidator.min.js"></script>
	<script src="../assets/js/form2json.js"></script>
	<!-- form validation for new account http://bootstrapvalidator.com/getting-started/-->
	<script>
	$(document).ready(function() {
    	$('#signin-form').bootstrapValidator({
	        fields: {

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
	  			success: function(xhr){
	  				if(xhr.valid)
	  				{
	  					window.location.href = "/index.jsp";
	  				}else
	  				{
	  					window.location.href = "/user/login.jsp?error="+ encodeURIComponent("Invalid username or password");  
	  				}
	  				
	  			},
	  			error: function(status, error){
	  				window.location.href = "/user/login.jsp?error="+ encodeURIComponent("Invalid username or password");  			
	  			}
			});
	    });
	});
	</script>
	<!-- New user form scripts -->
	<script>
	$(document).ready(function() {
    	$('#new-user-form').bootstrapValidator({
		        fields: {
		            username: {
		                validators: {
		                    notEmpty: {
		                        message: 'The username is required'
		                    },
		                    stringLength: {
		                        min: 5,
		                        max: 30,
		                        message: 'The username must be more than 6 and less than 30 characters long'
		                    },
		                    remote: {
		                        url: '/rest/user/validate',
		                        message: 'The username is not available'
		                    }
		                }
		            },
		            password: {
		                validators: {
		                    notEmpty: {
		                        message: 'The password is required'
		                    },
		                    stringLength: {
		                        min: 5,
		                        max: 30,
		                        message: 'The password must be more than 6 and less than 30 characters long'
		                    },
		                    identical: {
		                        field: 'confirmPassword',
		                        message: 'The password and its confirm are not the same'
		                    }
		                }
		            },
		            confirmPassword: {
		                validators: {
		                    identical: {
		                        field: 'password',
		                        message: 'The password and its confirm are not the same'
		                    }
		                }
		            },
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
		                	phone: {
	                            country: 'US',
	                            message: 'The value is not valid %s phone number'
	                        },
		                    remote: {
		                        url: '/rest/user/validate',
		                        message: 'The phone number is already in use'
		                    },
		                    regexp: {
		                        regexp: /^(\d+-?)+\d+$/,
		                        message: 'The phone number must match xxx-xxx-xxxx'
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
	  				window.location.href = "/user/login.jsp?success=" + encodeURIComponent("Account creation successful, please login to get started!");
	  			},
	  			error: function(xhr, status, error){
	  				window.location.href = "/user/login.jsp?error="+ encodeURIComponent("Unable to create account, server was unable to process request");  			
	  			}
			});
	    });
	});
	</script>
  </body>
</html>
