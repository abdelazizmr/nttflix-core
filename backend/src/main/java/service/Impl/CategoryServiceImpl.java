package service.Impl;

import Impl.CategoryDAOImpl;
import model.Categorie;
import model.Movie;
import service.CategoryService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/categories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoryServiceImpl implements CategoryService {

    private CategoryDAOImpl categoryDAO = new CategoryDAOImpl();

    @Override
    @GET
    public List<Categorie> listCategories() {
        return categoryDAO.listCategories();
    }

    @Override
    @GET
    @Path("/{id}/movies")
    public List<Movie> listOfMoviesByCategory(@PathParam("id") int id) {
        System.out.println("category id = "+id);
        return categoryDAO.getMoviesByCategoryId(id);
    }
}
