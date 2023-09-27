package com.example.stocksdatamanager.repository;

import com.example.stocksdatamanager.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
}