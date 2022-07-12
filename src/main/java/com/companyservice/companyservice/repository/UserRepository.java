package com.companyservice.companyservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.User;


@Repository
public interface UserRepository extends CrudRepository<User, String>,PagingAndSortingRepository<User, String>{
	//@Query(value = "SELECT * FROM users WHERE EMAIL = ?1", nativeQuery = true)
	User findByEmail(String email);
	
	//@Query(value = "SELECT * FROM USERS WHERE COMPANY_ID = ?1", nativeQuery = true)
	List<User> findByCompanyId(String companyId);
	Page<User> findByCompanyId(Company companyId,Pageable paging);
	User getById(String id);
	User getByEmail(String email);
	boolean existsByEmail(String email);
	int countByCompanyId(Company companyId);
	Page<User> getByCompanyIdAndName(Company companyId,String name,Pageable paging);
	Page<User> getByCompanyIdAndEmail(Company companyId,String email,Pageable paging);
	Page<User> getByCompanyIdAndPhone(Company companyId,long phone,Pageable paging);
	Page<User> getByCompanyIdAndNameAndEmailAndPhone(Company companyId,String name,String email,long phone,Pageable paging);
	Page<User> getByCompanyIdAndNameAndEmail(Company companyId,String name,String email,Pageable paging);
	Page<User> getByCompanyIdAndNameAndPhone(Company companyId,String name,long phone,Pageable paging);
	Page<User> getByCompanyIdAndEmailAndPhone(Company companyId,String email,long phone,Pageable paging);
}
