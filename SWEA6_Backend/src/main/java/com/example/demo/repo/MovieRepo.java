package com.example.demo.repo;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Movie;
import com.example.demo.model.userRegistration;

@Repository
public interface MovieRepo extends JpaRepository<Movie,Integer> {
	List<Movie> findByid(int id);
	/*@Query("SELECT title FROM movies WHERE currentrunning = :value")
	List<Movie> findBycurrentrunning(int value);*/
}


