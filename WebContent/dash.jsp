<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.helper.SessionHolder" %>
<%@ page import="com.helper.HibernateUtil" %>
<%@ page import="com.to.*" %>
<%@ page import="java.util.List" %>
<%
	boolean isAdmin = false;
	SessionHolder sh = (SessionHolder)session.getAttribute("user_obj");
	if(sh == null) {
		response.sendRedirect("index.jsp");
	}
	else {
		Role r = sh.getRole();
		if(r == Role.Admin)
			isAdmin = true;
	}
	
	HibernateUtil hu = HibernateUtil.getUtilObject();
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/custom.css" rel="stylesheet" type="text/css" />
<title>Dashboard</title>
</head>
<body>
<!-- Header -->
<nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
        <a class="navbar-brand col-sm-3 col-md-2 mr-0"  href="ChangePassword.jsp">Change Password</a>
      <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
          <a class="nav-link" href="AccountServ?logout">Sign out</a>
        </li>
      </ul>
</nav>
<!-- End Header -->

<div class="container-fluid">
<div class="row">
<%
	String htClass = "col-md-12 ml-sm-auto col-lg-12";
	
	if(isAdmin) { 
	htClass = "col-md-9 ml-sm-auto col-lg-10 px-4";
%>

<%@include file="CommonHeader.html" %>

<% } %>

<main class="<%=htClass %>">
<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
	<h2 class="h2">RL Reports</h2>
</div>
	<div class="table-responsive">
            <table class="table table-striped table-sm">
              <thead>
                <tr>
                  <th>Id</th>
                  <th>Type</th>
                  <th>Creation Date</th>
                  <th>Department</th>
                  <th>Completed</th>
                </tr>
              </thead>
              <tbody>
               <% 
	               List<Compliance> l = null;
	               if(isAdmin) {
	            	   l = hu.getAllCompliances();
	               }
	               else {
	            	   Employee e = hu.getEmployee(sh.getEmpId());
	            	   l = hu.getCompliance(e.getDept());
	               }
	               for(Compliance c:l) {
	            	   String clickUrl = "";
	            	   if(isAdmin)
	            		   clickUrl += "AdminView.jsp?view=StatusReport:"+ c.getComplianceId();
	            %>
               	<tr style="cursor: pointer;" onclick="location.href='<%=clickUrl %>'">
	               	<td><%=c.getComplianceId() %></td>
	               	<td><%=c.getRlType().name() %></td>
	               	<td><%=c.getCreateDate() %></td>
	               	<td><%=c.getDept().getDeptName() %></td>
	               	<td><%=c.isStatus() %></td>
               	</tr>
               	<tr>
	               	<td colspan="5">
						   <b>Detals: </b> <%=c.getDetails() %>
					</td>
               	</tr>
               	<% if(!isAdmin) { 
               		StatusReport sr = hu.getStatusReport(c.getComplianceId(), sh.getEmpId());
               	%>
               	<tr>
               		<td colspan="5">
               		<% if(!c.isStatus()) { %>
               			<form class="form-signin" id="changePassword" action="UserCtrl" method="post">
               			<div class="form-label-group">
				    	<textarea class="form-control" name="comments" required autofocus><%=(sr != null) ? sr.getComments() : "" %></textarea>
				    	<label for="password">Comments</label>
				    </div>
				    <input type="hidden" name="cmpId" value="<%=c.getComplianceId() %>" required />
				    <input type="hidden" name="deptId" value="<%=c.getDept().getDeptId() %>" required />
				    <% if(sr != null) { %>
				    	<input type="hidden" name="srId" value="<%=sr.getStatusRptId() %>" required />
				    <% } %>
  				 	 <button name="addComments" class="btn btn-lg btn-primary btn-block" type="submit">Add Comments</button>
               		</form>
               		<% } else { %>
               		<% if(sr != null) { %>
               		<b>Comments: </b><%=sr.getComments() %>
               		<% } %>
               		<% } %>
               		</td>
               	</tr>
               	<% } %>
               	<% } //endfor %>
            </table>
          </div>
</main>
</div>
</div>

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>