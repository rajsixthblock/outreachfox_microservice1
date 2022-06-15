package com.companyservice.companyservice.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Autowired
    private JavaMailSender sender;
	
	public void sendEmail(String to,String subject, String body) throws MessagingException, IOException {
		 MimeMessage msg = sender.createMimeMessage();
	
	     MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			
	     helper.setTo(to);
	     helper.setSubject(subject);
	     helper.setText(body, true);
	     
	     sender.send(msg);
	     System.out.println("\n Activation link sent successfully to registered mail id.! \n");
	}
	
//	private JavaMailSenderImpl sender = new JavaMailSenderImpl();
//	public void mailProcess(String to,String subject, String text) throws MessagingException, IOException {
//		 System.out.println("entered in mail service.!");
//		 System.out.println("\n");
//		 MimeMessage msg = sender.createMimeMessage();
//	     MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//	     helper.setTo(to);
//	     helper.setSubject(subject);
//	     helper.setText(text, true);
//	     
//	     sender.send(msg);
//
//	}
//	
//	@Bean
//	public JavaMailSender getJavaMailSender() {
//	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//	    mailSender.setHost("smtp.gmail.com");
//	    mailSender.setPort(587);
//	      
//	    mailSender.setUsername("bhuvaneshwarthavadaboina744@gmail.com");
//	    mailSender.setPassword("lbxgvyxeypivrenq");
//	      
//	    Properties props = mailSender.getJavaMailProperties();
//	    props.put("mail.transport.protocol", "smtp");
//	    props.put("mail.smtp.auth", true);
//	    props.put("mail.smtp.starttls.enable", true);
//	    props.put("mail.debug", true);
//	      
//	    return mailSender;
//	}

}
