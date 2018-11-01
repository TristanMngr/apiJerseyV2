$(document).ready(function () {
	$("#btnSubmitBuscarUsuarios").click(function () {
        mostrarUsuarios();
    });
});

function mostrarUsuarios() {
    $(".imgLoader").removeClass('displayNone');
    var nombre = $("#nombre").val();
    var x = document.forms["myForm"]["username"].value;
    
    $.ajax({
    	url: "/admin/users/" + nombre, 
        type: "GET",
        asynchronous: false,
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            $.each(dataRecibida, function (key, valor) {
            	if(key == "username") {
            		document.forms["myForm"]["username"].value = valor;
            	}
            	if(key == "lastLogin") {
            		document.forms["myForm"]["lastLogin"].value = valor;
            	}
            	if(key == "cantListas") {
            		document.forms["myForm"]["cantListas"].value = valor;
            	}
            	if(key == "cantAlarmas") {
            		document.forms["myForm"]["cantAlarmas"].value = valor;
            	}
            });
            $(".imgLoader").addClass('displayNone');
        }
    });
    return false;
}