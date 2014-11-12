 <div class="modal fade" id="close-account-modal" tabindex="-1" role="dialog" aria-labelledby="close-account-modal-Label" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" id="close-account-modal-Label">Close Account</h4>
	      </div>
	      <div class="modal-body">
	      	<p>Are you sure you would like to close the account?</p>
	      	<p>Please remove all funds before proceeding</p>
	      	<p class="text-danger"><strong>WARNING: This cannot be undone</strong></p>
	       	<form id="close-account-form">
				<div class="modal-footer">
				<button type="button" class="btn btn-default"data-dismiss="modal">Close</button>
				<button type="submit" class="btn btn-danger">Submit</button>
				</div>
			</form>
	      </div>
	    </div>
	  </div>
	</div>

<script>
	$(document).ready(function() {
    	$('#close-account-form')
	    .on('submit',  function(e) {
		    
	        // Prevent form submission
	        e.preventDefault();
	
	        // Get the form instance
	        var $form = $(e.target);
				
	        // Use JQuery Ajax to submit form data
	        $.ajax({
	  			url: "/rest/account?id=<%=accountId%>",
	  			type:"DELETE",
	  			success: function(){
	  				window.location.href = "/index.jsp";
	  			},
	  			error: function(xhr, status, error){
	  				showErrorMessage(" Unable to complete transaction"); 			
	  			}
			});
	    });
	});
</script> 