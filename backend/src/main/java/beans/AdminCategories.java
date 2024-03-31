package beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import model.Categorie;
import model.Movie;
import Impl.CategoryDAOImpl;

@ManagedBean(name = "adminCategories", eager = true)
@SessionScoped
public class AdminCategories implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Categorie> allCategories;
	private List<Categorie> filteredCategories;
	private List<Movie> allProducts;
	private List<Movie> categoryProducts;
	private Categorie selectedCategorie;
	private Categorie categorieToAdd = new Categorie();
	private int categorie;
	private Movie productToAdd = new Movie();
	private CategoryDAOImpl categDao = new CategoryDAOImpl();
	private boolean editMode = false;
	private boolean addMode = false;

	private CategoryDAOImpl categService;
	{
		categService = new CategoryDAOImpl();
	}

	@PostConstruct
	public void init() {
		allCategories = getAllCategories();
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
		categorieToAdd = new Categorie();
		addMode = false;
	}

	public void addCategorie() {

		if (categorieToAdd != null) {
			categService.addCategorie(categorieToAdd);
			System.out.println("Ajout de la catégorie avec Succès");
			addMessage(FacesMessage.SEVERITY_INFO, "Ajout Réussi", "Ajout de la catégorie avec Succès");
		} else {
			addMessage(FacesMessage.SEVERITY_WARN, "Ajout échoué", "Erreur lors de l'ajout de la catégorie");
		}
		addMode = false;
	}

	public void updateCategorie() {
		if (selectedCategorie != null) {
			System.out.println("Updating... => " + selectedCategorie);
			categService.updateCategorie(selectedCategorie);
			System.out.println("Modification de la catégorie avec Succès");
			addMessage(FacesMessage.SEVERITY_INFO, "Modification Réussie", "Modification de la catégorie avec Succès");
		} else {
			addMessage(FacesMessage.SEVERITY_WARN, "Modification échouée",
					"Erreur lors de la modification de la catégorie");
		}
		editMode = false;
	}

	public void deleteSelectedCategorie() {
		categService.removeCategorie(selectedCategorie.getId());
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("msgDel",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Categorie Supprimée", selectedCategorie.toString()));
		allCategories = getAllCategories();
	}

	public List<Movie> getCategoriyProducts() {
		Categorie categ = categDao.getCategorieById(categorie);
		categoryProducts = new ArrayList<Movie>(categ.getMovies());
		return categoryProducts;
	}

	public List<Categorie> getAllCategories() {
		allCategories = categDao.listCategories();
		return allCategories;
	}

	public Movie getProductToAdd() {
		return productToAdd;
	}

	public void setProductToAdd(Movie productToAdd) {
		this.productToAdd = productToAdd;
	}

	public int getCategorie() {
		return categorie;
	}

	public void setCategorie(int categorie) {
		this.categorie = categorie;
	}

	public void setAllCategories(List<Categorie> allCategories) {
		this.allCategories = allCategories;
	}

	public List<Categorie> getFilteredCategories() {
		return filteredCategories;
	}

	public void setFilteredCategories(List<Categorie> filteredCategories) {
		this.filteredCategories = filteredCategories;
	}

	public List<Movie> getAllProducts() {
		return allProducts;
	}

	public void setAllProducts(List<Movie> allProducts) {
		this.allProducts = allProducts;
	}

	public List<Movie> getCategoryProducts() {
		return categoryProducts;
	}

	public void setCategoryProducts(List<Movie> categoryProducts) {
		this.categoryProducts = categoryProducts;
	}

	public Categorie getSelectedCategorie() {
		return selectedCategorie;
	}

	public void setSelectedCategorie(Categorie selectedCategorie) {
		this.selectedCategorie = selectedCategorie;
	}

	public Categorie getCategorieToAdd() {
		return categorieToAdd;
	}

	public void setCategorieToAdd(Categorie categorieToAdd) {
		this.categorieToAdd = categorieToAdd;
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
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	public Categorie getCategoryById(Integer idcat) {
		return categService.getCategorieById(idcat);
	}
}
