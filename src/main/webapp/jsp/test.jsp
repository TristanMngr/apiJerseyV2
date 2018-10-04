<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>${it}</h1>
<c:forEach items="${it.listado}" var="map">
    <tr>
        <c:forEach items="${map}" var="entry">

            <td>${entry.value}</td>

        </c:forEach>
    </tr>
</c:forEach>
<h2>JSP URI, URL, Context</h2>

Request Context Path: <%= request.getContextPath() %> <br>
Request URI:          <%= request.getRequestURI() %> <br>
Request URL:          <%= request.getRequestURL() %> <br>
Request ServletPath:  <%= request.getServletPath() %> <br>
</html>