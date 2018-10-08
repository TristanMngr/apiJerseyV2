<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
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
		
		//request.setAttribute("Authorization", "Basic dXNlcjpwYXNzd29yZA==");
		//response.setHeader("Authorization", "Basic dXN1YXJpbzpwYXNzd29yZA==");
	%>

<h2>Hello, please log in:</h2>
<br><br>
<form action="http://<%=URL%>/login" method=post>
    <p><strong>Please Enter Your User Name: </strong>
    <input type="text" name="j_username" size="25">
    <p><p><strong>Please Enter Your Password: </strong>
    <input type="password" size="15" name="j_password">
    <p><p>
    <input type="submit" value="Submit">
    <input type="reset" value="Reset">
</form>



</body>
</html>