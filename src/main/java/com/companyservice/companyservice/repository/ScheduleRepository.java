package com.companyservice.companyservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.companyservice.companyservice.entity.Schedule;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, String>,PagingAndSortingRepository<Schedule, String>{

	Schedule getById(String id);

	boolean existsByTimeZone(String timeZone);
}
