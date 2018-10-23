$(document).ready(function () {
    getUserLists();

    $("#btnSubmitCrearLista").click(function () {
        $(this).attr('disabled', true);
        crearLista();
    });
});

function appendDomLista(lista) {
    var card = document.createElement('div');
    card.setAttribute('class', 'card');
    ////
    var cardHeader = document.createElement('div');
    cardHeader.setAttribute('class', 'card-header');
    cardHeader.setAttribute('id', 'heading_' + lista.id);
    card.appendChild(cardHeader);
    ////
    var h5 = document.createElement('h5');
    h5.setAttribute('class', 'mb-0');
    cardHeader.appendChild(h5);
    ////
    var buttonCollapse = document.createElement('button');
    buttonCollapse.setAttribute('class', 'btn btn-link collapsed');
    buttonCollapse.setAttribute('data-toggle', 'collapse');
    buttonCollapse.setAttribute('data-target', '#collapse_' + lista.id);
    buttonCollapse.setAttribute('aria-expanded', 'false');
    buttonCollapse.setAttribute('aria-controls', 'collapse_' + lista.id);
    buttonCollapse.appendChild(document.createTextNode(lista.nombre));
    h5.appendChild(buttonCollapse);
    ////
    var cardCollapse = document.createElement('div');
    cardCollapse.setAttribute('id', 'collapse_' + lista.id);
    cardCollapse.setAttribute('class', 'collapse');
    cardCollapse.setAttribute('aria-labelledby', 'heading_' + lista.id);
    cardCollapse.setAttribute('data-parent', '#accordion');
    card.appendChild(cardCollapse);
    ////
    var cardBody = document.createElement('div');
    cardBody.setAttribute('class', 'card-body');
    $.each(lista.eventos, function (_, evento) {
        var h6Evento = document.createElement('h6');
        h6Evento.setAttribute('class', 'evento');
        h6Evento.appendChild(document.createTextNode(evento.id + ' - ' + evento.name.text + ' - ' + evento.start.local));
        cardBody.appendChild(h6Evento);
        cardBody.appendChild(document.createElement('hr'));
    });

    cardCollapse.appendChild(cardBody);
    ////    

    $("#accordion").append(card);
}

function getUserLists() {
    $.ajax("/eventsLists/getUserLists", {
        type: "GET",
        asynchronous: false,
        data: {
            userId: 1
        },
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            $.each(dataRecibida, function (_, lista) {
                appendDomLista(lista);
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
                alert("Se creó correctamente la lista");
                appendDomLista(dataRecibida.lista);
            }
            $("button").attr('disabled', false);
            $(".btnDismiss").click();
            $(".imgLoader").addClass('displayNone');




//            $("#subPageContainer").remove();
//            $("main").load("/html/listas/index.html");
        }
    });
    return false;
}