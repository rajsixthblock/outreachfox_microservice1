package com.companyservice.companyservice.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.companyservice.companyservice.entity.Company;
import com.companyservice.companyservice.entity.Payment;
import com.companyservice.companyservice.entity.Subscription;
import com.companyservice.companyservice.entity.User;
import com.companyservice.companyservice.exception.BadRequestException;
import com.companyservice.companyservice.exception.DetailsNotFound;
import com.companyservice.companyservice.service.PaymentService;

import net.minidev.json.JSONObject;
@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes= {PaymentControllerTests.class})
public class PaymentControllerTests {
	@Mock
	PaymentService paymentService;
	@InjectMocks
	PaymentController paymentController;
	@Test
	public void test_save() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Subscription subscription = new Subscription();
		subscription.setAmount(100);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(1000);
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setId("111a");
		subscription.setStatus(true);
		Payment payment = new Payment();
		payment.setPaymentId("111a");
		payment.setCompanyId(companyDetails);
		payment.setAmount(100);
		payment.setPaidDate(new Date(0,0,0));
		payment.setPaymentMode("online");
		payment.setSubscriptionId(subscription);
		payment.setStatus(true);
		payment.setTransactionId("1111aa");
		String[] ids = {"111", null};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != null) {
				when(paymentService.savePayment(companyDetails.getCompanyId(), subscription.getId(), payment)).thenReturn(payment);
				ResponseEntity response =   paymentController.save(companyDetails.getCompanyId(), subscription.getId(), payment);
				assertEquals(HttpStatus.CREATED, response.getStatusCode());
			}else {
				int id = i;
				when(paymentService.savePayment(companyDetails.getCompanyId(), subscription.getId(), payment)).thenReturn(null);
				Throwable ex = assertThrows(
						BadRequestException.class, () -> {
							paymentController.save(companyDetails.getCompanyId(), subscription.getId(), payment);
				      });
				   assertEquals("Payment creation failed", ex.getMessage());
			}
		}
	}
	
	@Test
	public void test_getByPaymentID() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Subscription subscription = new Subscription();
		subscription.setAmount(100);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(1000);
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setId("111a");
		subscription.setStatus(true);
		Payment payment = new Payment();
		payment.setPaymentId("111a");
		payment.setCompanyId(companyDetails);
		payment.setAmount(100);
		payment.setPaidDate(new Date(0,0,0));
		payment.setPaymentMode("online");
		payment.setSubscriptionId(subscription);
		payment.setStatus(true);
		payment.setTransactionId("1111aa");
		String[] ids = {"111", ""};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != "") {
				when(paymentService.getPaymentID(payment.getPaymentId())).thenReturn(payment);
				ResponseEntity response =   paymentController.getByPaymentID(payment.getPaymentId());
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				//when(paymentService.getPaymentID(payment.getPaymentId())).thenReturn(payment);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							paymentController.getByPaymentID(ids[id]);
				      });
				   assertEquals("Payment details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_updatePayment() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Subscription subscription = new Subscription();
		subscription.setAmount(100);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(1000);
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setId("111a");
		subscription.setStatus(true);
		Payment payment = new Payment();
		payment.setPaymentId("111a");
		payment.setCompanyId(companyDetails);
		payment.setAmount(100);
		payment.setPaidDate(new Date(0,0,0));
		payment.setPaymentMode("online");
		payment.setSubscriptionId(subscription);
		payment.setStatus(true);
		payment.setTransactionId("1111aa");
		String[] ids = {"111", ""};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != "") {
				when(paymentService.updatePayment(payment.getPaymentId(), payment)).thenReturn(payment);
				ResponseEntity response =   paymentController.updatePayment(payment.getPaymentId(),payment);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				//when(paymentService.updatePayment(payment.getPaymentId(), payment)).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							paymentController.updatePayment(ids[id],payment);
				      });
				   assertEquals("Payment details not found", ex.getMessage());
			}
		}
	}
	@Test
	public void test_deletePayment() throws Exception {
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Subscription subscription = new Subscription();
		subscription.setAmount(100);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(1000);
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setId("111a");
		subscription.setStatus(true);
		Payment payment = new Payment();
		payment.setPaymentId("111a");
		payment.setCompanyId(companyDetails);
		payment.setAmount(100);
		payment.setPaidDate(new Date(0,0,0));
		payment.setPaymentMode("online");
		payment.setSubscriptionId(subscription);
		payment.setStatus(true);
		payment.setTransactionId("1111aa");
		String[] ids = {"111", ""};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			if(ids[i] != "") {
				when(paymentService.deletePaymentByID(payment.getPaymentId())).thenReturn("Successfull");
				ResponseEntity response =   paymentController.deletePayment(payment.getPaymentId());
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				int id = i;
				//when(paymentService.deletePaymentByID(payment.getPaymentId())).thenReturn(null);
				Throwable ex = assertThrows(
						DetailsNotFound.class, () -> {
							paymentController.deletePayment(ids[id]);
				      });
				   assertEquals("Payment details not found", ex.getMessage());
			}
		}
	}
	
	@Test
	public void test_getPayments() throws Exception {
		List<Payment> payments = new ArrayList();
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Subscription subscription = new Subscription();
		subscription.setAmount(100);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(1000);
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setId("111a");
		subscription.setStatus(true);
		Payment payment = new Payment();
		payment.setPaymentId("111a");
		payment.setCompanyId(companyDetails);
		payment.setAmount(100);
		payment.setPaidDate(new Date(0,0,0));
		payment.setPaymentMode("online");
		payment.setSubscriptionId(subscription);
		payment.setStatus(true);
		payment.setTransactionId("1111aa");
		payments.add(payment);
		payments.add(null);
		Page<Payment> emptyPage = null;
		int i = 0;
		for(i = 0; i < payments.size(); i++) {
			
			if(payments.get(i) != null) {
				Page<Payment> pageDetails = new PageImpl<>(payments);
				when(paymentService.getPaymentDetails( 1, 10)).thenReturn(pageDetails);
				ResponseEntity<Page<Payment>> response =  paymentController.getPayments(1,10);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				when(paymentService.getPaymentDetails( 1, 10)).thenReturn(null);
				Throwable exception = assertThrows(
						DetailsNotFound.class, () -> {
							paymentController.getPayments(0,0);
						});
				assertEquals("Payment details not found", exception.getMessage());
			}
		}
		
	}
	@Test
	public void test_getPayments_2() throws Exception {
		List<Payment> payments = new ArrayList();
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Subscription subscription = new Subscription();
		subscription.setAmount(100);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(1000);
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setId("111a");
		subscription.setStatus(true);
		Payment payment = new Payment();
		payment.setPaymentId("111a");
		payment.setCompanyId(companyDetails);
		payment.setAmount(100);
		payment.setPaidDate(new Date(0,0,0));
		payment.setPaymentMode("online");
		payment.setSubscriptionId(subscription);
		payment.setStatus(true);
		payment.setTransactionId("1111aa");
		payments.add(payment);
		payments.add(null);
		JSONObject payload = new JSONObject();
		payload.appendField("name", "sai");
		payload.appendField("email", "sai@gmail.com");
		payload.appendField("phone", "0000000000");
		Page<Payment> emptyPage = null;
		String[] ids = {"111", ""};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			Page<Payment> pageDetails = new PageImpl<>(payments);
			when(paymentService.getPaymentDetails( 1, 10, companyDetails.getCompanyId())).thenReturn(pageDetails);
			if(ids[i] !="") {
				ResponseEntity<Page> response = (ResponseEntity<Page>) paymentController.getPayments(companyDetails.getCompanyId(),1,10);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				when(paymentService.getPaymentDetails( 1, 10, companyDetails.getCompanyId())).thenReturn(emptyPage);
				Throwable exception = assertThrows(
						DetailsNotFound.class, () -> {
							paymentController.getPayments(companyDetails.getCompanyId(),0,0);
						});
				assertEquals("Payment details not found", exception.getMessage());
			}
		}
		
	}
	@Test
	public void test_filter() throws Exception {
		List<Payment> payments = new ArrayList();
		Company companyDetails = new Company("111", "Ashok", "MIMICS", 0, "ashok@gmail.com", "password2", true, "Hyd", null, null, null);
		Subscription subscription = new Subscription();
		subscription.setAmount(100);
		subscription.setCurrencyType("dollar");
		subscription.setEmailsLimit(1000);
		subscription.setPlanName("Basic");
		subscription.setPlanType("monthly");
		subscription.setId("111a");
		subscription.setStatus(true);
		Payment payment = new Payment();
		payment.setPaymentId("111a");
		payment.setCompanyId(companyDetails);
		payment.setAmount(100);
		payment.setPaidDate(new Date(0,0,0));
		payment.setPaymentMode("online");
		payment.setSubscriptionId(subscription);
		payment.setStatus(true);
		payment.setTransactionId("1111aa");
		payments.add(payment);
		payments.add(null);
		JSONObject payload = new JSONObject();
		payload.appendField("name", "sai");
		payload.appendField("email", "sai@gmail.com");
		payload.appendField("phone", "0000000000");
		Page<Payment> emptyPage = null;
		String[] ids = {"111", ""};
		int i = 0;
		for(i = 0; i < ids.length; i++) {
			Page<Payment> pageDetails = new PageImpl<>(payments);
			when(paymentService.filteredPayments(payload, 1, 10)).thenReturn(pageDetails);
			if(ids[i] !="") {
				ResponseEntity<Page> response = (ResponseEntity<Page>) paymentController.filter(payload,1,10);
				assertEquals(HttpStatus.OK, response.getStatusCode());
			}else {
				when(paymentService.filteredPayments(payload, 1, 10)).thenReturn(emptyPage);
				Throwable exception = assertThrows(
						DetailsNotFound.class, () -> {
							paymentController.filter(payload,0,0);
						});
				assertEquals("Payment details not found", exception.getMessage());
			}
		}
		
	}
}
