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
    	compareList();
    });

});

function compareList() {
	alert("Not yet implemented;");
	return;
	usuario1 = $('#usuarios1 :selected').text();
	lista1 = $('#listas1 :selected').text();
	usuario2 = $('#usuarios2 :selected').text();
	lista2 = $('#listas2 :selected').text();
	$.ajax("/admin/compare?user1=" + usuario1 + "&list1=" + lista1 + "&user2=" + usuario2 + "&list2=" + lista2, {
        type: "GET",
        asynchronous: false,
        complete: function (response) {
        	alert(response.responseText);
        }
    });
};

function armarListaAlarmas(usuario, lista) {
	if(usuario == "")
		return;
	
	$.ajax("/admin/users/" + usuario + "/events", {
        type: "GET",
        asynchronous: false,
        complete: function (response) {
        	var dataRecibida = $.parseJSON(response.responseText);
            $.each (dataRecibida, function (index) {
                var linea = '<option value="' + index + '" >' + dataRecibida[index].nombre + '</option>';
                if (lista == 1)
                	$('select#listas1').append(linea);
                
                if (lista == 2)
                	$('select#listas2').append(linea);

            });
        }
    });
    return false;
};

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
	var usuario = "";
	if ($("select#usuarios1").val() != ""){
		console.log("Showing");
		$('div.listas1').removeClass("displayNone");
		usuario = $('#usuarios1 :selected').text();
		flag = flag + 1;
	}
    else {
    	console.log("Hiding");
    	$('div.listas1').addClass("displayNone");
    }
	
	if ($("select#usuarios2").val() != ""){
		$('div.listas2').removeClass("displayNone");
		usuario = $('#usuarios2 :selected').text();
		flag = flag + 1;
	}
    else {
    	$('div.listas2').addClass("displayNone");
    }
    
	if(flag == 2){
		$('button#btnSubmitBuscarEventos').removeClass("displayNone");
		armarListaAlarmas(usuario,2);
	}
	else
	{
		$('button#btnSubmitBuscarEventos').addClass("displayNone");
		armarListaAlarmas(usuario,1);
	}
	
    return false;
}