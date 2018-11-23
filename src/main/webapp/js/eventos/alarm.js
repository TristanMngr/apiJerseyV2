$(document).ready(function () {

	$("#btnSubmitCrearAlarmas").hide();
	
    $("#btnSubmitCreateAlarm").click(function () {
        alert("Create Alarm. Not yet implemented");
        createAlarm();
        $("#btnSubmitCrearAlarmas").hide();
    });

});

function createAlarm() {
	
    var codigo = $("#codigo").val();
    var nombre = $("#nombre").val();
    var categoryId = $("#categorias option:selected").val();
    var desde = $("#desde").val();
    var hasta = $("#hasta").val();
	
    var nombreLista = $("#createAlarm").find('input[name="nombreAlarma"]').val();
    
    var postData = {}
    postData.name = nombreLista;
    
    postData.codigo = codigo;
    postData.nombre = nombre;
    postData.categoryId = categoryId;
    postData.desde = desde;
    postData.hasta = hasta;
    var json = JSON.stringify(postData)
    
    $.ajax("/alarms/createAlarm", {
        type: "POST",
        dataType: "text",
        data: json,
        asynchronous: false,
        complete: function (response) {
        	alert(response.status);	
            $(".btnDismiss").click();
            $(".imgLoader").addClass('displayNone');
        }
    });
    return false;
}