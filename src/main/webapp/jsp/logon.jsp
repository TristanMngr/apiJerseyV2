<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script rel="javascript" type="text/javascript">
	var clientId = "MyApp";
	var clientSecret = "MySecret";
	
	// var authorizationBasic = $.base64.btoa(clientId + ':' + clientSecret);
	var authorizationBasic = window.btoa(clientId + ':' + clientSecret);

	function sendobject() {
		$.ajax({
		    type: 'POST',
		    url: 'http://localhost:8080/login',
		    data: { username: 'John', password: 'Smith', grant_type: 'password' },
		    dataType: "json",
		    contentType: 'application/x-www-form-urlencoded; charset=utf-8',
		    xhrFields: {
		       withCredentials: true
		    },
		    // crossDomain: true,
		    headers: {
		       'Authorization': 'Basic ' + authorizationBasic
		    },
		    //beforeSend: function (xhr) {
		    //},
/* 		    success: function (result) {
		       var token = result;
		    },
		    //complete: function (jqXHR, textStatus) {
		    //},
		    error: function (req, status, error) {
		    alert(error);
		    } */
		});
	}
</script>

<h2>Hello, please log in:</h2>
<br><br>
<form method=post>
<table border="0" cellspacing="2" cellpadding="1">
<tr>
  <td>Username:</td>
  <td><input size="12" value="" name="j_username" maxlength="25" type="text"></td>
</tr>
<tr>
  <td>Password:</td>
  <td><input size="12" value="" name="j_password" maxlength="25" type="password"></td>
</tr>
<tr>
  <td colspan="2" align="center">
    <!-- <input name="submit" type="submit" value="Login"  onclick="login()"> -->
    <input type="button" onclick="sendobject()" value="submit"></input>
  </td>
</tr>
</table>
</form>


</body>
</html>