$(document).ready(function () {
    getCurrentUserAlarms();
    armarSelectCategorias();


    $("#btnCreateAlarm").click(function () {
        createAlarm();
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
                $('select#alarmCategory').append(linea);
            });
        }
    });
    return false;
}

function createAlarm() {
    var alarmName = $("#alarmName").val();
    var alarmCategory = $("#alarmCategory").val();
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
                var cuerpoTabla = $('table#alarmList').find('tbody');

                var filaTr = document.createElement('tr');
                filaTr.setAttribute("id", alarmName);

                var celdaTd = document.createElement('td');
                celdaTd.appendChild(document.createTextNode(alarmName));
                filaTr.appendChild(celdaTd);

                celdaTd = document.createElement('td');
                celdaTd.appendChild(document.createTextNode(alarmCategory));
                filaTr.appendChild(celdaTd);

                celdaTd = document.createElement('td');
                var removeButton = celdaTd.appendChild(document.createElement("button"));
                removeButton.setAttribute('class', 'btn btn-danger btnRemove');
                removeButton.setAttribute('type', 'button');
                removeButton.setAttribute('data-toggle', 'button');
                removeButton.setAttribute("id", alarmName);
                removeButton.appendChild(document.createTextNode('Remove'));

                cuerpoTabla.append(filaTr);
            }
            //TODO add response
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
                    var cuerpoTabla = $('table#alarmList').find('tbody');

                    var filaTr = document.createElement('tr');
                    filaTr.setAttribute("id", alarm.name);
                    var celdaTd = document.createElement('td');
                    celdaTd.appendChild(document.createTextNode(alarm.name));
                    filaTr.appendChild(celdaTd);

                    celdaTd = document.createElement('td');
                    celdaTd.appendChild(document.createTextNode(alarm.paramsEventBrite.categoryId));
                    filaTr.appendChild(celdaTd);

                    celdaTd = document.createElement('td');
                    var removeButton = celdaTd.appendChild(document.createElement("button"));
                    removeButton.setAttribute('class', 'btn btn-danger btnRemove');
                    removeButton.setAttribute('type', 'button');
                    removeButton.setAttribute('data-toggle', 'button');
                    removeButton.appendChild(document.createTextNode('Remove'));
                    removeButton.setAttribute("id", alarm.name);
                    filaTr.appendChild(celdaTd);

                    cuerpoTabla.append(filaTr);
                })
            }
        }
    });
    return false;
}

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
                $("tr#"+buttonName).remove();
            }
        }
    });
    return false;
});

