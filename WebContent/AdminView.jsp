<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.helper.SessionHolder" %>
<%@ page import="com.to.Role" %>
<%@ taglib prefix="emp" uri="WEB-INF/tags.tld" %>
<%
SessionHolder sh = (SessionHolder)session.getAttribute("user_obj");
if(sh == null || sh.getRole() != Role.Admin) {
	response.sendRedirect("index.jsp");
	return;
}
String match = request.getParameter("view");
if(match == null || match.equals("")) {
	response.sendRedirect("index.jsp");
	return;
}
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View/Edit <%=match %></title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/custom.css" rel="stylesheet" type="text/css" />
</head>
<body>

<nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
        <a class="navbar-brand col-sm-3 col-md-2 mr-0"  href="ChangePassword.jsp">Change Password</a>
      <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
          <a class="nav-link" href="AccountServ?logout">Sign out</a>
        </li>
      </ul>
</nav>

<div class="container-fluid">
<div class="row">


<%@include file="CommonHeader.html" %>

<main class="col-md-9 ml-sm-auto col-lg-10 px-4">
<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
	<h2 class="h2">View/Edit <%=match %></h2>
	<% if(session.getAttribute("msg") != null) { %>
		  		<p style="color: red"><%=(String)session.getAttribute("msg") %></p>
		  	<% session.removeAttribute("msg"); %>
    <% } %>
</div>

<div class="table-responsive">
<table class="table table-striped table-sm">
<emp:vetable obj="<%=match %>" />
</table>
</div>

</main>
</div>
</div>

</body>
</html>