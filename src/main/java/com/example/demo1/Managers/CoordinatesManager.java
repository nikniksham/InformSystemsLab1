package com.example.demo1.Managers;

import com.example.demo1.DBObjects.Coordinates;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;


@ApplicationScoped
public class CoordinatesManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");

    public Long createNewCoordinates(double x, int y) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Coordinates new_coordinates = new Coordinates();
            new_coordinates.setX(x);
            new_coordinates.setY(y);

            em.persist(new_coordinates);
            em.getTransaction().commit();
            em.close();

            return new_coordinates.getId();
        } catch (Exception e) {
            em.close();
            return null;
        }
    }

    public boolean deleteCoordinatesById(long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Coordinates WHERE id = :id").setParameter("id", id).executeUpdate();
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception ex) {
            em.close();
            return false;
        }
    }

    public Coordinates getCoordinatesById(long id) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT u FROM Coordinates u WHERE u.id = :id").setParameter("id", id);
        try {
            Coordinates coordinates = (Coordinates) query.getSingleResult();
            em.close();
            return coordinates;
        } catch (Exception ex) {
            em.close();
            return null;
        }
    }

    public boolean editCoordinatesById(long coordinates_id, double x, int y) {
        Coordinates coordinates = getCoordinatesById(coordinates_id);
        if (coordinates != null) {
            EntityManager em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
                coordinates.setX(x);
                coordinates.setY(y);
                em.merge(coordinates);
                em.getTransaction().commit();
                em.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
