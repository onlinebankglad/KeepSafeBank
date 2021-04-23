package com.lti.dto;

public class TransactionDto {
	int fromacc_no;
	int toAccNO;
	double amount;
	String tmodes;
	
	public String getTmode() {
		return tmodes;
	}
	public void setTmode(String tmode) {
		this.tmodes = tmode;
	}
	public int getFromacc_no() {
		return fromacc_no;
	}
	public void setFromacc_no(int fromacc_no) {
		this.fromacc_no = fromacc_no;
	}
	public int getToAccNO() {
		return toAccNO;
	}
	public void setToAccNO(int toAccNO) {
		this.toAccNO = toAccNO;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
