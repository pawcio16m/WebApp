<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Home Page</title>
</head>
<body>
<%
String user = null;
if	(session.getAttribute("login") == null){
	out.println("You're not logged in.");
}
else {
	user = (String) session.getAttribute("login");
	String login = null;
	Cookie[] cookies = request.getCookies();
	if (cookies !=null){
		for (Cookie cookie : cookies){
			if (cookie.getName().equals("login")) login = cookie.getValue();
		}
		out.println("Hello "+login+", you're logged in");
	}	
}
%>
 <center><h2>Activities Organizer</h2></center>
<form action="WebApp" method="post">
    <input type="submit" name="register" value="Sign Up" />
    <input type="submit" name="login" value="Sign In" />
    <input type="submit" name="logout" value="Log out" />
</form>
</body>
</html>