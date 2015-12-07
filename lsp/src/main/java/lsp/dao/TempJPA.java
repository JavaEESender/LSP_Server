/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsp.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import lsp.entity.Temp;

/**
 *
 * @author Alexandr
 */
public class TempJPA extends JPA {

    public List<Temp> getAllTemp() {
        EntityManager manager = getManager();
        try {
            return manager.createNamedQuery("Temp.findAll").getResultList();
        } finally {
            manager.close();
        }
    }

    public List<Temp> getTemp(int forTable) {
        EntityManager manager = getManager();
        try {
            return manager.createNamedQuery("Temp.findByForTable").setParameter("forTable", forTable).getResultList();
        } finally {
            manager.close();
        }
    }

    public boolean setTemp(String ip, Date date, int forTable) {
        EntityManager manager = getManager();
        boolean stat;
        try {
            if (manager.createNamedQuery("Temp.findByIp").setParameter("ip", ip).getResultList().isEmpty()) {
                Temp temp = new Temp(Integer.SIZE, ip, date, forTable);
                manager.getTransaction().begin();
                manager.persist(temp);
                manager.getTransaction().commit();
                stat = true;
            } else {
                Temp change = (Temp) manager.createNamedQuery("Temp.findByIp").setParameter("ip", ip).getSingleResult();
                change.setLiveTo(date);
                manager.getTransaction().begin();
                manager.persist(change);
                manager.getTransaction().commit();
                stat = false;
            }
        } finally {
            manager.close();
        }
        return stat;
    }

    public void delTemp(String ip) {
        refresh();
        EntityManager manager = getManager();
        try {
            Temp temp = (Temp) manager.createNamedQuery("Temp.findByIp").setParameter("ip", ip).getSingleResult();
            manager.getTransaction().begin();
            manager.remove(temp);
            manager.getTransaction().commit();
            new LogJPA().addLog("localhost", "delete inet for " + temp.getIp());
        } finally {
            manager.close();
        }
    }

    public String getExpireTime(String ip) {
        EntityManager manager = getManager();
        try {
            Temp temp = (Temp) manager.createNamedQuery("Temp.findByIp").setParameter("ip", ip).getSingleResult();
            Date expire = temp.getLiveTo();
            Date current = new Date();
            Date diff = new Date(expire.getTime() - current.getTime());
            long hour = diff.getTime() / 1000 / 60 / 60;
            long min = diff.getTime() / 1000 / 60 % 60;
            if (min < 10) {
                return hour + ":" + "0" + min;
            } else {
                return hour + ":" + min;
            }
        } catch (Exception ex) {
            return null;
        } finally {
            manager.close();
        }
    }
}
