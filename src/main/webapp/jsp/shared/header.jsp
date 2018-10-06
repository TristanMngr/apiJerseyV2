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
        <script src="/js/jquery-3.3.1.min.js"></script>
        <script src="/js/bootstrap/bootstrap.min.js"></script>
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
                            <a class="dropdown-item" href="/events">Eventos Disponibles</a>
                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/users">Usuarios</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="dropdownTelegram" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Telegram</a>
                        <div class="dropdown-menu" aria-labelledby="dropdownTelegram">
                            <a class="dropdown-item" href="/telegram">Página Principal</a>

                        </div>
                    </li>
                </ul>
            </div>
        </nav>
        <main role="main">