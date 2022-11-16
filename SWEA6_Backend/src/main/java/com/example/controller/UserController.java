package com.example.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import com.example.demo.model.PaymentCard;
import com.example.demo.model.userRegistration;
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
	  
	  //@Autowired
	  //PaymentCard paymentcard;
	  
	  
	  @PostMapping("/SignUp")
	  public boolean registerUser(@RequestBody userRegistration userRegistration) throws MessagingException {
		 //logger.info("Received controller");
		 System.out.println("Controller called for user Registration "+userRegistration.toString());
		 //userRegistration existingUser = us.getUserByemail(userRegistration.getEmail());
		 int max=99999;
		 int min=10000;
		 int rand = (int) Math.floor(Math.random()*(max-min+1)+min);
		 userRegistration.setVerificationcode(Integer.toString(rand));
		 userRegistration.setActivated("False");
		 
			/*
			 * paymentcard.setCardnumber(userRegistration.getCardnumber());
			 * paymentcard.setExpirymonth(userRegistration.getCardexpirymonth());
			 * paymentcard.setExpiryyear(userRegistration.getCardexpiryyear());
			 * paymentcard.setNameoncard(userRegistration.getNameoncard());
			 * paymentcard.setId(userRegistration.getId()); paymentRepo.save(paymentcard);
			 */
		 
		 //ResponseEntity.ok(paymentRepo.save(PayemtCard));
		 List<userRegistration> accountData = repo.findByemail(userRegistration.getEmail());
		 if(accountData.isEmpty()) {
			 ResponseEntity.ok(repo.save(userRegistration));
			 String vcode=Integer.toString(rand);
			 emailsenderservice.sendemailwithattachment(userRegistration.getEmail(),"Please use this Verification code to activate account with bookmyshow : "+vcode,"verificationcode from BookMyShow");
			 return true;
		 }else {
			 return false;
		 }
		 //ResponseEntity.ok(paymentRepo.save(PaymentCard));
			}
	  @PostMapping("/verifyuser")
	  public boolean addpayment(@RequestBody userRegistration userRegistration) {
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
		 String vc=userRegistration.getVerificationcode();
		 int vcx=Integer.parseInt(vc);
		
		// ResponseEntity.ok(paymentRepo.save(PaymentCard));
		 if(vcode == vcx) {
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
			 return true;
		 }else {
			 return false;
		 }
		 
		 //return stat;		
	  }
	  
	  @PostMapping("/LoginUser")
	  public ResponseEntity<Boolean> userLogin(@RequestBody userRegistration userRegistration) {
		  String status="";
		  Boolean s= false;
		  /*try {
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
		  if(status.equals("True")) {*/
		 return ResponseEntity.ok(repo.existsByEmailAndPassword(userRegistration.getEmail(), userRegistration.getPassword()));
			/*
			 * }else { return ResponseEntity.ok(false); }
			 */
		  }
	  
	  
	  @PostMapping("/addpayment")
	  public ResponseEntity<PaymentCard> paymentCard(@RequestBody PaymentCard paymentCard) {
		 System.out.println("Controller called for user payment "+paymentCard.toString());
		 return ResponseEntity.ok(paymentRepo.save(paymentCard));
	  }
	  
	  // Checks the number of cards that a user currently has
		/*
		 * @GetMapping(path = "/numberofcards/{userId}") public ResponseEntity<?>
		 * getPaymentCards(@PathVariable int userId) throws IdNotFoundException { return
		 * ResponseEntity.status(200).body(paymentService.getPaymentsById(userId)); }
		 */
	  
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
	  
	  

	  
	  @PostMapping("/getuserdetails")
	  public ResponseEntity<userRegistration>
	  getAccountByNumber(@RequestBody userRegistration userRegistration ) {
	      String email=userRegistration.getEmail();
		  List<userRegistration> accountData = repo.findByemail(email);
		  
		  if (accountData.isEmpty()) { 
			  
			  return new ResponseEntity<userRegistration>(HttpStatus.NO_CONTENT); }
		  
		  System.out.println(email);
		  
		  
		  userRegistration pqr=accountData.get(0);
		  
		  return new ResponseEntity<userRegistration>(pqr,
		  HttpStatus.OK);
	  
	  }
	
	  
	  @PostMapping("/verifyforgotpassword")
	  public boolean verifyforgotpassword(@RequestBody userRegistration user)throws MessagingException {
		     System.out.print("entered verify");
		  	 int max=99999;
			 int min=10000;
			 int randx = (int) Math.floor(Math.random()*(max-min+1)+min);
			 String pcode=Integer.toString(randx);
			 List<userRegistration> accountData = repo.findByemail(user.getEmail());
			 if(accountData.isEmpty()) {
				 return false;
			 }else {
				 System.out.println("reached else");
			 emailsenderservice.sendemailwithattachment(user.getEmail(),"Please use this Verification code to resetpassword with bookmyshow : "+pcode,"verificationcode from BookMyShow");
			 //userRegistration.setPassvercode(code);
			 try {
		            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemabooking", "root", "");
		            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE users SET passvercode = ? WHERE email = ?");
		            preparedStatement.setString(1, pcode);
		            preparedStatement.setString(2, user.getEmail());
		            preparedStatement.executeUpdate();
		            preparedStatement.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		   }
			 return true;
			 }
		
		  
		  
	  }
	  @PostMapping("/forgotpasswordstat")
	  public boolean frogotpassword(@RequestBody userRegistration userRegistration)throws MessagingException {
		  String vcode="";
		  try {
		 Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemabooking", "root", "");
		 java.sql.Statement stmt = conn.createStatement();
		 
         ResultSet resultSet = stmt.executeQuery("SELECT passvercode FROM users WHERE email = " + "'" + userRegistration.getEmail() + "'");
         while (resultSet.next()) {
         	vcode=resultSet.getString("passvercode"); 
         }
		  } catch (SQLException e) {
	            e.printStackTrace();
	        }
		 String vc=userRegistration.getPassvercode();
		 System.out.println(vc);
		 int pcode=Integer.parseInt(vc);
		 int vcodex=Integer.parseInt(vcode);
		 if(vcodex == pcode) {
				/*
				 * try { Connection conn =
				 * DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemabooking",
				 * "root", ""); PreparedStatement preparedStatement =
				 * conn.prepareStatement("UPDATE users SET password = ? WHERE email = ?");
				 * preparedStatement.setString(1, userRegistration.getPassword());
				 * preparedStatement.setString(2, userRegistration.getEmail());
				 * preparedStatement.executeUpdate(); preparedStatement.close(); } catch
				 * (SQLException e) { e.printStackTrace(); }
				 */
			 return true;
		 }else {
			 return false;
		 }
		
		  
		  
	  }
	  @PostMapping("/fpasssucess")
	  public boolean fpasssucess(@RequestBody userRegistration userRegistration) {
		  
			  try { 
				  Connection conn =DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemabooking","root", ""); 
				  PreparedStatement preparedStatement = conn.prepareStatement("UPDATE users SET password = ? WHERE email = ?");
			  preparedStatement.setString(1, userRegistration.getPassword());
			  preparedStatement.setString(2, userRegistration.getEmail());
			  preparedStatement.executeUpdate(); preparedStatement.close(); 
			  }
			  catch(SQLException e) { 
				  e.printStackTrace(); 
				  }
			 
		  return true;
	  }
	  @PostMapping("/updatepassword")
	  public boolean updatepassword(@RequestBody userRegistration userRegistration) {
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
			 return true;
		 }else {
			 Status="password change unsuccessfull";
			 return false;
		 }
	  }
	  @PostMapping("/editprofile")
	  public boolean editProfile(@RequestBody userRegistration userRegistration) throws SQLException,MessagingException{
	        int id = userRegistration.getId();
	        String fullname = userRegistration.getFirstname();
	        String lastname=userRegistration.getLastname();
	        String phone = userRegistration.getPhone();
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
	        String cardnumber=userRegistration.getCardnumber();
	        String cardexpirymonth=userRegistration.getCardexpirymonth();
	        String cardexpiryyear=userRegistration.getCardexpiryyear();
	        String nameoncard=userRegistration.getNameoncard();
	        
	        try {
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemabooking", "root", "");
	            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE users SET firstname = ?, phone = ?, address1 = ?, address2 = ?, city = ?, state = ?, country = ?, zipcode = ?, promotion = ?, lastname=?,cardnumber=?,cardexpirymonth=?,cardexpiryyear=?,nameoncard=? WHERE email = ?");
	            preparedStatement.setString(1, fullname);
	            preparedStatement.setString(2, phone);
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
	            preparedStatement.setString(10, lastname);
	            preparedStatement.setString(11, cardnumber);
	            preparedStatement.setString(12, cardexpirymonth);
	            preparedStatement.setString(13, cardexpiryyear);
	            preparedStatement.setString(14, nameoncard);
	            preparedStatement.setString(15, email);
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
	        emailsenderservice.sendemailwithattachment(email,"Your profile has been succesfully updated","Profile Updated");
return true;
	  }
	  @PostMapping("/sendstatus")
	  public boolean sendstatus(@RequestBody userRegistration userRegistration) {
		  String status="";
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
		  return true;
		  }else { return false; }
		 
	  }
	  @PostMapping("/Dummyemail")
	  public String dumyemail(@RequestBody userRegistration userRegistration) throws MessagingException{
		  
		  emailsenderservice.sendemailwithattachment(userRegistration.getEmail(),"Your profile has been succesfully updated","Profile Updated");
		  return "email sent Successfully";
	  }
	  
	    // Returns all show times for a movie
		@GetMapping(path = "/getshowtimes/{movieId}")
		public List<Entry<Date,Time>> showDateAndTime(@PathVariable int movieId) {
			List<Entry<Date,Time>> pairList = new ArrayList<>();
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemabooking", "root", "password");
				java.sql.Statement stmt = conn.createStatement();
				ResultSet resultSet = stmt.executeQuery("SELECT date,time FROM showtimes WHERE movieid = "+ "'" + movieId + "'");
				while(resultSet.next()) {
					Entry<Date,Time> curTime = new AbstractMap.SimpleEntry<>(resultSet.getDate("date"),resultSet.getTime("time"));
					pairList.add(curTime);
				}
				return pairList;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return null;
		}
	  
}
