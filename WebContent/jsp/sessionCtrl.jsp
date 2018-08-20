<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Session Controller</title>
</head>
<body>
<%
String user = null;
if	(session.getAttribute("login") == null){
	out.println("You're not logged in.");
%>
	<%@include file = "/html/notLoggedUserView.html" %>
<%}
else {
	user = (String) session.getAttribute("login");
	String login = null;
	Cookie[] cookies = request.getCookies();
	if (cookies !=null){
		for (Cookie cookie : cookies){
			if (cookie.getName().equals("login")) login = cookie.getValue();
		}
		out.print("Hello "+login+", you're logged in");
%>
		<%@include  file="/html/loggedUserView.html" %>
<%	}	
}
%>
</body>
</html>