/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watea.creditservice.watea.agip.controller;

import com.watea.creditservice.watea.agip.controller.exceptions.NonexistentEntityException;
import com.watea.creditservice.watea.agip.controller.exceptions.PreexistingEntityException;
import com.watea.creditservice.watea.agip.entities.XxwCot;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class XxwCotJpaController implements Serializable {

    public XxwCotJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(XxwCot xxwCot) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(xxwCot);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findXxwCot(xxwCot.getLineId()) != null) {
                throw new PreexistingEntityException("XxwCot " + xxwCot + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(XxwCot xxwCot) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            xxwCot = em.merge(xxwCot);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                BigDecimal id = xxwCot.getLineId();
                if (findXxwCot(id) == null) {
                    throw new NonexistentEntityException("The xxwCot with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(BigDecimal id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            XxwCot xxwCot;
            try {
                xxwCot = em.getReference(XxwCot.class, id);
                xxwCot.getLineId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The xxwCot with id " + id + " no longer exists.", enfe);
            }
            em.remove(xxwCot);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<XxwCot> findXxwCotEntities() {
        return findXxwCotEntities(true, -1, -1);
    }

    public List<XxwCot> findXxwCotEntities(int maxResults, int firstResult) {
        return findXxwCotEntities(false, maxResults, firstResult);
    }

    private List<XxwCot> findXxwCotEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(XxwCot.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public XxwCot findXxwCot(BigDecimal id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(XxwCot.class, id);
        } finally {
            em.close();
        }
    }

    public int getXxwCotCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<XxwCot> rt = cq.from(XxwCot.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
