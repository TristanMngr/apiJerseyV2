var categoriesList = [];

$(document).ready(function () {
    categoriesList = armarSelectCategorias();
    getCurrentUserAlarms();

    $("#btnCreateAlarm").click(function () {
        createAlarm();
    });

});


function armarSelectCategorias() {
    var categoriesList = [];
    $.ajax("/events/categories", {
        type: "GET",
        asynchronous: false,
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            $.each(dataRecibida.categories, function (key, valor) {
                var linea = '<option value="' + valor.id + '" >' + valor.name + '</option>';
                categoriesList[valor.id.toString()] = valor.name;
                $('select#alarmCategory').append(linea);
            });
        }
    });
    return categoriesList;
}

function createAlarm() {
    var alarmName = $("#alarmName").val();
    var alarmCategory = $("#alarmCategory").val();
    var divMessage = $("div#message")[0];
    $.ajax("/alarms/create", {
        type: "POST",
        data: {
            'name': alarmName,
            'categoryId': alarmCategory
        },
        asynchronous: false,
        complete: function (response) {
            if (response.error) {
                alert("error")
            } else {
                // TODO add all alarm from user
                var dataRecibida = $.parseJSON(response.responseText);
                if (dataRecibida.error === 0) {
                    createTable(alarmName, alarmCategory);

                    var cuerpoTabla = $('table#alarmList').find('tbody');

                    var filaTr = document.createElement('tr');
                    filaTr.setAttribute("id", alarmName);

                    var celdaTd = document.createElement('td');
                    celdaTd.appendChild(document.createTextNode(alarmName));
                    filaTr.appendChild(celdaTd);

                    celdaTd = document.createElement('td');
                    celdaTd.appendChild(document.createTextNode(alarmCategory));
                    filaTr.appendChild(celdaTd);

                    var celdaTd = document.createElement('td');
                    var viewEvents = document.createElement("button");
                    viewEvents.setAttribute('class', 'btn btn-primary btnEvents');
                    viewEvents.setAttribute('type', 'button');
                    viewEvents.setAttribute('data-toggle', 'collapse');
                    viewEvents.setAttribute('data-target', '#accordion');
                    viewEvents.setAttribute('aria-expanded', 'true');
                    viewEvents.setAttribute('aria-controls', 'accordion');
                    viewEvents.appendChild(document.createTextNode('See All'));
                    celdaTd.appendChild(viewEvents);
                    filaTr.appendChild(celdaTd);

                    var celdaTd = document.createElement('td');
                    var removeButton = celdaTd.appendChild(document.createElement("button"));
                    removeButton.setAttribute('class', 'btn btn-danger btnRemove');
                    removeButton.setAttribute('type', 'button');
                    removeButton.setAttribute('data-toggle', 'button');
                    removeButton.setAttribute("id", alarmName);
                    removeButton.appendChild(document.createTextNode('Remove'));
                    celdaTd.appendChild(removeButton);
                    filaTr.appendChild(celdaTd);

                    // add collapse data
                    var filaTr = document.createElement('tr');
                    filaTr.setAttribute("class", alarmName);
                    var celdaTd = document.createElement('td');
                    celdaTd.setAttribute('colspan', '4');
                    var div = document.createElement('div');
                    div.setAttribute('id', 'event-' + alarmName);
                    div.setAttribute('class', 'collapse');

                    filaTr.append(celdaTd);
                    celdaTd.append(div);
                    cuerpoTabla.append(filaTr);

                    divMessage.setAttribute('class', 'col-12 alert alert-success');
                } else {
                    divMessage.setAttribute('class', 'col-12 alert alert-danger');
                }

                divMessage.setAttribute('role', 'alert');
                divMessage.append(document.createTextNode(''));

                divMessage.innerHTML = dataRecibida.message;
            }
        }
    });
    return false;
}


function getCurrentUserAlarms() {
    $.ajax("/users/currentUserAlarm", {
        type: "GET",
        asynchronous: false,
        complete: function (response) {
            if (response.error) {
                // TODO handle error
                alert("error")
            } else {
                // TODO add all alarm from user

                var dataRecibida = $.parseJSON(response.responseText);

                $.each(dataRecibida, function (key, alarm) {
                    createTable(alarm.name, alarm.paramsEventBrite.categoryId);
                })
            }
        }
    });
    return false;
}


function createTable(alarmName, alarmCategory) {

    var cuerpoTabla = $('table#alarmList').find('tbody');
    var filaTr = document.createElement('tr');
    filaTr.setAttribute("id", alarmName);

    var celdaTd = document.createElement('td');
    celdaTd.append(document.createTextNode(alarmName));
    filaTr.append(celdaTd);

    celdaTd = document.createElement('td');

    celdaTd.append(document.createTextNode(alarmCategory));
    filaTr.append(celdaTd);

    var celdaTd = document.createElement('td');
    var viewEvents = document.createElement("button");
    viewEvents.setAttribute('id', 'date-' + alarmName);
    viewEvents.setAttribute('class', 'btn btn-primary btnEvents');
    viewEvents.setAttribute('type', 'button');
    viewEvents.setAttribute('data-toggle', 'collapse');
    viewEvents.setAttribute('data-target', '#event-' + alarmName);
    viewEvents.setAttribute('aria-expanded', 'true');
    viewEvents.setAttribute('aria-controls', 'accordion');
    viewEvents.append(document.createTextNode('See All'));
    celdaTd.append(viewEvents);
    filaTr.append(celdaTd);

    var celdaTd = document.createElement('td');
    var removeButton = document.createElement("button");
    removeButton.setAttribute('class', 'btn btn-danger btnRemove');
    removeButton.setAttribute('type', 'button');
    removeButton.setAttribute('data-toggle', 'button');
    removeButton.setAttribute("id", alarmName);
    removeButton.appendChild(document.createTextNode('Remove'));
    celdaTd.append(removeButton);
    filaTr.append(celdaTd);

    cuerpoTabla.append(filaTr);


    // add collapse data
    var filaTr = document.createElement('tr');
    filaTr.setAttribute("class", alarmName);
    var celdaTd = document.createElement('td');
    celdaTd.setAttribute('colspan', '4');
    var div = document.createElement('div');
    div.setAttribute('id', 'event-' + alarmName);
    div.setAttribute('class', 'collapse');

    filaTr.append(celdaTd);
    celdaTd.append(div);
    cuerpoTabla.append(filaTr);
}


$(document).on("click", "button.btnEvents", function () {
    var buttonName = $(this).attr('id').split('-').pop();
    console.log(buttonName);


    $.ajax("/alarms/" + buttonName, {
        type: "GET",
        asynchronous: false,
        complete: function (response) {
            if (response.error) {
                // TODO handle error
                alert("error")
            } else {
                $('table#'+ buttonName).remove();
                var data = $.parseJSON(response.responseText);
                var inputElement = $('div#event-' + buttonName);

                var table = document.createElement('table');
                table.setAttribute('id', buttonName);

                var thead = document.createElement('thead');
                var tbody = document.createElement('tbody');

                var tr = document.createElement('tr');

                var thId = document.createElement('th');
                thId.append(document.createTextNode('Codigo'));

                var thName = document.createElement('th');
                thName.append(document.createTextNode('Nombre'));

                var thStart = document.createElement('th');
                thStart.append(document.createTextNode('Inicio'));

                var thEnd = document.createElement('th');
                thEnd.append(document.createTextNode('Fin'));

                tr.append(thId);
                tr.append(thName);
                tr.append(thStart);
                tr.append(thEnd);

                thead.append(tr);

                table.append(thead);
                table.append(tbody);

                $.each(data.events, function (key, event) {
                    var tr = document.createElement('tr');

                    var tdId = document.createElement('td');
                    tdId.append(document.createTextNode(event.id));

                    var tdName = document.createElement('td');
                    tdName.append(document.createTextNode(event.name));

                    var tdStart = document.createElement('td');
                    tdStart.append(document.createTextNode(event.start));

                    var tdEnd = document.createElement('td');
                    tdEnd.append(document.createTextNode(event.end));

                    tr.append(tdId);
                    tr.append(tdName);
                    tr.append(tdStart);
                    tr.append(tdEnd);

                    tbody.append(tr);
                });

                inputElement.append(table);
            }
        }
    });
    return false;
});

$(document).on("click", "button.btnRemove", function () {
    var buttonName = $(this).attr('id');
    $.ajax("/alarms/destroy", {
        type: "POST",
        data: {
            'name': buttonName
        },
        asynchronous: false,
        complete: function (response) {
            if (response.error) {
                // TODO handle error
                alert("error")
            } else {
                console.log(buttonName)
                $("tr#" + buttonName).remove();
                $("table#eventsList").remove();
            }
        }
    });
    return false;
});

