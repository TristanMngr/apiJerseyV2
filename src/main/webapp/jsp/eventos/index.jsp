<jsp:include page="/jsp/shared/header.jsp" flush="true" /> 
<img src="/images/loading.gif" class="imgLoader displayNone" />
<div hidden id="categoriesJson">${it.categories}</div>
<h1 class="display-3 text-center">Eventos</h1>
<hr/>
<form class="col-12" method="post" action="/events/buscarEvento">
    <div class="row">
        <div class="col-md-4">
            <div class="form-group row">
                <label for="codigo" class="col-md-3 col-form-label">Código</label>
                <div class="col-md-9">
                    <input type="text" class="form-control" id="codigo" name="codigo" >
                </div>
            </div>
            <div class="form-group row notCodigo">
                <label for="nombre" class="col-md-3 col-form-label">Nombre</label>
                <div class="col-md-9">
                    <input type="text" class="form-control" id="nombre" name="nombre" placeholder="">
                </div>
            </div>
            <!--            <div class="form-group row">
                            <label for="descripcion" class="col-md-3 col-form-label">Descripción</label>
                            <div class="col-md-10">
                                <input type="text" class="form-control" id="descripcion" name="descripcion" placeholder="">
                            </div>
                        </div>-->
            <div class="form-group row notCodigo">
                <label for="categorias" class="col-md-3 col-form-label">Categoría</label>
                <div class="col-md-9">
                    <select class="form-control" id="categorias" name="categorias">
                        <option value="">Todas</option>
                    </select>
                </div>
            </div>
            <div class="form-group row notCodigo">
                <label for="desde" class="col-md-3 col-form-label">Desde</label>
                <div class="col-md-9">
                    <input type="date" class="form-control" id="desde" name="desde" placeholder="">
                </div>
            </div>
            <div class="form-group row notCodigo">
                <label for="hasta" class="col-md-3 col-form-label">Hasta</label>
                <div class="col-md-9">
                    <input type="date" class="form-control" id="hasta" name="hasta" placeholder="">
                </div>
            </div>
            <div class="row">
                <div class="col-12 text-center">
                    <button type="button" id="btnSubmit" class="btn btn-info">Buscar</button>
                </div>
            </div>
        </div>
        <div class="col-md-8 table-responsive">
            <table id="eventosEncontrados" class="table table-hover table-sm">
                <thead>
                    <tr>
                        <th>Código</th>
                        <th>Nombre</th>
                        <th>Inicio</th>
                        <th>Fin</th>
                    </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </div>
    </div>
</form>
<script src="/js/eventos/index.js"></script>
<jsp:include page="/jsp/shared/footer.jsp" flush="true" /> 
