<jsp:include page="/jsp/shared/header.jsp" flush="true" /> 
<div class="jumbotron">
    <div class="container">
        <h1 class="display-3 text-center">Eventos</h1>
        <!--<p>This is a template for a simple marketing or informational website. It includes a large callout called a jumbotron and three supporting pieces of content. Use it as a starting point to create something more unique.</p>-->
        <!--<p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more &raquo;</a></p>-->
    </div>
</div>
<form class="col-12" method="post" action="/events/buscarEvento">
    <div class="row">
        <div class="col-md-6 offset-md-3">
            <h4 class="text-center">Buscar Evento</h4>
            <div class="form-group row">
                <label for="codigo" class="col-md-2 col-form-label">Código</label>
                <div class="col-md-8">
                    <input type="text" class="form-control" id="codigo" name="codigo" >
                </div>
            </div>
            <div class="form-group row">
                <label for="nombre" class="col-md-2 col-form-label">Nombre</label>
                <div class="col-md-8">
                    <input type="text" class="form-control" id="nombre" name="nombre" placeholder="">
                </div>
            </div>
            <div class="form-group row">
                <label for="descripcion" class="col-md-2 col-form-label">Descripción</label>
                <div class="col-md-8">
                    <input type="text" class="form-control" id="descripcion" name="descripcion" placeholder="">
                </div>
            </div>
            <div class="form-group row">
                <label for="desde" class="col-md-2 col-form-label">Desde</label>
                <div class="col-md-8">
                    <input type="date" class="form-control" id="desde" name="desde" placeholder="">
                </div>
            </div>
            <div class="form-group row">
                <label for="hasta" class="col-md-2 col-form-label">Hasta</label>
                <div class="col-md-8">
                    <input type="date" class="form-control" id="hasta" name="hasta" placeholder="">
                </div>
            </div>
            <div class="row">
                <div class="col-12 text-center">
                    <button type="submit" id="btnSubmit" class="btn btn-info">Buscar</button>
                </div>
            </div>
        </div>
    </div>
</form>
<jsp:include page="/jsp/shared/footer.jsp" flush="true" /> 
