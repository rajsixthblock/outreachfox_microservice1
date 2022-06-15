package com.companyservice.companyservice.entity;

public class Password {
	String oldPassword ;
	String newPassword;
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public Password(String oldPassword, String newPassword) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
	public Password() {
		super();
	}
	@Override
	public String toString() {
		return "Password [oldPassword=" + oldPassword + ", newPassword=" + newPassword + "]";
	}
	
}
