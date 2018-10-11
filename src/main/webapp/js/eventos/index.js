$(document).ready(function () {

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
        crearLista();
    });

    $("#btnSubmitAgregarEvento").click(function () {
        agregarEventoEnLista();
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
            var linea;
            $.each(dataRecibida.events, function (key, valor) {
                linea = '<tr>';
                linea += '<td>' + valor.id + '</td>';
                linea += '<td>' + valor.name.text + '</td>';
                linea += '<td>' + formatEventBriteDate(valor.start.local) + '</td>';
                linea += '<td>' + formatEventBriteDate(valor.end.local) + '</td>';
                linea += '<td><div data-toggle="modal" data-target="#addEventToList" ><button onclick="addDataToModal(' + valor.id + ',\'' + valor.name.text + '\')" type="button" class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="left" title="Agregar a una lista" ><i class="fas fa-file-download" style="font-size: 20px;"></i></button></div></td>';
                linea += '</tr>';
                cuerpoTabla.append(linea);
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
    $.ajax("/eventsLists/getFromUser", {
        type: "GET",
        data: {
            userId: 1,
        },
        asynchronous: false,
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            $.each(dataRecibida, function (key, valor) {
                $("select#modalSelectListas").append('<option value="' + valor.id + '">' + valor.nombre + '</option>');
            });
        }
    });
    return false;
}

function crearLista() {
    $(".imgLoader").removeClass('displayNone');
    var nombreLista = $("#nombreLista").val();
    $.ajax("/eventsLists/crearLista", {
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
            }
            $(".imgLoader").addClass('displayNone');
        }
    });
    return false;
}

function addDataToModal(codigo, nombre) {
    $("h5#modalLabel").html('Agregar ' + nombre.substr(0, 30) + ' a una lista');
    $("#modalCodigoEvento").val(codigo);
}

function agregarEventoEnLista() {
    var codigo = $("#modalCodigoEvento").val();
    var lista = $("select#modalSelectListas").find(":selected").val();
    $.ajax("/eventsLists/agregarEvento", {
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
            }else{
                alert("Error!! No se pudo agregar el evento a la lista deseada");
            }
            $("#btnModalDismiss").click();
            $(".imgLoader").addClass('displayNone');
        }
    });
}
