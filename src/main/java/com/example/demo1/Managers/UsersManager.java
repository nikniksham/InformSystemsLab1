package com.example.demo1.Managers;

import com.example.demo1.DBObjects.Users;
import jakarta.persistence.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class UsersManager {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
    private final MessageDigest md = MessageDigest.getInstance("SHA-512");

    public UsersManager() throws NoSuchAlgorithmException {}

    public Users getUserById(Long id) {
        if (id == null) {return null;}
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

    public boolean checkLoginExists(String login) {
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

    public boolean checkAdminExists() {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = cb.createQuery();
            Root us = cq.from(Users.class);
            cq.where(cb.equal(us.get("status"), 2));
            em.createQuery(cq).setMaxResults(1).getSingleResult();
            em.close();
            return true;
        } catch (Exception ex) {
            em.close();
            return false;
        }
    }

    public boolean addUser(String login, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            Users new_user = new Users();
            new_user.setLogin(login);
            new_user.setPassword(md.digest(password.getBytes(StandardCharsets.UTF_8)));
            if (!checkAdminExists()) {
                new_user.setStatus(2);
            } else {
                new_user.setStatus(0);
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

    public boolean submitAnApplication(Users user) {
        if (user != null && user.getStatus() == 0) {
            EntityManager em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
                user.setStatus(1);
                em.merge(user);
                em.getTransaction().commit();
                em.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public boolean approveAnApplication(Long user_id) {
        if (user_id != null) {
            Users user = getUserById(user_id);
            if (user != null) {
                EntityManager em = emf.createEntityManager();
                try {
                    em.getTransaction().begin();
                    user.setStatus(2);
                    em.merge(user);
                    em.getTransaction().commit();
                    em.close();
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean deleteUser(Long user_id) {
        if (user_id != null) {
            Users user = getUserById(user_id);
            if (user != null && user.getStatus() < 2) {
                EntityManager em = emf.createEntityManager();
                try {
                    em.getTransaction().begin();
                    em.createQuery("DELETE FROM Users WHERE id = :id").setParameter("id", user_id).executeUpdate();
                    em.getTransaction().commit();
                    em.close();
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return false;
    }

    public boolean checkPasswordUser(String password, Users user) {
        if (user != null && password != null) {
            return Arrays.equals(user.getPassword(), md.digest(password.getBytes(StandardCharsets.UTF_8)));
        }
        return false;
    }

    public boolean setNewPassword(String password, Users user) {
        if (user != null) {
            EntityManager em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
                user.setPassword(md.digest(password.getBytes(StandardCharsets.UTF_8)));
                em.merge(user);
                em.getTransaction().commit();
                em.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public boolean editUser(String new_login, Users user) {
        if (user != null) {
            if (checkLoginExists(new_login)) {
                return false;
            }
            EntityManager em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
                user.setLogin(new_login);
                em.merge(user);
                em.getTransaction().commit();
                em.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public List<Users> getAllUsers() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT u FROM Users u ORDER BY u.status DESC");
        try {
            List<Users> resultList = (List<Users>) query.getResultList();
            em.close();
            return resultList;
        } catch (Exception ex) {
            em.close();
            return null;
        }
    }
}
