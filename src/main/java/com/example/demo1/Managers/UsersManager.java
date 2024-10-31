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
        EntityManager em = emf.createEntityManager();
        try {
            Users user = em.find(Users.class, id);
            em.close();
            return user;
        } catch (Exception ex) {
            em.close();
            return null;
        }
    }

    public Users getUserByLogin(String login) {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Users> cq = cb.createQuery(Users.class);
            Root<Users> us = cq.from(Users.class);
            cq.where(cb.equal(us.get("login"), login));
            Users user = em.createQuery(cq).setMaxResults(1).getSingleResult();
            em.close();
            return user;
        } catch (Exception ex) {
            em.close();
            return null;
        }
    }

    public boolean checkLoginExists(String login) {
        return getUserByLogin(login) != null;
    }

    public Long loginUser(String login, String password) {
        Users user = getUserByLogin(login);
        try {
            if (!Arrays.equals(user.getPassword(), md.digest(password.getBytes(StandardCharsets.UTF_8)))) {
                return null;
            }
            return user.getId();
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean checkAdminExists() {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Users> cq = cb.createQuery(Users.class);
            Root<Users> us = cq.from(Users.class);
            cq.where(cb.equal(us.get("status"), 2));
            em.createQuery(cq).setMaxResults(1).getSingleResult();
            em.close();
            return true;
        } catch (Exception ex) {
            em.close();
            return false;
        }
    }

    public boolean checkPasswordExists(String password) {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Users> cq = cb.createQuery(Users.class);
            Root<Users> us = cq.from(Users.class);
            cq.where(cb.equal(us.get("password"), md.digest(password.getBytes(StandardCharsets.UTF_8))));
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
                Users detachedUsers = getUserById(user_id);
                EntityManager em = emf.createEntityManager();
                try {
                    Users managedUser = em.merge(detachedUsers);
                    em.getTransaction().begin();
                    em.remove(managedUser);
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
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Users> cq = cb.createQuery(Users.class);
            Root<Users> us = cq.from(Users.class);
            cq.orderBy(cb.asc(us.get("id")));
            List<Users> resultList = em.createQuery(cq).getResultList();
            em.close();
            return resultList;
        } catch (Exception ex) {
            em.close();
            return null;
        }
    }
}
