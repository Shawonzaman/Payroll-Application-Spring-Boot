package com.payroll.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.payroll.model.CompanyEntity;
import com.payroll.model.EmployeeEntity;
import com.payroll.model.SalaryEntity;
import com.payroll.repository.CompanyRepo;
import com.payroll.repository.SalaryRepo;
import com.payroll.service.EmployeeService;
import com.payroll.utils.SalaryCalculatorUtils;

@Controller
public class SalaryController {
	
	@Autowired
	CompanyRepo companyRepo;
	
	@Autowired
	SalaryRepo salaryRepo;
	
	@Autowired
	EmployeeService employeeService;
	
	@RequestMapping("/company")
	public String companyDetails(Model model) {
		CompanyEntity companyEntity = companyRepo.findById(1).get();
	    model.addAttribute("company", companyEntity);
	     
	    return "company_home";
	}
	
	@PostMapping("/company/addToAccount")
	public String updateAccount(@RequestParam("addedAmount") double addedAmount) {
		System.out.println("Inside Add to Account");
		CompanyEntity companyEntity = companyRepo.findById(1).get();
		System.out.println(companyEntity.toString());
		double updatedAmount = companyEntity.getAccount()+addedAmount;
		companyEntity.setAccount(updatedAmount);
		companyRepo.save(companyEntity);
	     
	    return "redirect:/company";
	}
	
	@PostMapping("/company/calculateSalary")
	public String calculateSalary(@RequestParam("basicSalary") double basicSalary, Model model) {
		System.out.println("Inside calculate salary");
		List<SalaryEntity> salaryEntities = salaryRepo.findAll();
		System.out.println(salaryEntities.size());
		CompanyEntity companyEntity = companyRepo.findById(1).get();
		SalaryCalculatorUtils salaryCalculatorUtils = new SalaryCalculatorUtils();
		
		salaryCalculatorUtils.calculateAndUpdateSalary(basicSalary, salaryEntities);
		
		for (SalaryEntity salaryEntity : salaryEntities) {
			System.out.println(salaryEntity.toString());
			salaryRepo.save(salaryEntity);
			employeeService.updateByGrade(salaryEntity.getGrade(), salaryEntity.getGross());
		}
		
		List<EmployeeEntity> employeeEntities = employeeService.listAll();
		double payableSalary = 0.0;
		for (EmployeeEntity employeeEntity : employeeEntities) {
			payableSalary += employeeEntity.getSalary();
		}
		model.addAttribute("list", salaryEntities);
		companyEntity.setSalary(payableSalary);
		companyRepo.save(companyEntity);
	    return "salary_info";
	}
	
	@PostMapping("/company/transfer")
	public String transferSalary() {
		System.out.println("Inside transfer salary");
		
		CompanyEntity companyEntity = companyRepo.findById(1).get();
		if(companyEntity.getAccount() < companyEntity.getSalary()){
			return "redirect:/company";
		}
		
		List<EmployeeEntity> employeeEntities = employeeService.listAll();
		
		for (EmployeeEntity employeeEntity : employeeEntities) {
			employeeEntity.setAccount(employeeEntity.getAccount()+employeeEntity.getSalary());
			employeeService.save(employeeEntity);
		}
		double currentBalance = companyEntity.getAccount() - companyEntity.getSalary();
		companyEntity.setAccount(currentBalance);
		companyEntity.setSalary(0.0);
		companyRepo.save(companyEntity);
	    return "redirect:/company";
	}
	
	@RequestMapping("/salaryInfo")
	public String viewAllEmployee(Model model) {
		List<SalaryEntity> salaryEntities = salaryRepo.findAll();
	    model.addAttribute("list", salaryEntities);
	     
	    return "salary_info";
	}

	@RequestMapping("/")
	public String home(){
		return "redirect:/company";
	}
}
