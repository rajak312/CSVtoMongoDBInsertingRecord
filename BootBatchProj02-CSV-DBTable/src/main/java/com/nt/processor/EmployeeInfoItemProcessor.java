package com.nt.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.nt.model.Employee;

@Component
public class EmployeeInfoItemProcessor implements ItemProcessor<Employee,Employee> {

	@Override
	public Employee process(Employee emp) throws Exception {
		// TODO Auto-generated method stub
		if(emp.getSalary()>10000) {
			emp.setSalary(Math.round(emp.getSalary())+emp.getSalary()*0.4f);
			emp.setNetsalary(Math.round(emp.getSalary())+emp.getSalary()*0.2f);
		}
		return null;
	}

}
