jQuery(document).ready(function($){
	
	$("#dob").datepicker({
		dateFormat: "dd/mm/yy",
		maxDate: new Date(),
		changeMonth: true,
		changeYear: true,
		yearRange: "-90:+nn"
	});
});