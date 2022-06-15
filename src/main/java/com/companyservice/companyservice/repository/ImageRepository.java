package com.companyservice.companyservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.companyservice.companyservice.entity.Images;
import com.companyservice.companyservice.entity.User;



@Repository
public interface ImageRepository extends CrudRepository<Images, String>,PagingAndSortingRepository<Images, String> {
	Images getById(String id);
	Images getByUserId(User userId);
	boolean existsById(String id);
}
