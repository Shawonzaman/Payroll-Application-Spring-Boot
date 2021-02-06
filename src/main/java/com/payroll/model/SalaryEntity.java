package com.payroll.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SalaryEntity {
	@Id
	private int grade;
	private double basic;
	private double rent;
	private double medical;
	private double gross;
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public double getBasic() {
		return basic;
	}
	public void setBasic(double basic) {
		this.basic = basic;
	}
	public double getRent() {
		return rent;
	}
	public void setRent(double rent) {
		this.rent = rent;
	}
	public double getMedical() {
		return medical;
	}
	public void setMedical(double medical) {
		this.medical = medical;
	}
	
	public double getGross() {
		return gross;
	}
	public void setGross(double gross) {
		this.gross = gross;
	}
	@Override
	public String toString() {
		return "SalaryEntity [grade=" + grade + ", basic=" + basic + ", rent=" + rent + ", medical=" + medical
				+ ", gross=" + gross + "]";
	}
	
		
	
}
