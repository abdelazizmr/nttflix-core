package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import com.google.gson.Gson;
import model.Categorie;
import model.Movie;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpResponse;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

@ManagedBean(name = "movieRestBean", eager = true)
@SessionScoped
public class MovieBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String restResource = "http://localhost:8080/NTTflix_core_war/api/movies";

    private List<Movie> movies = new ArrayList<>();
    private Movie selectedMovie = new Movie();
    private String result = "No data";

    private List<Movie> filteredMovies;



    public void getMovieDetails(int id) {
        try {
            HttpGet request = new HttpGet(restResource + "/" + id);
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                JSONObject jsonObject = new JSONObject(responseBody);

                selectedMovie = new Movie();
                selectedMovie.setId(jsonObject.getInt("id"));
                selectedMovie.setTitle(jsonObject.getString("title"));
                selectedMovie.setDescription(jsonObject.getString("description"));
                selectedMovie.setRating(jsonObject.getDouble("rating"));
                selectedMovie.setPic(jsonObject.getString("pic"));

                JSONObject categorieObj = jsonObject.getJSONObject("categorie");
                Categorie categorie = new Categorie();
                categorie.setId(categorieObj.getInt("id"));
                categorie.setName(categorieObj.getString("name"));

                selectedMovie.setCategorie(categorie);
                System.out.println("******************** Selected Movie *****************");
                System.out.println(selectedMovie);

                // If you have comments, you can parse them here
            }
        } catch (Exception e) {
            System.out.println("Error while fetching movie details: " + e.getMessage());
        }
    }



    public List<Movie> getMovies() {
        try {
            HttpGet request = new HttpGet(restResource);
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                JSONArray jsonArray = new JSONArray(responseBody);
                movies.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Movie movie = new Movie();
                    movie.setId(jsonObject.getInt("id"));
                    movie.setTitle(jsonObject.getString("title"));
                    movie.setDescription(jsonObject.getString("description"));
                    movie.setRating(jsonObject.getDouble("rating"));
                    movie.setPic(jsonObject.getString("pic"));

                    JSONObject categorieObj = jsonObject.getJSONObject("categorie");
                    Categorie categorie = new Categorie();
                    categorie.setId(categorieObj.getInt("id"));
                    categorie.setName(categorieObj.getString("name"));

                    movie.setCategorie(categorie);

                    // If you have comments, you can parse them here

                    movies.add(movie);
                }
            }
        } catch (Exception e) {
            System.out.println("Error while fetching movies: " + e.getMessage());
        }
        System.out.println("***************** Movies List *********************");
        System.out.println(movies);
        return movies;
    }



    public void setMovies(List<Movie> movies) {
        this.movies = movies;
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


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
