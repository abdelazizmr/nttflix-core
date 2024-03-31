package Impl;

import dao.MovieService;
import model.Comment;
import model.Movie;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MovieDAOImpl implements Serializable, MovieService {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(MovieDAOImpl.class.getName());

    public MovieDAOImpl() {
    }

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
    public void addMovie(Movie movie) {
        System.out.println(movie);
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.persist(movie);
        session.getTransaction().commit();
        logger.info("Movie successfully saved, Movie Details=" + movie);
    }

    @Override
    public void updateMovie(Movie movie) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.merge(movie);
        session.getTransaction().commit();
        logger.info("Movie successfully updated, Movie Details=" + movie);
    }

    @Override
    public List<Movie> listMovies() {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Movie> moviesList = session.createQuery("from Movie", Movie.class).list();
        session.getTransaction().commit();
        return moviesList;
    }

    @Override
    public Movie getMovieById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Movie movie = session.get(Movie.class, id);
        session.getTransaction().commit();
        return movie;
    }

    @Override
    public void removeMovie(int id) {
        try (Session session = this.sessionFactory.openSession()) {
            session.beginTransaction();
            Movie movie = session.get(Movie.class, id);
            if (movie != null) {
                session.remove(movie);
                session.getTransaction().commit();
                //logger.info("Movie deleted successfully, Movie details: " + movie);
            }
        } catch (HibernateException ex) {
            logger.info("Error deleting movie with ID " + id);
            throw ex;
        }
    }

    @Override
    public void addComment(Comment comment) {
        System.out.println(comment);
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.persist(comment);
        session.getTransaction().commit();
        logger.info("Movie successfully saved, Movie Details=" + comment);
    }
    @Override
    public List<Movie> getMoviesByKeyword(String keyword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Movie> query = session.createQuery("FROM Movie WHERE title LIKE :keyword", Movie.class);
            query.setParameter("keyword", "%" + keyword + "%");
            return query.getResultList();
        } catch (Exception e) {
            // Handle any exceptions here
            e.printStackTrace();
            return null;
        }
    }


}

