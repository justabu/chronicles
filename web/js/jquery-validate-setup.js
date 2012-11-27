$.validator.addMethod(
	"dateTW", 
	validTWDate,
	"Please enter a valid date."
);

$.validator.addMethod(
	"monthInPastForYear",
	validMonthInPastForYear,
	"Month is in the future."
);

$.validator.addMethod(
	"royYear", 
	validRoyYear, 
	"Enter a valid Year."
);