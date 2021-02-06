package com.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payroll.model.EmployeeEntity;
import com.payroll.model.SalaryEntity;

@Repository
public interface SalaryRepo extends JpaRepository<SalaryEntity, Integer>{
	
}
