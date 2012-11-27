$(document).ready(function() {
	$("#profile_form").validate({
		onfocusout:false,
		onkeyup: false,
		rules: {
			firstName: { required: true },
			lastName: { required: true },
			shortDescription: { maxlength: 2000 },
			dateOfJoining: { dateTW: true }
		}
	});
});
