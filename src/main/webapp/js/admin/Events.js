$(document).ready(function () {

	getCountEvents(1);
	getCountEvents(3);
	getCountEvents(7);
	getCountEvents(30);
	getCountEvents(-1);

});

function getCountEvents(days) {
	$(".imgLoader").removeClass('displayNone');

	$.ajax("/admin/events/search?since=" + days , {
		type: "GET",
		asynchronous: false,
        error: function (req, status, error) {
        	alert("error");
        },
		complete: function (response) {
			var $badge = $("#todayListGroupItem").find('.badge');
			
			if(days == 3)
				var $badge = $("#last3ListGroupItem").find('.badge');
			
			if(days == 7)
				var $badge = $("#last7ListGroupItem").find('.badge');
			
			if(days == 30)
				var $badge = $("#last30ListGroupItem").find('.badge');
			
			if(days == -1)
				var $badge = $("#lastInfListGroupItem").find('.badge');
			
			$badge.text(response.responseText);
		}
	});
	$(".imgLoader").addClass('displayNone');
	return false;
}