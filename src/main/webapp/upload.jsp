<%-- //[START all]--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<%-- //[START imports]--%>
<%-- //[END imports]--%>

<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/stylesheets/main.css"/>
</head>

<body>

<form action="UploadServlet" method="post" enctype="multipart/form-data">
    Select file to upload:
    <input type="file" name="dataFile" id="fileChooser"/><br/><br/>
    <input type="submit" value="Upload" />
</form>

</body>
</html>
<%-- //[END all]--%>
