$(document).ready(function () {
    getUserLists();

    $("#btnSubmitCrearLista").click(function () {
        $(this).attr('disabled', true);
        crearLista();
    });

    $("#btnSubmitEditLista").click(function () {
        $(this).attr('disabled', true);
        editLista();
    });

    $(document).on('click', ".btnDeleteList", function () {
        var listaId = $(this).data('hexid');
        deleteList(listaId);
    });

    $(document).on('click', ".btnEditList", function () {
        var nombre = $(this).data("nombre");
        var listaId = $(this).data("hexid");
        $("#editList").find(".modal-title").html("Editar los datos de la lista " + nombre);
        $("#editList").find('input[name="listaId"]').val(listaId);
    })
});

function appendDomLista(lista) {
    var card = document.createElement('div');
    card.setAttribute('class', 'card');
    ////
    var cardHeader = document.createElement('div');
    cardHeader.setAttribute('class', 'card-header');
    cardHeader.setAttribute('id', 'heading_' + lista.hexId);
    card.appendChild(cardHeader);
    ////
    var h5 = document.createElement('h5');
    h5.setAttribute('class', 'mb-0');
    cardHeader.appendChild(h5);
    ////
    var buttonCollapse = document.createElement('button');
    buttonCollapse.setAttribute('class', 'btn btn-link collapsed');
    buttonCollapse.setAttribute('data-toggle', 'collapse');
    buttonCollapse.setAttribute('data-target', '#collapse_' + lista.hexId);
    buttonCollapse.setAttribute('aria-expanded', 'false');
    buttonCollapse.setAttribute('aria-controls', 'collapse_' + lista.hexId);
    buttonCollapse.appendChild(document.createTextNode(lista.nombre));
    h5.appendChild(buttonCollapse);
    ////
    var buttonDelete = document.createElement('button');
    buttonDelete.setAttribute('class', 'btn btn-sm btn-danger btnDeleteList float-right');
    buttonDelete.setAttribute('data-hexid', lista.hexId);
    var icon = document.createElement('i');
    icon.setAttribute('class', 'fas fa-trash-alt');
//    icon.setAttribute('style', 'font-size: 20px;');
    buttonDelete.appendChild(icon);
    h5.appendChild(buttonDelete);
    ////
    var buttonEdit = document.createElement('button');
    buttonEdit.setAttribute('class', 'btn btn-sm btn-success btnEditList float-right');
    buttonEdit.setAttribute('data-hexid', lista.hexId);
    buttonEdit.setAttribute('data-toggle', "modal");
    buttonEdit.setAttribute('data-target', "#editList");
    buttonEdit.setAttribute('data-name', lista.nombre);
    buttonEdit.setAttribute('style', 'margin-right: 20px;');
    var icon = document.createElement('i');
    icon.setAttribute('class', 'fas fa-edit');
    buttonEdit.appendChild(icon);
    h5.appendChild(buttonEdit);
    ////
    var cardCollapse = document.createElement('div');
    cardCollapse.setAttribute('id', 'collapse_' + lista.hexId);
    cardCollapse.setAttribute('class', 'collapse');
    cardCollapse.setAttribute('aria-labelledby', 'heading_' + lista.hexId);
    cardCollapse.setAttribute('data-parent', '#accordion');
    card.appendChild(cardCollapse);
    ////
    var cardBody = document.createElement('div');
    cardBody.setAttribute('class', 'card-body');
    $.each(lista.eventsObj, function (_, eventoJson) {
        var evento = $.parseJSON(eventoJson);
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

function deleteList(listaId) {
    $(".imgLoader").removeClass('displayNone');
    $.ajax("/eventsLists/delete", {
        type: "POST",
        asynchronous: false,
        data: {
            listaId: listaId,
        },
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            if (!dataRecibida.error) {
                $("#heading_" + listaId).parent().remove();
            }
            $(".imgLoader").addClass('displayNone');
        }
    });
    return false;
}

function getUserLists() {
    $(".imgLoader").removeClass('displayNone');
    $.ajax("/eventsLists/getFromUser", {
        type: "GET",
        asynchronous: false,
        data: {
            userId: "5be4d37edd70291dc4e618a2",
        },
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            $.each(dataRecibida.eventsLists, function (_, lista) {
                appendDomLista(lista);
            });
            $(".imgLoader").addClass('displayNone');
        }
    });
    return false;
}

function crearLista() {
    $(".imgLoader").removeClass('displayNone');
    var nombreLista = $("#editList").find('input[name="nombreLista"]').val();
    $.ajax("/eventsLists/create", {
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
        }
    });
    return false;
}

function editLista() {
    $(".imgLoader").removeClass('displayNone');
    var listaId = $("#editList").find('input[name="listaId"]').val();
    var nombreLista = $("#editList").find('input[name="nombreLista"]').val();
    $.ajax("/eventsLists/edit", {
        type: "POST",
        data: {
            'listaId': listaId,
            'nombreLista': nombreLista,
        },
        asynchronous: false,
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            if (!dataRecibida.error) {
                alert("Se modificó correctamente la lista");
                $("#heading_" + listaId).find(".btn-link").html(nombreLista);
            }
            $("button").attr('disabled', false);
            $(".btnDismiss").click();
            $(".imgLoader").addClass('displayNone');
        }
    });
    return false;
}