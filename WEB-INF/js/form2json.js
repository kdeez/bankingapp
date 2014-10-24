/**
 * This snippet of code overrides the default behavior when submitting an HTML form.
 * This will take all form elements and serialize them to a JSON string and submit via AJAX XHR
 */
$.fn.serializeObject = function()
{
   var o = {};
   var a = this.serializeArray();
   $.each(a, function() {
       if (o[this.name]) {
           if (!o[this.name].push) {
               o[this.name] = [o[this.name]];
           }
           o[this.name].push(this.value || '');
       } else {
           o[this.name] = this.value || '';
       }
   });
   return o;
};

$.fn.toJSONString = function()
{
	return JSON.stringify(this.serializeObject())
}

function showSuccessMessage(message)
{
	var element = document.getElementById("user-feedback-message");
	element.innerHTML = "<div class='alert alert-success alert-dismissible' role='alert'>"
						+ "<button type='button' class='close' data-dismiss='alert'>&times;</button>"
						+ "<strong>Success!</strong>" + message
						+ "</div>";
}

function showErrorMessage(message)
{
	var element = document.getElementById("user-feedback-message");
	element.innerHTML = "<div class='alert alert-danger alert-dismissible' role='alert'>"
						+ "<button type='button' class='close' data-dismiss='alert'>&times;</button>"
						+ "<strong>Error!</strong>" + message
						+ "</div>";
}


