package com.to;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

@Entity
public class Compliance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int complianceId;
	
	@Enumerated(EnumType.STRING)
	private RL rlType;
	
	@Type(type="text")
	private String details;
	
	private boolean status;
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	private Date createDate;
	
	@ManyToOne(optional = false)
	private Department dept;

	public int getComplianceId() {
		return complianceId;
	}

	public void setComplianceId(int complianceId) {
		this.complianceId = complianceId;
	}

	public RL getRlType() {
		return rlType;
	}

	public void setRlType(RL rlType) {
		this.rlType = rlType;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	public Compliance() {}

	public Compliance(RL rlType, String details, boolean status, Date createDate, Department dept) {
		super();
		this.rlType = rlType;
		this.details = details;
		this.status = status;
		this.createDate = createDate;
		this.dept = dept;
	}
}
