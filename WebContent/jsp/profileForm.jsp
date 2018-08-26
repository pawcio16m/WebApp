<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import = "webApp.backend.ApplicationConsts"%>
<%@  taglib  prefix="c"   uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<title>Profile form</title>
</head>
<body>
<h2>Profile Form</h2>
<form name="form" action="ProfileFillerServlet" method="post">
<table>
    <tr>
        <td>First Name</td>
        <td><input type="text" name="firstName" /></td>
    </tr>
    <tr>
        <td>Last Name</td>
        <td><input type="text" name="lastName" /></td>
    </tr>
    <tr>
        <td>Age</td>
        <td><input type="number" name="age" /></td>
    </tr>
    <tr>
        <td>Phone Number</td>
        <td><input type="text" name="phoneNumber" /></td>
    </tr>
    <tr>
        <td>City</td>
		<td><select name="city">
		  <c:forEach items="${supportedCities}" var="city">
		    <option value="${city}">
		        ${city}
		    </option>
		  </c:forEach>
		</select></td>
    </tr>
    <tr> 
        <td>Prefered Activity</td>
        <td><select name="preferedActivity">
          <c:forEach items="${preferedActivities}" var="preferedActivity">
            <option value="${preferedActivity}">
                ${preferedActivity}
            </option>
          </c:forEach>
        </select></td>
    </tr>
</table>
<br>
<p><b>If you want to change password as well please fill this form</b></p>
<table> 
    <tr>
        <td>Old password</td>
        <td><input type="password" name="oldPassword" /></td>
    <tr>
        <td>New password</td>
        <td><input type="password" name="newPassword" /></td>
        <td>Password should have at least 8 characters and contains at least: one upper, one lower case and number</td>
    </tr>
    <tr>
        <td><p style="color:red;"><%=(request.getAttribute("errMessage") == null) ? ""
            : request.getAttribute("errMessage")%></p></td>
    </tr>
    <tr>
        <td><input type="submit" value="Update your profile"></input>
             <input type="reset" value="Reset"></input></td>
    </tr>
</table>
</form> 
</body>
</html>