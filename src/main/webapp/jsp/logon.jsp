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
        <style type="text/css">
            body {
                padding-top: 3.5rem;
            }
        </style>
        <script src="/js/jquery-3.3.1.min.js"></script>
        <script src="/js/bootstrap/bootstrap.min.js"></script>
        <script rel="javascript" type="text/javascript">
            var clientId = "MyApp";
            var clientSecret = "MySecret";

            function sendobject() {
                $(".imgLoader").removeClass("displayNone");
                var x = $("form").serializeArray();
                $.each(x, function (i, field) {
                    if (field.name == 'j_username')
                        clientId = field.value;

                    if (field.name == 'j_password')
                        clientSecret = field.value;

                });

                var authorizationBasic = btoa(clientId + ':' + clientSecret);
                var postData = {}
                postData.username = clientId;
                var json = JSON.stringify(postData)


                $.ajax({
                    type: 'POST',
                    url: '/login',
                    dataType: "text",
                    data: json,
                    contentType: 'application/x-www-form-urlencoded; charset=utf-8',
                    xhrFields: {
                        withCredentials: true
                    },
                    headers: {
                        'Authorization': 'Basic ' + authorizationBasic
                    },
                    success: function (result) {
                        var token = result;
                        console.log("success");
                        location.href = "/"
                        $(".imgLoader").addClass("displayNone");
                        return true;
                    },
                    error: function (req, status, error) {
                        alert(req.responseText);
                        console.log(req);
                        console.log(status);
                        console.log(error);
                        window.location.reload();
                        $(".imgLoader").addClass("displayNone");
                        return true;
                    }
                });
            }
        </script>
        <%
            String ServerName = request.getServerName();
            String URL = "";
            if (ServerName != null && !ServerName.equals("localhost")) {
                URL = ServerName;
            } else {
                URL = ServerName + ":" + request.getLocalPort();
            }
        %>
    </head>
    <body>
        <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarsExampleDefault">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item dropdown">

                    </li>
                </ul>
            </div>
        </nav>
        <!--<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>-->
        <img src="/images/loading.gif" class="imgLoader displayNone" />
        <main role="main">
            <div id="subPageContainer">
                <h1 class="text-center">Inicio de sesión</h1>
                <hr/>
                <div class="col-12">
                    <div class="row">
                        <div class="col-lg-6 offset-lg-3 col-md-8 offset-md-2 col-12">
                            <form method=post id="loginForm">

                                <div class="form-group row">
                                    <label for="j_username" class="col-md-3 col-form-label">Usuario</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" name="j_username" maxlength="25" required/>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="j_password" class="col-md-3 col-form-label">Password</label>
                                    <div class="col-md-9">
                                        <input type="password" class="form-control" name="j_password" maxlength="25" required/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-12 text-center">
                                        <button type="button" onclick="sendobject()" class="btn btn-success">Ingresar</button>
                                        <a href="http://<%=URL%>/jsp/signup.jsp" type="button" onclick="sendobject()" class="btn btn-info">Registrarse</a>
                                    </div>
                                </div>


                            </form>   
                        </div>
                    </div>
                </div>
            </div>

        </main>

        <footer class="footer">
            <div class="container">
                <p class="text-muted">Sistema de administración de eventos - &copy; Grupo 5 TACS UTNBA</p>
            </div>
        </footer>
    </body>
</html>