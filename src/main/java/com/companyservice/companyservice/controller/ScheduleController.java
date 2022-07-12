package com.companyservice.companyservice.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.Authorization.authorizationservice.annotation.Authorization;
import com.Authorization.authorizationservice.annotation.Logging;
import com.companyservice.companyservice.entity.Schedule;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.service.ScheduleService;

@CrossOrigin
@RestController
@Authorization
@Logging
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;
	
	@PostMapping("/schedule/create")
	public ResponseEntity<?> create( @RequestBody @Valid Schedule payload) throws Exception {
		Schedule newSchedule = scheduleService.creation(payload);
		if(newSchedule.getScheduleId() != null) {
			return new ResponseEntity<>(newSchedule, HttpStatus.CREATED);
		}
		else {
			throw new BadRequestException("Schedule creation failed");
		}
	}
	
	
	@GetMapping("/schedule/getAllSchedules/{page}/{limit}")
	public ResponseEntity<?> getScheduleTimeZones(@PathVariable int page,@PathVariable int limit) throws Exception {
		if(page == 0) {
			page = 1;
		}
		if(limit == 0) {
			limit = 10;
		}
		//List<Schedule> schedules =  scheduleService.getScheduleTimeZonesDetails(page,limit);
		Page<Schedule> schedules =  scheduleService.getScheduleTimeZonesDetails(page,limit);
		if(!schedules.isEmpty()) {
			return new ResponseEntity<>(schedules, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Schedule details not found");
		}
	}
	
	@GetMapping("/schedule/getByID/{id}")
	public ResponseEntity<?> getByScheduleTimeZoneID(@PathVariable String id) throws Exception {
		Optional<Schedule> newSchedule =  scheduleService.getScheduleTimeZoneByID(id);
		if(!newSchedule.isEmpty()) {
			return new ResponseEntity<>(newSchedule, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Schedule details not found");
		}
	}
	
	@PutMapping("/schedule/update/{id}")
	public ResponseEntity<?> updateScheduleTimeZone(@PathVariable String id, @RequestBody Schedule payload) throws Exception {
		Schedule newSchedule =  scheduleService.updateScheduleTimeZone(id, payload);
		if(newSchedule != null) {
			return new ResponseEntity<>(newSchedule, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Schedule details not found");
		}
	}
	
	@DeleteMapping("/schedule/delete/{id}")
	public ResponseEntity<?> deleteScheduleTimeZone(@PathVariable String id) throws Exception {
		String response =  scheduleService.deleteScheduleTimeZoneByID(id);
		if(response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Schedule details not found");
		}
		
	}
}
