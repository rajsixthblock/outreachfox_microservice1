package com.companyservice.companyservice.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.companyservice.companyservice.entity.AdminUser;
import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.Template;
import com.companyservice.companyservice.entity.User;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.service.TemplateService;

import net.minidev.json.JSONObject;
@ExtendWith(MockitoExtension.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes= {TemplateControllerTests.class})
public class TemplateControllerTests {
	@Mock
	TemplateService templateService;
	
	@InjectMocks
	TemplateController templateController;
	@Mock
	MultipartFile file;
	@Mock
	HttpServletRequest request;
	@Test
	public void test_save() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		AdminUser userDetails = new AdminUser("111", "Bhuvan", 0, "bhuvan@gmail.com", "password", true, null, null);
		Template template = new Template();
		template.setCompanyId(companyDetails);
		template.setName("Basic");
		template.setTemplateData(null);
		template.setTemplateId("111a");
		template.setStatus(true);
		template.setAdminId(userDetails);
		
		String[] ids = {"11a", null};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != null) {
				when(templateService.saveTemplate(template)).thenReturn(template);
				ResponseEntity response =   templateController.save(template);
				assertEquals(HttpStatus.CREATED, response.getStatusCode());
			}else {
				int id = i;
				when(templateService.saveTemplate(template)).thenReturn(null);
				Throwable ex = assertThrows(
						BadRequestException.class, () -> {
							templateController.save(template);
				      });
				   assertEquals("Template creation failed", ex.getMessage());
			}
		}
	}
//	@Test
//	public void test_companyTemplatesAndAdminTemplates() throws Exception {
//		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
//		AdminUser userDetails = new AdminUser("111", "Bhuvan", 0, "bhuvan@gmail.com", "password", true, null, null);
//		Template template = new Template();
//		template.setCompanyId(companyDetails);
//		template.setName("Basic");
//		template.setTemplateData(null);
//		template.setTemplateId("111a");
//		template.setStatus(true);
//		template.setAdminId(userDetails);
//		JSONObject result = new JSONObject();
//		
//		String[] ids = {"11a", null};
//		int i = 0;
//		for(i = 0; i < ids.length; i++) {
//			if(ids[i] != null) {
//				when(templateService.companyTemplatesAndAdminTemplates(companyDetails.getCompanyId())).thenReturn(result);
//				ResponseEntity response =   templateController.companyTemplatesAndAdminTemplates(companyDetails.getCompanyId());
//				assertEquals(HttpStatus.OK, response.getStatusCode());
//			}else {
//				int id = i;
//				when(templateService.companyTemplatesAndAdminTemplates(companyDetails.getCompanyId())).thenReturn(null);
//				Throwable ex = assertThrows(
//						DetailsNotFound.class, () -> {
//							templateController.companyTemplatesAndAdminTemplates(companyDetails.getCompanyId());
//				      });
//				   assertEquals("Template details not found", ex.getMessage());
//			}
//		}
//	}
	@Test
	public void test_getTemplates() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		AdminUser userDetails = new AdminUser("111", "Bhuvan", 0, "bhuvan@gmail.com", "password", true, null, null);
		Template template = new Template();
		template.setCompanyId(companyDetails);
		template.setName("Basic");
		template.setTemplateData(null);
		template.setTemplateId("111a");
		template.setStatus(true);
		template.setAdminId(userDetails);
		List<Template> result = new ArrayList();
		result.add(template);
		result.add(template);
		String[] ids = {"11a", null};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != null) {
				when(templateService.getTemplateDetails()).thenReturn(result);
				ResponseEntity response =   templateController.getTemplates();
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				when(templateService.getTemplateDetails()).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							templateController.getTemplates();
				      });
				   assertEquals("Template details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_getByTemplateID() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		AdminUser userDetails = new AdminUser("111", "Bhuvan", 0, "bhuvan@gmail.com", "password", true, null, null);
		Template template = new Template();
		template.setCompanyId(companyDetails);
		template.setName("Basic");
		template.setTemplateData(null);
		template.setTemplateId("111a");
		template.setStatus(true);
		template.setAdminId(userDetails);
		
		String[] ids = {"11a", null};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != null) {
				when(templateService.getTemplateID(template.getTemplateId())).thenReturn(template);
				ResponseEntity response =   templateController.getByTemplateID(template.getTemplateId());
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				when(templateService.getTemplateID(template.getTemplateId())).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							 templateController.getByTemplateID(template.getTemplateId());
				      });
				   assertEquals("Template details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_updateTemplate() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		AdminUser userDetails = new AdminUser("111", "Bhuvan", 0, "bhuvan@gmail.com", "password", true, null, null);
		Template template = new Template();
		template.setCompanyId(companyDetails);
		template.setName("Basic");
		template.setTemplateData(null);
		template.setTemplateId("111a");
		template.setStatus(true);
		template.setAdminId(userDetails);
		
		String[] ids = {"11a", null};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != null) {
				when(templateService.updateTemplate(template.getTemplateId(),template)).thenReturn(template);
				ResponseEntity response =  templateController.updateTemplate(template.getTemplateId(),template);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				when(templateService.updateTemplate(template.getTemplateId(),template)).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							templateController.updateTemplate(template.getTemplateId(),template);
				      });
				   assertEquals("Template details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_deleteTemplate() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		AdminUser userDetails = new AdminUser("111", "Bhuvan", 0, "bhuvan@gmail.com", "password", true, null, null);
		Template template = new Template();
		template.setCompanyId(companyDetails);
		template.setName("Basic");
		template.setTemplateData(null);
		template.setTemplateId("111a");
		template.setStatus(true);
		template.setAdminId(userDetails);
		
		String[] ids = {"11a", null};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != null) {
				when(templateService.deleteTemplateByID(template.getTemplateId())).thenReturn("Successfull");
				ResponseEntity response =   templateController.deleteTemplate(template.getTemplateId());
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				when(templateService.deleteTemplateByID(template.getTemplateId())).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							templateController.deleteTemplate(template.getTemplateId());
				      });
				   assertEquals("Template details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_save_file() throws Exception {
		String link = "http://localhost:8080/img.jpg";
		
		String[] ids = {"11a", null};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != null) {
				when(templateService.saveImage(file)).thenReturn(link);
				ResponseEntity response =   templateController.save(file);
				assertEquals(HttpStatus.CREATED, response.getStatusCode());
			}else {
				int id = i;
				when(templateService.saveImage(file)).thenReturn(null);
				Throwable ex = assertThrows(
						BadRequestException.class, () -> {
							templateController.save(file);
				      });
				   assertEquals("Image data storing failed.!", ex.getMessage());
			}
		}
	}
	@Test
	public void test_view_file() throws Exception {
		
		String link = "http://localhost:8080/img.jpg";
		Resource resource = new UrlResource(link);
		when(templateService.viewTemplateImage("img.jpg")).thenReturn(resource);
		ResponseEntity response =   templateController.viewFile("img.jpg", request);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
	}
	@Test
	public void test_adminTemplates() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		AdminUser userDetails = new AdminUser("111", "Bhuvan", 0, "bhuvan@gmail.com", "password", true, null, null);
		Template template = new Template();
		template.setCompanyId(companyDetails);
		template.setName("Basic");
		template.setTemplateData(null);
		template.setTemplateId("111a");
		template.setStatus(true);
		template.setAdminId(userDetails);
		List<Template> result = new ArrayList();
		result.add(template);
		result.add(template);
		Page<Template> emptyPage = null;
		Page<Template> pageDetails = new PageImpl<>(result);
		List res = new ArrayList();
		res.add(pageDetails);
		res.add(emptyPage);
		int i = 0;
		for(i = 0; i < res.size(); i++) {
			if(res.get(i)!= null) {
				when(templateService.adminTemplates(userDetails.getAdminId(), 1, 10)).thenReturn(pageDetails);
				ResponseEntity response = templateController.adminTemplates(userDetails.getAdminId(), 1, 10);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				when(templateService.adminTemplates(userDetails.getAdminId(), 1, 10)).thenReturn(emptyPage);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							templateController.adminTemplates(userDetails.getAdminId(), 0, 0);
				      });
				   assertEquals("Template details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_adminTemplatesWithCompanyId() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		AdminUser userDetails = new AdminUser("111", "Bhuvan", 0, "bhuvan@gmail.com", "password", true, null, null);
		Template template = new Template();
		template.setCompanyId(companyDetails);
		template.setName("Basic");
		template.setTemplateData(null);
		template.setTemplateId("111a");
		template.setStatus(true);
		template.setAdminId(userDetails);
		List<Template> result = new ArrayList();
		result.add(template);
		result.add(template);
		Page<Template> emptyPage = null;
		Page<Template> pageDetails = new PageImpl<>(result);
		List res = new ArrayList();
		res.add(pageDetails);
		res.add(emptyPage);
		int i = 0;
		for(i = 0; i < res.size(); i++) {
			if(res.get(i)!= null) {
				when(templateService.adminTemplate( 1, 10)).thenReturn(pageDetails);
				ResponseEntity response = templateController.adminTemplate( 1, 10);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				when(templateService.adminTemplate( 1, 10)).thenReturn(emptyPage);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							templateController.adminTemplate( 0, 0);
				      });
				   assertEquals("Template details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_getTemplatesWithPagination() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		AdminUser userDetails = new AdminUser("111", "Bhuvan", 0, "bhuvan@gmail.com", "password", true, null, null);
		Template template = new Template();
		template.setCompanyId(companyDetails);
		template.setName("Basic");
		template.setTemplateData(null);
		template.setTemplateId("111a");
		template.setStatus(true);
		template.setAdminId(userDetails);
		List<Template> result = new ArrayList();
		result.add(template);
		result.add(template);
		Page<Template> emptyPage = null;
		Page<Template> pageDetails = new PageImpl<>(result);
		List res = new ArrayList();
		res.add(pageDetails);
		res.add(emptyPage);
		int i = 0;
		for(i = 0; i < res.size(); i++) {
			if(res.get(i)!= null) {
				when(templateService.getTemplateDetails( 1, 10)).thenReturn(pageDetails);
				ResponseEntity response = templateController.getTemplates( 1, 10);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				when(templateService.getTemplateDetails( 1, 10)).thenReturn(emptyPage);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							templateController.getTemplates( 0, 0);
				      });
				   assertEquals("Template details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_companyTemplatesWithPagination() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		AdminUser userDetails = new AdminUser("111", "Bhuvan", 0, "bhuvan@gmail.com", "password", true, null, null);
		Template template = new Template();
		template.setCompanyId(companyDetails);
		template.setName("Basic");
		template.setTemplateData(null);
		template.setTemplateId("111a");
		template.setStatus(true);
		template.setAdminId(userDetails);
		List<Template> result = new ArrayList();
		result.add(template);
		result.add(template);
		Page<Template> emptyPage = null;
		Page<Template> pageDetails = new PageImpl<>(result);
		List res = new ArrayList();
		res.add(pageDetails);
		res.add(emptyPage);
		int i = 0;
		for(i = 0; i < res.size(); i++) {
			if(res.get(i)!= null) {
				when(templateService.companyTemplates( companyDetails.getCompanyId(),1, 10)).thenReturn(pageDetails);
				ResponseEntity response = templateController.companyTemplates(companyDetails.getCompanyId(), 1, 10);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				when(templateService.companyTemplates( companyDetails.getCompanyId(), 1, 10)).thenReturn(emptyPage);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							templateController.companyTemplates( companyDetails.getCompanyId(),0, 0);
				      });
				   assertEquals("Template details not found", ex.getMessage());
			}
		}
	}
}
