<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.helper.*" %>
<%@ page import="com.to.Role" %>
<%
SessionHolder sh = (SessionHolder)session.getAttribute("user_obj");
if(sh == null) {
	response.sendRedirect("index.jsp");
	return;
}
HibernateUtil hu = HibernateUtil.getUtilObject();
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Change Password</title><link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
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
	<h2 class="h2">Change Password</h2>
</div>

<form class="form-signin" id="changePassword" action="AccountServ" method="post">
	<div class="text-center mb-4">
			<% if(session.getAttribute("msg") != null) { %>
		  		<p style="color: red"><%=(String)session.getAttribute("msg") %></p>
		  	<% session.removeAttribute("msg"); %>
		  	<% } %>
	</div>
	<div class="form-label-group">
    	<input id="password" class="form-control" name="password" placeholder="Password" required autofocus type="password" />
    	<label for="password">Password</label>
    </div>
    
    <button name="changePassword" class="btn btn-lg btn-primary btn-block" type="submit">Change Password</button>
</form>

</main>
</div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/custom.js"></script>
</body>
</html>