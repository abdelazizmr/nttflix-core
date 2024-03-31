package service;



import model.Comment;
import model.Movie;
import model.Response;

import java.util.List;


public interface MovieService {

    List<Movie> listMovies();
    Movie getMovie(int id);
    List<Movie> getMovieByKeyword(String keyword);
    Response addComment(int movieId, Comment comment);


}