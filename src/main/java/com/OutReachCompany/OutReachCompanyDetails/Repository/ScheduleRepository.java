package com.OutReachCompany.OutReachCompanyDetails.Repository;

import org.springframework.data.repository.CrudRepository;
import com.OutReachCompany.OutReachCompanyDetails.model.Schedule;

public interface ScheduleRepository extends CrudRepository<Schedule, String>{

	Schedule getById(String id);

	boolean existsByTimeZone(String timeZone);
}
