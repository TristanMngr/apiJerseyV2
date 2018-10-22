$(document).ready(function () {
    getUserLists();
});

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
                cardBody.appendChild(document.createTextNode("lista de eventos"));
                cardCollapse.appendChild(cardBody);
                ////    

                $("#accordion").append(card);
            });
        }
    });
    return false;
}