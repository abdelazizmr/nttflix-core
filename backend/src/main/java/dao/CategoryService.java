package dao;

import java.util.List;

import model.Categorie;
import model.Movie;

public interface CategoryService {
    public void addCategorie(Categorie categorie);
    public void updateCategorie(Categorie categorie);
    public List<Categorie> listCategories();
    public List<Categorie> selectCatByKeyword(String keyWord);
    public Categorie getCategorieById(int id);
    public void removeCategorie(int id);

    public List<Movie> getMoviesByCategoryId(int id);
}
