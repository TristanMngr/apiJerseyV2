<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
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
        </style>        <style>

            /* Style the container for inputs */
            .container {
                background-color: #f1f1f1;
                padding: 20px;
            }

            /* The message box is shown when the user clicks on the password field */
            #message {
                display: none;
                background: #f1f1f1;
                color: #000;
                position: relative;
                padding: 20px;
                margin-top: 10px;
            }

            #message p {
                padding: 10px 35px;
                font-size: 18px;
            }

            /* Add a green text color and a checkmark when the requirements are right */
            .valid {
                color: green;
            }

            .valid:before {
                position: relative;
                left: -35px;
                content: "✔";
            }

            /* Add a red text color and an "x" when the requirements are wrong */
            .invalid {
                color: red;
            }

            .invalid:before {
                position: relative;
                left: -35px;
                content: "✖";
            }
        </style>
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
        <main role="main">
            <div id="subPageContainer">
                <h1 class="text-center">Registro de usuario</h1>
                <hr/>
                <div class="col-12">
                    <div class="row">
                        <div class="col-lg-6 offset-lg-3 col-md-8 offset-md-2 col-12">
                            <form id="form">
                                <div class="form-group row">
                                    <label for="usrname" class="col-md-3 col-form-label">Usuario</label>
                                    <div class="col-md-9">
                                        <input type="text" class="form-control" id="usrname" name="usrname" required/>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="psw" class="col-md-3 col-form-label">Contraseña</label>
                                    <div class="col-md-9">
                                        <input type="password" class="form-control" id="psw" name="psw" title="Debe contener al menos un número, una mayúscula, una minúscula y 8 caracteres como mínimo" required/>
                                    </div>
                                </div>
                                <div class="form-group row">
                                    <label for="psw2" class="col-md-3 col-form-label">Repetir Contraseña</label>
                                    <div class="col-md-9">
                                        <input type="password" class="form-control" id="psw2" name="psw2" title="Debe contener al menos un número, una mayúscula, una minúscula y 8 caracteres como mínimo" required/>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-12 text-center">
                                        <button type="button" onclick="sendobject()" class="btn btn-success">Guardar</button>
                                        <a type="button" href="/" class="btn btn-danger">Volver</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>



                <div id="message">
                    <h3>Password must contain the following:</h3>
                    <p id="letter" class="invalid">
                        A <b>lowercase</b> letter
                    </p>
                    <p id="capital" class="invalid">
                        A <b>capital (uppercase)</b> letter
                    </p>
                    <p id="number" class="invalid">
                        A <b>number</b>
                    </p>
                    <p id="length" class="invalid">
                        Minimum <b>8 characters</b>
                    </p>
                    <p id="match" class="invalid">
                        Passwords <b>don't match</b>
                    </p>
                </div>

                <script
                src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
                <script>
                                            var password1 = document.getElementById("psw");
                                            var password2 = document.getElementById("psw2");
                                            var letter = document.getElementById("letter");
                                            var capital = document.getElementById("capital");
                                            var number = document.getElementById("number");
                                            var length = document.getElementById("length");
                                            var match = document.getElementById("match");
                                            var correctPassword = 0;

                                            // When the user clicks on the password field, show the message box
                                            password1.onfocus = function () {
                                                document.getElementById("message").style.display = "block";
                                            }

                                            password2.onfocus = function () {
                                                document.getElementById("message").style.display = "block";
                                            }

                                            // When the user clicks outside of the password field, hide the message box
                                            password1.onblur = function () {
                                                document.getElementById("message").style.display = "none";
                                            }

                                            password2.onblur = function () {
                                                document.getElementById("message").style.display = "none";
                                            }

                                            function passwordChecker() {
                                                correctPassword = 0;
                                                // Validate lowercase letters
                                                var lowerCaseLetters = /[a-z]/g;
                                                if (password1.value.match(lowerCaseLetters)) {
                                                    letter.classList.remove("invalid");
                                                    letter.classList.add("valid");
                                                    correctPassword = correctPassword + 1;
                                                } else {
                                                    letter.classList.remove("valid");
                                                    letter.classList.add("invalid");
                                                }

                                                // Validate capital letters
                                                var upperCaseLetters = /[A-Z]/g;
                                                if (password1.value.match(upperCaseLetters)) {
                                                    capital.classList.remove("invalid");
                                                    capital.classList.add("valid");
                                                    correctPassword = correctPassword + 1;
                                                } else {
                                                    capital.classList.remove("valid");
                                                    capital.classList.add("invalid");
                                                }

                                                // Validate numbers
                                                var numbers = /[0-9]/g;
                                                if (password1.value.match(numbers)) {
                                                    number.classList.remove("invalid");
                                                    number.classList.add("valid");
                                                    correctPassword = correctPassword + 1;
                                                } else {
                                                    number.classList.remove("valid");
                                                    number.classList.add("invalid");
                                                }

                                                // Validate length
                                                if (password1.value.length >= 8) {
                                                    length.classList.remove("invalid");
                                                    length.classList.add("valid");
                                                    correctPassword = correctPassword + 1;
                                                } else {
                                                    length.classList.remove("valid");
                                                    length.classList.add("invalid");
                                                }

                                                // Validate match

                                                if (password1.value == password2.value) {
                                                    match.classList.remove("invalid");
                                                    match.classList.add("valid");
                                                    document.getElementById("match").innerHTML = "Passwords <b>match</b>";
                                                    correctPassword = correctPassword + 1;
                                                } else {
                                                    match.classList.remove("valid");
                                                    match.classList.add("invalid");
                                                    document.getElementById("match").innerHTML = "Passwords <b>don't match</b>";
                                                }

                                            }

                                            // When the user starts to type something inside the password field
                                            password1.onkeyup = passwordChecker;
                                            password2.onkeyup = passwordChecker;

                                            function sendobject() {
                                                if (correctPassword != 5) {
                                                    alert("Passwords did not meet requirements");
                                                    return;
                                                }

                                                var x = $("form").serializeArray();
                                                $.each(x, function (i, field) {
                                                    if (field.name == 'usrname')
                                                        clientId = field.value;

                                                    if (field.name == 'psw')
                                                        clientSecret = field.value;

                                                });

                                                var authorizationBasic = btoa(clientId + ':' + clientSecret);
                                                var postData = {}
                                                postData.username = clientId;
                                                var json = JSON.stringify(postData)

                                                $.ajax({
                                                    type: 'POST',
                                                    url: '/users/',
                                                    dataType: "text",
                                                    data: json,
                                                    //contentType: 'application/x-www-form-urlencoded; charset=utf-8',
                                                    xhrFields: {
                                                        withCredentials: true
                                                    },
                                                    headers: {
                                                        'Authorization': 'Basic ' + authorizationBasic
                                                    },
                                                    success: function (result, status, error) {
                                                        var obj = JSON.parse(result);
                                                        console.log("--------");
                                                        window.location.href = obj.URL;
                                                        return true;
                                                    },
                                                    error: function (req, status, error) {
                                                        alert(error);
                                                        console.log("error");
                                                        console.log(req);
                                                        console.log(status);
                                                        console.log(error);
                                                        //window.location.reload();
                                                        return true;
                                                    }
                                                });
                                            }
                </script>
            </div>

        </main>

        <footer class="footer">
            <div class="container">
                <p class="text-muted">Sistema de administración de eventos - &copy; Grupo 5 TACS UTNBA</p>
            </div>
        </footer>
    </body>
</html>