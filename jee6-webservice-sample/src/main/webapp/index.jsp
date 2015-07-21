<%@page import="java.util.jar.Manifest,java.io.InputStream,java.util.Collection" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" /> 
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JEE 6 WebServices Sample</title>
</head>
<body>
<%
	InputStream inputStream = getServletConfig().getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF");
	Manifest manifest = new Manifest(inputStream);
	String applVersion = manifest.getMainAttributes().getValue("Implementation-Version");
	
	Collection<String> employeePortMapping = getServletConfig().getServletContext().getServletRegistration("EmployeePort").getMappings();
	pageContext.setAttribute("employeePortMapping", employeePortMapping);
 %>
	<h2>JEE 6 WebServices Sample</h2>
	<p>Version: <%= applVersion %></p>
	<table>
		<thead>
			<tr>
				<th>ServletName</th>
				<th>&nbsp;</th>
				<th>WebServices WSDL-Path</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>EmployeePort</td>
				<td>&nbsp;</td>
				<td>
					<c:forEach var="servletMapping" items="${pageScope.employeePortMapping}" varStatus="status">
						<a href="${baseURL}${servletMapping}?WSDL">${baseURL}${servletMapping}?WSDL</a>
						${not status.last ? '<br/>' : ''}
					</c:forEach>
				<td>
			</tr>
		</tbody>
	</table>
</body>
</html>