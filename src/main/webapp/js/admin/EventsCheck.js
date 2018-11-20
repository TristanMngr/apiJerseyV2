$(document).ready(function () {

	$('input#codigo').keyup(function () {
		keyUpInputCodigo();
	});

	$("#btnSubmitBuscarEventos").click(function () {
		//mostrarUsuarios();
		mostrarEventos();
	});

	$('#exampleModal').on('show.bs.modal', function (event) { 
		$(".imgLoader").removeClass('displayNone'); // Show progress bar
		
		var button = $(event.relatedTarget); // Button that triggered the modal
		var codigo = button.data('codigo'); // Extract info from data-* attributes

		var title = "Cantidad de Usuarios";   	   
		var modal = $(this);
		modal.find('.modal-title').text(title);
		
		$.ajax("/admin/events/" + codigo, {
			type: "GET",
			asynchronous: false,
			complete: function (response) {
				modal.find('.modal-body').text("Usuarios: " + response.responseText);
			}
		});
		
		
		$(".imgLoader").addClass('displayNone'); // Hide progress bar
		
	});

});

function mostrarEventos() {
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
			$.each(dataRecibida.events, function (key, valor) {
				var filaTr = document.createElement('tr');
				var celdaTd = document.createElement('td');
				celdaTd.appendChild(document.createTextNode(valor.id));
				filaTr.appendChild(celdaTd);
				//
				celdaTd = document.createElement('td');
				celdaTd.appendChild(document.createTextNode(valor.name.text));
				filaTr.appendChild(celdaTd);
				//
				celdaTd = document.createElement('td');
				celdaTd.appendChild(document.createTextNode(formatEventBriteDate(valor.start.local)));
				filaTr.appendChild(celdaTd);
				//
				celdaTd = document.createElement('td');
				celdaTd.appendChild(document.createTextNode(formatEventBriteDate(valor.end.local)));
				filaTr.appendChild(celdaTd);
				//
				celdaTd = document.createElement('td');
				var divModal = document.createElement('div');
				divModal.setAttribute('data-toggle', 'modal');
				divModal.setAttribute('data-target', '#exampleModal');   
				divModal.setAttribute('data-nombre', valor.name.text);
				divModal.setAttribute('data-codigo', valor.id);
				celdaTd.appendChild(divModal);
				var buttonModal = document.createElement('button');
				buttonModal.setAttribute('type', 'button');
				buttonModal.setAttribute('class', 'btn btn-info btn-sm btnAddEvento');
				buttonModal.setAttribute('data-toggle', 'tooltip');
				buttonModal.setAttribute('data-placement', 'left');
				buttonModal.setAttribute('title', 'Count Users');
				divModal.appendChild(buttonModal);
				var icon = document.createElement('i');
				icon.setAttribute('class', 'fas fa-users');
				icon.setAttribute('style', 'font-size: 20px;');
				buttonModal.appendChild(icon);
				filaTr.appendChild(celdaTd);
				//
				cuerpoTabla.append(filaTr);
			});
			$(".imgLoader").addClass('displayNone');
		}
	});
	return false;
}


function formatEventBriteDate(date) {
	var options = {year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric'};
	var fecha = new Date(date).toLocaleDateString("es-ES", options);
	return fecha;
}

function mostrarUsuarios() {
	$(".imgLoader").removeClass('displayNone');
	var cuerpoTabla = $('table#eventosEncontrados').find('tbody');
	cuerpoTabla.html('');
	var codigo = $("#codigo").val();
	var nombre = $("#nombre").val();
	var categoryId = $("#categorias option:selected").val();
	var desde = $("#desde").val();
	var hasta = $("#hasta").val();

	$.ajax("/admin/users", {
		type: "GET",
		asynchronous: false,
		complete: function (response) {
			var dataRecibida = $.parseJSON(response.responseText);
			$.each (dataRecibida.users, function (index) {

				var filaTr = document.createElement('tr');
				var celdaTd = document.createElement('td');
				celdaTd.appendChild(document.createTextNode(dataRecibida.users[index]));
				filaTr.appendChild(celdaTd);
				cuerpoTabla.append(filaTr);

			});
		}
	});
	$(".imgLoader").addClass('displayNone');
	return false;
}

function keyUpInputCodigo() {
	if ($("input#codigo").val() != "")
		$('div.notCodigo').addClass("displayNone");
	else
		$('div.notCodigo').removeClass("displayNone");
	return false;
};