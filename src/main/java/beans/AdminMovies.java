package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import Impl.CategoryService;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import model.Category;
import model.Movie;
import Impl.MovieDAOImpl;

@ManagedBean(name = "adminMovies", eager = true)
@SessionScoped
public class AdminMovies implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Movie> allMovies;
    private List<Movie> filteredMovies;
    private Movie selectedMovie;
    private Movie movieToAdd = new Movie();
    private MovieDAOImpl movieDao = new MovieDAOImpl();
    private boolean editMode = false;
    private boolean addMode = false;
    private List<Category> categories;

    @Inject
    private CategoryService categoryService;

    private MovieDAOImpl movieService;
    {
        movieService = new MovieDAOImpl();
    }

    @PostConstruct
    public void init() {

        allMovies = getAllMovies();
        categories = categoryService.getAllCategories();
        System.out.println(categories);
    }

    public void edit() {
        System.out.println("edit clicked");
        editMode = true;
        addMode = false;
    }

    public void cancelUpdate() {
        editMode = false;
    }

    public void prepareAdd() {
        addMode = true;
        editMode = false;
    }

    public void cancelAdd() {
        movieToAdd = new Movie();
        addMode = false;
    }

    public void addMovie() {
        if (movieToAdd != null) {
            movieService.addMovie(movieToAdd);
            System.out.println("Ajout du film avec succès");
            addMessage(FacesMessage.SEVERITY_INFO, "Ajout Réussi", "Ajout du film avec succès");
        } else {
            addMessage(FacesMessage.SEVERITY_WARN, "Ajout échoué", "Erreur lors de l'ajout du film");
        }
        addMode = false;
    }

    public void updateMovie() {
        if (selectedMovie != null) {
            System.out.println("Updating... => " + selectedMovie);
            movieService.updateMovie(selectedMovie);
            System.out.println("Modification du film avec succès");
            addMessage(FacesMessage.SEVERITY_INFO, "Modification Réussie", "Modification du film avec succès");
        } else {
            addMessage(FacesMessage.SEVERITY_WARN, "Modification échouée", "Erreur lors de la modification du film");
        }
        editMode = false;
    }

    public void deleteSelectedMovie() {
        movieService.removeMovie(selectedMovie.getId());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("msgDel", new FacesMessage(FacesMessage.SEVERITY_INFO, "Film Supprimé", selectedMovie.toString()));
        allMovies = getAllMovies();
    }

    public List<Movie> getAllMovies() {
        allMovies = movieDao.listMovies();
        return allMovies;
    }

    public List<Movie> getFilteredMovies() {
        return filteredMovies;
    }

    public void setFilteredMovies(List<Movie> filteredMovies) {
        this.filteredMovies = filteredMovies;
    }

    public Movie getSelectedMovie() {
        return selectedMovie;
    }

    public void setSelectedMovie(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
    }

    public Movie getMovieToAdd() {
        return movieToAdd;
    }

    public void setMovieToAdd(Movie movieToAdd) {
        this.movieToAdd = movieToAdd;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public boolean isAddMode() {
        return addMode;
    }

    public void setAddMode(boolean addMode) {
        this.addMode = addMode;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }
}
