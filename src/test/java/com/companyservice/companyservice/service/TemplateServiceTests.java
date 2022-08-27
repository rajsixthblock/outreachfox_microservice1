package com.companyservice.companyservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.NestedRuntimeException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import com.companyservice.companyservice.entity.AdminUser;
import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.Payment;
import com.companyservice.companyservice.entity.Subscription;
import com.companyservice.companyservice.entity.Template;
import com.companyservice.companyservice.entity.User;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.repository.TemplateRepository;

import net.minidev.json.JSONObject;
@ExtendWith(MockitoExtension.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes= {TemplateServiceTests.class})
public class TemplateServiceTests {
	@Mock
	TemplateRepository templateRepository;
	@InjectMocks
	TemplateService templateService;
	
	@Test
	public void test_saveTemplate() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Template newTemplate= new Template();
		newTemplate.setCompanyId(companyDetails);
		newTemplate.setAdminId(null);
		newTemplate.setName("Template_1");
		newTemplate.setStatus(true);
		newTemplate.setTemplateData(new JSONObject());
		newTemplate.setTemplateId("111a");
		
		String[] list = {"111", null};
		//assertEquals(companyDetails.getCompanyId(),user1.getCompanyId().getCompanyId());
		for(int i= 0; i< list.length; i++) {
			if(list[i] == "111") {
				when(templateRepository.existsByName(newTemplate.getName())).thenReturn(false);
				when(templateRepository.save(newTemplate)).thenReturn(newTemplate);
				Template res = templateService.saveTemplate(newTemplate);
				assertEquals(newTemplate.getTemplateId(),newTemplate.getTemplateId());
			}else {
				when(templateRepository.existsByName(newTemplate.getName())).thenReturn(false);
				when(templateRepository.save(newTemplate)).thenThrow(new NestedRuntimeException("Exception occured") {});
				//Template res = templateService.saveTemplate(newTemplate);
				Throwable exception = assertThrows(
						Exception.class, () -> {
							templateService.saveTemplate(newTemplate);
						           }
						   );
						assertEquals("Exception occured", exception.getMessage());
			}
		}
	}
	@Test
	public void test_badRequestException() {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Template newTemplate= new Template();
		newTemplate.setCompanyId(companyDetails);
		newTemplate.setAdminId(null);
		newTemplate.setName("Template_1");
		newTemplate.setStatus(true);
		newTemplate.setTemplateData(new JSONObject());
		newTemplate.setTemplateId("111a");
		when(templateRepository.existsByName(newTemplate.getName())).thenReturn(true);
		boolean value = true;
		if(value) {
			when(templateRepository.existsByName(newTemplate.getName())).thenReturn(true);
			Throwable ex = assertThrows(
					BadRequestException.class, () -> {
						templateService.saveTemplate(newTemplate);
			      });
			   assertEquals("Template details already exist with this name.", ex.getMessage());
			   
		}
	}
	@Test
	public void test_getTemplateDetails() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		List<Template> list = new ArrayList();
		Template newTemplate= new Template();
		newTemplate.setCompanyId(companyDetails);
		newTemplate.setAdminId(null);
		newTemplate.setName("Template_1");
		newTemplate.setStatus(true);
		newTemplate.setTemplateData(new JSONObject());
		newTemplate.setTemplateId("111a");
		list.add(newTemplate);
		list.add(newTemplate);
		
		String[] list_1 = {"111", null};
		//assertEquals(companyDetails.getCompanyId(),user1.getCompanyId().getCompanyId());
		for(int i= 0; i< list_1.length; i++) {
			if(list_1[i] == "111") {
				when(templateRepository.findAll()).thenReturn(list);
				List<Template> res = templateService.getTemplateDetails();
				assertEquals(list.size(),res.size());
			}else {
				when(templateRepository.findAll()).thenThrow(new NestedRuntimeException("Exception occured") {});
				//List<Template> res = templateService.getTemplateDetails();
				Throwable exception = assertThrows(
						Exception.class, () -> {
							templateService.getTemplateDetails();
						           }
						   );
						assertEquals("Exception occured", exception.getMessage());
			}
		}
	}
	@Test
	public void test_adminTemplate() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		List<Template> list = new ArrayList();
		Template newTemplate= new Template();
		newTemplate.setCompanyId(companyDetails);
		newTemplate.setAdminId(null);
		newTemplate.setName("Template_1");
		newTemplate.setStatus(true);
		newTemplate.setTemplateData(new JSONObject());
		newTemplate.setTemplateId("111a");
		list.add(newTemplate);
		list.add(newTemplate);
		Pageable paging = PageRequest.of(1, 10);
		Page<Template> emptyPage = null;
		Page<Template> pageDetails = new PageImpl<>(list);
		String[] list_1 = {"111", null};
		//assertEquals(companyDetails.getCompanyId(),user1.getCompanyId().getCompanyId());
		for(int i= 0; i< list_1.length; i++) {
			if(list_1[i] == "111") {
				when(templateRepository.getByAdminId(Mockito.any(Pageable.class))).thenReturn(pageDetails);
				Page<Template> res = templateService.adminTemplate(1,10);
				assertEquals(list.size(),pageDetails.getContent().size());
			}else {
				paging = PageRequest.of(1, 10);
				when(templateRepository.getByAdminId(Mockito.any(Pageable.class))).thenThrow(new NestedRuntimeException("Exception occured") {});
				//List<Template> res = templateService.getTemplateDetails();
				Throwable exception = assertThrows(
						Exception.class, () -> {
							templateService.adminTemplate(1,10);
						           }
						   );
						assertEquals("Exception occured", exception.getMessage());
			}
		}
	}
//	@Test
//	public void test_companyTemplatesAndAdminTemplates() throws Exception {
//		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
//		List<Template> list_1 = new ArrayList();
//		Template newTemplate= new Template();
//		newTemplate.setCompanyId(companyDetails);
//		newTemplate.setAdminId(null);
//		newTemplate.setName("Template_1");
//		newTemplate.setStatus(true);
//		newTemplate.setTemplateData(new JSONObject());
//		newTemplate.setTemplateId("111a");
//		list_1.add(newTemplate);
//		list_1.add(newTemplate);
//		List<Template> list_2 = new ArrayList();
//		AdminUser adminUser = new AdminUser();
//		adminUser.setAdminId("111a");
//		adminUser.setEmail("admin@gmail.com");
//		adminUser.setName("Admin");
//		adminUser.setPassword("admin");
//		adminUser.setPhone(0000000000);
//		adminUser.setStatus(true);
//		Template newTemplate_2= new Template();
//		newTemplate_2.setCompanyId(null);
//		newTemplate_2.setAdminId(adminUser);
//		newTemplate_2.setName("Template_1");
//		newTemplate_2.setStatus(true);
//		newTemplate_2.setTemplateData(new JSONObject());
//		newTemplate_2.setTemplateId("111a");
//		list_2.add(newTemplate_2);
//		list_2.add(newTemplate_2);
//		JSONObject templates = new JSONObject();
//		templates.appendField("companyTemplate", list_1);
//		templates.appendField("adminTemplates", list_2);
//		when(templateRepository.getByCompanyId(companyDetails)).thenReturn(list_1);
//		when(templateRepository.getByAdminId()).thenReturn(list_2);
//		JSONObject res = templateService.companyTemplatesAndAdminTemplates(companyDetails.getCompanyId());
//		assertEquals(res.containsKey("companyTemplate"),true);
//		assertEquals(res.containsKey("adminTemplates"),true);
//	}
	@Test
	public void test_getTemplateID() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Template newTemplate= new Template();
		newTemplate.setCompanyId(companyDetails);
		newTemplate.setAdminId(null);
		newTemplate.setName("Template_1");
		newTemplate.setStatus(true);
		newTemplate.setTemplateData(new JSONObject());
		newTemplate.setTemplateId("111a");
		
		String[] list_1 = {"111", null};
		//assertEquals(companyDetails.getCompanyId(),user1.getCompanyId().getCompanyId());
		for(int i= 0; i< list_1.length; i++) {
			if(list_1[i] == "111") {
				when(templateRepository.getById(newTemplate.getTemplateId())).thenReturn(newTemplate);
				Template res = templateService.getTemplateID(newTemplate.getTemplateId());
				assertEquals(res.getTemplateId(),newTemplate.getTemplateId());
			}else {
				when(templateRepository.getById(newTemplate.getTemplateId())).thenThrow(new NestedRuntimeException("Exception occured") {});
				Throwable exception = assertThrows(
						Exception.class, () -> {
							templateService.getTemplateID(newTemplate.getTemplateId());
						           }
						   );
						assertEquals("Exception occured", exception.getMessage());
			}
		}
	}
	@Test
	public void test_updateTemplate() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Template newTemplate= new Template();
		newTemplate.setCompanyId(companyDetails);
		newTemplate.setAdminId(null);
		newTemplate.setName("Template_1");
		newTemplate.setStatus(true);
		newTemplate.setTemplateData(new JSONObject());
		newTemplate.setTemplateId("111a");
		templateService.setTemplateData(newTemplate, newTemplate);
		when(templateRepository.existsById(newTemplate.getTemplateId())).thenReturn(true);
		when(templateRepository.getById(newTemplate.getTemplateId())).thenReturn(newTemplate);
//		when(templateRepository.existsByName(newTemplate.getName())).thenReturn(false);
		when(templateRepository.save(newTemplate)).thenReturn(newTemplate);
		Template res = templateService.updateTemplate(newTemplate.getTemplateId(),newTemplate);
		assertEquals(res.getTemplateId(),newTemplate.getTemplateId());
	}
	@Test
	public void test_deleteTemplateByID() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Template newTemplate= new Template();
		newTemplate.setCompanyId(companyDetails);
		newTemplate.setAdminId(null);
		newTemplate.setName("Template_1");
		newTemplate.setStatus(true);
		newTemplate.setTemplateData(new JSONObject());
		newTemplate.setTemplateId("111a");
		//templateService.setTemplateData(newTemplate, newTemplate);
		
		String[] list_1 = {"111", null};
		//assertEquals(companyDetails.getCompanyId(),user1.getCompanyId().getCompanyId());
		for(int i= 0; i< list_1.length; i++) {
			if(list_1[i] == "111") {
				when(templateRepository.existsById(newTemplate.getTemplateId())).thenReturn(true);
				templateRepository.deleteById(newTemplate.getTemplateId());
				String res = templateService.deleteTemplateByID(newTemplate.getTemplateId());
				assertEquals(res,"Template deleted successfully.");
			}else {
				when(templateRepository.existsById(newTemplate.getTemplateId())).thenReturn(true);
				//templateRepository.deleteById(newTemplate.getTemplateId());
				doThrow(new NestedRuntimeException("Exception occured") {}).when(templateRepository).deleteById("111a");
				Throwable exception = assertThrows(
					Exception.class, () -> {
						templateService.deleteTemplateByID(newTemplate.getTemplateId());
					           }
					   );
					assertEquals("Exception occured", exception.getMessage());
			}
		}
	}
	@Test
	public void test_dataNotFoundExceptionForDelete() {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Template newTemplate= new Template();
		newTemplate.setCompanyId(companyDetails);
		newTemplate.setAdminId(null);
		newTemplate.setName("Template_1");
		newTemplate.setStatus(true);
		newTemplate.setTemplateData(new JSONObject());
		newTemplate.setTemplateId("111a");
		
		when(templateRepository.existsById(newTemplate.getTemplateId())).thenReturn(false);
		boolean value = true;
		if(value == true) {
			when(templateRepository.existsById(newTemplate.getTemplateId())).thenReturn(false);
			//userRepository.deleteById(userDetails.getUserId());
			Throwable ex = assertThrows(
					DetailsNotFound.class, () -> {
						templateService.deleteTemplateByID(newTemplate.getTemplateId());
			      });
			   assertEquals("Template details not exist on file.", ex.getMessage());
			   
		}
	}
}
