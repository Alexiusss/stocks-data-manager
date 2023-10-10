package com.example.stocksdatamanager.repository.company;

import com.example.stocksdatamanager.model.Company;
import com.example.stocksdatamanager.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CrudCompanyRepository extends BaseRepository<Company> {
    @Query("SELECT c FROM Company c WHERE c.isEnabled=true")
    List<Company> findAllActive();
}