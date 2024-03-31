package model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;



@Entity
@Table(name="categorie")

public class Categorie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;


	//bi-directional many-to-one association to Produit
	@OneToMany(mappedBy="categorie")
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

	public Movie addMovie(Movie movie) {
		getMovies().add(movie);
		movie.setCategorie(this);

		return movie;
	}

	public Movie removeMovie(Movie movie) {
		getMovies().remove(movie);
		movie.setCategorie(null);

		return movie;
	}

	@Override
	public String toString() {
		  return id + " => " + name ;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Categorie))//changed this from (getClass() != obj.getClass())
			return false;
		Categorie other = (Categorie) obj;
		if (id == 0) {
			if (other.id != 0)
				return false;
		} else if (! (id==other.id))
			return false;
		return true;
	}
}