package com.companyservice.companyservice.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.companyservice.companyservice.entity.Company;

@Repository
public interface CompanyRepository extends CrudRepository<Company, String>,PagingAndSortingRepository<Company, String>{

	Optional<Company> findById(String id);

	Company getById(String id);

	void deleteById(String id);

	boolean existsByemail(String email);

}
