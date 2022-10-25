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
@Table(name="users")

public class userRegistration {
	
	@Id
	@GeneratedValue
	private int id;
	private String uname;
	private String uusername;
	private String upassword;
	private String uhash;
	private String uemail;
	private int umobile;
	private String uaddress;

	@Override
	public String toString() {
		return "userRegistration [id=" + id + ", uname=" + uname + ", uusername=" + uusername + ", upassword="
				+ upassword + ", uhash=" + uhash + ", uemail=" + uemail + ", umobile=" + umobile + ", uaddress="
				+ uaddress + "]";
	}
	
	

	public userRegistration(int id, String uname, String uusername, String upassword, String uhash, String uemail,
			int umobile, String uaddress) {
		super();
		this.id = id;
		this.uname = uname;
		this.uusername = uusername;
		this.upassword = upassword;
		this.uhash = uhash;
		this.uemail = uemail;
		this.umobile = umobile;
		this.uaddress = uaddress;
	}



	public userRegistration() {
		super();
		// TODO Auto-generated constructor stub
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUusername() {
		return uusername;
	}

	public void setUusername(String uusername) {
		this.uusername = uusername;
	}

	public String getUpassword() {
		return upassword;
	}

	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}

	public String getUhash() {
		return uhash;
	}

	public void setUhash(String uhash) {
		this.uhash = uhash;
	}

	public String getUemail() {
		return uemail;
	}

	public void setUemail(String uemail) {
		this.uemail = uemail;
	}

	public int getUmobile() {
		return umobile;
	}

	public void setUmobile(int umobile) {
		this.umobile = umobile;
	}

	public String getUaddress() {
		return uaddress;
	}

	public void setUaddress(String uaddress) {
		this.uaddress = uaddress;
	}
 
}
