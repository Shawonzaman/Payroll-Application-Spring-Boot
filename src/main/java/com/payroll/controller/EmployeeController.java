package com.payroll.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.payroll.model.EmployeeEntity;
import com.payroll.model.SalaryEntity;
import com.payroll.repository.SalaryRepo;
import com.payroll.service.EmployeeService;

@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	SalaryRepo salaryRepo;
	
	@RequestMapping("/employee")
	public String viewAllEmployee(Model model) {
	    List<EmployeeEntity> listEmployees = employeeService.listAll();
	    System.out.println(listEmployees.toString());
	    model.addAttribute("list", listEmployees);
	     
	    return "EmployeeList";
	}
	
	@RequestMapping("/employee/new")
	public String showNewEmployeePage(Model model) {
	    EmployeeEntity employeeEntity = new EmployeeEntity();
	    model.addAttribute("employee", employeeEntity);
	     
	    return "new_employee";
	}
	
	@RequestMapping(value = "/employee/save", method = RequestMethod.POST)
	public String saveEmployee(@ModelAttribute("employee") EmployeeEntity employeeEntity) {
	    employeeService.save(employeeEntity);
	     
	    return "redirect:/employee";
	}
	
	@RequestMapping("/employee/edit/{id}")
	public ModelAndView showEditEmployeePage(@PathVariable(name = "id") long id) {
		System.out.println("Edit Called");
	    ModelAndView mav = new ModelAndView("edit_employee");
	    EmployeeEntity employeeEntity = employeeService.get(id);
	    mav.addObject("employee", employeeEntity);
	     
	    return mav;
	}
	
	@RequestMapping("/employee/delete/{id}")
	public String deleteEmployee(@PathVariable(name = "id") long id) {
	    employeeService.delete(id);
	    return "redirect:/employee";       
	}
	
	@PostMapping("/employee/update/{id}")
	public String updateUser(@PathVariable("id") long id, EmployeeEntity employeeEntity, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        employeeEntity.setId(id);
	        return "update-user";
	    }
	        
	    employeeService.save(employeeEntity);
	    List<EmployeeEntity> listEmployees = employeeService.listAll();
	    model.addAttribute("list", listEmployees);
	    return "redirect:/employee";
	}
	
	@RequestMapping("/grade/new")
	public String showNewGradePage(Model model) {
	    SalaryEntity salaryEntity = new SalaryEntity();
	    model.addAttribute("salary", salaryEntity);
	     
	    return "new_grade";
	}
	
	@RequestMapping("/grade/edit/{id}")
	public ModelAndView showEditGradePage(@PathVariable(name = "id") int id) {
		System.out.println("Edit Called");
	    ModelAndView mav = new ModelAndView("edit_grade");
	    Optional<SalaryEntity> salaryEntity = salaryRepo.findById(id);
	    mav.addObject("salary", salaryEntity);
	     
	    return mav;
	}
	
	@RequestMapping("/grade/delete/{id}")
	public String deleteGrade(@PathVariable(name = "id") int id) {
	    salaryRepo.deleteById(id);
	    return "redirect:/salaryInfo";       
	}
	
	@PostMapping("/grade/update/{id}")
	public String updateGrade(@PathVariable("id") int id, SalaryEntity salaryEntity, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        salaryEntity.setGrade(id);;
	        return "edit_grade";
	    }
	        
	    salaryRepo.save(salaryEntity);
	    List<SalaryEntity> salaryEntities = salaryRepo.findAll();
	    model.addAttribute("list", salaryEntities);
	    return "redirect:/salaryInfo";
	}
	
	@RequestMapping(value = "/grade/save", method = RequestMethod.POST)
	public String saveGrade(@ModelAttribute("salary") SalaryEntity salaryEntity) {
		salaryRepo.save(salaryEntity);
	     
	    return "redirect:/salaryInfo";
	}
	
}
