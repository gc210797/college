<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.helper.*" %>
<%@ page import="com.to.*" %>
<%
SessionHolder sh = (SessionHolder)session.getAttribute("user_obj");
if(sh == null || sh.getRole() != Role.Admin) {
	response.sendRedirect("index.jsp");
	return;
}
else if(request.getParameter("id") == null) {
	response.sendRedirect("AdminView.jsp?view=Compliance");
	return;
}
HibernateUtil hu = HibernateUtil.getUtilObject();
Compliance c = hu.getCompliance(Integer.parseInt(request.getParameter("id")));
%>
<!DOCTYPE>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit Compliance</title>
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
	<h2 class="h2">Edit Department</h2>
</div>

<form class="form-signin" id="editComp" action="AdminCtrl" method="post">
<div class="form-label-group">
    	<select id="rl" name="rl" class="custom-select d-block w-100" required>
    		<option value="">--Type--</option>
    		<% for(RL d: RL.values()) { %>
    			<option <%=(c.getRlType() == d) ? "selected" : "" %> value="<%=d %>"><%=d %></option>
    		<% } %>
    	</select>
    </div>
	<div class="form-label-group">
    	<select id="dept" name="dept" class="custom-select d-block w-100">
    		<option value="">--Department--</option>
    		<% for(Department d: hu.getAllDepartment()) { %>
    			<% if(c.getDept() != null) { %>
    			<option <%=d.equals(c.getDept()) ? "selected" : "" %> value="<%=d.getDeptId() %>"><%=d.getDeptName() %></option>
    		<% } else { %>
    		<option value="<%=d.getDeptId() %>"><%=d.getDeptName() %></option>
    		<% } %>
    		<% } %>
    	</select>
    </div>
    <div class="form-label-group">
    	<textarea id="details" class="form-control" name="details" placeholder="Deatilas" required><%=c.getDetails() %></textarea>
    	<label for="details">Details</label>
    </div>
    <input type="hidden" name="id" value="<%=c.getComplianceId() %>" required />
    <button name="editComp" class="btn btn-lg btn-primary btn-block" type="submit">Edit Compliance</button>
</form>

</main>
</div>
</div>

</body>
</html>