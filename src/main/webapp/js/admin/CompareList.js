$(document).ready(function () {
	armarListaUsuarios();
	hideForm();
	
    $('select#usuarios1').keyup(function () {
    	showEventsForm();
    });
    
    $('select#usuarios1').click(function () {
    	showEventsForm();
    });
    
    $('select#usuarios2').keyup(function () {
    	showEventsForm();
    });
       
    $('select#usuarios2').click(function () {
    	showEventsForm();
    });
    
    $("#btnSubmitBuscarEventos").click(function () {
        alert("Not yet implemented;");
    });

});


function armarListaUsuarios() {
    $.ajax("/admin/users", {
        type: "GET",
        asynchronous: false,
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            $.each (dataRecibida.users, function (index) {
                var linea = '<option value="' + index + '" >' + dataRecibida.users[index] + '</option>';
                $('select#usuarios1').append(linea);
                $('select#usuarios2').append(linea);
            });
        }
    });
    return false;
};

function hideForm() {
	$('div.listas1').addClass("displayNone");
	$('div.listas2').addClass("displayNone");
	$('button#btnSubmitBuscarEventos').addClass("displayNone");
};

function showEventsForm() {
	var flag = 0;
	if ($("select#usuarios1").val() != ""){
		console.log("Showing");
		$('div.listas1').removeClass("displayNone");
		flag = flag + 1;
	}
    else {
    	console.log("Hiding");
    	$('div.listas1').addClass("displayNone");
    }
	
	if ($("select#usuarios2").val() != ""){
		$('div.listas2').removeClass("displayNone");
		flag = flag + 1;
	}
    else {
    	$('div.listas2').addClass("displayNone");
    }
    
	if(flag == 2){
		$('button#btnSubmitBuscarEventos').removeClass("displayNone");
	}
	else
	{
		$('button#btnSubmitBuscarEventos').addClass("displayNone");
	}
	
    return false;
}