/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsp.dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import lsp.entity.Filials;

/**
 *
 * @author Alexandr
 */
public class FilialsJPA extends JPA {

    public List<Filials> getAllFilials() {
        EntityManager manager = getManager();
        try {
            return manager.createNamedQuery("Filials.findAll").getResultList();
        } finally {
            manager.close();
        }
    }

    public void setCheckStat(int id, int stat) {
        EntityManager manager = getManager();
        try {
            Filials filial = (Filials) manager.createNamedQuery("Filials.findById").setParameter("id", id).getSingleResult();
            filial.setCheckStat(stat);
            filial.setLastPing(new Date());
            manager.getTransaction().begin();
            manager.persist(filial);
            manager.getTransaction().commit();
        } finally {
            manager.close();
        }
    }

    public String getLastPing(int id) {
        EntityManager manager = getManager();
        try {
            Filials filial = (Filials) manager.createNamedQuery("Filials.findById").setParameter("id", id).getSingleResult();
            Date lastPing = filial.getLastPing();
            Date current = new Date();
            Date diff = new Date(current.getTime() - lastPing.getTime());
            long day = diff.getTime() / 1000 / 60 / 60 / 24;
            long hour = diff.getTime() / 1000 / 60 / 60;
            long min = diff.getTime() / 1000 / 60 % 60;
            if (day < 1) {
                if (hour < 1) {
                    if (min < 10) {
                        return "0" + min + " min";
                    } else {
                        return min + " min";
                    }
                } else {
                    if (min < 10) {
                        return hour + " h" + " : " + "0" + min + " min";
                    } else {
                        return hour + " h" + " : " + min + " min";
                    }
                }
            } else {
                hour = diff.getTime() / 1000 / 60 / 60 % 24;
                if (min < 10) {
                    return day + " d" + " : " + hour + " h" + " : " + "0" + min + " min";
                } else {
                    return day + " d" + " : " + hour + " h" + " : " + min + " min";
                }
            }
        } catch (Exception ex) {
            return null;
        } finally {
            manager.close();
        }
    }

    public List<Filials> getAddressNoPing() {
        EntityManager manager = getManager();
        try {
            List<Filials> filials = manager.createNamedQuery("Filials.findByCheckStat").setParameter("checkStat", 1).getResultList();
            List<Filials> filials2 = manager.createNamedQuery("Filials.findByCheckStat").setParameter("checkStat", 2).getResultList();
            filials.addAll(filials2);
            return filials;
        } finally {
            manager.close();
        }
    }

    public List<Filials> getAddressNoLAN() {
        EntityManager manager = getManager();
        try {
            return manager.createNamedQuery("Filials.findByCheckStat").setParameter("checkStat", 1).getResultList();
        } finally {
            manager.close();
        }
    }

    public List<Filials> getAddressNoWAN() {
        EntityManager manager = getManager();
        try {
            return manager.createNamedQuery("Filials.findByCheckStat").setParameter("checkStat", 2).getResultList();
        } finally {
            manager.close();
        }
    }

    public Filials getFilialInfo(String addr) {
        EntityManager manager = getManager();
        try {
            return (Filials) manager.createNamedQuery("Filials.findByAddress").setParameter("address", addr).getSingleResult();
        } finally {
            manager.close();
        }
    }
}
