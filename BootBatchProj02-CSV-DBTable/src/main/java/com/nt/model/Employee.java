package com.nt.model;

import lombok.Data;

@Data
public class Employee {
	
	private Integer empno;
	private String ename;
	private String addrs;
	private Float salary;
	private Float grosssalary;
	private Float netsalary;
	
	
	public Integer getEmpno() {
		return empno;
	}


	public void setEmpno(Integer empno) {
		this.empno = empno;
	}


	public String getEname() {
		return ename;
	}


	public void setEname(String ename) {
		this.ename = ename;
	}


	public String getAddrs() {
		return addrs;
	}


	public void setAddrs(String addrs) {
		this.addrs = addrs;
	}


	public Float getSalary() {
		return salary;
	}


	public void setSalary(Float salary) {
		this.salary = salary;
	}


	public Float getGrosssalary() {
		return grosssalary;
	}


	public void setGrosssalary(Float grosssalary) {
		this.grosssalary = grosssalary;
	}


	public Float getNetsalary() {
		return netsalary;
	}


	public void setNetsalary(Float netsalary) {
		this.netsalary = netsalary;
	}


	@Override
	public String toString() {
		return "Employee [empno=" + empno + ", ename=" + ename + ", addrs=" + addrs + ", salary=" + salary
				+ ", grosssalary=" + grosssalary + ", netsalary=" + netsalary + "]";
	}

}
