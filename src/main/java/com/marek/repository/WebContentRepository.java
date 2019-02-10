package com.marek.repository;

import com.marek.model.WebContent;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class WebContentRepository {
    private EntityManagerFactory entityManagerFactory;

    public WebContentRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("com.marek.model");
    }

    public WebContent save(WebContent webContent) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            long count = (long) entityManager.createQuery("SELECT COUNT(w.url) FROM WebContent w WHERE w.url like :url")
                    .setParameter("url", webContent.getUrl())
                    .getSingleResult();
            if (count != 0) {
                entityManager.createQuery("UPDATE WebContent w SET w.pageContent = :pageContent, w.contentType = :contentType WHERE w.url = :url")
                        .setParameter("pageContent", webContent.getPageContent())
                        .setParameter("url", webContent.getUrl())
                        .setParameter("contentType", webContent.getContentType())
                        .executeUpdate();
            } else entityManager.persist(webContent);
            entityManager.getTransaction().commit();
            return webContent;

        } finally {
            entityManager.close();
        }
    }

    public WebContent getByURL(String url) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            WebContent webContent = (WebContent) entityManager.createQuery("SELECT w FROM WebContent w WHERE w.url like :url")
                    .setParameter("url", url)
                    .getSingleResult();
            return webContent;
        } finally {
            entityManager.close();
        }
    }

    public List<WebContent> getAllWebContents() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<WebContent> webContents;
        try {
            entityManager.getTransaction().begin();
            webContents = (List<WebContent>) entityManager.createQuery("SELECT w FROM WebContent w")
                    .getResultList();
            return webContents;
        } finally {
            entityManager.close();
        }
    }

    public List<WebContent> searchWebContentsWithGivenString(String searchParameter) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<WebContent> webContents;
        try {
            entityManager.getTransaction().begin();
            webContents = (List<WebContent>) entityManager.createNativeQuery("SELECT * FROM WebContent WHERE pageContent LIKE '%" + searchParameter + "%'")
                    .getResultList();
            return webContents;
        } finally {
            entityManager.close();
        }
    }
}

