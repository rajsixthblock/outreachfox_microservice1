package com.OutReachCompany.OutReachCompanyDetails.SecurityConfiguration;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SecurityConfig {
	
	
	String secretKey = "$@4par5s*sw$orde6bncryx`&ption$+==";
	
	
	public String passwordEncryption(String password) {
		AES256TextEncryptor encryption = new AES256TextEncryptor();
		encryption.setPassword(secretKey);
		String encryptedPassword = encryption.encrypt(password);
		encryption = new AES256TextEncryptor();
		return encryptedPassword;
	}
	public String passwordDecryption(String encryptedPassword) {
		AES256TextEncryptor encryption = new AES256TextEncryptor();
		encryption.setPassword(secretKey);
		String decryptedPassword = encryption.decrypt(encryptedPassword);
		encryption = new AES256TextEncryptor();
		return decryptedPassword;
	}
}
