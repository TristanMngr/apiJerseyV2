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
