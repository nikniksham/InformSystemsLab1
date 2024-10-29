package com.example.demo1.Managers;

import com.example.demo1.DBObjects.Tokens;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.servlet.http.Cookie;
import java.sql.Timestamp;
import java.util.Random;

@ApplicationScoped
public class TokenManager {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("test");
    private final Random rng = new Random(72382);

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

    public Tokens getTokenByCode(String code) {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Tokens> cq = cb.createQuery(Tokens.class);
            Root<Tokens> tok = cq.from(Tokens.class);
            cq.where(cb.equal(tok.get("code"), code));
            Tokens token = em.createQuery(cq).setMaxResults(1).getSingleResult();
            em.close();
            return token;
        } catch (Exception ex) {
            em.close();
            return null;
        }
    }

    public boolean checkCodeExists(String code) {
        return getTokenByCode(code) != null;
    }

    public boolean checkCodeExistsAndExpiration(String code) {
        Tokens token = getTokenByCode(code);
        if (token != null) {
            return token.checkExpirationDate();
        }
        return false;
    }

    public String generateCode(int length)
    {
        String characters = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789";
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
        Tokens detachedToken = getTokenByCode(code);
        EntityManager em = emf.createEntityManager();
        try {
            Tokens managedToken = em.merge(detachedToken);
            em.getTransaction().begin();
            em.remove(managedToken);
            em.getTransaction().commit();
            em.close();
            return true;
        } catch (Exception ex) {
            em.close();
            return false;
        }
    }

    public String getAuthCode(Cookie[] cookies) {
        try {
            for (Cookie c : cookies) {
                if (c.getName().equals("avtoritet")) {
                    return c.getValue();
                }
            }
        } catch (Exception e) {}
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
        try {
            for (Cookie c : cookies) {
                if (c.getName().equals("avtoritet")) {
                    try {
                        Tokens token = getTokenByCode(c.getValue());
                        if (token != null && token.checkExpirationDate()) {
                            return token.getUser_id();
                        }
                    } catch (Exception ex) {
                        return null;
                    }
                }
            }
        } catch (Exception e) {}
        return null;
    }
}
