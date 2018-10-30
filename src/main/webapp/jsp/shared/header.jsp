<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <!--<meta charset="UTF-8">-->
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <!--<link rel="icon" href="../../../../favicon.ico">-->
        <title>Administrador de Eventos TACS</title>
        <!-- Bootstrap core CSS -->
        <link href="/css/bootstrap/bootstrap.min.css" rel="stylesheet">
        <!--<link href="/css/bootstrap/bootstrap-theme.min.css" rel="stylesheet">-->
        <link href="/css/styles.css" rel="stylesheet">
        <link href="/css/stickyFooter.css" rel="stylesheet">
        <link href="/css/index.css" rel="stylesheet">
        <style type="text/css">
            body {
                padding-top: 3.5rem;
            }
        </style>
        <script src="/js/jquery-3.3.1.min.js"></script>
        <script src="/js/bootstrap/bootstrap.min.js"></script>
        <script src="/js/index.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
    </head>
    <%--         <h1>${it.hello} ${it.world}</h1> --%>
    <%
        String ServerName = request.getServerName();
        String URL = "http://" + ServerName;
        String UserName = "";
        if (ServerName == null || ServerName.equals("localhost")) {
            URL += ":" + request.getLocalPort();
        }
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    UserName = cookie.getValue();
                }
            }
        }

    %>
    <body>
        <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
            <a class="navbar-brand linkReload" data-loadhtml="main/index.html" href="#">Inicio</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarsExampleDefault">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="dropdownEventos" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Eventos</a>
                        <div class="dropdown-menu" aria-labelledby="dropdownEventos">
                            <a class="dropdown-item linkReload" data-loadhtml="eventos/index.html" href="#">Eventos Disponibles</a>
                            <a class="dropdown-item linkReload" data-loadhtml="listas/index.html" href="#">Listas</a>

                        </div>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link btnMenuPrincipal" href="/users">Usuarios</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="dropdownTelegram" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Telegram</a>
                        <div class="dropdown-menu" aria-labelledby="dropdownTelegram">
                            <a class="dropdown-item btnMenuPrincipal" href="/telegram">Página Principal</a>
                        </div>
                    </li>
                </ul>
                <ul class="nav navbar-text navbar-right">
                    <%-- <li><%=UserName%></li>  --%>
                    <li class="nav-item">
                        <a href="/logout"><%=UserName%></a>
                    </li>
                </ul>
            </div>

        </nav>
        <img src="/images/loading.gif" class="imgLoader displayNone" />
        <main role="main">