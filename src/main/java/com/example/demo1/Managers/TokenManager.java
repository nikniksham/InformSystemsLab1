package com.example.demo1.Managers;

import com.example.demo1.DBObjects.Tokens;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.servlet.http.Cookie;

import java.sql.Timestamp;
import java.util.Random;

@ApplicationScoped
public class TokenManager {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
    private Random rng = new Random(72382);
    private String characters = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789";

    public TokenManager() {}

    public String createNewAccessToken(long user_id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        Tokens new_token = new Tokens();
        new_token.setUser_id(user_id);
        String code = generateCode(200);
        new_token.setCode(code);
        new_token.setCreationDate(new Timestamp(new java.util.Date().getTime()));

        em.persist(new_token);
        em.getTransaction().commit();
        em.close();

        return code;
    }

    public boolean checkCodeExists(String code) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT u FROM Tokens u WHERE u.code = :code").setParameter("code", code);
        try {
            query.getSingleResult();
            em.close();
            return true;
        } catch (Exception ex) {
            em.close();
            return false;
        }
    }

    public boolean checkCodeExistsAndExpiration(String code) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT u FROM Tokens u WHERE u.code = :code").setParameter("code", code);
        try {
            Tokens token = (Tokens) query.getSingleResult();
            em.close();
            return token.checkExpirationDate();
        } catch (Exception ex) {
            em.close();
            return false;
        }
    }

    public String generateCode(int length)
    {
        char[] code = new char[length];
        boolean run = true;
        while (run) {
            for (int i = 0; i < length; i++) {
                code[i] = characters.charAt(rng.nextInt(characters.length()));
            }
            run = checkCodeExists(new String(code));
        }
        return new String(code);
    }

    public boolean deleteTokenByCode(String code) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Tokens WHERE code = :code").setParameter("code", code).executeUpdate();
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception ex) {
            em.close();
            return false;
        }
    }

    public String getAuthCode(Cookie[] cookies) {
        for (Cookie c : cookies) {
            if (c.getName().equals("avtoritet")) {
                return c.getValue();
            }
        }
        return null;
    }

    public boolean userConnectionValid(Cookie[] cookies) {
        String code = getAuthCode(cookies);
        if (code != null) {
            return checkCodeExistsAndExpiration(code);
        }
        return false;
    }

    public Long getUserId(Cookie[] cookies) {
        for (Cookie c : cookies) {
            if (c.getName().equals("avtoritet")) {
                EntityManager em = emf.createEntityManager();
                Query query = em.createQuery("SELECT u FROM Tokens u WHERE u.code = :code").setParameter("code", c.getValue());
                try {
                    Tokens token = (Tokens) query.getSingleResult();
                    em.close();
                    if (token.checkExpirationDate()) {
                        return token.getUser_id();
                    }
                } catch (Exception ex) {
                    em.close();
                    return null;
                }
            }
        }
        return null;
    }
}
