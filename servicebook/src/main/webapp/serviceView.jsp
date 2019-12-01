<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@ page import="com.example.servicebook.Environment"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Services</title>
</head>
<body>
	<h3>Environments and Services</h3>
	<hr size="4" color="gray" />
	<table>
		<c:forEach items="${environments}" var="environment">
			<tr>
				<td>Environment Name : <c:out value="${environment.name}" /></td>
				<td>Domain:<c:out value="${environment.domain}" /></td>
				<td><c:out value="${environment.errorMsg}" /></td>
			</tr>
			<tr>
				<td>APIs</td>
				<td>Endpoints</td>
			</tr>
			<c:forEach items="${environment.services}" var="service">
				<tr>
					<td rowspan="${service.endpoints.size()}">
						<c:out value="${service.name}" />
					</td>
					<td>
						<c:out value="${service.getEndpointDisplay()}" />
					</td>
				</tr>
			</c:forEach>
		</c:forEach>
	</table>
</body>
</html>