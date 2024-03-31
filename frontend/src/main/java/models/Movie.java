package models;

public class Movie {
    private int id;
    private String title;
    private String description;
    private double rating;
    private String pic;

    public Movie(){};

    public Movie(int id, String title, String description, double rating, String pic) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rating = rating;
        this.pic = pic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}