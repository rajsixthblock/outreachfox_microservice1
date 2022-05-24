package com.OutReachCompany.OutReachCompanyDetails.Utilities;

import lombok.Data;

@Data
public class JwtRequest {

	private String email;
	private String password;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public JwtRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	@Override
	public String toString() {
		return "JwtRequest [email=" + email + ", password=" + password + "]";
	}
	
}
