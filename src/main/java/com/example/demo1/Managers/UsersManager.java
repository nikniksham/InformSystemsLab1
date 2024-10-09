package com.example.demo1.Managers;

import com.example.demo1.DBObjects.Users;
import jakarta.persistence.*;

import jakarta.enterprise.context.ApplicationScoped;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@ApplicationScoped
public class UsersManager {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
    private final MessageDigest md = MessageDigest.getInstance("SHA-512");

    public UsersManager() throws NoSuchAlgorithmException {}

    public Users getUserById(long id) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT u FROM Users u WHERE u.id = :id").setParameter("id", id);
        try {
            Users user = (Users) query.getSingleResult();
            em.close();
            return user;
        } catch (Exception ex) {
            em.close();
            return null;
        }
    }

    public boolean checkLoginDontExists(String login) {
        EntityManager em = emf.createEntityManager();
        try {
            em.createQuery("SELECT u FROM Users u WHERE u.login = :login").setParameter("login", login).getSingleResult();
            em.close();
            return true;
        } catch (Exception ex) {
            em.close();
            return false;
        }
    }

    public boolean checkAdminExists() {
        EntityManager em = emf.createEntityManager();
        try {
            em.createQuery("SELECT u FROM Users u WHERE u.status = 2").getSingleResult();
            em.close();
            return true;
        } catch (Exception ex) {
            em.close();
            return false;
        }
    }

    public Long loginUser(String login, String password) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT u FROM Users u WHERE u.login = :login").setParameter("login", login);
        try {
            Users us_res = (Users) query.getSingleResult();
            if (!Arrays.equals(us_res.getPassword(), md.digest(password.getBytes(StandardCharsets.UTF_8)))) {
                em.close();
                return null;
            }
            em.close();
            return us_res.getId();
        } catch (Exception ex) {
            em.close();
            return null;
        }
    }

    public boolean addUser(String login, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Users new_user = new Users();
            new_user.setLogin(login);
            new_user.setPassword(md.digest(password.getBytes(StandardCharsets.UTF_8)));
            if (checkAdminExists()) {
                new_user.setStatus(0);
            } else {
                new_user.setStatus(2);
            }

            em.persist(new_user);
            em.getTransaction().commit();
            em.close();

            return true;
        } catch (Exception e) {
            em.close();
            return false;
        }
    }
}
