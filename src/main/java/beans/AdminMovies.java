package beans;

import Impl.MovieDAOImpl;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.bean.ManagedBean;
import jakarta.faces.bean.SessionScoped;
import jakarta.faces.context.FacesContext;
import model.Movie;


import java.io.Serializable;
import java.util.List;
@ManagedBean(name = "adminMovies", eager = true)
@SessionScoped
public class AdminMovies implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Movie> allMovies;
    private Movie selectedMovie;
    private Movie movieToAdd = new Movie();
    private MovieDAOImpl movieDAO = new MovieDAOImpl();
    private boolean editMode = false;
    private boolean addMode = false;

    @PostConstruct
    public void init() {
        allMovies = getAllMovies();
    }

    public void edit() {
        System.out.println("Edit clicked");
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
            movieDAO.addMovie(movieToAdd);
            System.out.println("Movie added successfully");
            addMessage(FacesMessage.SEVERITY_INFO, "Movie Added", "Movie added successfully");
        } else {
            addMessage(FacesMessage.SEVERITY_WARN, "Addition Failed", "Error adding movie");
        }
        addMode = false;
    }

    public void updateMovie() {
        if (selectedMovie != null) {
            System.out.println("Updating movie: " + selectedMovie);
            movieDAO.updateMovie(selectedMovie);
            System.out.println("Movie updated successfully");
            addMessage(FacesMessage.SEVERITY_INFO, "Update Successful", "Movie updated successfully");
        } else {
            addMessage(FacesMessage.SEVERITY_WARN, "Update Failed", "Error updating movie");
        }
        editMode = false;
    }

    public void deleteSelectedMovie() {
        movieDAO.removeMovie(selectedMovie.getId());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("msgDel", new FacesMessage(FacesMessage.SEVERITY_INFO, "Movie Deleted", selectedMovie.toString()));
        allMovies = getAllMovies();
    }

    public List<Movie> getAllMovies() {
        allMovies = movieDAO.listMovies();
        return allMovies;
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

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }
}
