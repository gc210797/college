package com.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.to.*;
import com.helper.*;

/**
 * Servlet implementation class AdminCtrl
 */
public class AdminCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminCtrl() {
        super();
    }
    
    private void addEmployee(HttpServletRequest request, HttpServletResponse response, HibernateUtil hu, boolean update) {
    	Date d = null;
    	
    	try {
    		d = new SimpleDateFormat("dd/MM/yy").parse(request.getParameter("dob"));
    	} catch(Exception ex) {
    		System.out.println(ex);
    		request.getSession(false).setAttribute("msg", "Error parsing date");
    		return;
    	}
    	
    	Employee e = new Employee(request.getParameter("firstname"),
    			                  request.getParameter("lastname"),
    			                  d,
    			                  request.getParameter("email"),
    			                  hu.encryptPassword(request.getParameter("password")),
    			                  hu.getDepartment(Integer.parseInt(request.getParameter("dept"))),
    			                  Role.valueOf(request.getParameter("role")));
    	if(!update) {
    		hu.addObject(e);
    		request.getSession(false).setAttribute("msg", "Employee added successfully");
    	}
    	else {
    		e.setEmpid(Integer.parseInt(request.getParameter("id")));
    		hu.updateObject(e);
    		request.getSession(false).setAttribute("msg", "Employee deleted successfully");
    	}
    }
    
    private void addDepartment(HttpServletRequest request, HttpServletResponse response, HibernateUtil hu) {
    	Department d = new Department(request.getParameter("deptName"));
    	hu.addObject(d);
    	request.getSession().setAttribute("msg", "Department added successfully");
    }
    
    private void addCompliance(HttpServletRequest request, HttpServletResponse response, HibernateUtil hu, boolean update) {
    	Compliance c = new Compliance(RL.valueOf(request.getParameter("rl")),
    			                      request.getParameter("details"),
    			                      false,
    			                      new Date(),
    			                      hu.getDepartment(Integer.parseInt(request.getParameter("dept"))));
    	if(!update) {
    		hu.addObject(c);
    		request.getSession(false).setAttribute("msg", "Compliance added successfully");
    	}
    	else {
    		c.setComplianceId(Integer.parseInt(request.getParameter("id")));
    		hu.updateObject(c);
    		request.getSession(false).setAttribute("msg", "Compliance updated successfully");
    	}
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionHolder sh = (SessionHolder)request.getSession(false).getAttribute("user_obj");
		if(sh == null || sh.getRole() != Role.Admin) {
			response.sendRedirect("index.jsp");
			return;
		}
		
		HibernateUtil hu = HibernateUtil.getUtilObject();
		
		if(request.getParameter("addEmp") != null) {
			this.addEmployee(request, response, hu, false);
			response.sendRedirect("AddEmployee.jsp");
		}
		else if(request.getParameter("addDept") != null) {
			this.addDepartment(request, response, hu);
			response.sendRedirect("AddDepartment.jsp");
		}
		else if(request.getParameter("addComp") != null) {
			this.addCompliance(request, response, hu, false);
			response.sendRedirect("AddCompliance.jsp");
		}
		else if(request.getParameter("del") != null) {
			if(request.getParameter("id") == null) {
				response.sendRedirect("AdminView.jsp?view=Employee");
				return;
			}
			switch(request.getParameter("del")) {
			case "Employee":
				hu.delEmployee(hu.getEmployee(Integer.parseInt(request.getParameter("id"))));
				request.getSession().setAttribute("msg", "Employee deleted successfully");
				response.sendRedirect("AdminView.jsp?view=Employee");
				break;
			case "Department":
				hu.delDepartment(hu.getDepartment(Integer.parseInt(request.getParameter("id"))));
				request.getSession().setAttribute("msg", "Department delete successfully");;
				response.sendRedirect("AdminView.jsp?view=Department");
				break;
			case "Compliance":
				hu.delCompliance(hu.getCompliance(Integer.parseInt(request.getParameter("id"))));
				request.getSession().setAttribute("msg", "Compliance deleted successfully");
				response.sendRedirect("AdminView.jsp?view=Compliance");
				break;
			}
		}
		else if(request.getParameter("editEmp") != null) {
			addEmployee(request, response, hu, true);
			response.sendRedirect("AdminView.jsp?view=Employee");
		}
		else if(request.getParameter("editComp") != null) {
			addCompliance(request, response, hu, true);
			response.sendRedirect("AdminView.jsp?view=Compliance");
		}
		else {
			response.sendRedirect("AccountServ");
		}
	}
}
