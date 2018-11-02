$(document).ready(function () {
	armarListaUsuarios();
	
    $('select#usuarios1').click(function () {
    	showEventsForm();
    });
});


function armarListaUsuarios() {
    $.ajax("/admin/users", {
        type: "GET",
        asynchronous: false,
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            $.each (dataRecibida.users, function (index) {
                console.log (dataRecibida.users[index]);
                var linea = '<option value="' + index + '" >' + dataRecibida.users[index] + '</option>';
                $('select#usuarios1').append(linea);
                $('select#usuarios2').append(linea);
            });
        }
    });
    return false;
};

function showEventsForm() {
    if ($("select#usuarios1").val() != "")
    	$('select#listas1').addClass("displayNone");
    else {
    	alert("hiding");
    	$('div.notCodigo').removeClass("displayNone");
    }
    	
    
    return false;
}