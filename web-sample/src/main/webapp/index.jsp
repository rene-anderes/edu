<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<body>
	<h2>Hello World!</h2>
	<p>Host: <c:out value="${header.host}"/></p>
	<p>Servlet aufrufen: <a href="SimpleServlet.do">SimpleServlet.do</a>
</body>
</html>
