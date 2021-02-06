package com.payroll.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payroll.model.EmployeeEntity;
import com.payroll.repository.EmployeeRepo;

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepo employeeRepo;
	
	public List<EmployeeEntity> listAll() {
        return employeeRepo.findAll();
    }
     
    public void save(EmployeeEntity employeeEntity) {
        employeeRepo.save(employeeEntity);
    }
     
    public EmployeeEntity get(long id) {
        return employeeRepo.findById(id).get();
    }
     
    public void delete(long id) {
        employeeRepo.deleteById(id);
    }
    
	public void updateByGrade(int grade, double salary) {
		List<EmployeeEntity> employeebyGrade = employeeRepo.findByGrade(grade);
		System.out.println(employeebyGrade.size());
		for (EmployeeEntity employeeEntity : employeebyGrade) {
			employeeEntity.setSalary(salary);
		}
    }
}
