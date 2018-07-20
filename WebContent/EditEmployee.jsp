<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.helper.*" %>
<%@ page import="com.to.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
SessionHolder sh = (SessionHolder)session.getAttribute("user_obj");
if(sh == null || sh.getRole() != Role.Admin) {
	response.sendRedirect("index.jsp");
	return;
}
else if(request.getParameter("id") == null) {
	response.sendRedirect("AdminView.jsp?view=Employee");
	return;
}
HibernateUtil hu = HibernateUtil.getUtilObject();
Employee e = hu.getEmployee(Integer.parseInt(request.getParameter("id")));
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit Employee</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/custom.css" rel="stylesheet" type="text/css" />
<link href="css/jquery-ui.css" rel="stylesheet" type="text/css" />
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
	<h2 class="h2">Edit Employee</h2>
</div>

<form class="form-signin" id="editEmp" action="AdminCtrl" method="post">

<div class="form-label-group">
    	<input id="firstname" class="form-control" name="firstname" value="<%=e.getFirstName() %>" placeholder="First Name" required type="text" />
    	<label for="firstname">First Name</label>
    </div>
    
    <div class="form-label-group">
    	<input id="lastname" class="form-control" name="lastname" value="<%=e.getLastName() %>" placeholder="Last Name" required type="text" />
    	<label for="lastname">Last Name</label>
    </div>

	<div class="form-label-group">
        <input id="inputEmail" class="form-control" name="email" value="<%=e.getEmail() %>" placeholder="Email address" required type="email">
        <label for="inputEmail">Email</label>
    </div>
    
    <div class="form-label-group">
    	<input id="dob" class="form-control" name="dob" placeholder="dob" value="<%=new SimpleDateFormat("dd/MM/yy").format(e.getDob()) %>" required type="text" />
    	<label for="dob">DOB</label>
    </div>
    
    <div class="form-label-group">
    
    	<select id="role" name="role" class="custom-select d-block w-100" required>
    		<option value="">--Role--</option>
    		<% for(Role r: Role.values()) { %>
    			<option <%=(r == e.getRole()) ? "selected" : "" %> value="<%=r %>"><%=r %></option>
    		<% } %>
    	</select>
    	
    </div>
    
    <div class="form-label-group">
    	<select id="dept" name="dept" class="custom-select d-block w-100" required>
    		<option value="">--Department--</option>
    		<% for(Department d: hu.getAllDepartment()) { %>
    			<option <%=d.equals(e.getDept()) ? "selected" : "" %> value="<%=d.getDeptId() %>"><%=d.getDeptName() %></option>
    		<% } %>
    	</select>
    </div>
    <input type="hidden" name="id" value="<%=e.getEmpid() %>" required />
      <button name="editEmp" class="btn btn-lg btn-primary btn-block" type="submit">Edit Employee</button>


</form>
</main>

</div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="js/custom.js"></script>
</body>
</html>