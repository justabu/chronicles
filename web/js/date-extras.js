function isAfterTWConception(date) {
	var year = 1993;
	var month = 7;
	var day = 1;
	
	var javascriptMonth = month - 1;
	
	return isAfter(new Date(year, javascriptMonth, day), date); 
}

function isAfter(thresholdDate, inputDate) {
	return inputDate >= thresholdDate;
}

function isInPast(date) {
	var today = new Date();
	return date <= today
}

function isValidDayForMonthInYear(day, month, year) {
	var monthDays = {
		1:31, 2:(isLeapYear(year) ? 29 : 28), 3:31, 
		4:30, 5:31, 6:30, 
		7:31, 8:31, 9:30, 
		10:31, 11:30, 12:31
	}
	return day <= monthDays[month];
}

function isLeapYear(year) {
	return (year % 4 == 0 && year % 100 != 0 || year % 400 == 0);
}

function validMonthInPastForYear(value) {
	var inputYear = parseInt($('#year').val());
	if (!inputYear) return true;
	
	var months = {
		"January":0, "February":1, "March":2, 
		"April":3, "May":4, "June":5,
		"July":6, "August":7, "September":8, 
		"October":9, "November":10, "December":11
	};
	if(value == "") return true;
	var selectedMonth = months[value];
	
	if(selectedMonth == null) return false;
	
	var selectedDate = new Date(inputYear, selectedMonth, 1);
	
	return isInPast(selectedDate);

}

function validRoyYear(value, element) {
	var year = parseInt(value);
	if(!isAfter(1962, year)) return false;
	if(!isInPast(new Date(year, 1, 1))) return false;
	return true;
}

function validTWDate(value, element) {
	if(value == "") return true;
	var dateComponents = /^(\d?\d)\/(\d?\d)\/(\d\d\d\d)$/.exec(value);
	
	if(dateComponents == null) return false;
	
	var day = parseInt(dateComponents[1]);
	var month = parseInt(dateComponents[2],10);
	var year = parseInt(dateComponents[3]);
	
	var javascriptMonth = month - 1;
	
	var date = new Date(year, javascriptMonth, day);
	
	if(!isValidDayForMonthInYear(day, month, year)) return false;
	if(!isAfterTWConception(date)) return false;
	if(!isInPast(date)) return false;

	return true;
}