package com.to;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

@Entity
public class StatusReport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int statusRptId;
	/*
	 * ManyToOne
	 * ref: https://docs.jboss.org/hibernate/jpa/2.1/api/javax/persistence/ManyToOne.html
	*/
	@ManyToOne(optional = false)
	private Employee emp;
	
	@ManyToOne(optional = false)
	private Department dept;
	
	@ManyToOne(optional = false)
	private Compliance comp;
	
	@Type(type="text")
	private String comments;
	private Date createDate;
	public int getStatusRptId() {
		return statusRptId;
	}
	public void setStatusRptId(int statusRptId) {
		this.statusRptId = statusRptId;
	}
	public Employee getEmp() {
		return emp;
	}
	public void setEmp(Employee emp) {
		this.emp = emp;
	}
	public Department getDept() {
		return dept;
	}
	public void setDept(Department dept) {
		this.dept = dept;
	}
	public Compliance getComp() {
		return comp;
	}
	public void setComp(Compliance comp) {
		this.comp = comp;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public StatusReport() {}
	public StatusReport(Employee emp, Department dept, Compliance comp, String comments, Date createDate) {
		super();
		this.emp = emp;
		this.dept = dept;
		this.comp = comp;
		this.comments = comments;
		this.createDate = createDate;
	}
}