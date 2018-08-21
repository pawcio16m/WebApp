<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "webApp.backend.RegisteredUser" %>
<%
String user = null;
if	(session.getAttribute("login") == null){
	out.println("You're not logged in.");
%>
	<%@include file = "/html/notLoggedUserView.html" %>
<%
}
else {
	user = (String) session.getAttribute("login");
	String login = null;
	Cookie[] cookies = request.getCookies();
	if (cookies !=null){
		for (Cookie cookie : cookies){
			if (cookie.getName().equals("login")) login = cookie.getValue();
		}
		out.print("Hello "+login+", you're logged in");
		if (RegisteredUser.isAdminMode(login)) {
%>
		<%@include  file="/html/adminView.html" %>
<%
		} 
		else {
%>
		<%@include  file="/html/loggedUserView.html" %>
<%	
	   	}
	}	
}
%>