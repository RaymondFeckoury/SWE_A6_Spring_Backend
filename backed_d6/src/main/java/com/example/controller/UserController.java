package com.example.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.userRegistration;
import com.example.demo.model.PaymentCard;
import com.example.demo.repo.UserRepo;
import com.example.demo.repo.PaymentRepo;
import com.example.excepion.IdNotFoundException;
import com.example.service.userservice;
import com.example.service.PaymentService;
import com.example.service.imple.Emailsenderservice;
import com.example.service.imple.userimple;


@RestController
@CrossOrigin(origins="http://localhost:4200") 
@RequestMapping("/api")	
public class UserController {
	@Autowired
	private UserRepo repo;

	  @Autowired 
	  private userservice us;
	  
	  @Autowired 
	  private PaymentRepo paymentRepo;
	  @Autowired 
	  private PaymentService paymentService;
	  @Autowired
	  Emailsenderservice emailsenderservice;
	  
	  
	  @PostMapping("/SignUp")
	  public String registerUser(@RequestBody userRegistration userRegistration) throws MessagingException {
		 //logger.info("Received controller");
		 System.out.println("Controller called for user Registration "+userRegistration.toString());
		 //userRegistration existingUser = us.getUserByemail(userRegistration.getEmail());
		 int max=99999;
		 int min=10000;
		 int rand = (int) Math.floor(Math.random()*(max-min+1)+min);
		 userRegistration.setVerificationcode(rand);
		 userRegistration.setActivated("False");
		 
		 //ResponseEntity.ok(paymentRepo.save(PayemtCard));
		 ResponseEntity.ok(repo.save(userRegistration));
		 String vcode=Integer.toString(rand);
		 emailsenderservice.sendemailwithattachment(userRegistration.getEmail(),"Please use this Verification code to activate account with bookmyshow : "+vcode,"verificationcode from BookMyShow");
		 //ResponseEntity.ok(paymentRepo.save(PaymentCard));
		 return "verification code sent successfully";
			}
	  @GetMapping("/verifyuser")
	  public String addpayment(@RequestBody userRegistration userRegistration) {
		 //logger.info("Received controller");
		 //System.out.println("Controller called for user Registration "+PaymentCard.toString());
		 //userRegistration existingUser = us.getUserByemail(userRegistration.getEmail());
		 //ResponseEntity.ok(paymentRepo.save(PayemtCard));
		 //ResponseEntity.ok(repo.save(userRegistration));
		  int vcode=0;
		  try {
		 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemabooking", "root", "");
		 java.sql.Statement stmt = conn.createStatement();
		 
         ResultSet resultSet = stmt.executeQuery("SELECT verificationcode FROM users WHERE email = " + "'" + userRegistration.getEmail() + "'");
			//userID = resultSet.getInt("userID");
         while (resultSet.next()) {
         	vcode=resultSet.getInt("verificationcode"); 
         }
         //int code=Integer.parseInt(vcode);
		  } catch (SQLException e) {
	            e.printStackTrace();
	        }
		 //List<userRegistration> accountData = repo.findByemail(userRegistration.getEmail());
		 String stat="";
		 //int vcode=userRegistration.getVerificationcode();
		 //userRegistration pqr=accountData.get(13);
		 int vc=userRegistration.getVerificationcode();
		
		// ResponseEntity.ok(paymentRepo.save(PaymentCard));
		 if(vcode == vc) {
			 stat="Success";
			 userRegistration.setActivated("True");
			 try {
		            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemabooking", "root", "");
		            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE users SET activated = ? WHERE email = ?");
		            preparedStatement.setString(1,userRegistration.getActivated() );
		            preparedStatement.setString(2, userRegistration.getEmail());
		            preparedStatement.executeUpdate();
		            preparedStatement.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
			 return stat;
		 }else {
			 return "unsuccess";
		 }
		 
		 //return stat;
			
			
	  }
	  
	  @PostMapping("/LoginUser")
	  public ResponseEntity<Boolean> userLogin(@RequestBody userRegistration userRegistration) {
		  String status="";
		  Boolean s= false;
		  try {
				 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemabooking", "root", "");
				 java.sql.Statement stmt = conn.createStatement();
				 
		         ResultSet resultSet = stmt.executeQuery("SELECT activated FROM users WHERE email = " + "'" + userRegistration.getEmail() + "'");
		         while (resultSet.next()) {
		         	status=resultSet.getString("activated"); 
		         }
				  } catch (SQLException e) {
			            e.printStackTrace();
			        }
		  System.out.println(status);
		  if(status.equals("True")) {
		 return ResponseEntity.ok(repo.existsByEmailAndPassword(userRegistration.getEmail(), userRegistration.getPassword()));
	  }else {
		  return ResponseEntity.ok(false);
	  }
		  }
	  
	  @PostMapping("/addpayment")
	  public ResponseEntity<PaymentCard> paymentCard(@RequestBody PaymentCard paymentCard) {
		 System.out.println("Controller called for user payment "+paymentCard.toString());
		 return ResponseEntity.ok(paymentRepo.save(paymentCard));
	  }
	  
	  // Checks the number of cards that a user currently has
	  @GetMapping(path = "/numberofcards/{userId}")
	  public ResponseEntity<?> getPaymentCards(@PathVariable int userId) throws IdNotFoundException { 
		  return ResponseEntity.status(200).body(paymentService.getPaymentsById(userId));
	  }
	  
	  // only admins have rights to inspect all users data
	  
	  @GetMapping(path = "/allusers") 
	  public ResponseEntity<?> getUsers() { 
		// get all users, returns empty list if user table is empty

		return ResponseEntity.status(200).body(us.getAllUsers());
	  
	  
	  }
	  
	  // To display user info on the update profile page
	  @GetMapping(path = "/getuserinfo/{userId}") 
	  public ResponseEntity<?> getUserInfo(@PathVariable int userId, @RequestBody userRegistration userReg) throws IdNotFoundException { 
		return ResponseEntity.status(200).body(us.getUserById(userId));
	  
	  }
	  
	  

	  
	  @GetMapping("/{id}")
	  public ResponseEntity<userRegistration>
	  getAccountByNumber(@PathVariable("id") int id) {
	  
		  List<userRegistration> accountData = repo.findByid(id);
		  
		  if (accountData.isEmpty()) { 
			  
			  return new ResponseEntity<userRegistration>(HttpStatus.NO_CONTENT); }
		  
		  System.out.println(id);
		  
		  
		  userRegistration pqr=accountData.get(0);
		  
		  return new ResponseEntity<userRegistration>(pqr,
		  HttpStatus.OK);
	  
	  }
	 
	  
	  @GetMapping("/emailcheck")
	  public void triggeremail() throws MessagingException
		{
			emailsenderservice.sendemailwithattachment("kvssrpunnit999@gmail.com","Moviebookingsystem sent you a link to reset your password please click here to continue","Reset your password");
		}
	  
	  @GetMapping("/verifyforgotpassword")
	  public String verifyfrogotpassword(@RequestBody userRegistration userRegistration)throws MessagingException {
		  	 int max=99999;
			 int min=10000;
			 int rand = (int) Math.floor(Math.random()*(max-min+1)+min);
			 String code=Integer.toString(rand);
			 //userRegistration.setPassvercode(code);
			 try {
		            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemabooking", "root", "");
		            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE users SET passvercode = ? WHERE email = ?");
		            preparedStatement.setString(1, code);
		            preparedStatement.setString(2, userRegistration.getEmail());
		            preparedStatement.executeUpdate();
		            preparedStatement.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
			 emailsenderservice.sendemailwithattachment(userRegistration.getEmail(),"Please use this Verification code to activate account with bookmyshow : "+code,"verificationcode from BookMyShow");
		  return "verfication code sent succesfully for password change";
		  
	  }
	  @GetMapping("/forgotpassword")
	  public String frogotpassword(@RequestBody userRegistration userRegistration)throws MessagingException {
		  int vcode=0;
		  try {
		 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemabooking", "root", "");
		 java.sql.Statement stmt = conn.createStatement();
		 
         ResultSet resultSet = stmt.executeQuery("SELECT passvercode FROM users WHERE email = " + "'" + userRegistration.getEmail() + "'");
         while (resultSet.next()) {
         	vcode=resultSet.getInt("passvercode"); 
         }
		  } catch (SQLException e) {
	            e.printStackTrace();
	        }
		 String vc=userRegistration.getPassvercode();
		 int pcode=Integer.parseInt(vc);
		 if(vcode == pcode) {
			 try {
		            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemabooking", "root", "");
		            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE users SET password = ? WHERE email = ?");
		            preparedStatement.setString(1, userRegistration.getPassword());
		            preparedStatement.setString(2, userRegistration.getEmail());
		            preparedStatement.executeUpdate();
		            preparedStatement.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
			 return "password change success";
		 }else {
			 return "password change unsuccess";
		 }
		  
		  
	  }
	  @GetMapping("/updatepassword")
	  public String updatepassword(@RequestBody userRegistration userRegistration) {
		  String Status="None";
		  String vcode="";
		  try {
		 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemabooking", "root", "");
		 java.sql.Statement stmt = conn.createStatement();
		 
         ResultSet resultSet = stmt.executeQuery("SELECT password FROM users WHERE email = " + "'" + userRegistration.getEmail() + "'");
         while (resultSet.next()) {
         	vcode=resultSet.getString("password"); 
         }
		  } catch (SQLException e) {
	            e.printStackTrace();
	        }
		  System.out.println(vcode);
		  
		 String vc=userRegistration.getPassword();
		 System.out.println(vc);
		 if(vcode.equals(vc)) {
			 try {
		            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemabooking", "root", "");
		            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE users SET password = ? WHERE email = ?");
		            preparedStatement.setString(1, userRegistration.getUpdatedpass());
		            preparedStatement.setString(2, userRegistration.getEmail());
		            preparedStatement.executeUpdate();
		            preparedStatement.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
			 Status="Successfully updated password";
			 return Status;
		 }else {
			 Status="password change unsuccessfull";
			 return Status;
		 }
	  }
	  @PostMapping("/editprofile")
	  public String editProfile(@RequestBody userRegistration userRegistration) throws SQLException{
	        int id = userRegistration.getId();
	        String fullname = userRegistration.getFullname();
	        int phone = userRegistration.getPhone();
	        String address1 = userRegistration.getAddress1();
	        String address2 = userRegistration.getAddress2();
	        String city = userRegistration.getCity();
	        String state = userRegistration.getState();
	        String country = userRegistration.getCountry();
	        String zipcode = userRegistration.getZipcode();
	        /*int cardNo = userRegistration.getCardNo();
	        String month = userRegistration.getMonth();
	        int year = userRegistration.getYear();
	        String name = userRegistration.getName();*/
	        String promotion = userRegistration.getPromotion();
	        String email=userRegistration.getEmail();
	        
	        try {
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemabooking", "root", "");
	            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE users SET fullname = ?, phone = ?, address1 = ?, address2 = ?, city = ?, state = ?, country = ?, zipcode = ?, promotion = ? WHERE email = ?");
	            preparedStatement.setString(1, fullname);
	            preparedStatement.setInt(2, phone);
	            preparedStatement.setString(3, address1);
	            preparedStatement.setString(4, address2);
	            preparedStatement.setString(5, city);
	            preparedStatement.setString(6, state);
	            preparedStatement.setString(7, country);
	            preparedStatement.setString(8, zipcode);
	            /*preparedStatement.setInt(9, cardNo);
	            preparedStatement.setString(10, month);
	            preparedStatement.setInt(11, year);
	            preparedStatement.setString(12, name);*/
	            preparedStatement.setString(9, promotion);
	            preparedStatement.setString(10, email);
	            //preparedStatement.setLong(5, id);
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            //RName="Working";
	            //System.out.println(Name);
	            //java.sql.Statement stmt = conn.createStatement();
	            /*ResultSet resultSet = stmt.executeQuery("SELECT fullname FROM users WHERE fullname = " + "'" + """ + "'");
				//userID = resultSet.getInt("userID");
	            while (resultSet.next()) {
	            	RName=resultSet.getString("Name"); 
	            }*/
	        }catch(SQLException exception){
	        	exception.printStackTrace();
	        }
	      
	        
	        return "ignore this";
	  }
	  
}
