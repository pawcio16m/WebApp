<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
<script> 
function validate()
{ 
 var email = document.form.email.value;
 var username = document.form.username.value; 
 var password = document.form.password.value;
 
 if (email == null || email == "" )
 { 
	 alert("Email can't be blank"); 
	 return false; 
 }
 else if (login == null || login == "")
 { 
	 alert("login can't be blank"); 
	 return false; 
 }
 else if(password.length < 6)
 { 
	 alert("Password must be at least 6 characters long."); 
	 return false; 
 } 
 } 
</script> 
</head>
<body>
<h2>Registration Form</h2>
<form name="form" action="RegistrationServlet" method="post" onsubmit="return validate()">
<table>
	<tr>
 		<td>Email</td>
 		<td><input type="text" name="email" /></td>
 	</tr>
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
 		<td><input type="submit" value="Sign Up"></input>
 		     <input type="reset" value="Reset"></input></td>
 	</tr>
</table>
</form> 
</body>
</html>