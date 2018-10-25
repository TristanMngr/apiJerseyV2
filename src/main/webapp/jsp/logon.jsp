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

	function sendobject() {
		var x = $("form").serializeArray();
	    $.each(x, function(i, field){
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
		    data : json,
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
 		    	window.location.reload();
 		    	return true;
		    },
 		    error: function (req, status, error) {
		    	alert(req.responseText);
		    	console.log(req);
		    	console.log(status);
		    	console.log(error);
		    	window.location.reload();
		    	return true;
		    }
		});
	}
</script>

	<%
 		String ServerName=request.getServerName();
		String URL = "";
		if( ServerName != null && !ServerName.equals("localhost") ) {
	       URL = ServerName;
	   	}
		else
		{
			URL = ServerName + ":" + request.getLocalPort();
		}
	%>

<h2>Hello, please log in:</h2>
<br><br>
<form method=post id="loginForm">
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
<h2>If you don't have an account, just <a href="http://<%=URL%>/jsp/signup.jsp">Signup</a></h2>
</form>


</body>
</html>