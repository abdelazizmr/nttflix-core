package beans;

import java.io.*;
import java.util.List;
import java.util.UUID;


import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import Impl.CategoryDAOImpl;
import dao.CategoryService;
import dao.MovieService;

import model.Categorie;
import model.Movie;
import Impl.MovieDAOImpl;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

@ManagedBean(name = "adminMovies", eager = true)
@SessionScoped
public class AdminMovies implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Movie> allMovies;
    private List<Movie> filteredMovies;
    private Movie selectedMovie;
    private Movie movieToAdd = new Movie();

    private boolean editMode = false;
    private boolean addMode = false;
    private String path;

    private UploadedFile file;

    private List<Categorie> allCategories;
    private CategoryService categService;
    {
        categService = new CategoryDAOImpl();
    }

    private MovieService movieService;
    {
        movieService = new MovieDAOImpl();
    }

    @PostConstruct
    public void init() {
        allMovies = getAllMovies();
    }

    public List<Categorie> getAllCategories() {
        allCategories = categService.listCategories();
        return allCategories;
    }

    public void setAllCategories(List<Categorie> categories) {
        this.allCategories = categories;
    }

    public void setAllMovies(List<Movie> allMovies) {
        this.allMovies = allMovies;
    }

    public void edit() {
        System.out.println("edit clicked");
        editMode = true;
        addMode = false;
    }

    public void cancelUpdate() {
        editMode = false;
    }

    public void prepareAdd() {
        addMode = true;
        editMode = false;
    }

    public void cancelAdd() {
        movieToAdd = new Movie();
        addMode = false;
    }

    public void addMovie() {
        if (movieToAdd != null) {
            movieService.addMovie(movieToAdd);
            System.out.println("Ajout du film avec succès");
            addMessage(FacesMessage.SEVERITY_INFO, "Ajout Réussi", "Ajout du film avec succès");
        } else {
            addMessage(FacesMessage.SEVERITY_WARN, "Ajout échoué", "Erreur lors de l'ajout du film");
        }
        addMode = false;
    }

    public void updateMovie() {
        if (selectedMovie != null) {
            System.out.println("Updating... => " + selectedMovie);
            movieService.updateMovie(selectedMovie);
            System.out.println("Modification du film avec succès");
            addMessage(FacesMessage.SEVERITY_INFO, "Modification Réussie", "Modification du film avec succès");
        } else {
            addMessage(FacesMessage.SEVERITY_WARN, "Modification échouée", "Erreur lors de la modification du film");
        }
        editMode = false;
    }

    public void deleteSelectedMovie() {
        movieService.removeMovie(selectedMovie.getId());
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage("msgDel", new FacesMessage(FacesMessage.SEVERITY_INFO, "Film Supprimé", selectedMovie.toString()));
        allMovies = getAllMovies();
    }

    public List<Movie> getAllMovies() {
        allMovies = movieService.listMovies();
        return allMovies;
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

    public Movie getMovieToAdd() {
        return movieToAdd;
    }

    public void setMovieToAdd(Movie movieToAdd) {
        this.movieToAdd = movieToAdd;
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
    }

    public boolean isAddMode() {
        return addMode;
    }

    public void setAddMode(boolean addMode) {
        this.addMode = addMode;
    }



    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }


    public void copyFile(String fileName, InputStream in) {
        try {
            OutputStream out = new FileOutputStream(new File(fileName));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println("Erreur dans CopyFile !");
            System.out.println(e.getMessage());
        }
    }

    public void upload(FileUploadEvent event) {
        System.out.println("**************** Calling upload ! ****************");

        if (file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);

            try {
                copyFile(getPath() + event.getFile().getFileName(), event.getFile().getInputStream());
            } catch (IOException e) {
                System.out.println("Erreur dans upload !");
                e.printStackTrace();
            }
            System.out.println("Upload avec Succès");
        } else {
            System.out.println("File n'est pas différent de NULL");
        }
    }

    public String getPath() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
                .getContext();
        path = servletContext.getRealPath("") + "images" + File.separator + "photos";
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        System.out.println("Name = " + file.getFileName() + " ans Size = " + file.getSize());
        this.file = file;
        if (this.file.getSize() > 0) {
            System.out.println("Afeter test file != null => file = "+this.file);
            String randomName = UUID.randomUUID().toString() + file.getFileName();
            try {
                copyFile(getPath() + File.separator + randomName, file.getInputStream());
                if (editMode) {
                    selectedMovie.setPic(randomName);
                }
                if (addMode) {
                    movieToAdd.setPic(randomName);
                }

            } catch (IOException e) {
                System.out.println("Erreur dans upload !");
                e.printStackTrace();
            }
        }
    }
}