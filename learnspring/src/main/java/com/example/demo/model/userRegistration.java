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
	private int phone;
	private String activated;
	private String email;
	private String password;
	private String address1;
	private String address2;
	private String city;
	private String state;
	private String country;
	private String zipcode;
	private String promotion;
	//private PaymentCard card;
	
	
	
	public int getId() {
		return id;
	}
	public userRegistration() {
		super();
		// TODO Auto-generated constructor stub
	}
	public userRegistration(int id, String fullName, int phone, String activated, String email, String password,
			String address1, String address2, String city, String state, String country, String zipcode,
			String promotion) {
		super();
		this.id = id;
		this.fullname = fullName;
		this.phone = phone;
		this.activated = activated;
		this.email = email;
		this.password = password;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.country = country;
		this.zipcode = zipcode;
		this.promotion = promotion;
	}
	@Override
	public String toString() {
		return "userRegistration [id=" + id + ", fullname=" + fullname + ", phone=" + phone + ", activated=" + activated
				+ ", email=" + email + ", password=" + password + ", address1=" + address1
				+ ", address2=" + address2 + ", city=" + city + ", state=" + state + ", country=" + country
				+ ", zipcode=" + zipcode + ", cardNo=" + ", promotion=" + promotion + "]";
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
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
	public String getActivated() {
		return activated;
	}
	public void setActivated(String activated) {
		this.activated = activated;
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