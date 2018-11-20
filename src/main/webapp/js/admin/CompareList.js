$(document).ready(function () {
    alert("ss66");
    armarListaUsuarios();
//    hideForm();

    $('select.usuarios').change(function () {
        showEventsForm($(this).data('nroselect'));
    });

    $('select.listas').change(function () {
        showSubmitButton();
    });

    $("#btnSubmitBuscarEventos").click(function () {
        compareList();
    });

});

function showSubmitButton() {
    var listasElegidas = 1;
    $.each($("select.listas"), function () {
        if ($(this).val() == "") {
            listasElegidas = 0;
            return false;
        }
    });

    if (listasElegidas)
        $('button#btnSubmitBuscarEventos').removeClass("displayNone");
    else
        $('button#btnSubmitBuscarEventos').addClass("displayNone");
}

function compareList() {

    $(".imgLoader").removeClass('displayNone');
    var cuerpoTabla = $('table#eventosEncontrados').find('tbody');
    cuerpoTabla.html('');

    var usuario1 = $('#usuarios1 :selected').text();
    var lista1 = $('#listas1 :selected').text();
    var usuario2 = $('#usuarios2 :selected').text();
    var lista2 = $('#listas2 :selected').text();
    $.ajax("/admin/compare?user1=" + usuario1 + "&list1=" + lista1 + "&user2=" + usuario2 + "&list2=" + lista2, {
        type: "GET",
        asynchronous: false,
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            $.each(dataRecibida, function (key, valor) {
                var filaTr = document.createElement('tr');
                var celdaTd = document.createElement('td');
                celdaTd.appendChild(document.createTextNode(valor.id));
                filaTr.appendChild(celdaTd);
                //
                celdaTd = document.createElement('td');
                celdaTd.appendChild(document.createTextNode(valor.name));
                filaTr.appendChild(celdaTd);
                //
                celdaTd = document.createElement('td');
                celdaTd.appendChild(document.createTextNode(formatEventBriteDate(valor.start)));
                filaTr.appendChild(celdaTd);
                //
                celdaTd = document.createElement('td');
                celdaTd.appendChild(document.createTextNode(formatEventBriteDate(valor.end)));
                filaTr.appendChild(celdaTd);
                //
                cuerpoTabla.append(filaTr);
            });
            $(".imgLoader").addClass('displayNone');

        }
    });
}


function armarSelectListas(usuario, lista) {
    if (usuario == "")
        return;

    $('select#listas' + lista).html('<option value=""></option>');
    $.ajax("/admin/users/" + usuario + "/events", {
        type: "GET",
        asynchronous: false,
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            $.each(dataRecibida, function (index) {
                var linea = '<option value="' + index + '" >' + dataRecibida[index].nombre + '</option>';
                $('select#listas' + lista).append(linea);
            });
        }
    });
    return false;
}
;

function armarListaUsuarios() {
    $.ajax("/admin/users", {
        type: "GET",
        asynchronous: false,
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            $.each(dataRecibida.users, function (index) {
                var linea = '<option value="' + index + '" >' + dataRecibida.users[index] + '</option>';
                $.each($("select.usuarios"), function () {
                    $(this).append(linea);
                });
            });
        }
    });
    return false;
}


function showEventsForm(listaUsuarios) {
    var usuario = "";
    if ($("select#usuarios" + listaUsuarios).val() != "") {
        console.log("Showing");
        $('div.listas' + listaUsuarios).removeClass("displayNone");
        usuario = $('#usuarios' + listaUsuarios + ' :selected').text();
        armarSelectListas(usuario, listaUsuarios);
    } else {
        console.log("Hiding");
        $('div.listas' + listaUsuarios).addClass("displayNone");
    }

    return false;
}
;

function formatEventBriteDate(date) {
    var options = {year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric'};
    var fecha = new Date(date).toLocaleDateString("es-ES", options);
    return fecha;
}