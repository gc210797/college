package com.helper;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import java.util.Date;
import java.util.List;
import com.to.*;

public class ViewEditTable extends SimpleTagSupport {
	private String obj;
	private String output;

	HibernateUtil hu;
	
	private String dateFormat(Date obj) {
		return new SimpleDateFormat("dd/MM/yy").format(obj);
	}
	
	private void empTab() {
		output += "<thead><tr>";
		output += "<td>Id</td>";
		output += "<td>First Name</td>";
		output += "<td>Last Name</td>";
		output += "<td>DOB</td>";
		output += "<td>Email</td>";
		output += "<td>Department</td>";
		output += "<td>Action</td>";
		output += "</tr></thead>";

		List<Employee> l = hu.getAllEmployee();

		output += "<tbody>";
		for(Employee e: l) {
			output += "<tr>";
			output += "<td>"+ e.getEmpid() +"</td>";
			output += "<td>"+ e.getFirstName() +"</td>";
			output += "<td>"+ e.getLastName() +"</td>";
			output += "<td>"+ dateFormat(e.getDob()) +"</td>";
			output += "<td>"+ e.getEmail() +"</td>";
			output += "<td>"+ e.getDept().getDeptName() +"</td>";
			output += "<td><a href='EditEmployee.jsp?id="+e.getEmpid()+"'>Edit</a> | "
					  + "<a href='AdminCtrl?del=Employee&id="+e.getEmpid()+"'>Delete</a></td>";
			output += "</tr>";
		}
		output += "</tbody>";
	}
	
	private void deptTab() {
		output += "<thead><tr>";
		output += "<td>Id</td>";
		output += "<td>Name</td>";
		output += "<td>Action</td>";
		output += "</tr></thead>";
		
		List<Department> l = hu.getAllDepartment();
		
		output += "<tbody>";
		for(Department d: l) {
			output += "<tr>";
			output += "<td>"+ d.getDeptId() +"</td>";
			output += "<td>"+ d.getDeptName() +"</td>";
			output += "<td><a href='AdminCtrl?del=Department&id="+d.getDeptId()+"'>Delete</a></td>";
			output += "</tr>";
		}
		output += "</tbody>";
	}
	
	private void compTab() {
		output += "<thead><tr>";
		output += "<td>Id</td>";
		output += "<td>Type</td>";
		output += "<td>Creation Date</td>";
		output += "<td>Deparmtment</td>";
		output += "<td>Completed</td>";
		output += "<td>Action</td>";
		output += "</tr></thead>";
		
		List<Compliance> l = hu.getAllCompliances();
		output += "<tbody>";
		
		for(Compliance c: l) {
			output += "<tr>";
			output += "<td>"+ c.getComplianceId() +"</td>";
			output += "<td>"+ c.getRlType().name() +"</td>";
			output += "<td>"+ dateFormat(c.getCreateDate()) +"</td>";
			output += "<td>"+ c.getDept().getDeptName() +"</td>";
			output += "<td>"+ c.isStatus() +"</td>";
			output += "<td><a href='EditCompliance.jsp?id="+c.getComplianceId()+"'>Edit</a> | "
					  + "<a href='AdminCtrl?del=Compliance&id="+c.getComplianceId()+"'>Delete</a></td>";
			output += "</tr>";
			output += "<tr>";
			output += "<td>"+ c.getDetails() +"</td>";
			output += "</tr>";
		}
		
		output += "</tbody>";
	}
	
	private void statusRptTab() {
		int cmpId = Integer.parseInt(this.obj.split(":")[1]);
		output += "<thead><tr>";
		output += "<td>Id</td>";
		output += "<td>Emp Id</td>";
		output += "<td>Name</td>";
		output += "<td>Email</td>";
		output += "</tr></thead>";
		
		List<StatusReport> l = hu.getStatusReport(hu.getCompliance(cmpId));
		
		output += "<tbody>";
		
		for(StatusReport s: l) {
			output += "<tr>";
			output += "<td>"+ s.getStatusRptId() +"</td>";
			output += "<td>"+ s.getEmp().getEmpid() +"</td>";
			output += "<td>"+ s.getEmp().getFirstName() + " " + s.getEmp().getLastName() +"</td>";
			output += "<td>"+ s.getEmp().getEmail() +"</td>";
			output += "</tr>";
			
			output += "<tr>";
			output += "<td><b>Comments: </b>"+ s.getComments() +"</td>";
			output += "</tr>";
		}
		
		output += "</tbody>";
	}

	@Override
	public void doTag() throws JspException, IOException {
		output = "";
		hu = HibernateUtil.getUtilObject();
		JspWriter out = getJspContext().getOut();
		String objMod = obj.split(":")[0];
		switch(objMod) {
		case "Employee":
			empTab();
			break;
		case "Department":
			deptTab();
			break;
		case "Compliance":
			compTab();
			break;
		case "StatusReport":
			statusRptTab();
			break;
		}
		//System.out.println(output);
		out.print(this.output);
	}

	public void setObj(String obj) {
		this.obj = obj;
	}
	
}
