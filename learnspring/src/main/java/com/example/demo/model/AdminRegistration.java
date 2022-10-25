package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@ToString
@Table(name="admin")

public class AdminRegistration {
	
	@Id
	@GeneratedValue
	private int id;
	private String aname;
	private String ausername;
	private String apassword;
	private String ahash;
	private String aemail;
	private int amobile;
	private String aaddress;
	private int aactive;
	
	@Override
	public String toString() {
		return "AdminRegistration [id=" + id + ", aname=" + aname + ", ausername=" + ausername + ", apassword="
				+ apassword + ", ahash=" + ahash + ", aemail=" + aemail + ", amobile=" + amobile + ", aaddress="
				+ aaddress + ", aactive=" + aactive + "]";
	}
	
	
	public AdminRegistration(int id, String aname, String ausername, String apassword, String ahash, String aemail,
			int amobile, String aaddress, int aactive) {
		super();
		this.id = id;
		this.aname = aname;
		this.ausername = ausername;
		this.apassword = apassword;
		this.ahash = ahash;
		this.aemail = aemail;
		this.amobile = amobile;
		this.aaddress = aaddress;
		this.aactive = aactive;
	}



	public AdminRegistration() {
		super();
		// TODO Auto-generated constructor stub
	}



	public int getId() {
		return id;
	}
	public void setId(int aid) {
		this.id = aid;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getAusername() {
		return ausername;
	}
	public void setAusername(String ausername) {
		this.ausername = ausername;
	}
	public String getApassword() {
		return apassword;
	}
	public void setApassword(String apassword) {
		this.apassword = apassword;
	}
	public String getAhash() {
		return ahash;
	}
	public void setAhash(String ahash) {
		this.ahash = ahash;
	}
	public String getAemail() {
		return aemail;
	}
	public void setAemail(String aemail) {
		this.aemail = aemail;
	}
	public int getAmobile() {
		return amobile;
	}
	public void setAmobile(int amobile) {
		this.amobile = amobile;
	}
	public String getAaddress() {
		return aaddress;
	}
	public void setAaddress(String aaddress) {
		this.aaddress = aaddress;
	}
	public int getAactive() {
		return aactive;
	}
	public void setAactive(int aactive) {
		this.aactive = aactive;
	}
	
}

