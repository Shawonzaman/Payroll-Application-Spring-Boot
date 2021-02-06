package com.payroll.utils;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import com.payroll.model.EmployeeEntity;
import com.payroll.model.SalaryEntity;
import com.payroll.repository.SalaryRepo;

public class SalaryCalculatorUtils {
	
	private static final double INCREAMENT = 5000.00;
	
	public void calculateAndUpdateSalary(double basicSalary, List<SalaryEntity> allGrades) {
		int i =0;
		for (SalaryEntity salaryEntity : allGrades) {
			double basic = basicSalary + ((double)i*INCREAMENT);
			double rent = basic * (double)((double)20/(double)100);
			double medical = basic * (double)((double)15/(double)100);
			double gross = basic + rent + medical;
			System.out.println("Basic "+ salaryEntity.getGrade()+" :"+basic);
			salaryEntity.setBasic(basic);
			salaryEntity.setRent(rent);
			salaryEntity.setMedical(medical);
			salaryEntity.setGross(gross);
			
			System.out.println(salaryEntity.toString());
			i++;
		}
	}

}
