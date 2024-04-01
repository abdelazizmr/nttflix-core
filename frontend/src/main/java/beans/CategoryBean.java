package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.primefaces.shaded.json.JSONArray;
import org.primefaces.shaded.json.JSONObject;

import model.Categorie;
import model.Movie;

@ManagedBean(name = "categoryBean", eager = true)
@SessionScoped
public class CategoryBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String restResource = "http://localhost:8080/NTTflix_core_war/api/categories";

    private List<Categorie> categories = new ArrayList<>();

    private List<Movie> moviesByCategory = new ArrayList<>();
    private Categorie selectedCategory;

    public List<Categorie> getCategories() {
        try {
            HttpGet request = new HttpGet(restResource);
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                JSONArray jsonArray = new JSONArray(responseBody);
                categories.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Categorie category = new Categorie();
                    category.setId(jsonObject.getInt("id"));
                    category.setName(jsonObject.getString("name"));
                    categories.add(category);
                }
            }

            System.out.println("*********** categories *************");
            System.out.println(categories);

        } catch (Exception e) {
            System.out.println("Error while fetching categories: " + e.getMessage());
        }

        return categories;
    }

    public void setCategories(List<Categorie> categories) {
        this.categories = categories;
    }

    public List<Movie> getMoviesByCategory() {
        return moviesByCategory;
    }

    public void setMoviesByCategory(List<Movie> moviesByCategory) {
        this.moviesByCategory = moviesByCategory;
    }

    public void getMoviesByCategoryId(int categoryId) {
        try {
            HttpGet request = new HttpGet(restResource + "/" + categoryId + "/movies");
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                String responseBody = EntityUtils.toString(entity);
                JSONArray jsonArray = new JSONArray(responseBody);
                moviesByCategory.clear();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Movie movie = new Movie();
                    movie.setId(jsonObject.getInt("id"));
                    movie.setTitle(jsonObject.getString("title"));
                    movie.setDescription(jsonObject.getString("description"));
                    movie.setRating(jsonObject.getDouble("rating"));
                    movie.setPic(jsonObject.getString("pic"));

                    // If you have more attributes to set for the movie, you can add them here

                    moviesByCategory.add(movie);

                    System.out.println("********* movies by a given category ***********");
                    System.out.println(moviesByCategory);
                }
            }
        } catch (Exception e) {
            System.out.println("Error while fetching movies by category: " + e.getMessage());
        }
    }

    public Categorie getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Categorie selectedCategory) {
        this.selectedCategory = selectedCategory;
    }
}
