package com.lti.dto;

public class BeneficaryDto {

	int custId;
	int accNo;
	String bnickName;
	
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public int getAccNo() {
		return accNo;
	}
	public void setAccNo(int accNo) {
		this.accNo = accNo;
	}

	public String getBnickName() {
		return bnickName;
	}
	public void setBnickName(String bnickName) {
		this.bnickName = bnickName;
	}
}
