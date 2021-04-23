package com.lti.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="tbl_customersss")
public class Customer {
@Id
@SequenceGenerator(name="customer_seq",initialValue=100,allocationSize=1)
@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="customer_seq")
int custId;
int custAge;
String custName;
String parentsName;
String permanentAddress;
String residentialAddress;
String pan;
String aadhar;
String panno;
String aadharno;
String mobileNumber;
String emailID;
boolean isApproved=false;
String loginPassword;
String state;
String city;
int pinCode;

 

@OneToMany(mappedBy="customer")
List<Beneficary> benificary;

@OneToMany(mappedBy="customer")
List<Account> account;



public String getPanno() {
	return panno;
}

public void setPanno(String panno) {
	this.panno = panno;
}

public String getAadharno() {
	return aadharno;
}

public void setAadharno(String aadharno) {
	this.aadharno = aadharno;
}

public String getPan() {
return pan;
}

public void setPan(String pan) {
this.pan = pan;
}

public String getAadhar() {
return aadhar;
}

public void setAadhar(String aadhar) {
this.aadhar = aadhar;
}

public String getState() {
return state;
}

public void setState(String state) {
this.state = state;
}

public String getCity() {
return city;
}

public void setCity(String city) {
this.city = city;
}

public int getPinCode() {
return pinCode;
}

public void setPinCode(int pinCode) {
this.pinCode = pinCode;
}


public int getCustId() {
return custId;
}

public void setCustId(int custId) {
this.custId = custId;
}

public int getCustAge() {
return custAge;
}

public void setCustAge(int custAge) {
this.custAge = custAge;
}

public String getCustName() {
return custName;
}

public void setCustName(String custName) {
this.custName = custName;
}

public String getParentsName() {
return parentsName;
}

public void setParentsName(String parentsName) {
this.parentsName = parentsName;
}

public String getPermanentAddress() {
return permanentAddress;
}

public void setPermanentAddress(String permanentAddress) {
this.permanentAddress = permanentAddress;
}

public String getResidentialAddress() {
return residentialAddress;
}

public void setResidentialAddress(String residentialAddress) {
this.residentialAddress = residentialAddress;
}




public String getMobileNumber() {
return mobileNumber;
}

public void setMobileNumber(String mobileNumber) {
this.mobileNumber = mobileNumber;
}

public String getEmailID() {
return emailID;
}

public void setEmailID(String emailID) {
this.emailID = emailID;
}

public boolean isApproved() {
return isApproved;
}

public void setApproved(boolean isApproved) {
this.isApproved = isApproved;
}

public String getLoginPassword() {
return loginPassword;
}

public void setLoginPassword(String loginPassword) {
this.loginPassword = loginPassword;
}

@JsonIgnore
public List<Beneficary> getBenificary() {
return benificary;
}

public void setBenificary(List<Beneficary> benificary) {
this.benificary = benificary;
}

 @JsonIgnore
public List<Account> getAccount() {
return account;
}

public void setAccount(List<Account> account) {
this.account = account;
}


}