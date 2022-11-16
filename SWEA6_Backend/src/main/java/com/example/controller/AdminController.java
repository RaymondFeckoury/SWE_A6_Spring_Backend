package com.example.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
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
import com.example.demo.model.Movie;
import com.example.demo.model.ShowRoom;
import com.example.demo.model.UpcomingShow;
import com.example.demo.model.showtimes;
import com.example.demo.repo.AdminRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.repo.scheduling;
import com.example.excepion.IdNotFoundException;
import com.example.demo.repo.MovieRepo;
import com.example.demo.repo.UpcomingShowRepo;
import com.example.demo.repo.ShowRoomRepo;
import com.example.service.AdminService;
import com.example.service.MovieService;
import com.example.service.imple.Emailsenderservice;
import com.example.service.imple.userimple;


@RestController
@CrossOrigin(origins="http://localhost:4200") 
@RequestMapping("/admin")	
public class AdminController {
	
	@Autowired
	private AdminRepo repo;
	@Autowired 
	private AdminService as;
	@Autowired
	UserRepo ur;  
	@Autowired
	Emailsenderservice emailsenderservice;
	@Autowired
	private MovieRepo movierepo;
	@Autowired
	private MovieService ms;
	@Autowired
	private ShowRoomRepo showroomrepo;
	@Autowired
	private UpcomingShowRepo upcomingshowrepo;
	@Autowired
	private scheduling schedule;
	
	  
	@PostMapping("/admin")
	public ResponseEntity<AdminRegistration> registerUser(@RequestBody AdminRegistration AdminRegistration) {
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
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
				 	  
	@GetMapping(path = "/alladmins") 
	public ResponseEntity<?> getAdmins() { 
		return ResponseEntity.status(200).body(as.getAllAdmins());
	}
	
	@PostMapping("/addmovie")
	public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
		 return ResponseEntity.ok(movierepo.save(movie));
	}
	
	// Should only be used to add all 6 show rooms before running the project. Not needed afterwards
	@PostMapping("/addshowroom")
	public ResponseEntity<ShowRoom> addShowRoom(@RequestBody ShowRoom showroom) {
		 return ResponseEntity.ok(showroomrepo.save(showroom));
	}
	
	@PostMapping("/addupcomingshow")
	public ResponseEntity<UpcomingShow> addShow(@RequestBody UpcomingShow show) {
		 return ResponseEntity.ok(upcomingshowrepo.save(show));
	}
	
	@GetMapping(path = "/allmovies") 
	public ResponseEntity<?> getMovies() { 
		return ResponseEntity.status(200).body(ms.getAllMovies());
	}
	
	
	// To display movie info on the booking page
	@GetMapping(path = "/getmovieinfo/{movieId}") 
	public ResponseEntity<?> getMovieInfo(@PathVariable int movieId, @RequestBody Movie movie) throws IdNotFoundException { 
		return ResponseEntity.status(200).body(ms.getMovieById(movieId));
	}
	
	// Returns the title, rating, and trailer of each "coming soon" movie
	@GetMapping(path = "/currentlyrunning") 
	public List<List<String>> getCurrentlyRunning() {
		List<String> titles = new ArrayList<>();
		List<String> ratings = new ArrayList<>();
		List<String> trailers = new ArrayList<>();
		ResultSet resultSet = null;
	 	try {
	 		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemabooking", "root", "password");
	 		java.sql.Statement stmt = conn.createStatement();
	 		stmt = conn.createStatement();
	 		resultSet = stmt.executeQuery("SELECT title,rating,trailer FROM movies WHERE currentrunning = 1");
	 		while(resultSet.next()) {
	 			titles.add(resultSet.getString("title"));
	 			titles.add(resultSet.getString("rating"));
	 			titles.add(resultSet.getString("trailer"));
	 		}
	 		List<List<String>> allData = new ArrayList<>();
	 		allData.add(titles);
	 		allData.add(ratings);
	 		allData.add(trailers);
	 		return allData;
	 	} catch (SQLException e1) {
	 		e1.printStackTrace();
	 	}
	 	return  null;
	}
	
	// Returns the title, rating, and trailer of each upcoming movie
	@GetMapping(path = "/upcomingmovies") 
	public List<List<String>> getUpcomingMovies() {
		List<String> titles = new ArrayList<>();
		List<String> ratings = new ArrayList<>();
		List<String> trailers = new ArrayList<>();
		ResultSet resultSet = null;
		try {
		 	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemabooking", "root", "password");
		 	java.sql.Statement stmt = conn.createStatement();
		 	stmt = conn.createStatement();
		 	resultSet = stmt.executeQuery("SELECT title,rating,trailer FROM movies WHERE currentrunning = 0");
		 	while(resultSet.next()) {
		 		titles.add(resultSet.getString("title"));
		 		titles.add(resultSet.getString("rating"));
		 		titles.add(resultSet.getString("trailer"));
		 	}
		 	List<List<String>> allData = new ArrayList<>();
		 	allData.add(titles);
		 	allData.add(ratings);
		 	allData.add(trailers);
		 	return allData;
		 } catch (SQLException e1) {
		 	e1.printStackTrace();
		 }
		 return  null;
	}
	
	  @PostMapping("/schedulemovie")
	  public boolean schedulemovie(@RequestBody showtimes showtime) throws SQLException {
		  List<showtimes> data = schedule.findAll();
		  DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		  String date = df.format(showtime.getDate());
		  Time time = showtime.getTime();
		  String moviename = showtime.getMoviename();
		  Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cinemabooking", "root", "password");
		  java.sql.Statement stmt = conn.createStatement();
		  int movieid = 0;
		  ResultSet resultSet = stmt.executeQuery("SELECT id FROM movies WHERE title = "+ "'" + moviename + "'");
		  while(resultSet.next()) {
			  movieid = resultSet.getInt("id");
			  System.out.println(movieid);
		  }
		  showtime.setMovieid(movieid);
		  int count = 0;
		  for(int i=0;i<data.size();i++) {
			 String cdate = df.format(data.get(i).getDate());
			 System.out.println("date"+date+" " +cdate+" " + "time"+time+"  " +data.get(i).getTime()+" "+"movieid"+ movieid+" "+data.get(i).getMovieid()+" "+"srid"+data.get(i).getShowroomid()+" "+showtime.getShowroomid());
			 if(date.equals(cdate) && time.equals(data.get(i).getTime()) && data.get(i).getShowroomid().equals(showtime.getShowroomid())) {
				 count++;
				 System.out.println();
			 }
			
		  }
		  
		  if (count>=1) {
			 return false;
		  } else {
			  schedule.save(showtime);
			  return true;
		  }
		  
	  }
	  
}