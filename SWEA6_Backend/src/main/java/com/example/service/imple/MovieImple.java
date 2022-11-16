package com.example.service.imple;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.demo.repo.*;
import com.example.excepion.IdNotFoundException;
import com.example.demo.model.Movie;
import com.example.demo.model.userRegistration;
import com.example.service.MovieService;

@Service
public class MovieImple implements MovieService {
	
	@Autowired
	MovieRepo movierepo;
	
	@Override
	@ResponseBody
	public List<Movie> getAllMovies() {
		return movierepo.findAll();
	}
	
	
	@Override
	public void save(Movie movie) {
		movierepo.save(movie);
	}


	@Override
	@ResponseBody
	public Movie getMovieById(int id) throws IdNotFoundException {
		List<Movie> movie = movierepo.findByid(id);
		if (movie.isEmpty())
			throw new IdNotFoundException("Sorry user with " + id + " not found!");
		return movie.get(0);
	}
	
}
