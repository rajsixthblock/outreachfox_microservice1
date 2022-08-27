package com.companyservice.companyservice.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.NestedRuntimeException;
import org.springframework.test.context.junit4.SpringRunner;

import com.companyservice.companyservice.SecurityConfiguration.SecurityConfig;
import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.User;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.repository.AudienceRepository;
import com.companyservice.companyservice.repository.CampaignRepository;
import com.companyservice.companyservice.repository.CompanyRepository;
import com.companyservice.companyservice.repository.UserRepository;

import net.minidev.json.JSONObject;
@ExtendWith(MockitoExtension.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes= {CompanyServiceTests.class})
public class CompanyServiceTests {
	@Mock
	CompanyRepository companyRepository;
	@Mock
	SecurityConfig securityConfig;
	@Mock
	UserRepository userRepository;
	@Mock 
	CampaignRepository campaignRepository;
	@Mock
	AudienceRepository audienceRepository;
	@InjectMocks
	CompanyService companyService;
	@Test
	public void test_companyRegistration() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User user1 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
		when(companyRepository.existsByemail(companyDetails.getEmail())).thenReturn(false);
		String[] list = {"111", ""};
		//assertEquals(companyDetails.getCompanyId(),user1.getCompanyId().getCompanyId());
		for(int i= 0; i< list.length; i++) {
			if(list[i] == "111") {
				when(companyRepository.save(companyDetails)).thenReturn(companyDetails);
				when(userRepository.save(Mockito.any(User.class))).thenReturn(user1);
				User res = companyService.companyRegistration(companyDetails);
				//res.setCompanyId(companyDetails);
				assertEquals(companyDetails.getCompanyId(), user1.getCompanyId().getCompanyId());
			}else {
				int id = i;
				companyDetails.setCompanyId(null);
				companyDetails.setCompanyName(null);
				when(companyRepository.save(companyDetails)).thenThrow(new NestedRuntimeException("Exception occured") {});
				Throwable exception = assertThrows(
				Exception.class, () -> {
					companyService.companyRegistration(companyDetails);
				           }
				   );
				assertEquals("Exception occured", exception.getMessage());
			}
		}
	}
	@Test
	public void test_badRequestException() {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User userDetails = new User();
		userDetails.setCompanyId(companyDetails);
		userDetails.setUserId("111");
		userDetails.setEmail("abc@gmail.com");
		userDetails.setName("abc");
		userDetails.setPassword("abc");
		userDetails.setPhone(0000000000);
		userDetails.setStatus(true);
		when(companyRepository.existsByemail(companyDetails.getEmail())).thenReturn(true);
		boolean value = true;
		if(value) {
			when(companyRepository.existsByemail(companyDetails.getEmail())).thenReturn(true);
			Throwable ex = assertThrows(
					BadRequestException.class, () -> {
						companyService.companyRegistration(companyDetails);
			      });
			   assertEquals("Company already exist with this email", ex.getMessage());
			   
		}
	}
	@Test
	public void test_getCompaniesDetails() throws Exception {
		List<Company> list = new ArrayList();
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		list.add(companyDetails);
		list.add(companyDetails);
		//User user1 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
		
		String[] ids = {"111",""};
		for(int j=0; j< ids.length; j++) {
			if(ids[j] == "111") {
				when(companyRepository.findAll()).thenReturn(list);
				List<Company> res = companyService.getCompaniesDetails();
				assertEquals(res.size(),list.size());
			}else {
				int id = j;
				when(companyRepository.findAll()).thenThrow(new NestedRuntimeException("Exception occured") {});
				Throwable exception = assertThrows(
						Exception.class, () -> {
							companyService.getCompaniesDetails();
				});
				assertEquals("Exception occured", exception.getMessage());
			}
		}
	}
	@Test
	public void test_getCompanyByID() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		when(companyRepository.getById(companyDetails.getCompanyId())).thenReturn(companyDetails);
		Company res = companyService.getCompanyByID(companyDetails.getCompanyId());
		assertEquals(res.getCompanyId(),companyDetails.getCompanyId());
	}
	@Test
	public void test_deleteCompanyByID() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		when(companyRepository.existsById(companyDetails.getCompanyId())).thenReturn(true);
		companyRepository.deleteById(companyDetails.getCompanyId());
		String res = companyService.deleteCompanyByID(companyDetails.getCompanyId());
		assertEquals(res,"Company deleted successfully");
	}
	@Test
	public void test_updateCompany() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		when(companyRepository.existsById(companyDetails.getCompanyId())).thenReturn(true);
		when(companyRepository.getById(companyDetails.getCompanyId())).thenReturn(companyDetails);
		companyService.setcompanyData(companyDetails, companyDetails);
//		when(companyRepository.existsByemail(companyDetails.getEmail())).thenReturn(false);
		when(companyRepository.save(companyDetails)).thenReturn(companyDetails);
		Company res = companyService.updateCompany(companyDetails.getCompanyId(),companyDetails);
		assertEquals(res.getCompanyId(),companyDetails.getCompanyId());
	}
	@Test
	public void test_activateCompany() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		User user1 = new User("111",companyDetails,"userName",0000000000,"userEmail@gmail.com","password",true,null,null);
		when(companyRepository.getById(companyDetails.getCompanyId())).thenReturn(companyDetails);
		when(companyRepository.save(companyDetails)).thenReturn(companyDetails);
		when(userRepository.getById(user1.getUserId())).thenReturn(user1);
		when(userRepository.save(user1)).thenReturn(user1);
		Company res = companyService.activateCompany(companyDetails.getCompanyId(),user1.getUserId());
		assertEquals(res.getCompanyId(),companyDetails.getCompanyId());
	}
	@Test
	public void test_counting() throws Exception {
		List<Company> list = new ArrayList();
		Company companyDetails_1 = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Company companyDetails_2 = new Company("112", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		list.add(companyDetails_1);
		list.add(companyDetails_2);
		long value = 2;
		HashMap<String,JSONObject> count = new HashMap<String,JSONObject>();
		for(int i = 0; i < list.size(); i++) {
			when(campaignRepository.countByCompanyId(list.get(i))).thenReturn(value);
			when(audienceRepository.countByCompanyId(list.get(i))).thenReturn(value);
			JSONObject object = new JSONObject();
			object.appendField("campaignesCount", value);
			object.appendField("contactsCount", value);
			count.put(list.get(i).getCompanyId(), object);
		}
		HashMap res = companyService.counting(list);
		assertEquals(res,count);
	}
}
