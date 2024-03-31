package models;

// Actor.java
public class Actor {
    private int id;
    private String name;
    private String pic;
    private int movieId; // This is used to link an actor to a movie

    // Constructor, getters, and setters

    public Actor(int id, String name, String pic, int movieId) {
        this.id = id;
        this.name = name;
        this.pic = pic;
        this.movieId = movieId;
    }

    public Actor(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
}