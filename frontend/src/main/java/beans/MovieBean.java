package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import com.google.gson.Gson;
import model.Movie;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpResponse;

@ManagedBean(name = "movieRestBean", eager = true)
@SessionScoped
public class MovieBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String restResource = "http://localhost:8080/RestWebservice/api/movies/";

    private List<Movie> movies = new ArrayList<>();
    private Movie selectedMovie = new Movie();
    private String result = "No data";

    public void getAllMovies() {
        try {
            HttpGet request = new HttpGet(restResource);
            CloseableHttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(request);
            String responseBody = response.getReasonPhrase();
            Gson gson = new Gson();
            Movie[] movieArray = gson.fromJson(responseBody, Movie[].class);
            movies = Arrays.asList(movieArray);
        } catch (Exception e) {
            System.out.println("Error while fetching movies: " + e.getMessage());
        }
    }

    public void getOneMovie(int id) {
        try {
            HttpGet request = new HttpGet(restResource + id);
            CloseableHttpClient client = HttpClients.createDefault();
            HttpResponse response = client.execute(request);
            String responseBody = response.getReasonPhrase();
            Gson gson = new Gson();
            selectedMovie = gson.fromJson(responseBody, Movie.class);
        } catch (Exception e) {
            System.out.println("Error while fetching movie: " + e.getMessage());
        }
    }


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


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
