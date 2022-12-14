package com.example.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.AdminRegistration;
import com.example.demo.model.userRegistration;
import com.example.demo.repo.AdminRepo;
import com.example.demo.repo.UserRepo;
import com.example.service.AdminService;
import com.example.service.imple.Emailsenderservice;
import com.example.service.imple.userimple;


@RestController
@CrossOrigin(origins="http://localhost:4200") 
@RequestMapping("/adm")	
public class AdminController {
	@Autowired
	private AdminRepo repo;

	
	  @Autowired 
	  private AdminService as;
	  
	  
	  @Autowired
	  UserRepo ur;
	  
	  @Autowired
	  Emailsenderservice emailsenderservice;
	
	  
	  @PostMapping("/admin")
	  public ResponseEntity<AdminRegistration> registerUser(@RequestBody AdminRegistration AdminRegistration) {
		 //logger.info("Received controller");
		 System.out.println("Controller called for user Registration "+AdminRegistration.toString());
		 return ResponseEntity.ok(repo.save(AdminRegistration));
	  }
	  
	  @PostMapping("/adminlogin")
	  public ResponseEntity<Boolean> adminLogin(@RequestBody AdminRegistration AdminRegistration) {
		 return ResponseEntity.ok(repo.existsByEmailAndPassword(AdminRegistration.getEmail(), AdminRegistration.getPassword()));
	  }
	  @PostMapping("/newadmin")
	  public boolean newadmin(@RequestBody AdminRegistration AdminRegistration) throws MessagingException{
		  List<AdminRegistration> accountData = repo.findByemail(AdminRegistration.getEmail());
			 if(accountData.isEmpty()) {
				 List<userRegistration> accountData2 = ur.findByemail(AdminRegistration.getEmail()); 
				 if(accountData.isEmpty()) {
					 repo.save(AdminRegistration);
					 emailsenderservice.sendemailwithattachment(AdminRegistration.getEmail(),"Registered as admin succesfully","admin registration");
					 return true;
				 }
				 else {
					 return false;
				 }
				 }else {
					 return false;
				 }
			 }
				 
		  
	  
	  
	  // only admins have rights to inspect all users data
	  
	  @GetMapping(path = "/alladmins") 
	  public ResponseEntity<?> getAdmins() { 
		// get all users, returns empty list if user table is empty

		  return ResponseEntity.status(200).body(as.getAllAdmins());
	  
	  
	  }
	  
		/*
		 * @GetMapping("/admin/{id}") public ResponseEntity<List<AdminRegistration>>
		 * getAccountByNumber(@PathVariable("id") int id) {
		 * 
		 * List<AdminRegistration> accountData = repo.findByid(id);
		 * 
		 * if (accountData.isEmpty()) {
		 * 
		 * return new ResponseEntity<List<AdminRegistration>>(HttpStatus.NO_CONTENT); }
		 * 
		 * System.out.println(id);
		 * 
		 * 
		 * AdminRegistration pqr=accountData.get(0);
		 * 
		 * return new
		 * ResponseEntity<List<AdminRegistration>>(accountData,HttpStatus.OK);
		 * 
		 * }
		 */
}