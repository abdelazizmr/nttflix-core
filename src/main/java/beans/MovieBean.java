package beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Movie;
import model.Comment;
import service.MovieService;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "movieBean")
@SessionScoped
public class MovieBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private MovieService movieService;

    private List<Movie> movies;
    private Movie selectedMovie;
    private String comment;
    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public Movie getSelectedMovie() {
        return selectedMovie;
    }

    public void setSelectedMovie(Movie selectedMovie) {
        this.selectedMovie = selectedMovie;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public void loadMovies() {
        movies = movieService.listMovies();
    }
    public void addComment() {
        try {
            if (selectedMovie != null && comment != null && !comment.isEmpty()) {
                Comment newComment = new Comment();
                newComment.setComment(comment);
                movieService.addComment(selectedMovie.getId(), newComment);
                loadMovies();
            }
        } catch (Exception e) {
            // Handle exception (e.g., log error, show error message)
        }
    }
}
