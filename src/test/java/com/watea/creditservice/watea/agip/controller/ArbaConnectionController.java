/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.watea.creditservice.watea.agip.controller;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class ArbaConnectionController implements Serializable {

    public ArbaConnectionController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public String getArbaService() {
        EntityManager em = getEntityManager();
        try {
            return (String) em.createNativeQuery("SELECT XXW_COT_PROCESS_PKG.get_arba_service() FROM DUAL").getSingleResult();
        } finally {
            em.close();
        }
    }

}
