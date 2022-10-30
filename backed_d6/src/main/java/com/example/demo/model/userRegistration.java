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
import com.example.demo.model.PaymentCard;

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
	private String fullname;
	private String email;
	private String password;
	private int phone;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String zipcode;
	private String promotion;
	private int verificationcode;
	private String activated;
	private String passvercode;
	private String updatedpass;
	public String getUpdatedpass() {
		return updatedpass;
	}
	public void setUpdatedpass(String updatedpass) {
		this.updatedpass = updatedpass;
	}
	public String getPassvercode() {
		return passvercode;
	}
	public void setPassvercode(String passvercode) {
		this.passvercode = passvercode;
	}
	public String getActivated() {
		return activated;
	}
	public void setActivated(String activated) {
		this.activated = activated;
	}
	public int getVerificationcode() {
		return verificationcode;
	}
	public void setVerificationcode(int verificationcode) {
		this.verificationcode = verificationcode;
	}
	public userRegistration(int id, String fullname, String email, String password, int phone, String address1,
			String address2, String city, String state, String country, String zipcode, String promotion,int verificationcode,String activated,String passvercode
			,String updatedpass) {
		
		super();
		this.id = id;
		this.fullname = fullname;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipcode = zipcode;
		this.promotion = promotion;
		this.verificationcode=verificationcode;
		this.activated=activated;
		this.passvercode=passvercode;
		this.updatedpass=updatedpass;
	}
	public userRegistration() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "userRegistration [id=" + id + ", fullname=" + fullname + ", email=" + email + ", password=" + password
				+ ", phone=" + phone + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city
				+ ", state=" + state + ", country=" + country + ", zipcode=" + zipcode + ", promotion=" + promotion
				+ "]";
	}
	//private PaymentCard card;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
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
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getPromotion() {
		return promotion;
	}
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	
 
}
