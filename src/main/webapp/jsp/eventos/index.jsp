<!DOCTYPE html>
<html>
    <head>
        <%@ page contentType="text/html; charset=iso-8859-1" language="java"  %>
        <!--<meta charset="utf-8">-->
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <!--<link rel="icon" href="../../../../favicon.ico">-->
        <title>Administrador de Eventos TACS</title>
        <!-- Bootstrap core CSS -->
        <link href="/css/bootstrap/bootstrap.min.css" rel="stylesheet">
        <link href="/css/styles.css" rel="stylesheet">
        <link href="/css/stickyFooter.css" rel="stylesheet">
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
                            <a class="dropdown-item" href="#">Buscar Evento</a>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%=URL%>/users">Usuarios</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="dropdownTelegram" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Telegram</a>
                        <div class="dropdown-menu" aria-labelledby="dropdownTelegram">
                            <a class="dropdown-item" href="<%=URL%>/telegram">P�gina Principal</a>

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
        </main>











        <div class="contEvento">
            <h3>Buscar evento</h3>
            <form method="post" action="http://<%=URL%>/events/buscarEvento">
                <label>C�digo del evento</label> 
                <input type="number" name="eventId" onkeypress="return event.charCode >= 48 && event.charCode <= 57" required/>
                <button type="submit">Buscar</button>
            </form>
            <form method="post" action="http://<%=URL%>/events/buscarEvento">
                <label>Nombre del evento</label> <input type="text"	name="eventNombre" required/>
                <button type="submit">Buscar</button>
            </form>
        </div>



        <!-- footer begin -->
        <footer class="footer">
            <div class="container">
                <p class="text-muted">Sistema de administraci�n de eventos - &copy; Grupo 5 TACS UTNBA</p>
            </div>
        </footer>
        <!-- footer close -->

        <!-- Bootstrap core JavaScript
        ================================================== -->
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="/js/jquery-3.3.1.slim.min.js"></script>
        <script src="/js/bootstrap/bootstrap.min.js"></script>
    </body>
</html>
