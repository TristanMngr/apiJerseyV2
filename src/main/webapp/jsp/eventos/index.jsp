<!DOCTYPE html>
<html lang="en">
    <head>
        <%@ page contentType="text/html; charset=utf-8" language="java"  %>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
        <meta name="description" content=""/>
        <meta name="author" content=""/>
        <!--<link rel="icon" href="../../../../favicon.ico">-->
        <title>Administrador de Eventos TACS</title>
        <!-- Bootstrap core CSS -->
        <link href="/css/bootstrap/bootstrap.min.css" rel="stylesheet"/>
        <link href="/css/styles.css" rel="stylesheet"/>
        <link href="/css/stickyFooter.css" rel="stylesheet"/>
        <style type="text/css">
            body {
                padding-top: 3.5rem;
            }
        </style>
    </head>
    <%--         <h1>${it.hello} ${it.world}</h1> --%>
    <%
        String ServerName = request.getServerName();
        String URL = "http://" + ServerName;
        if (ServerName == null || ServerName.equals("localhost")) {
            URL += ":" + request.getLocalPort();
        }
    %>
    <body>
        <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
            <a class="navbar-brand" href="#">Inicio</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarsExampleDefault">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="dropdownEventos" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Eventos</a>
                        <div class="dropdown-menu" aria-labelledby="dropdownEventos">
                            <a class="dropdown-item" href="<%=URL%>/events">Eventos Disponibles</a>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=URL%>/users">Usuarios</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="dropdownTelegram" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Telegram</a>
                        <div class="dropdown-menu" aria-labelledby="dropdownTelegram">
                            <a class="dropdown-item" href="<%=URL%>/telegram">Página Principal</a>

                        </div>
                    </li>
                </ul>
            </div>
        </nav>
        <main role="main">
            <div class="jumbotron">
                <div class="container">
                    <h1 class="display-3 text-center">Eventos</h1>
                    <!--<p>This is a template for a simple marketing or informational website. It includes a large callout called a jumbotron and three supporting pieces of content. Use it as a starting point to create something more unique.</p>-->
                    <!--<p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more &raquo;</a></p>-->
                </div>
            </div>
            <form class="col-12" method="post" action="<%=URL%>/events/buscarEvento">
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
        </main>

        <!-- footer begin -->
        <footer class="footer">
            <div class="container">
                <p class="text-muted">Sistema de administración de eventos - &copy; Grupo 5 TACS UTNBA</p>
            </div>
        </footer>
        <!-- footer close -->

        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="/js/jquery-3.3.1.min.js"></script>
        <script src="/js/bootstrap/bootstrap.min.js"></script>
        <script src="/js/eventos/index.js"></script>
    </body>
</html>
