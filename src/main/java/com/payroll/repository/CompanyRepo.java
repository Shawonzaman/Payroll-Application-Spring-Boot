package com.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payroll.model.CompanyEntity;

@Repository
public interface CompanyRepo  extends JpaRepository<CompanyEntity, Integer>{

}
