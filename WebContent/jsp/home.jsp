<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Home Page</title>
</head>
<body>
<h2>Activities Organizer</h2>
<jsp:include page="sessionCtrl.jsp" />
<%=(request.getAttribute("registeredUserTable") == null) ? ""
	: request.getAttribute("registeredUserTable")%>
</body>
</html>