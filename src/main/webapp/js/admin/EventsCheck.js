$(document).ready(function () {
	
	alert("Document Ready");
	
    $('input#codigo').keyup(function () {
        keyUpInputCodigo();
    });
	
    $("#btnSubmitBuscarEventos").click(function () {
        mostrarUsuarios();
    });
	
});

function mostrarUsuarios() {
    $(".imgLoader").removeClass('displayNone');
    var cuerpoTabla = $('table#eventosEncontrados').find('tbody');
    cuerpoTabla.html('');
    var codigo = $("#codigo").val();
    var nombre = $("#nombre").val();
    var categoryId = $("#categorias option:selected").val();
    var desde = $("#desde").val();
    var hasta = $("#hasta").val();

    $.ajax("/admin/users", {
        type: "GET",
        asynchronous: false,
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            $.each (dataRecibida.users, function (index) {
            	
                var filaTr = document.createElement('tr');
                var celdaTd = document.createElement('td');
                celdaTd.appendChild(document.createTextNode(dataRecibida.users[index]));
                filaTr.appendChild(celdaTd);
                cuerpoTabla.append(filaTr);

            });
        }
    });
    $(".imgLoader").addClass('displayNone');
    return false;
}

function keyUpInputCodigo() {
    if ($("input#codigo").val() != "")
        $('div.notCodigo').addClass("displayNone");
    else
        $('div.notCodigo').removeClass("displayNone");
    return false;
};