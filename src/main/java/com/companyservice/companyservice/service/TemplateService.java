package com.companyservice.companyservice.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.companyservice.companyservice.entity.*;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.repository.TemplateRepository;

import net.minidev.json.JSONObject;


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
				//if(e instanceof DataIntegrityViolationException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
				//}
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
			//if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			//}
		}
		//return null;
	}
	public Page<Template> getTemplateDetails(int pageNo, int pageSize) throws Exception {
		Pageable paging = PageRequest.of(pageNo-1, pageSize);
		try {
			Page<Template> templateDetails =  templateRepository.findAllByOrderByUpdatedAtDesc(paging);
			return templateDetails;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}
	public Page<Template> adminTemplates(String adminId,int pageNo, int pageSize) throws Exception {
		Pageable paging = PageRequest.of(pageNo-1, pageSize);
		AdminUser user = new AdminUser();
		user.setAdminId(adminId);
		try {
			Page<Template> templateDetails =  templateRepository.findByAdminIdOrderByUpdatedAtDesc(user,paging);
			return templateDetails;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}
	public Page<Template> adminTemplate(int pageNo, int pageSize) throws Exception {
		Pageable paging = PageRequest.of(pageNo-1, pageSize);
		AdminUser user = new AdminUser();
		//user.setAdminId(adminId);
		try {
			Page<Template> templateDetails =  templateRepository.getByAdminId(paging);
			return templateDetails;
		}
		catch(Exception e){
			//if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			//}
		}
		//return null;
	}
	public List<Template> companyTemplatesAndAdminTemplates(String companyId) throws Exception {
		//Pageable paging = PageRequest.of(pageNo-1, pageSize);
		Company company = new Company();
		company.setCompanyId(companyId);
		try {
			List<Template> companyAdminTemplates =  templateRepository.getByCompanyId(company);
//			List<Template> adminTemplates =  templateRepository.getByAdminId();
			return companyAdminTemplates;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}
	public Page<Template> companyTemplates(String companyId,int pageNo, int pageSize) throws Exception {
		Pageable paging = PageRequest.of(pageNo-1, pageSize);
		Company company = new Company();
		company.setCompanyId(companyId);
		try {
			Page<Template> templateDetails =  templateRepository.findByCompanyIdOrderByUpdatedAtDesc(company,paging);
			return templateDetails;
		}
		catch(Exception e){
			if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			}
		}
		return null;
	}
	public Template getTemplateID(String id) throws Exception {
		try {
			Template templateDetails = templateRepository.getById(id);
			return templateDetails;
		}
		catch(Exception e){
			//if(e instanceof SQLException) {
				throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			//}
		}
		//return null;
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

	Template setTemplateData(Template payload, Template TemplateDetails) {
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
			//	if(e instanceof SQLException) {
					throw new Exception(((NestedRuntimeException) e).getMostSpecificCause().getMessage());
			//	}
			}
		}
		else 
			throw new DetailsNotFound("Template details not exist on file."); 
			//return null;
	}
	public String saveImage(MultipartFile file) throws IllegalStateException, IOException {
		String directoryName = new File("..").getCanonicalPath()+"\\file\\uploads\\templateImages";
		File directory = new File(directoryName);
	    if (!directory.exists()){
	    	System.out.println(directory);
	        directory.mkdirs();
	    }
	    int dotIndex = file.getOriginalFilename().lastIndexOf('.');
		String name = (dotIndex == -1) ? file.getOriginalFilename() : file.getOriginalFilename().substring(0, dotIndex);
		String extension = "";
		if (dotIndex > 0) {
		    extension = file.getOriginalFilename().substring(dotIndex+1);
		}
		String fileName = name+"_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"."+extension;
		String path = directory+"\\"+name+"_"+new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"."+extension;
		file.transferTo( new File(path));
		return fileName;
	}
	public Resource viewTemplateImage(String fileName) throws IOException {
		String directoryName = new File("..").getCanonicalPath()+"\\file\\uploads\\templateImages\\";
		File directory = new File(directoryName);
		Path file = Paths.get(directoryName).resolve(fileName).normalize();
		Resource resource = new UrlResource(file.toUri());
		if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("Could not read the file!");
        }
	}
}
