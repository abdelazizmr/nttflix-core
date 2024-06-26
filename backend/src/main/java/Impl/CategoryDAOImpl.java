package Impl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import model.Categorie;
import model.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import dao.CategoryService;
import util.HibernateUtil;

@ManagedBean(name = "categorieService", eager = true)
@RequestScoped
public class CategoryDAOImpl implements CategoryService {
    private static final Logger logger = Logger.getLogger(CategoryDAOImpl.class.getName());

    private final SessionFactory sessionFactory = getSessionFactory();

    protected SessionFactory getSessionFactory() {
        try {
            return HibernateUtil.getSessionFactory();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not create SessionFactory", e);
            throw new IllegalStateException("Could not create SessionFactory");
        }
    }

    @Override
    public void addCategorie(Categorie categorie) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.persist(categorie);
        session.getTransaction().commit();
        logger.info("Categorie saved successfully, Categorie Details="+categorie);
    }

    @Override
    public void updateCategorie(Categorie categorie) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(categorie);
        session.getTransaction().commit();
        logger.info("Categorie updated successfully, Categorie Details="+categorie);
    }

    @Override
    public List<Categorie> listCategories() {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Categorie> CategoriesList = session.createQuery("from Categorie", Categorie.class).list();
        session.getTransaction().commit();
        return CategoriesList;
    }

    @Override
    public List<Categorie> selectCatByKeyword(String keyWord) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Categorie> CategoriesList = session.createQuery("from Categorie c WHERE c.name LIKE '%"+keyWord+"%'", Categorie.class).list();
        session.getTransaction().commit();
        return CategoriesList;
    }
    @Override
    public Categorie getCategorieById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Categorie categorie =  session.getReference(Categorie.class, Integer.valueOf(id));
        session.getTransaction().commit();
        logger.info("Categorie loaded successfully, Categorie details="+categorie);
        return categorie;
    }

    @Override
    public void removeCategorie(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Categorie categorie = session.getReference(Categorie.class, Integer.valueOf(id));

        if(null != categorie){
            session.delete(categorie);
        }
        session.getTransaction().commit();
        logger.info("Categorie deleted successfully, Categorie details="+categorie);
    }

    @Override
    public List<Movie> getMoviesByCategoryId(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();

        // Create CriteriaBuilder
        CriteriaBuilder builder = session.getCriteriaBuilder();
        // Create CriteriaQuery
        CriteriaQuery<Movie> criteria = builder.createQuery(Movie.class);
        // Specify criteria root
        Root<Movie> root = criteria.from(Movie.class);
        // Join with Category to filter movies by category ID
        criteria.select(root).where(builder.equal(root.get("categorie").get("id"), id));

        // Execute query and retrieve results
        List<Movie> movies = session.createQuery(criteria).getResultList();

        session.getTransaction().commit();

        logger.info("Movies by category = "+movies);

        return movies;
    }


}
