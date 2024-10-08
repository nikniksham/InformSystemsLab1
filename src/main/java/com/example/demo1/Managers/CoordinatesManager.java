package com.example.demo1.Managers;

import com.example.demo1.DBObjects.Coordinates;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


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
}
