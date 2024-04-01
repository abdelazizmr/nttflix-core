package service;

import model.Categorie;
import model.Movie;

import java.util.List;

public interface CategoryService {
    public List<Categorie> listCategories();
    public List<Movie> listOfMoviesByCategory(int id);
}
