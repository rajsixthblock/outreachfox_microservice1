package com.companyservice.companyservice.controller;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Authorization.authorizationservice.annotation.Authorization;
import com.Authorization.authorizationservice.annotation.Logging;
import com.companyservice.companyservice.entity.*;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.service.MailStatusService;

@RestController
@CrossOrigin
@Logging
@Authorization
public class MailStatusController {

	@Autowired
	private MailStatusService mailStatusService;
	
	@PostMapping("/mailStatus/create/{audienceId}/{campaignId}")
	public ResponseEntity<?> save(@PathVariable String audienceId, @PathVariable String campaignId, @RequestBody @Valid MailStatus payload) throws Exception {
		if((audienceId == null) || (campaignId == null))
		{
			throw new BadRequestException("Audience Id and Campaign Id are required.");
		}
		else {
			Audience audience = new Audience();
			audience.setId(audienceId);
			payload.setAudienceId(audience);
			
			Campaign campaign = new Campaign();
			campaign.setCampaginId(campaignId);
			payload.setCampaignId(campaign);
		}
		MailStatus newMailStatus = mailStatusService.saveMailStatus(payload);
		if(newMailStatus.getMailStatusId() != null) {
			return new ResponseEntity<>(newMailStatus, HttpStatus.CREATED);
		}
		else {
			throw new BadRequestException("Mail Status creation failed");
		}
	}
	
	@GetMapping("/mailStatus/getAllMailStatus")
	public ResponseEntity<?> getMailStatuss() throws Exception {
		List<MailStatus> newMailStatus =  mailStatusService.getMailStatusDetails();
		if(newMailStatus != null) {
			return new ResponseEntity<>(newMailStatus, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Mail Status details not found");
		}
	}
	
	
	@GetMapping("/mailStatus/getByID/{id}")
	public ResponseEntity<?> getByMailStatusID(@PathVariable String id) throws Exception {
		Optional<MailStatus> newMailStatus =  mailStatusService.getMailStatusID(id);
		if(!newMailStatus.isEmpty()) {
			return new ResponseEntity<>(newMailStatus, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Mail Status details not found");
		}
	}
	
	@GetMapping("/mailStatus/getByCampaignID/{campaignId}")
	public ResponseEntity<?> getLeadsByCampaignID(@PathVariable String campaignId) throws Exception {
		Optional<MailStatus> newMailStatus =  mailStatusService.getLeadDetailsByCampaignId(campaignId);
		if(!newMailStatus.isEmpty()) {
			return new ResponseEntity<>(newMailStatus, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Mail Status details not found");
		}
	}
	
	@PutMapping("/mailStatus/update/{mailStatusId}/{audienceId}/{campaignId}")
	public ResponseEntity<?> updateMailStatus(@PathVariable String mailStatusId, @PathVariable String audienceId,
			@PathVariable String campaignId , @RequestBody MailStatus payload) throws Exception {
		if((audienceId == null) || (campaignId == null))
		{
			throw new BadRequestException("Audience Id and Campaign Id are required.");
		}
		else {
			Audience audience = new Audience();
			audience.setId(audienceId);
			payload.setAudienceId(audience);
			
			Campaign campaign = new Campaign();
			campaign.setCampaginId(campaignId);
			payload.setCampaignId(campaign);
		}
		MailStatus newMailStatus =  mailStatusService.updateMailStatus(mailStatusId, payload);
		if(newMailStatus != null) {
			return new ResponseEntity<>(newMailStatus, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Mail Status details not found");
		}
	}
	
	@DeleteMapping("/mailStatus/delete/{id}")
	public ResponseEntity<?> deleteMailStatus(@PathVariable String id) throws Exception {
		String response =  mailStatusService.deleteMailStatusByID(id);
		if(response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Mail Status details not found");
		}
		
	}
}
