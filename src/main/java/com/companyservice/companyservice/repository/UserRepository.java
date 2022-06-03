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
	@Query(value = "SELECT * FROM USERS WHERE EMAIL = ?1", nativeQuery = true)
	User findByEmail(String email);
	
	@Query(value = "SELECT * FROM USERS WHERE COMPANY_ID = ?1", nativeQuery = true)
	List<User> findByCompanyId(String companyId);
	Page<User> findByCompanyId(Company companyId,Pageable paging);
	User getById(String id);

	boolean existsByemail(String email);
}
