package com.example.demo1.Managers;

import com.example.demo1.DBObjects.Information;
import com.example.demo1.ENUMs.TypeOfOperation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import java.sql.Timestamp;

@ApplicationScoped
public class InformationManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");

    public boolean createInformation(long user_id, long vehicle_id, TypeOfOperation typeOfOperation) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Information new_information = new Information();
            new_information.setUser_id(user_id);
            new_information.setVehicle_id(vehicle_id);
            new_information.setTypeOfOperation(typeOfOperation.getId());
            new_information.setModifDate(new Timestamp(new java.util.Date().getTime()));

            em.persist(new_information);
            em.getTransaction().commit();
            em.close();

            return true;
        } catch (Exception e) {
            em.close();
            return false;
        }
    }

    public boolean checkUserIsAuthor(long user_id, long vehicle_id) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT u FROM Information u WHERE u.user_id = :user_id AND u.vehicle_id = :vehicle_id AND u.typeOfOperation = :typeOfOperation")
                .setParameter("user_id", user_id).setParameter("vehicle_id", vehicle_id).setParameter("typeOfOperation", TypeOfOperation.CREATE.getId());
        try {
            Information information = (Information) query.getSingleResult();
            em.close();
            return true;
        } catch (Exception ex) {
            em.close();
            return false;
        }
    }
}
