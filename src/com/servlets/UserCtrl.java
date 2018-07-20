package com.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.to.*;
import com.helper.*;

/**
 * Servlet implementation class UserCtrl
 */
public class UserCtrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserCtrl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionHolder sh = (SessionHolder)request.getSession(false).getAttribute("user_obj");
		if(sh == null || sh.getRole() != Role.User) {
			response.sendRedirect("index.jsp");
			return;
		}
		
		HibernateUtil hu = HibernateUtil.getUtilObject();
		
		if(request.getParameter("addComments") != null) {
			Employee e = hu.getEmployee(sh.getEmpId());
			int cid = Integer.parseInt(request.getParameter("cmpId"));
			Compliance c = hu.getCompliance(cid);
			Department d = hu.getDepartment(Integer.parseInt(request.getParameter("deptId")));
			List<Employee> el = hu.getEmployee(d);
			
			StatusReport sr = new StatusReport(e, d, c, request.getParameter("comments"), new Date());
			if(request.getParameter("srId") != null)
				hu.addObject(sr);
			else
				hu.updateObject(sr);
			List<StatusReport> srl = hu.getStatusReport(c);
			if(srl.size() == el.size()) {
				c.setStatus(true);
				hu.updateObject(c);
			}
		}
		
		response.sendRedirect("AccountServ");
	}

}
