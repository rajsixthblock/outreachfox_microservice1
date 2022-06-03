package com.companyservice.companyservice.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.companyservice.companyservice.entity.*;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.repository.TemplateRepository;


@Service
public class TemplateService {
	@Autowired
	private TemplateRepository templateRepository;
	
	public Template saveTemplate(Template payload) throws Exception {
		Template newTemplate= new Template();
		if(templateRepository.existsByName(payload.getName())) {
			throw new BadRequestException("Template details already exist with this name.");
		}
		else {
			try {
				newTemplate = templateRepository.save(payload);
			}catch(Exception e) {
				if(e instanceof DataIntegrityViolationException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}
		return newTemplate;
	}
	
	public List<Template> getTemplateDetails() throws Exception {
		try {
			List<Template> templateDetails = (List<Template>) templateRepository.findAll();
			return templateDetails;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}
	public List<Template> getTemplateDetails(int pageNo, int pageSize) throws Exception {
		Pageable paging = PageRequest.of(pageNo-1, pageSize);
		try {
			Page<Template> templateDetails =  templateRepository.findAll(paging);
			return templateDetails.toList();
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}
	
	public Optional<Template> getTemplateID(String id) throws Exception {
		try {
			Optional<Template> templateDetails = templateRepository.findById(id);
			return templateDetails;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}
	

	public Template updateTemplate(String templateId, Template payload) throws Exception {
		if(templateRepository.existsById(templateId)) {
			Template TemplateDetails = templateRepository.getById(templateId);
			if(TemplateDetails.getName().equals(payload.getName())) {
				TemplateDetails = setTemplateData(payload, TemplateDetails);
			}
			else {
				if(templateRepository.existsByName(payload.getName())) {
					throw new BadRequestException("Template details already exist with this name.");
				}
				else {
					TemplateDetails = setTemplateData(payload, TemplateDetails);
				}
			}
			
			try {
				TemplateDetails = templateRepository.save(TemplateDetails);
				return TemplateDetails;
			}
			catch(Exception e){
				if(e instanceof SQLException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}
		else {
			throw new DetailsNotFound("Template details not found.!"); 
		}
		return null;
	}

	private Template setTemplateData(Template payload, Template TemplateDetails) {
		if(payload.getName() != null) {
			TemplateDetails.setName(payload.getName());
		}
		if(payload.isStatus()) {
			TemplateDetails.setStatus(payload.isStatus());
		}else if(!payload.isStatus()) {
			TemplateDetails.setStatus(payload.isStatus());
		}
		if(payload.getTemplateData() != null) {
			TemplateDetails.setTemplateData(payload.getTemplateData());
		}
		return TemplateDetails;
	}

	public String deleteTemplateByID(String id) throws Exception {
		if(templateRepository.existsById(id)) {
			try {
				templateRepository.deleteById(id);
				return "Template deleted successfully.";
			}
			catch(Exception e){
				if(e instanceof SQLException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				}
			}
		}
		else 
			throw new DetailsNotFound("Template details not exist on file."); 
			return null;
	}

}
