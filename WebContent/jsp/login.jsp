<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>Login</h2>
<form name="form" action="LoginServlet" method="post" onsubmit="return validate()">
<table>
 	<tr>
 		<td>Login</td>
 		<td><input type="text" name="login" /></td>
 	</tr>
 	<tr>
 		<td>Password</td>
 		<td><input type="password" name="password" /></td>
 	</tr>
 	<tr>
 		<td><%=(request.getAttribute("errMessage") == null) ? ""
 			: request.getAttribute("errMessage")%></td>
 	</tr>
 	<tr>
 		<td><input type="submit" value="Sign in"></input>
 	</tr>
</table>
</form> 
</body>
</html>