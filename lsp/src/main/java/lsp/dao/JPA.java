/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lsp.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Alexandr
 */
public class JPA {

    private EntityManagerFactory factory;

    public JPA() {
        this.factory = Persistence.createEntityManagerFactory("lspPU");
    }

    public EntityManagerFactory getFactory() {
        return factory;
    }

    public void setFactory(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public EntityManager getManager() {
        return factory.createEntityManager();
    }

    public void refresh() {
        factory.getCache().evictAll();
    }
}
