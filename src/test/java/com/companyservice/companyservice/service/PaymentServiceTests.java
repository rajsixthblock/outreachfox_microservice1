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

import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.Payment;
import com.companyservice.companyservice.entity.Subscription;
import com.companyservice.companyservice.entity.User;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.repository.CompanyRepository;
import com.companyservice.companyservice.repository.PaymentRepository;
@ExtendWith(MockitoExtension.class)
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes= {PaymentServiceTests.class})
public class PaymentServiceTests {
	@Mock
	PaymentRepository paymentRepository;
	@Mock
	CompanyRepository companyRepository;
	@InjectMocks
	PaymentService paymentService;
	@Mock
	SubscriptionService subscriptionService;
	@Test
	public void test_savePayment() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Subscription subscription = new Subscription();
		subscription.setAmount(1000);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(10000);
		subscription.setId("11a");
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setStatus(true);
		Payment newPayment= new Payment();
		newPayment.setAmount(1000);
		newPayment.setCompanyId(companyDetails);
		newPayment.setPaidDate(new Date(0,0,0));
		newPayment.setPaymentId("111a");
		newPayment.setTransactionId("112sd34");
		newPayment.setPaymentMode("online");
		newPayment.setStatus(true);
		newPayment.setSubscriptionId(subscription);
		companyDetails.setSubscriptionId(subscription);
		
		String[] list = {"111", null};
		//assertEquals(companyDetails.getCompanyId(),user1.getCompanyId().getCompanyId());
		for(int i= 0; i< list.length; i++) {
			if(list[i] == "111") {
				when(companyRepository.getById(companyDetails.getCompanyId())).thenReturn(companyDetails);
				when(subscriptionService.getById(subscription.getId())).thenReturn(subscription);
				when(paymentRepository.save(newPayment)).thenReturn(newPayment);
				when(companyRepository.save(companyDetails)).thenReturn(companyDetails);
				Payment res = paymentService.savePayment(companyDetails.getCompanyId(), subscription.getId(), newPayment);
				assertEquals(res.getPaymentId(),newPayment.getPaymentId());
			}else {
				//newPayment = null;
				when(companyRepository.getById(companyDetails.getCompanyId())).thenReturn(companyDetails);
				when(subscriptionService.getById(subscription.getId())).thenReturn(subscription);
				when(paymentRepository.save(newPayment)).thenThrow(new NestedRuntimeException("Exception occured") {});
//				when(companyRepository.save(null)).thenThrow(new NestedRuntimeException("Exception occured") {});
				Throwable exception = assertThrows(
					Exception.class, () -> {
						paymentService.savePayment(companyDetails.getCompanyId(), subscription.getId(), newPayment);
					           }
					   );
					assertEquals("Exception occured", exception.getMessage());
			}
		}
	}
	@Test
	public void test_getPaymentID() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Subscription subscription = new Subscription();
		subscription.setAmount(1000);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(10000);
		subscription.setId("11a");
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setStatus(true);
		Payment newPayment= new Payment();
		newPayment.setAmount(1000);
		newPayment.setCompanyId(companyDetails);
		newPayment.setPaidDate(new Date(0,0,0));
		newPayment.setPaymentId("111a");
		newPayment.setTransactionId("112sd34");
		newPayment.setPaymentMode("online");
		newPayment.setStatus(true);
		newPayment.setSubscriptionId(subscription);
		companyDetails.setSubscriptionId(subscription);
		
		String[] list = {"111", null};
		//assertEquals(companyDetails.getCompanyId(),user1.getCompanyId().getCompanyId());
		for(int i= 0; i< list.length; i++) {
			if(list[i] == "111") {
				when(paymentRepository.getById(newPayment.getPaymentId())).thenReturn(newPayment);
				Payment res = paymentService.getPaymentID( newPayment.getPaymentId());
				assertEquals(res.getPaymentId(),newPayment.getPaymentId());
			}else {
				when(paymentRepository.getById(newPayment.getPaymentId())).thenThrow(new NestedRuntimeException("Exception occured") {});
				Throwable exception = assertThrows(
					Exception.class, () -> {
						paymentService.getPaymentID( newPayment.getPaymentId());
					           }
					   );
					assertEquals("Exception occured", exception.getMessage());
			}
		}
	}
	@Test
	public void test_getPaymentDetails() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Subscription subscription = new Subscription();
		subscription.setAmount(1000);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(10000);
		subscription.setId("11a");
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setStatus(true);
		Payment newPayment= new Payment();
		newPayment.setAmount(1000);
		newPayment.setCompanyId(companyDetails);
		newPayment.setPaidDate(new Date(0,0,0));
		newPayment.setPaymentId("111a");
		newPayment.setTransactionId("112sd34");
		newPayment.setPaymentMode("online");
		newPayment.setStatus(true);
		newPayment.setSubscriptionId(subscription);
		companyDetails.setSubscriptionId(subscription);
		List result = new ArrayList();
		result.add(newPayment);
		result.add(newPayment);
		Page<Payment> emptyPage = null;
		Page<Payment> pageDetails = new PageImpl<>(result);
		Pageable paging = PageRequest.of(1, 10);
		String[] list = {"111", null};
		//assertEquals(companyDetails.getCompanyId(),user1.getCompanyId().getCompanyId());
		for(int i= 0; i< list.length; i++) {
			if(list[i] == "111") {
				when(paymentRepository.findAll(Mockito.any(Pageable.class))).thenReturn(pageDetails);
				Page<Payment> res = paymentService.getPaymentDetails(1,10);
				assertEquals(pageDetails.getContent().size(), result.size());
			}else {
				paging = PageRequest.of(1, 10); 
				when(paymentRepository.findAll(Mockito.any(Pageable.class))).thenThrow(new NestedRuntimeException("Exception occured") {});
				Throwable exception = assertThrows(
					Exception.class, () -> {
						paymentService.getPaymentDetails(1,10);
					           }
					   );
					assertEquals("Exception occured", exception.getMessage());
			}
		}
	}
	@Test
	public void test_getPaymentDetails_2() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Subscription subscription = new Subscription();
		subscription.setAmount(1000);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(10000);
		subscription.setId("11a");
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setStatus(true);
		Payment newPayment= new Payment();
		newPayment.setAmount(1000);
		newPayment.setCompanyId(companyDetails);
		newPayment.setPaidDate(new Date(0,0,0));
		newPayment.setPaymentId("111a");
		newPayment.setTransactionId("112sd34");
		newPayment.setPaymentMode("online");
		newPayment.setStatus(true);
		newPayment.setSubscriptionId(subscription);
		companyDetails.setSubscriptionId(subscription);
		List result = new ArrayList();
		result.add(newPayment);
		result.add(newPayment);
		Page<Payment> emptyPage = null;
		Page<Payment> pageDetails = new PageImpl<>(result);
		Pageable paging = PageRequest.of(1, 2);
		String[] list = {"111", null};
		//assertEquals(companyDetails.getCompanyId(),user1.getCompanyId().getCompanyId());
		for(int i= 0; i< list.length; i++) {
			if(list[i] == "111") {
				when(paymentRepository.getByCompanyId(Mockito.any(Company.class),Mockito.any(Pageable.class))).thenReturn(pageDetails);
				Page<Payment> res = paymentService.getPaymentDetails(1,10,"companyId");
				assertEquals(pageDetails.getContent().size(), result.size());
			}else {
				//paging = PageRequest.of(0, 0);
				companyDetails.setCompanyId(null);
				when(paymentRepository.getByCompanyId(Mockito.any(Company.class),Mockito.any(Pageable.class))).thenThrow(new NestedRuntimeException("Exception occured") {});
				Throwable exception = assertThrows(
					Exception.class, () -> {
						paymentService.getPaymentDetails(1,10,null);
					           }
					   );
					assertEquals("Exception occured", exception.getMessage());
			}
		}
	}
	@Test
	public void test_dataNotFoundExceptionForDelete() {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Subscription subscription = new Subscription();
		subscription.setAmount(1000);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(10000);
		subscription.setId("11a");
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setStatus(true);
		Payment newPayment= new Payment();
		newPayment.setAmount(1000);
		newPayment.setCompanyId(companyDetails);
		newPayment.setPaidDate(new Date(0,0,0));
		newPayment.setPaymentId("111a");
		newPayment.setTransactionId("112sd34");
		newPayment.setPaymentMode("online");
		newPayment.setStatus(true);
		newPayment.setSubscriptionId(subscription);
		companyDetails.setSubscriptionId(subscription);
		
		when(paymentRepository.existsById(newPayment.getPaymentId())).thenReturn(false);
		boolean value = true;
		if(value == true) {
			when(paymentRepository.existsById(newPayment.getPaymentId())).thenReturn(false);
			//userRepository.deleteById(userDetails.getUserId());
			Throwable ex = assertThrows(
					DetailsNotFound.class, () -> {
						paymentService.deletePaymentByID( newPayment.getPaymentId());
			      });
			   assertEquals("Payment does not exist on file.", ex.getMessage());
			   
		}
	}
	@Test
	public void test_deletePaymentByID() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Subscription subscription = new Subscription();
		subscription.setAmount(1000);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(10000);
		subscription.setId("11a");
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setStatus(true);
		Payment newPayment= new Payment();
		newPayment.setAmount(1000);
		newPayment.setCompanyId(companyDetails);
		newPayment.setPaidDate(new Date(0,0,0));
		newPayment.setPaymentId("111a");
		newPayment.setTransactionId("112sd34");
		newPayment.setPaymentMode("online");
		newPayment.setStatus(true);
		newPayment.setSubscriptionId(subscription);
		companyDetails.setSubscriptionId(subscription);
		
		String[] list = {"111", null};
		//assertEquals(companyDetails.getCompanyId(),user1.getCompanyId().getCompanyId());
		for(int i= 0; i< list.length; i++) {
			if(list[i] == "111") {
				when(paymentRepository.existsById(newPayment.getPaymentId())).thenReturn(true);
				paymentRepository.deleteById(newPayment.getPaymentId());
				String res = paymentService.deletePaymentByID( newPayment.getPaymentId());
				assertEquals(res,"Payment deleted successfully.");
			}else {
				when(paymentRepository.existsById(newPayment.getPaymentId())).thenReturn(true);
				//paymentRepository.deleteById(newPayment.getPaymentId());
				doThrow(new NestedRuntimeException("Exception occured") {}).when(paymentRepository).deleteById("111a");
				Throwable exception = assertThrows(
					Exception.class, () -> {
						paymentService.deletePaymentByID( newPayment.getPaymentId());
					           }
					   );
					assertEquals("Exception occured", exception.getMessage());
			}
		}
	}
	@Test
	public void test_updatePayment() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Subscription subscription = new Subscription();
		subscription.setAmount(1000);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(10000);
		subscription.setId("11a");
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setStatus(true);
		Payment newPayment= new Payment();
		newPayment.setAmount(1000);
		newPayment.setCompanyId(companyDetails);
		newPayment.setPaidDate(new Date(0,0,0));
		newPayment.setPaymentId("111a");
		newPayment.setPaymentMode("online");
		newPayment.setTransactionId("112sd34");
		newPayment.setStatus(true);
		newPayment.setSubscriptionId(subscription);
		companyDetails.setSubscriptionId(subscription);
		when(paymentRepository.existsById(newPayment.getPaymentId())).thenReturn(true);
		when(paymentRepository.getById(newPayment.getPaymentId())).thenReturn(newPayment);
//		when(paymentRepository.existsBytransactionId(newPayment.getTransactionId())).thenReturn(false);
		when(paymentRepository.save(newPayment)).thenReturn(newPayment);
		Payment res = paymentService.updatePayment( newPayment.getPaymentId(),newPayment);
		assertEquals(res.getPaymentId(),newPayment.getPaymentId());
	}
}
