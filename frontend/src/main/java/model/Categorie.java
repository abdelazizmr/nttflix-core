package model;


import java.util.List;

public class Categorie {
	private int id;

	private String name;

	private List<Movie> movies;

	public Categorie() {
	}

	public int getId() {
		return this.id;
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

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(List<Movie> movies) {
		this.movies = movies;
	}


	@Override
	public String toString() {
		return "Categorie{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}