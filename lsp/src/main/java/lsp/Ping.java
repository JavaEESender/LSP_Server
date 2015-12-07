/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lsp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import lsp.dao.FilialsJPA;
import lsp.entity.Filials;

/**
 *
 * @author Alexandr
 */
public class Ping implements Runnable {

    private final Filials filial;

    public Ping(Filials f) {
        this.filial = f;
    }

    @Override
    public void run() {
        Timer t = new Timer(20000, new ActionListener() {
            Console cmd;
            FilialsJPA dao = new FilialsJPA();

            @Override
            public void actionPerformed(ActionEvent ae) {
                cmd = new Console();
                boolean pingLAN = cmd.Ping(filial.getCheckIP());
                if (pingLAN) {
                    if (dao.getFilialInfo(filial.getAddress()).getCheckStat() != 0) {
                        dao.setCheckStat(filial.getId(), 0);
                    }
                } else {
                    if (filial.getAuth().equals("OpenVPN") || filial.getAuth().equals("IPTunnel")) {
                        boolean pingWAN = cmd.Ping(filial.getWanIP());
                        if (pingWAN) {
                            if (dao.getFilialInfo(filial.getAddress()).getCheckStat() != 1) {
                                dao.setCheckStat(filial.getId(), 1);
                            }
                        } else {
                            if (dao.getFilialInfo(filial.getAddress()).getCheckStat() != 2) {
                                dao.setCheckStat(filial.getId(), 2);
                            }
                        }
                    } else {
                        if (dao.getFilialInfo(filial.getAddress()).getCheckStat() != 1) {
                            dao.setCheckStat(filial.getId(), 1);
                        }
                    }
                }
            }
        });
        t.start();
    }
}
