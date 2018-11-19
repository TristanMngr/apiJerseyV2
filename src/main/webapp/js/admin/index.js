$(document).ready(function () {
    mostrarUsuarios();
    $("#btnSubmitBuscarUsuarios").click(function () {
        mostrarDatosUsuario($("#nombre").val());
    });

    $(document).on('click', ".usuario", function () {
        mostrarDatosUsuario($(this).html());
    })
});

function mostrarUsuarios() {
    $(".imgLoader").removeClass('displayNone');
    $.ajax("/admin/users", {
        type: "GET",
        asynchronous: false,
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            var tbody = $("table#usuarios").find("tbody");
            $.each(dataRecibida.users, function (_, usuario) {
                var fila = document.createElement('tr');
                ////
                var celda = document.createElement("td");
                celda.appendChild(document.createTextNode(usuario));
                celda.setAttribute('class', 'usuario');
                celda.setAttribute('style', 'cursor: pointer;');
                fila.appendChild(celda);
                tbody.append(fila);
            });
            $(".imgLoader").addClass('displayNone');
        }
    });
}

function mostrarDatosUsuario(nombre) {
    $(".imgLoader").removeClass('displayNone');
//    var nombre = $("#nombre").val();
//    var x = document.forms["myForm"]["username"].value;

    $.ajax({
        url: "/admin/users/" + nombre,
        type: "GET",
        asynchronous: false,
        error: function (req, status, error) {
            alert(req.responseText);
            window.location.reload();
            return true;
        },
        complete: function (response) {
            var dataRecibida = $.parseJSON(response.responseText);
            $.each(dataRecibida, function (key, valor) {
                if (key == "username") {
                    document.forms["myForm"]["username"].value = valor;
                }
                if (key == "lastLogin") {
                    document.forms["myForm"]["lastLogin"].value = valor;
                }
                if (key == "cantListas") {
                    document.forms["myForm"]["cantListas"].value = valor;
                }
                if (key == "cantAlarmas") {
                    document.forms["myForm"]["cantAlarmas"].value = valor;
                }
            });
            $(".imgLoader").addClass('displayNone');
        }
    });
    return false;
}