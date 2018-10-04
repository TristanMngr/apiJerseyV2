<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ page contentType="text/html; charset=iso-8859-1" language="java"  %>
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
	
	<h2>JSP URI, URL, Context</h2>

Request Context Path: <%= request.getContextPath() %> <br>
Request URI:          <%= request.getRequestURI() %> <br>
Request URL:          <%= request.getRequestURL() %> <br>
Request ServletPath:  <%= request.getServletPath() %> <br>
Request ServerName:  <%= request.getServerName() %> <br>
Request LocalPort:  <%= request.getLocalPort() %> <br>

URL <%=URL%>
</body>
</html>