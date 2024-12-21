package com.example.demo1.Managers;

import com.example.demo1.DBObjects.ImportLogs;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.*;

@ApplicationScoped
public class ImportLogsManager {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");

    public boolean createImportLog(long user_id, String filename, boolean result, int count) {
        EntityManager em = emf.createEntityManager();
//        try {
            em.getTransaction().begin();

            ImportLogs new_log = new ImportLogs();
            new_log.setUser_id(user_id);
            new_log.setFilename(filename);
            new_log.setCreationDate(new Timestamp(new java.util.Date().getTime()));
            new_log.setResult(result);
            new_log.setCount(count);

            em.persist(new_log);
            em.getTransaction().commit();
            em.close();

            return true;
//        } catch (Exception e) {
//            em.close();
//            return false;
//        }
    }

    public List<ImportLogs> getAllLogsPropUser(long user_id) {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ImportLogs> cq = cb.createQuery(ImportLogs.class);
            Root<ImportLogs> log = cq.from(ImportLogs.class);
            cq.orderBy(cb.desc(log.get("id")));
            cq.where(cb.equal(log.get("user_id"), user_id));
            List<ImportLogs> logs = em.createQuery(cq).getResultList();
            em.close();
            return logs;
        } catch (Exception ex) {
            em.close();
            return new ArrayList<>();
        }
    }

    public List<ImportLogs> getAllLogs() {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ImportLogs> cq = cb.createQuery(ImportLogs.class);
            Root<ImportLogs> log = cq.from(ImportLogs.class);
            cq.orderBy(cb.desc(log.get("id")));
            List<ImportLogs> logs = em.createQuery(cq).getResultList();
            em.close();
            return logs;
        } catch (Exception ex) {
            em.close();
            return new ArrayList<>();
        }
    }
}
