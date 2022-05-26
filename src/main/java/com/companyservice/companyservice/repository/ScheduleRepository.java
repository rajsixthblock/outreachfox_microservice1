package com.companyservice.companyservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.companyservice.companyservice.entity.Schedule;


public interface ScheduleRepository extends CrudRepository<Schedule, String>{

	Schedule getById(String id);

	boolean existsByTimeZone(String timeZone);
}
