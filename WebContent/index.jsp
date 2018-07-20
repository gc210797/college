<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.helper.SessionHolder" %>
<% 
	if(session.getAttribute("user_obj") != null)
		response.sendRedirect("AccountServ");
%>
<!DOCTYPE html>
<html class="home">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/custom.css" rel="stylesheet" type="text/css" />
<title>EMS</title>
</head>
<body class="home">

<form class="form-signin" action="AccountServ" method="post" >

	  <div class="text-center mb-4">
	  	<h1 class="h3 mb-3">Login</h1>
	  	<% if(session.getAttribute("err") != null) { %>
	  		<p style="color: red"><%=(String)session.getAttribute("err") %></p>
	  	<% session.removeAttribute("err"); %>
	  	<% } %>
	  </div>
      <div class="form-label-group">
        <input id="user_id" class="form-control" name="user_id" placeholder="User Id" required autofocus type="text">
        <label for="user_id">User Id</label>
      </div>
      <div class="form-label-group">
        <input id="password" class="form-control" name="password" placeholder="Password" required type="password">
        <label for="password">Password</label>
      </div>
     
      <button name="login" class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>