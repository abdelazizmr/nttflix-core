package Impl;

import dao.MovieService;
import model.Movie;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        MovieService movieService = new MovieDAOImpl();

        List<Movie> listMovies = movieService.listMovies();

        listMovies.stream().forEach(e -> System.out.println(e));
    }





}
