package com.example.service;

import java.util.List;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.demo.model.Movie;
import com.example.demo.model.userRegistration;
import com.example.excepion.IdNotFoundException;

public interface MovieService {
	
	@ResponseBody
	List<Movie> getAllMovies();
	public void save(Movie movie);
	Movie getMovieById(int id) throws IdNotFoundException;
	
}
