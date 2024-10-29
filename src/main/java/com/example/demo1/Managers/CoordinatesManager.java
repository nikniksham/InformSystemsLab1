package com.example.demo1.Managers;

import com.example.demo1.DBObjects.Coordinates;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


@ApplicationScoped
public class CoordinatesManager {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");

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
        Coordinates detachedCoordinates = getCoordinatesById(id);
        EntityManager em = emf.createEntityManager();
        try {
            Coordinates managedCoordinates = em.merge(detachedCoordinates);
            em.getTransaction().begin();
            em.remove(managedCoordinates);
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
        try {
            Coordinates coordinates = em.find(Coordinates.class, id);
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
