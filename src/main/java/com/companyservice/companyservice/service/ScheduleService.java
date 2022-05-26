package com.companyservice.companyservice.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.companyservice.companyservice.entity.Schedule;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.repository.ScheduleRepository;


@Service
public class ScheduleService {
	@Autowired
	private ScheduleRepository scheduleRepository;
	
	public Schedule creation(Schedule payload) throws Exception {
		if(scheduleRepository.existsByTimeZone(payload.getTimeZone())) {
			throw new BadRequestException("Time-Zone already exist with this time zone.");
		}else {
			Schedule newScheduleTimeZone = new Schedule();
			try {
				newScheduleTimeZone = scheduleRepository.save(payload);
			}catch(Exception e) {
				if(e instanceof DataIntegrityViolationException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
			return newScheduleTimeZone;
		}
	}
	
	
	public List<Schedule> getScheduleTimeZonesDetails() throws Exception {
		try {
			List<Schedule> scheduleDetails = (List<Schedule>) scheduleRepository.findAll();
			return scheduleDetails;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}
	
	public Optional<Schedule> getScheduleTimeZoneByID(String id) throws Exception {
		try {
			Optional<Schedule> scheduleDetails = scheduleRepository.findById(id);
			return scheduleDetails;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}

	public Schedule updateScheduleTimeZone(String id, Schedule payload) throws Exception {
		if(scheduleRepository.existsById(id)) {
			Schedule scheduleDetails = scheduleRepository.getById(id);
			if(scheduleDetails.getTimeZone().equals(payload.getTimeZone())){
				scheduleDetails = setsubscriptionData(payload, scheduleDetails);
			} 
			else {
				if(scheduleRepository.existsByTimeZone(payload.getTimeZone())) {
					throw new BadRequestException("Time-Zone already exist with this time zone.");
				}else {
					scheduleDetails = setsubscriptionData(payload, scheduleDetails);
				}
			}
			
			try {
				scheduleDetails = scheduleRepository.save(scheduleDetails);
				return scheduleDetails;
			}
			catch(Exception e){
				if(e instanceof SQLException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}else {
				throw new DetailsNotFound("Time-Zone details not found.!"); 
			}
		return null;
	}

	private Schedule setsubscriptionData(Schedule payload, Schedule scheduleDetails) {
		if(payload.getTimeZone() != null) {
			scheduleDetails.setTimeZone(payload.getTimeZone());
		}
		if(payload.getTime() != null) {
			scheduleDetails.setTime(payload.getTime());
		}
		if(payload.isStatus()) {
			scheduleDetails.setStatus(payload.isStatus());
		}
		else if(!payload.isStatus()) {
			scheduleDetails.setStatus(payload.isStatus());
		}
		return scheduleDetails;
	}


	public String deleteScheduleTimeZoneByID(String id) throws Exception {
		if(scheduleRepository.existsById(id)) {
			try {
				scheduleRepository.deleteById(id);
				return "Time-Zone deleted successfully.";
			}
			catch(Exception e){
				if(e instanceof SQLException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}
		else 
			throw new DetailsNotFound("Time-Zone does not exist on file."); 
			return null;
	}
}
