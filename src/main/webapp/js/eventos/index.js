$(document).ready(function () {

    armarSelectCategorias();

    armarSelectListasEventos();

    $('input#codigo').keyup(function () {
        keyUpInputCodigo();
    });

    $("#btnSubmit").click(function () {
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
                    linea += '<td><div data-toggle="modal" data-target="#addEventToList" data-whatever="' + valor + '"><button type="button" class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="left" title="Agregar a una lista" ><i class="fas fa-file-download" style="font-size: 20px;"></i></button></div></td>';
                    linea += '</tr>';
                    cuerpoTabla.append(linea);
                });
                $(".imgLoader").addClass('displayNone');
            }
        });
        return false
    });

    $("#btnCrearLista").click(function () {
        $(".seccionCrearLista").removeClass("displayNone");
    });

    $("#btnEsconderCrearLista").click(function () {
        $(".seccionCrearLista").addClass("displayNone");
    });

    $("#btnSubmitCrearLista").click(function () {
        $(".imgLoader").removeClass('displayNone');
        var nombreLista = $("#nombreLista").val();
        $.ajax("/events/crearLista", {
            type: "POST",
            data: {
                'nombreLista': nombreLista,
            },
            asynchronous: false,
            complete: function (response) {
                var dataRecibida = $.parseJSON(response.responseText);
                if (dataRecibida != "error") {
                    //regenerar el select con la nueva lista de valores recibidos
                    var i = 1;
                    $("select#selectListas").html('');
                    $.each(dataRecibida.events, function (key, valor) {
                        $("select#selectListas").append('<option value="' + valor.value + '">' + valor.texto + '</option>');
                    });
                }
                $(".imgLoader").addClass('displayNone');
            }
        });
        return false;
    });
});

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
    var options = {year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric' };
    var fecha = new Date(date).toLocaleDateString("es-ES", options);
    return fecha;
}

function armarSelectListasEventos() {
    $.ajax("/events/eventsLists", {
        type: "GET",
        data: {
            userId: 1,
        },
        asynchronous: false,
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            $.each(dataRecibida, function (key, valor) {
                $("select#selectListas").append('<option value="' + valor.id + '">' + valor.nombre + '</option>');
            });
        }
    });
    return false;
}