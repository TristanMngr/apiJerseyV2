$(document).ready(function () {

    var categs = $.parseJSON($("#categoriesJson").html());
    $.each(categs.categories, function (key, valor) {
        var linea = '<option value="' + valor.id + '" >' + valor.name + '</option>';
        $('select#categorias').append(linea);
    });

    $('input#codigo').keyup(function () {
        keyUpInputCodigo();
        return false;
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
        $.ajax("/events/buscarEvento", {
            type: "POST",
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
                    linea += '<td><div data-toggle="modal" data-target="#addEventToList" data-whatever="' + valor + '"><button type="button" class="btn btn-info btn-sm" data-toggle="tooltip" data-placement="left" title="Agregar a una lista" ><i class="fas fa-save" style="font-size: 20px;"></i></button></div></td>';
                    linea += '</tr>';
                    cuerpoTabla.append(linea);
                });
                $('[data-toggle="tooltip"]').tooltip();
                $(".imgLoader").addClass('displayNone');
            }
        });
        return false
    });
});

function keyUpInputCodigo() {
    if ($("input#codigo").val() != "")
        $('div.notCodigo').addClass("displayNone");
    else
        $('div.notCodigo').removeClass("displayNone");
    return false;
}

function formatEventBriteDate(date) {
    var options = {year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric', };
    var fecha = new Date(date).toLocaleDateString("es-ES", options)
    return fecha;
}