package service.Impl;

import Impl.MovieDAOImpl;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.Comment;
import model.Movie;
import model.Response;
import service.MovieService;

import java.util.List;

@Path("/movies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MovieServiceImpl implements MovieService {

    private MovieDAOImpl movieDAO = new MovieDAOImpl();

    @Override
    @GET
    @Path("/")
    public List<Movie> listMovies() {
        return movieDAO.listMovies();
    }

    @Override
    @GET
    @Path("/{id}")
    public Movie getMovie(@PathParam("id") int id) {
        return movieDAO.getMovieById(id);
    }

    @Override
    @GET
    @Path("/search")
    public List<Movie> getMovieByKeyword(@QueryParam("keyword") String keyword) {
        return movieDAO.getMoviesByKeyword(keyword);
    }

    @Override
    @POST
    @Path("/{movieId}/comments/add")
    public Response addComment(int movieId, Comment comment) {
        Response response = new Response();
        Movie movie = movieDAO.getMovieById(movieId);
        if (movie == null) {
            response.setStatus(false);
            response.setMessage("Movie doesn't exist");
            return response;
        }
        // Associate comment with movie
        comment.setMovie(movie);
        movieDAO.addComment(comment);
        response.setStatus(true);
        response.setMessage("Comment added successfully");
        return response;
    }

    @Override

    public List<Movie> getMoviesByKeyword(String keyword) {
        return movieDAO.getMoviesByKeyword(keyword);
    }

}
