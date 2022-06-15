package com.companyservice.companyservice.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.companyservice.companyservice.entity.*;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.repository.MailStatusRepository;

@Service
public class MailStatusService {
	@Autowired
	private MailStatusRepository mailStatusRepository;
	
	public MailStatus saveMailStatus(MailStatus payload) throws Exception {
		MailStatus newMailStatus= new MailStatus();
		
		try {
			newMailStatus = mailStatusRepository.save(payload);
		}catch(Exception e) {
			if(e instanceof DataIntegrityViolationException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return newMailStatus;
	}
	
	
	public List<MailStatus> getMailStatusDetails() throws Exception {
		try {
			List<MailStatus> mailStatusDetails = (List<MailStatus>) mailStatusRepository.findAll();
			return mailStatusDetails;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}
	
	
	public Optional<MailStatus> getMailStatusID(String id) throws Exception {
		try {
			Optional<MailStatus> mailStatusDetails = mailStatusRepository.findById(id);
			return mailStatusDetails;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}

	public MailStatus updateMailStatus(String mailStatusId, MailStatus payload) throws Exception {
		if(mailStatusRepository.existsById(mailStatusId)) {
			MailStatus MailStatusDetails = mailStatusRepository.getById(mailStatusId);
			
			if(payload.getAudienceId() != null) {
				MailStatusDetails.setAudienceId(payload.getAudienceId());
			}
			if(payload.getCampaignId() != null) {
				MailStatusDetails.setCampaignId(payload.getCampaignId());
			}
			if((payload.getSent()) || (!payload.getSent())) {
				MailStatusDetails.setSent(payload.getSent());
			}
			if((payload.getRead()) || (!payload.getRead())) {
				MailStatusDetails.setRead(payload.getRead());
			}
			if((payload.getBounced()) || (!payload.getBounced())) {
				MailStatusDetails.setBounced(payload.getBounced());
			}
			if((payload.getReplied()) || (!payload.getReplied())) {
				MailStatusDetails.setReplied(payload.getReplied());
			}
			if((payload.getPending()) || (!payload.getPending())) {
				MailStatusDetails.setPending(payload.getPending());
			}
			if((payload.getUnsubscribed()) || (!payload.getUnsubscribed())) {
				MailStatusDetails.setUnsubscribed(payload.getUnsubscribed());
			}
			if((payload.getError()) || (!payload.getError())) {
				MailStatusDetails.setError(payload.getError());
			}
			if(payload.getSentDate() != null) {
				MailStatusDetails.setSentDate(payload.getSentDate());
			}
				
			try {
				MailStatusDetails = mailStatusRepository.save(MailStatusDetails);
				return MailStatusDetails;
			}
			catch(Exception e){
				if(e instanceof SQLException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}
		else {
			throw new DetailsNotFound("MailStatus details not found.!"); 
		}
		return null;
	}

	public String deleteMailStatusByID(String id) throws Exception {
		if(mailStatusRepository.existsById(id)) {
			try {
				mailStatusRepository.deleteById(id);
				return "MailStatus deleted successfully.";
			}
			catch(Exception e){
				if(e instanceof SQLException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}
		else 
			throw new DetailsNotFound("MailStatus does not exist on file."); 
			return null;
	}


	public Optional<MailStatus> getLeadDetailsByCampaignId(String campaignId) throws Exception {
		try {
			Company company = new Company();
			company.setCompanyId(campaignId);
			Optional<MailStatus> MailStatusDetails = mailStatusRepository.findByCampaginId(company);
			return MailStatusDetails;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}

}
