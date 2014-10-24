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


//$(function() {
//    $('form').submit(function(evt) {
//    	var form = $('form');
//        var json = JSON.stringify(form.serializeObject())
//        var action = this.getAttribute("action");
//        xmlhttp= new XMLHttpRequest();
//		xmlhttp.open("POST", action, true);
//		xmlhttp.setRequestHeader("Content-Type","application/json");
//		xmlhttp.onreadystatechange=function()
//		  {
//		  if (xmlhttp.readyState==4 && xmlhttp.status==200)
//		    {
//			  var entity = JSON.parse(xmlhttp.responseText)
//			  if(entity)
//			  {
//				//here is where you can update the UI
//				document.getElementById("response-element").innerHTML= "Created=" + xmlhttp.responseText;
//			  }
//		    }
//		  }
//		xmlhttp.send(json);
//        return false;
//    });
//});