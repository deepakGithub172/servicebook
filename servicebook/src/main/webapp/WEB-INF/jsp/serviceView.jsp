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
<style>
    .red    {
        color: red;
}
</style>
</head>
<body>
	<h3>Environments and Services</h3>
	<hr size="4" color="gray" />
	<table>
		<c:forEach items="${environments}" var="environment">
			<tr>
				<td><c:out value="${environment.name}"/></td>
				<td class="red"><c:out value="${environment.errorMsg}" /></td>
			</tr>
			<tr>
				<th bgcolor="#C0C0C0">APIs</th>
				<th bgcolor="#C0C0C0">Endpoints</th>
			</tr>
			<c:forEach items="${environment.services}" var="service">
				<tr>
					<td><c:out value="${service.name}" /></td>
					<td class="red"><c:out value="${service.errorMsg}" /></td>
				</tr>
				<c:forEach items="${service.endpoints}" var="endpoint">
					<tr>
						<td></td>
						<td><c:out value="${endpoint}" /></td>
					</tr>
				</c:forEach>
			</c:forEach>
		</c:forEach>
	</table>
</body>
</html>