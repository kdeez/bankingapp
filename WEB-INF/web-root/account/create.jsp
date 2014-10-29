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
	<%@include file="/components/navbar.jsp" %>
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
								<input type="hidden" name="accountType" value="0">
								<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" title="Defaults to Checking">
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

<!-- form validation for new account http://bootstrapvalidator.com/getting-started/-->
<script>
$(document).ready(function() {
    $('#new-account-form')
    .bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	description: {
                message: 'The account name is not valid',
                validators: {
                    notEmpty: {
                        message: 'The account name is required and cannot be empty'
                    },
                    stringLength: {
                        min: 6,
                        max: 20,
                        message: 'The account name must be more than 6 and less than 20 characters long'
                    },
                }
            },
            accountType: {
            	message: 'You must choose an account type',
            	validators: {
            		between: {
            		    min: 0,
            		    max: 1,
            		    message: 'You must choose an account type'
            		}
                }
            }
        }
    })
    .on('success.form.bv', function(e) {
        // Prevent form submission
        e.preventDefault();

        // Get the form instance
        var $form = $(e.target);

        // Get the BootstrapValidator instance
        var bv = $form.data('bootstrapValidator');

        // Use Ajax to submit form data
        $.ajax({
  			url:$form.attr('action'),
  			type:"POST",
  			data:$form.toJSONString(),
  			contentType:"application/json; charset=utf-8",
  			dataType:"json",
  			success: function(){
  				window.location.href = "/index.jsp";
  			}
		});
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
