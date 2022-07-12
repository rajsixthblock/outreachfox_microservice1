package com.companyservice.companyservice.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.Payment;

@Repository
public interface CompanyRepository extends CrudRepository<Company, String>,PagingAndSortingRepository<Company, String>{

	Optional<Company> findById(String id);
	Company getById(String id);
	void deleteById(String id);
	boolean existsByemail(String email);
	Page<Company> getByCompanyName(String companyName,Pageable paging);
	Page<Company> getByEmail(String email,Pageable paging);
	Page<Company> getByPhone(long phone,Pageable paging);
	Page<Company> getByCompanyNameAndEmailAndPhone(String companyName,String email,long phone,Pageable paging);
	Page<Company> getByCompanyNameAndEmail(String companyName,String email,Pageable paging);
	Page<Company> getByCompanyNameAndPhone(String companyName,long phone,Pageable paging);
	Page<Company> getByEmailAndPhone(String email,long phone,Pageable paging);

}
