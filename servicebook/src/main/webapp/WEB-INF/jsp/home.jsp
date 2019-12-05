<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
<style>
form {
	display: block;
	text-align: center;
	border: 1px solid;
	padding: 10px;
}
</style>
</head>
<body>

	<form action="/services" id="envform">
		Environment : <select name="envName" form="envform">
			<c:forEach items="${environments}" var="environment">
				<option value="${environment.name()}"><c:out
						value="${environment.name()}" /></option>
			</c:forEach>
		</select> <input type="submit">
	</form>



</body>
</html>