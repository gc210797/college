package com.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.helper.HibernateUtil;
import com.helper.SessionHolder;
import com.to.*;

/**
 * Servlet implementation class AccountServ
 */
public class AccountServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountServ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(request.getParameter("logout") != null) {
			session.removeAttribute("user_obj");
			response.sendRedirect("index.jsp");
			HibernateUtil.getUtilObject().sessionClose();
		} else if(session != null && session.getAttribute("user_obj") != null) {
			request.getRequestDispatcher("dash.jsp").forward(request, response);
		}
		else {
			response.sendRedirect("index.jsp");
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("login") != null) {
			HttpSession session = request.getSession();
			HibernateUtil hu = HibernateUtil.getUtilObject();
			Employee e = hu.getUser(Integer.parseInt(request.getParameter("user_id")), hu.encryptPassword(request.getParameter("password")));
			if(e == null) {
				session.setAttribute("err", "Invalid Username/Password");
				response.sendRedirect("index.jsp");
			}
			else {
				session.setAttribute("user_obj", new SessionHolder(e.getEmpid(), e.getRole()));
				RequestDispatcher rd = request.getRequestDispatcher("dash.jsp");
				rd.forward(request, response);
			}
		}
		else if(request.getParameter("changePassword") != null) {
			SessionHolder sh = (SessionHolder)request.getSession(false).getAttribute("user_obj");
			if(sh == null) {
				response.sendRedirect("index.jsp");
				return;
			}
			HibernateUtil hu = HibernateUtil.getUtilObject();
			Employee e = hu.getEmployee(sh.getEmpId());
			e.setPassword(hu.encryptPassword(request.getParameter("password")));
			hu.updateObject(e);
			request.getSession(false).setAttribute("msg", "Password changed successfully");
			response.sendRedirect("ChangePassword.jsp");
		}
		else {
			response.sendRedirect("index.jsp");
		}
	}

}
