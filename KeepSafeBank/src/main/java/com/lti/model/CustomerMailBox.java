package com.lti.model;

 

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

 

@Entity
@Table(name="tbl_custmailbox")
public class CustomerMailBox {
@Id
@SequenceGenerator(name="m_seq",initialValue=1,allocationSize=1)
@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="m_seq")
int mId;    
public int getmId() {
    return mId;
}
public void setmId(int mId) {
    this.mId = mId;
}
String name;
String email;
String description;
public String getName() {
    return name;
}
public void setName(String name) {
    this.name = name;
}
public String getEmail() {
    return email;
}
public void setEmail(String email) {
    this.email = email;
}
public String getDescription() {
    return description;
}
public void setDescription(String description) {
    this.description = description;
}

 

 

 

 

 

}