/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsp.dao;

import javax.persistence.EntityManager;
import lsp.entity.Log;

/**
 *
 * @author Alexandr
 */
public class LogJPA extends JPA {

    public void addLog(String ip, String action) {
        EntityManager manager = getManager();
        try {
            Log log = new Log(Integer.SIZE, ip, action, null);
            manager.getTransaction().begin();
            manager.persist(log);
            manager.getTransaction().commit();
        } finally {
            manager.close();
        }
    }

}
