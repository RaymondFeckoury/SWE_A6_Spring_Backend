package com.example.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	  
	  
	  @PostMapping("/user")
	  public ResponseEntity<userRegistration> registerUser(@RequestBody userRegistration userRegistration) {
		 //logger.info("Received controller");
		 //System.out.println("Controller called for user Registration "+userRegistration.toString());
		 return ResponseEntity.ok(repo.save(userRegistration));
			
			
	  }
	  
	  @PostMapping("/userlogin")
	  public ResponseEntity<Boolean> userLogin(@RequestBody userRegistration userRegistration) {
		 return ResponseEntity.ok(repo.existsByEmailAndPassword(userRegistration.getEmail(), userRegistration.getPassword()));
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
	  
	  /* To update profile
	  @PutMapping(path = "/updateuserinfo/{userId}")
	  public ResponseEntity<?> updateUserInfo(@PathVariable int userId, @RequestBody userRegistration userReg) throws IdNotFoundException {
		    userRegistration curUser = us.getUserById(userId);
		    String firstname = null;
			String lastname = null;
			String password = null;
			String uaddress = null;
			// checks fields user want's to update
			if (userReg.getFirstname() != null) {
				firstname = userReg.getFirstname();
				curUser.setFirstname(firstname);
			}
			if (userReg.getLastname() != null) {
				lastname = userReg.getLastname();
				curUser.setLastname(lastname);
			}
			if (userReg.getPassword() != null) {
				password = userReg.getPassword();
				curUser.setPassword(password);
			}
			if (userReg.getUaddress() != null) {
				uaddress = userReg.getUaddress();
				curUser.setUaddress(uaddress);
			}
			// save changes and return updated userRegistration
			repo.save(curUser);
			return ResponseEntity.status(200).body(curUser);
	  }*/
	  
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
	        
	        try {
	            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemabooking", "root", "password");
	            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE users SET fullname = ?, phone = ?, address1 = ?, address2 = ?, city = ?, state = ?, country = ?, zipcode = ?, promotion = ? WHERE id = ?");
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
	            preparedStatement.setInt(10, id);
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

	  
	  @GetMapping("/{id}")
	  public ResponseEntity<List<userRegistration>>
	  getAccountByNumber(@PathVariable("id") int id) {
	  
		  List<userRegistration> accountData = repo.findByid(id);
		  
		  if (accountData.isEmpty()) { 
			  
			  return new ResponseEntity<List<userRegistration>>(HttpStatus.NO_CONTENT); }
		  
		  System.out.println(id);
		  
		  
		  userRegistration pqr=accountData.get(0);
		  
		  return new ResponseEntity<List<userRegistration>>(accountData,
		  HttpStatus.OK);
	  
	  }
}
	  
	/*
	 * @RequestMapping("/home") public String test() { return "Works!"; }
	 * 
	 * 
	 * @GetMapping("/users")
	 * 
	 * @ResponseBody public String listAll(Model model) { List<userRegistration>
	 * listUsers = repo.findAll(); model.addAttribute("listUsers", listUsers);
	 * return listUsers; }
	 * 
	 * }
	 */
