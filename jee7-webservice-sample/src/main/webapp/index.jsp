<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

	Map<String, ? extends ServletRegistration> registrations = getServletConfig().getServletContext().getServletRegistrations();
	pageContext.setAttribute("registrations", registrations);
 %>
 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="w3.css">
	<title>Specification-Title: ${ specificationTitle }</title>
</head>
<body>
	<section class="w3-container w3-content" style="max-with:600px">
		
	 	<p>Specification-Title:</p>
		<h2>${ specificationTitle }</h2>
		<p>Version: ${ applVersion } / Build-Time: ${ buildTime }</p>
		
		<table class="w3-table w3-bordered">
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
	</section>
</body>
</html>