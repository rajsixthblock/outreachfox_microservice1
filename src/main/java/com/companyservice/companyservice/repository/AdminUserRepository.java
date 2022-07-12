package com.companyservice.companyservice.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.companyservice.companyservice.entity.AdminUser;
import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.User;

public interface AdminUserRepository extends CrudRepository<AdminUser, String>,PagingAndSortingRepository<AdminUser, String>{
	AdminUser findByEmail(String email);
	AdminUser getByAdminId(String id);
	AdminUser getByEmail(String email);
	boolean existsByEmail(String email);
	boolean existsByPhone(long phone);
	boolean existsByEmailAndPhone(String email,long phone);
	boolean existsByAdminId(String adminId);

}
