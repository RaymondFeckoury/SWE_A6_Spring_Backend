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
	private String firstname;
	private String lastname;
	private String password;
	private String uhash;
	private String email;
	private int umobile;
	private String uaddress;
	//private PaymentCard card;

	@Override
	public String toString() {
		return "userRegistration [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", upassword="
				+ password + ", uhash=" + uhash + ", uemail=" + email + ", umobile=" + umobile + ", uaddress="
				+ uaddress + "]";
	}
	
	public userRegistration(int id, String firstname, String lastname, String upassword, String uhash, String uemail,
			int umobile, String uaddress) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = upassword;
		this.uhash = uhash;
		this.email = uemail;
		this.umobile = umobile;
		this.uaddress = uaddress;
	}
	
	/*public PaymentCard getPaymentCard() {
		return card;
	}
	
	public void setPaymentCard(PaymentCard card) {
		this.card = card;
	}*/

	public userRegistration() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String upassword) {
		this.password = upassword;
	}

	public String getUhash() {
		return uhash;
	}

	public void setUhash(String uhash) {
		this.uhash = uhash;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String uemail) {
		this.email = uemail;
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
