package com.example.demo1.Managers;

import com.example.demo1.DBObjects.Information;
import com.example.demo1.ENUMs.TypeOfOperation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.sql.Timestamp;

@ApplicationScoped
public class InformationManager {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");

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
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Information> cq = cb.createQuery(Information.class);
            Root<Information> inf = cq.from(Information.class);
            cq.where(cb.and(cb.equal(inf.get("user_id"), user_id), cb.equal(inf.get("vehicle_id"), vehicle_id), cb.equal(inf.get("typeOfOperation"), TypeOfOperation.CREATE.getId())));
            em.createQuery(cq).setMaxResults(1).getSingleResult();
            em.close();
            return true;
        } catch (Exception ex) {
            em.close();
            return false;
        }
    }

    public Long getAuthor(long vehicle_id) {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Information> cq = cb.createQuery(Information.class);
            Root<Information> inf = cq.from(Information.class);
            cq.where(cb.and(cb.equal(inf.get("vehicle_id"), vehicle_id), cb.equal(inf.get("typeOfOperation"), TypeOfOperation.CREATE.getId())));
            Information information = em.createQuery(cq).setMaxResults(1).getSingleResult();
            em.close();
            return information.getUser_id();
        } catch (Exception ex) {
            em.close();
            return null;
        }
    }

    public boolean checkNewLogs(long last_id) {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Information> cq = cb.createQuery(Information.class);
            Root<Information> inf = cq.from(Information.class);
            cq.where(cb.greaterThan(inf.get("id"), last_id));
            em.createQuery(cq).setMaxResults(1).getSingleResult();
            em.close();
            return true;
        } catch (Exception ex) {
            em.close();
            return false;
        }
    }

    public Long getLastInformationId() {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Information> cq = cb.createQuery(Information.class);
            Root<Information> inf = cq.from(Information.class);
            cq.orderBy(cb.desc(inf.get("id")));
            Information information = em.createQuery(cq).setMaxResults(1).getSingleResult();
            em.close();
            return information.getId();
        } catch (Exception ex) {
            em.close();
            return null;
        }
    }
}
