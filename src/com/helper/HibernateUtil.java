package com.helper;

import java.security.MessageDigest;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.to.*;

@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
public class HibernateUtil {
	private Session session;
	private Transaction transaction;
	private static HibernateUtil obj;
	
	/*
	 * ref: https://dzone.com/articles/storing-passwords-java-web 
	 */
	public String encryptPassword(String pass) {
		StringBuilder hash = new StringBuilder();
		
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] hashedBytes = sha.digest(pass.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'a', 'b', 'c', 'd', 'e', 'f'};
			for(int i = 0; i < hashedBytes.length; i++) {
				byte b = hashedBytes[i];
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			} 
		} catch(Exception ex) {
			System.out.println(ex);
		}
		return hash.toString();
	}
	
	private HibernateUtil() {
		try {
			Configuration config = new Configuration().configure();
			
			config.addAnnotatedClass(com.to.Department.class);
			config.addAnnotatedClass(com.to.Employee.class);
			config.addAnnotatedClass(com.to.Compliance.class);
			config.addAnnotatedClass(com.to.StatusReport.class);
			
			StandardServiceRegistryBuilder builder = 
					new StandardServiceRegistryBuilder().applySettings(config.getProperties());
			SessionFactory factory = config.buildSessionFactory(builder.build());
			
			this.session = factory.openSession();
		} catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	public void addObject(Object o) {
		try {
			this.transaction = this.session.beginTransaction();
			this.session.save(o);
			this.transaction.commit();
		} catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	
	public void updateObject(Object o) {
		try {
			this.transaction = this.session.beginTransaction();
			this.session.merge(o);
			this.transaction.commit();
		} catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	public Department getDepartment(int deptId) {
		Query q = this.session.createQuery("from Department where deptId=:did");
		q.setInteger("did", deptId);
		Department d = null;
		try {
			d = (Department)q.getSingleResult();
		} catch(Exception ex) {
			return null;
		}
		
		return d;
	}
	
	public List<Department> getAllDepartment() {
		Query q = this.session.createQuery("from Department order by deptId desc");
		List<Department> al = q.list();
		return al;
	}
	
	public Employee getUser(int userId, String password) {
		Query q = this.session.createQuery("from Employee where empid=:id and password=:pass");
		q.setInteger("id", userId);
		q.setString("pass", password);
		
		Employee e = null;
		try {
			e = (Employee)q.getSingleResult();
		} catch(Exception ex) {
			e = null;
		}
		
		return e;
	}
	
	public List<Employee> getAllUsers() {
		Query q = this.session.createQuery("from Employee where role=:r");
		q.setString("r", Role.User.name());
		List<Employee> l = q.list();
		return l;
	}
	
	public List<Employee> getAllEmployee() {
		Query q = this.session.createQuery("from Employee order by empid desc");
		List<Employee> l = q.list();
		return l;
	}
	
	public Employee getEmployee(int userId) {
		Query q = this.session.createQuery("from Employee where empid=:id");
		q.setInteger("id", userId);
		Employee e = null;
		try {
			e = (Employee)q.getSingleResult();
		} catch(Exception ex) {
			e = null;
		}
		return e;
	}
	
	public List<Employee> getEmployee(Department d) {
		Query q = this.session.createQuery("from Employee where dept_deptId=:did order by empid desc");
		q.setInteger("did", d.getDeptId());
		List<Employee> obj = q.list();
		return obj;
	}
	
	public Compliance getCompliance(int cId) {
		Query q = this.session.createQuery("from Compliance where complianceId=:cId");
		q.setInteger("cId", cId);
		Compliance obj = null;
		try {
			obj = (Compliance)q.getSingleResult();
		} catch(Exception ex) {
			obj = null;
		}
		return obj;
	}
	
	public List<Compliance> getCompliance(Department d) {
		Query q = this.session.createQuery("from Compliance where dept_deptId=:did order by complianceId desc");
		q.setInteger("did", d.getDeptId());
		List<Compliance> obj = q.list();
		return obj;
	}
	
	public List<Compliance> getCompliance(boolean val) {
		Query q = this.session.createQuery("from Compliance where status=:s order by complianceId desc");
		q.setBoolean("s", val);
		List<Compliance> obj = q.list();
		return obj;
	}
	
	public List<Compliance> getAllCompliances() {
		Query q = this.session.createQuery("from Compliance order by complianceId desc");
		List<Compliance> obj = q.list();
		return obj;
	}
	
	public StatusReport getStatusReport(int sId) {
		Query q = this.session.createQuery("from StatusReport where statusRptId=:sId");
		q.setInteger("sId", sId);
		StatusReport obj = null;
		try {
			obj = (StatusReport)q.getSingleResult();
		} catch(Exception ex) {
			obj = null;
		}
		return obj;
	}
	
	public StatusReport getStatusReport(int cId, int empid) {
		Query q = this.session.createQuery("from StatusReport where comp_complianceId=:cid and emp_empid=:eid");
		q.setInteger("cid", cId);
		q.setInteger("eid", empid);
		StatusReport obj = null;
		try {
			obj = (StatusReport)q.getSingleResult();
		} catch(Exception ex) {
			obj = null;
		}
		return obj;
	}
	
	public List<StatusReport> getStatusReport(Employee e) {
		Query q = this.session.createQuery("from StatusReport where emp_empid =:eid order by statusRptId desc");
		q.setInteger("eid", e.getEmpid());
		List<StatusReport> obj = q.list();
		return obj;
	}
	
	public List<StatusReport> getStatusReport(Department d) {
		Query q = this.session.createQuery("from StatusReport where dept_deptId =:did order by statusRptId desc");
		q.setInteger("did", d.getDeptId());
		List<StatusReport> obj = q.list();
		return obj;
	}
	
	public List<StatusReport> getStatusReport(Compliance c) {
		Query q = this.session.createQuery("from StatusReport where comp_complianceId =:cid order by statusRptId desc");
		q.setInteger("cid", c.getComplianceId());
		List<StatusReport> obj = q.list();
		return obj;
	}
	
	public List<StatusReport> getAllStatusReport() {
		Query q = this.session.createQuery("from StatusReport order by statusRptId desc");
		List<StatusReport> obj = q.list();
		return obj;
	}
	
	/*
	 * Session#delete is deprecated, so this method is used
	 * ref: https://stackoverflow.com/a/3716033 
	 */
	
	/*
	 * Document shows some weak relations in some tables
	 * But logically if some relation as such created or deleted others have to be altered too 
	 */
	
	public void delEmployee(Employee e) { 
		try {
			this.transaction = this.session.beginTransaction();
			
			Query q1 = this.session.createQuery("delete from StatusReport where emp_empid=:id");
			q1.setInteger("id", e.getEmpid());
			q1.executeUpdate();
			
			Query q2 = this.session.createQuery("delete from Employee where empid=:id");
			q2.setInteger("id", e.getEmpid());
			q2.executeUpdate();
			
			this.transaction.commit();
		} catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	public void delCompliance(Compliance c) {
		try {
			this.transaction = this.session.beginTransaction();
			
			Query q1 = this.session.createQuery("delete from StatusReport where comp_complianceId=:id");
			q1.setInteger("id", c.getComplianceId());
			q1.executeUpdate();
			
			Query q2 = this.session.createQuery("delete from Compliance where complianceId=:id");
			q2.setInteger("id", c.getComplianceId());
			q2.executeUpdate();
			
			this.transaction.commit();
		} catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	public void delDepartment(Department d) {
		try {
			this.transaction = this.session.beginTransaction();
			
			Query q1 = this.session.createQuery("delete from StatusReport where dept_deptId=:id");
			q1.setInteger("id", d.getDeptId());
			q1.executeUpdate();
			
			Query q2 = this.session.createQuery("delete from Compliance where dept_deptId=:id");
			q2.setInteger("id", d.getDeptId());
			q2.executeUpdate();
			
			Query q3 = this.session.createQuery("delete from Employee where dept_deptId=:id");
			q3.setInteger("id", d.getDeptId());
			q3.executeUpdate();
			
			Query q4 = this.session.createQuery("delete from Department where deptId=:id");
			q4.setInteger("id", d.getDeptId());
			q4.executeUpdate();
			
			this.transaction.commit();
		} catch(Exception ex) {
			System.out.println(ex);
		}
	}
	
	public static HibernateUtil getUtilObject() {
		if(obj == null)
			obj = new HibernateUtil();
		return obj;
	}
	
	public void sessionClose() {
		session.close();
		obj = null;
	}
}
