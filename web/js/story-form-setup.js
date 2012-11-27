$(document).ready(function() {   
	$("#story_form").validate({
		rules: {
			year: {
				required: true,
				royYear: true
			},
			title: {
				required: true
			},
			month: {
				monthInPastForYear: true
			},
			city: {
				required: true
			},
			content: {
				required: true
			}
		}
	});
});