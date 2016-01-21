<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.jar.Manifest,java.io.InputStream,java.util.*" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="req" value="${ pageContext.request }" />
<c:set var="baseURL" value="${ req.scheme }://${ req.serverName }:${ req.serverPort }${ req.contextPath }" /> 
<%
	InputStream inputStream = getServletConfig().getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF");
	Manifest manifest = new Manifest(inputStream);
	pageContext.setAttribute("applVersion", manifest.getMainAttributes().getValue("Implementation-Version"));
	pageContext.setAttribute("specificationTitle", manifest.getMainAttributes().getValue("Specification-Title"));
	pageContext.setAttribute("buildTime", manifest.getMainAttributes().getValue("Build-Time"));
	
	Collection<String> employeePortMapping = getServletConfig().getServletContext().getServletRegistration("EmployeePort").getMappings();
	pageContext.setAttribute("employeePortMapping", employeePortMapping);
	
	Map<String, ? extends ServletRegistration> registrations = getServletConfig().getServletContext().getServletRegistrations();
	pageContext.setAttribute("registrations", registrations);
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Specification-Title: ${ specificationTitle }</title>
<style type="text/css">
	body { font-family: sans-serif; }
	table { border: none; }	
	th { 
		background-color: #ffff99;
		text-align: left;
		padding: 0.5em 0.5em 0.5em 1em; /* top right bottom left */
		border-bottom-color: gray; 
		border-bottom-style: solid; 
		border-bottom-width: thin; 
	}
	td { 
		vertical-align: baseline; 
		padding: 0.5em 0.5em 0.5em 1em; /* top right bottom left */
		border-bottom-color: gray; 
		border-bottom-style: solid; 
		border-bottom-width: thin;
	}
</style>
</head>
<body>
 	<p>Specification-Title:</p>
	<h2>${ specificationTitle }</h2>
	<p>Version: ${ applVersion } :: Build-Time: ${ buildTime } /></p>
	
	<table>
		<thead>
			<tr>
				<th>ServletName</th>
				<th>Value</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="registration" items="${ pageScope.registrations }" varStatus="status">
				<tr>
					<td>${ registration.key }</td>
					<td>
						<c:forEach var="servletMapping" items="${ registration.value.mappings }" varStatus="status">
							<a href="${ baseURL }${ servletMapping }">${ baseURL }${ servletMapping }</a>${ not status.last ? '<br/>' : '' }
						</c:forEach>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<p>&nbsp;</p>
</body>
</html>