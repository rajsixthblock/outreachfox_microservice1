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
import com.companyservice.companyservice.service.TemplateService;

@RestController
@CrossOrigin
@Logging
@Authorization
public class TemplateController {

	@Autowired
	private TemplateService templateService;
	
	@PostMapping("/template/create")
	public ResponseEntity<?> save( @RequestBody @Valid Template payload) throws Exception {
		Template newTemplate = templateService.saveTemplate(payload);
		if(newTemplate.getTemplateId() != null) {
			return new ResponseEntity<>(newTemplate, HttpStatus.CREATED);
		}
		else {
			throw new BadRequestException("Template creation failed");
		}
	}
	
	@GetMapping("/template/getAllTemplates")
	public ResponseEntity<?> getTemplates() throws Exception {
		List<Template> newTemplate =  templateService.getTemplateDetails();
		if(!newTemplate.isEmpty()) {
			return new ResponseEntity<>(newTemplate, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Template details not found");
		}
	}
	
	
	@GetMapping("/template/getByID/{id}")
	public ResponseEntity<?> getByTemplateID(@PathVariable String id) throws Exception {
		Optional<Template> newTemplate =  templateService.getTemplateID(id);
		if(!newTemplate.isEmpty()) {
			return new ResponseEntity<>(newTemplate, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Template details not found");
		}
	}
	
	@PutMapping("/template/update/{templateId}")
	public ResponseEntity<?> updateTemplate(@PathVariable String templateId, @RequestBody Template payload) throws Exception {
		Template newTemplate =  templateService.updateTemplate(templateId, payload);
		if(newTemplate != null) {
			return new ResponseEntity<>(newTemplate, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Template details not found");
		}
	}
	
	@DeleteMapping("/template/delete/{id}")
	public ResponseEntity<?> deleteTemplate(@PathVariable String id) throws Exception {
		String response =  templateService.deleteTemplateByID(id);
		if(response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Template details not found");
		}
		
	}
}
