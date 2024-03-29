package dao;

import model.Comment;
import model.Movie;

import java.util.List;

public interface MovieService {
	public void addMovie(Movie movie);
	public void updateMovie(Movie movie);
	public List<Movie> listMovies();
	public Movie getMovieById(int id);
	public void removeMovie(int id);

	void addComment(Comment comment);
	List<Movie> getMoviesByKeyword(String keyword);
}
