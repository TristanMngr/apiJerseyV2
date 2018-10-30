$(document).ready(function () {
alert("ss");
    armarSelectCategorias();

    armarSelectListasEventos();

    $('input#codigo').keyup(function () {
        keyUpInputCodigo();
    });

    $("#btnSubmitBuscarEventos").click(function () {
        mostrarEventos();
    });

    $("#btnCrearLista").click(function () {
        $(".seccionCrearLista").removeClass("displayNone");
    });

    $("#btnEsconderCrearLista").click(function () {
        $(".seccionCrearLista").addClass("displayNone");
    });

    $("#btnSubmitCrearLista").click(function () {
        $(this).attr('disabled', true);
        crearLista();
    });

    $("#btnSubmitAgregarEvento").click(function () {
        agregarEventoEnLista();
    });

    $(document).on("click", "button.btnAddEvento", function () {
        var nombre = $(this).data('nombre');
        var codigo = $(this).data('codigo');
        $("h5#modalLabel").html('Agregar ' + nombre.substr(0, 30) + ' a una lista');
        $("#modalCodigoEvento").val(codigo);
//        var padre = $(this).parent();
//        var entregaId = padre.find('.entregaId').html();
//        var entregaNombre = padre.find('.entregaNombre').html();
//        var form = $('#subirCorreccion');
//        form.find('h4').html('Subir corrección para la entrega ' + entregaNombre);
//        form.find('form').attr('action', '/trabajos/subirCorreccion/' + entregaId);
    });
});



function mostrarEventos() {
    $(".imgLoader").removeClass('displayNone');
    var cuerpoTabla = $('table#eventosEncontrados').find('tbody');
    cuerpoTabla.html('');
    var codigo = $("#codigo").val();
    var nombre = $("#nombre").val();
    var categoryId = $("#categorias option:selected").val();
    var desde = $("#desde").val();
    var hasta = $("#hasta").val();

    $.ajax("/events/buscarEventos", {
        type: "GET",
        data: {
            'codigo': codigo,
            'nombre': nombre,
            'categoryId': categoryId,
            'desde': desde,
            'hasta': hasta,
        },
        asynchronous: false,
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            $.each(dataRecibida.events, function (key, valor) {
                var filaTr = document.createElement('tr');
                var celdaTd = document.createElement('td');
                celdaTd.appendChild(document.createTextNode(valor.id));
                filaTr.appendChild(celdaTd);
                //
                celdaTd = document.createElement('td');
                celdaTd.appendChild(document.createTextNode(valor.name.text));
                filaTr.appendChild(celdaTd);
                //
                celdaTd = document.createElement('td');
                celdaTd.appendChild(document.createTextNode(formatEventBriteDate(valor.start.local)));
                filaTr.appendChild(celdaTd);
                //
                celdaTd = document.createElement('td');
                celdaTd.appendChild(document.createTextNode(formatEventBriteDate(valor.end.local)));
                filaTr.appendChild(celdaTd);
                //
                celdaTd = document.createElement('td');
                var divModal = document.createElement('div');
                divModal.setAttribute('data-toggle', 'modal');
                divModal.setAttribute('data-target', '#addEventToList');
                celdaTd.appendChild(divModal);
                var buttonModal = document.createElement('button');
                buttonModal.setAttribute('type', 'button');
                buttonModal.setAttribute('class', 'btn btn-info btn-sm btnAddEvento');
                buttonModal.setAttribute('data-toggle', 'tooltip');
                buttonModal.setAttribute('data-placement', 'left');
                buttonModal.setAttribute('title', 'Agregar a una lista');
                buttonModal.setAttribute('data-nombre', valor.name.text);
                buttonModal.setAttribute('data-codigo', valor.id);
                divModal.appendChild(buttonModal);
                var icon = document.createElement('i');
                icon.setAttribute('class', 'fas fa-file-download');
                icon.setAttribute('style', 'font-size: 20px;');
                buttonModal.appendChild(icon);
                filaTr.appendChild(celdaTd);
                //
                cuerpoTabla.append(filaTr);
            });
            $(".imgLoader").addClass('displayNone');
        }
    });
    return false;
}

function armarSelectCategorias() {
    $.ajax("/events/categories", {
        type: "GET",
        asynchronous: false,
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            $.each(dataRecibida.categories, function (key, valor) {
                var linea = '<option value="' + valor.id + '" >' + valor.name + '</option>';
                $('select#categorias').append(linea);
            });
        }
    });
    return false;
}
function keyUpInputCodigo() {
    if ($("input#codigo").val() != "")
        $('div.notCodigo').addClass("displayNone");
    else
        $('div.notCodigo').removeClass("displayNone");
    return false;
}

function formatEventBriteDate(date) {
    var options = {year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric'};
    var fecha = new Date(date).toLocaleDateString("es-ES", options);
    return fecha;
}

function armarSelectListasEventos() {
    $(".imgLoader").removeClass('displayNone');
    $.ajax("/eventsLists/getFromUser", {
        type: "GET",
        data: {
            userId: "5bcbba1743b244dd134d6f45", //TODO getUserLogged()
        },
        asynchronous: false,
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            $.each(dataRecibida.eventsLists, function (_, lista) {
                $("select#modalSelectListas").append('<option value="' + lista.hexId + '">' + lista.nombre + '</option>');
            });
            $(".imgLoader").addClass('displayNone');
        }
    });
    return false;
}

function crearLista() {
    $(".imgLoader").removeClass('displayNone');
    var nombreLista = $("#nombreLista").val();
    $.ajax("/eventsLists/create", {
        type: "POST",
        data: {
            'nombreLista': nombreLista,
        },
        asynchronous: false,
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            if (!dataRecibida.error) {
                $("select#modalSelectListas").html('');
                armarSelectListasEventos();
                $("#btnEsconderCrearLista").click();
                $("#nombreLista").val('');
            }
            $(".imgLoader").addClass('displayNone');
            $("button").attr('disabled', false);
        }
    });
    return false;
}

//function addDataToModal(codigo, nombre) {
//    $("h5#modalLabel").html('Agregar ' + nombre.substr(0, 30) + ' a una lista');
//    $("#modalCodigoEvento").val(codigo);
//}

function agregarEventoEnLista() {
    $("#btnSubmitAgregarEvento").attr('disabled', true);
    $(".imgLoader").removeClass('displayNone');
    var codigo = $("#modalCodigoEvento").val();
    var lista = $("select#modalSelectListas").find(":selected").val();
    $.ajax("/eventsLists/addEvent", {
        type: "POST",
        data: {
            'codigo': codigo,
            'lista': lista,
        },
        asynchronous: false,
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            if (!dataRecibida.error) {
                alert("Se agregó correctamente el evento a la lista");
            } else {
                alert("Error!! No se pudo agregar el evento a la lista deseada");
            }
            $("#btnSubmitAgregarEvento").attr('disabled', false);
            $("#btnModalDismiss").click();
            $(".imgLoader").addClass('displayNone');
        }
    });
}
