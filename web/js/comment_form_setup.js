$(document).ready(function() {
	$("#comment_form").validate({
		errorLabelContainer: $("#error"),
		rules: {
			content: {required : true}
		}
	});
});