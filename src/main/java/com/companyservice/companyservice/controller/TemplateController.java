package com.companyservice.companyservice.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.Authorization.authorizationservice.annotation.Authorization;
import com.Authorization.authorizationservice.annotation.Logging;
import com.companyservice.companyservice.entity.*;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.service.TemplateService;

import net.minidev.json.JSONObject;

@RestController
@CrossOrigin
@Logging

public class TemplateController {

	@Autowired
	private TemplateService templateService;
	
	@Authorization
	@PostMapping("/template/create")
	public ResponseEntity<?> save( @RequestBody @Valid Template payload) throws Exception {
		Template newTemplate = templateService.saveTemplate(payload);
		if(newTemplate != null) {
			return new ResponseEntity<>(newTemplate, HttpStatus.CREATED);
		}
		else {
			throw new BadRequestException("Template creation failed");
		}
	}
	
	@Authorization
	@GetMapping("/template/getAllTemplates/{page}/{limit}")
	public ResponseEntity<?> getTemplates(@PathVariable int page,@PathVariable int limit) throws Exception {
		if(page == 0) {
			page = 1;
		}
		if(limit == 0) {
			limit = 10;
		}
		//List<Template> newTemplate =  templateService.getTemplateDetails(page, limit);
		Page<Template> newTemplate =  templateService.getTemplateDetails(page, limit);
		if(newTemplate != null) {
			return new ResponseEntity<>(newTemplate, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Template details not found");
		}
	}
	@Authorization
	@GetMapping("/template/admintemplates/{adminId}/{page}/{limit}")
	public ResponseEntity<?> adminTemplates(@PathVariable String adminId, @PathVariable int page,@PathVariable int limit) throws Exception {
		if(page == 0) {
			page = 1;
		}
		if(limit == 0) {
			limit = 10;
		}
		//List<Template> newTemplate =  templateService.getTemplateDetails(page, limit);
		Page<Template> newTemplate =  templateService.adminTemplates(adminId,page, limit);
		if(newTemplate != null) {
			return new ResponseEntity<>(newTemplate, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Template details not found");
		}
	}
	@Authorization
	@GetMapping("/template/admintemplates/{page}/{limit}")
	public ResponseEntity<?> adminTemplate( @PathVariable int page,@PathVariable int limit) throws Exception {
		if(page == 0) {
			page = 1;
		}
		if(limit == 0) {
			limit = 10;
		}
		//List<Template> newTemplate =  templateService.getTemplateDetails(page, limit);
		Page<Template> newTemplate =  templateService.adminTemplate(page, limit);
		if(newTemplate != null) {
			return new ResponseEntity<>(newTemplate, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Template details not found");
		}
	}
	@Authorization
	@GetMapping("/template/dropdowns/{companyId}")
	public ResponseEntity<?> companyTemplatesAndAdminTemplates(@PathVariable String companyId) throws Exception {
		//List<Template> newTemplate =  templateService.getTemplateDetails(page, limit);
		List<Template> newTemplate =  templateService.companyTemplatesAndAdminTemplates(companyId);
		if(newTemplate != null) {
			return new ResponseEntity<>(newTemplate, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Template details not found");
		}
	}
	@Authorization
	@GetMapping("/template/companytemplates/{companyId}/{page}/{limit}")
	public ResponseEntity<?> companyTemplates(@PathVariable String companyId, @PathVariable int page,@PathVariable int limit) throws Exception {
		if(page == 0) {
			page = 1;
		}
		if(limit == 0) {
			limit = 10;
		}
		//List<Template> newTemplate =  templateService.getTemplateDetails(page, limit);
		Page<Template> newTemplate =  templateService.companyTemplates(companyId,page, limit);
		if(newTemplate != null) {
			return new ResponseEntity<>(newTemplate, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Template details not found");
		}
	}
	
	@Authorization
	@GetMapping("/template/getAllTemplates")
	public ResponseEntity<?> getTemplates() throws Exception {
		
		List<Template> newTemplate =  templateService.getTemplateDetails();
		if(newTemplate != null) {
			return new ResponseEntity<>(newTemplate, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Template details not found");
		}
	}
	
	@Authorization
	@GetMapping("/template/getByID/{id}")
	public ResponseEntity<?> getByTemplateID(@PathVariable String id) throws Exception {
		Template newTemplate =  templateService.getTemplateID(id);
		if(newTemplate != null) {
			return new ResponseEntity<>(newTemplate, HttpStatus.OK);
		}
		else {
			throw new DetailsNotFound("Template details not found");
		}
	}
	
	@Authorization
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
	
	@Authorization
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
	
	@Authorization
	@PostMapping("/template/image")
	public ResponseEntity<?> save( @RequestParam("file") MultipartFile file) throws Exception{
		String fileName = templateService.saveImage(file);
		if(fileName != null) {
			return new ResponseEntity<>(fileName, HttpStatus.CREATED);
		}else {
			throw new BadRequestException("Image data storing failed.!");
		}
	}
	
	@GetMapping("/template/image/view/{imageName}")
	@ResponseBody
	public ResponseEntity<Resource> viewFile(@PathVariable String imageName,HttpServletRequest request) throws IOException {
				Resource resource =templateService.viewTemplateImage(imageName);
				 String contentType = null;
			        try {
			            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			        } catch (IOException ex) {
			            //logger.info("Could not determine file type.");
			        }

			        // Fallback to the default content type if type could not be determined
			        if(contentType == null) {
			            contentType = "application/octet-stream";
			        }
				return ResponseEntity.ok()
						 .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
	}
}
