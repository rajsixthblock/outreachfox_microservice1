package com.companyservice.companyservice.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import com.companyservice.companyservice.entity.Company;


public interface CompanyRepository extends CrudRepository<Company, String>{

	Optional<Company> findById(String id);

	Company getById(String id);

	void deleteById(String id);

	boolean existsByemail(String email);

}
